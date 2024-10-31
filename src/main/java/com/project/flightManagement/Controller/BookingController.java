package com.project.flightManagement.Controller;

import com.google.zxing.WriterException;
import com.itextpdf.text.DocumentException;
import com.project.flightManagement.DTO.ChoNgoiDTO.ChoNgoiDTO;
import com.project.flightManagement.DTO.HanhKhachDTO.HanhKhachDTO;
import com.project.flightManagement.DTO.HoldSeatDTO.HoldSeatRequest;
import com.project.flightManagement.Enum.VeEnum;
import com.project.flightManagement.Model.Ve;
import com.project.flightManagement.Payload.ResponseData;
import com.project.flightManagement.Repository.VeRepository;
import com.project.flightManagement.Service.*;
import com.project.flightManagement.Service.Impl.PdfService;
import jakarta.servlet.http.HttpServletRequest;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.core.io.InputStreamResource;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.thymeleaf.context.Context;
import org.thymeleaf.spring6.SpringTemplateEngine;
import org.xhtmlrenderer.pdf.ITextRenderer;
import org.xhtmlrenderer.simple.PDFRenderer;

import java.io.ByteArrayInputStream;
import java.io.ByteArrayOutputStream;
import java.io.IOException;
import java.net.URL;
import java.util.*;

@RestController
public class BookingController {
    @Autowired
    private ChoNgoiService choNgoiService;
    @Autowired
    private SocketIOService socketIOService;
    @Autowired
    private HoldSeatService holdSeatService;
    @Autowired
    private VeRepository veRepo;
    @Autowired
    private PaymentService paymentService;
    @Autowired
    private PdfService pdfService;

    // Endpoint to get seats for a flight by flight ID
    @GetMapping("/getChoNgoiByChuyenBayAndHangVe")
    public ResponseEntity<ResponseData> getSeatsByFlight(@RequestParam int idChuyenBay, @RequestParam int idHangVe) {
        ResponseData response = new ResponseData();
        List<ChoNgoiDTO> listCN = choNgoiService.getAllChoNgoiByIdChuyenBayandHangVe(idChuyenBay, idHangVe);
        if(!listCN.isEmpty()){
            response.setMessage("Get cho ngoi by id chuyen bay "+idChuyenBay+" and id hang ve "+idHangVe+" successfully");
            response.setData(listCN);
            response.setStatusCode(200);
            return new ResponseEntity<>(response, HttpStatus.OK);
        } else {
            response.setMessage("Get cho ngoi by id chuyen bay "+idChuyenBay+" and id hang ve "+idHangVe+" unsuccessfully");
            response.setData(null);
            response.setStatusCode(404);
            return new ResponseEntity<>(response, HttpStatus.NOT_FOUND);
        }
    }
    @PostMapping("/holdSeat")
    public ResponseEntity<ResponseData> holdSeat(@RequestBody HoldSeatRequest holdSeatRequest) {
        ResponseData response = new ResponseData();

        // 1. Kiểm tra đầu vào
        if (holdSeatRequest.getIdVe() == null || holdSeatRequest.getUserId() == null ||
                holdSeatRequest.getSeatId() == null || holdSeatRequest.getFlightId() == null) {
            response.setMessage("Invalid request: missing required fields");
            response.setStatusCode(400); // Bad Request
            return new ResponseEntity<>(response, HttpStatus.BAD_REQUEST);
        }

        // 2. Kiểm tra xem vé có tồn tại không
        Optional<Ve> optionalVe = veRepo.findById(holdSeatRequest.getIdVe());
        if (!optionalVe.isPresent()) {
            response.setMessage("Vé không tồn tại");
            response.setStatusCode(404); // Not Found
            return new ResponseEntity<>(response, HttpStatus.NOT_FOUND);
        }

        Ve ve = optionalVe.get();

        // 3. Kiểm tra trạng thái của ghế (phải là AVAILABLE)
        if (ve.getTrangThai() != VeEnum.EMPTY) {
            response.setMessage("Ghế đã được giữ hoặc đã đặt");
            response.setStatusCode(400); // Bad Request
            return new ResponseEntity<>(response, HttpStatus.BAD_REQUEST);
        }

        // 4. Giữ ghế và cập nhật trạng thái
        holdSeatService.holdSeat(holdSeatRequest.getIdVe());

        // Tạo một thông điệp để gửi tới các client qua Socket.IO
        String message = "User " + holdSeatRequest.getUserId() + " has held seat " + holdSeatRequest.getSeatId() + " for flight " + holdSeatRequest.getFlightId();

        // Gửi thông điệp qua Socket.IO
        socketIOService.emit("seat-held", message);

        // Phản hồi cho client
        response.setMessage("Seat held successfully.");
        response.setData(holdSeatRequest);
        response.setStatusCode(200);

        return new ResponseEntity<>(response, HttpStatus.OK);
    }

    @PostMapping("/confirmBooking")
    public ResponseEntity<ResponseData> confirmBooking(@RequestBody List<HanhKhachDTO> hanhKhachList, HttpServletRequest request) {
        ResponseData response = new ResponseData();
        int totalAmount = 0;  // Tổng số tiền cho tất cả các vé
        StringBuilder orderInfo = new StringBuilder("Thanh toan cho cac ve: ");
        List<String> passengerNames = new ArrayList<>();

        try {
            // Loop through each passenger in the list
            for (HanhKhachDTO hanhKhach : hanhKhachList) {
                // Kiểm tra nếu idVe bị null hoặc không hợp lệ
                if (hanhKhach.getIdVe() == null) {
                    response.setMessage("Dữ liệu không hợp lệ: idVe không được để trống cho hành khách: " + hanhKhach.getHoTen());
                    response.setStatusCode(400); // Bad Request
                    return new ResponseEntity<>(response, HttpStatus.BAD_REQUEST);
                }

                // Kiểm tra xem vé có tồn tại không
                Optional<Ve> optionalVe = veRepo.findById(hanhKhach.getIdVe());
                if (!optionalVe.isPresent()) {
                    response.setMessage("Vé không tồn tại: " + hanhKhach.getIdVe() + " cho hành khách: " + hanhKhach.getHoTen());
                    response.setStatusCode(404); // Not Found
                    return new ResponseEntity<>(response, HttpStatus.NOT_FOUND);
                }

                Ve ve = optionalVe.get();

                // Kiểm tra xem vé đã được đặt chưa
                if (ve.getTrangThai().equals(VeEnum.BOOKED)) {
                    response.setMessage("Vé này đã được đặt cho hành khách: " + hanhKhach.getHoTen());
                    response.setStatusCode(400);
                    return new ResponseEntity<>(response, HttpStatus.BAD_REQUEST);
                }

                // Cộng dồn giá vé
                totalAmount += ve.getGiaVe();
                passengerNames.add(hanhKhach.getHoTen());

                // Thêm thông tin vé vào orderInfo
                orderInfo.append(ve.getIdVe()).append(", ");

                // Lưu thông tin hành khách trong session (tùy chọn)
                request.getSession().setAttribute("hanhKhach_" + hanhKhach.getIdVe(), hanhKhach);
            }

            // Xóa dấu phẩy cuối cùng trong orderInfo
            if (orderInfo.length() > 0) {
                orderInfo.setLength(orderInfo.length() - 2);
            }

            // Tạo một URL thanh toán với tổng số tiền
            URL paymentUrl = paymentService.createPaymentUrl(totalAmount, orderInfo.toString(), request);

            // Trả về URL thanh toán duy nhất cho tất cả hành khách
            response.setMessage("Hãy thanh toán qua VNPay để xác nhận đặt vé cho tất cả hành khách.");
            Map<String, Object> responseData = new HashMap<>();
            responseData.put("paymentUrl", paymentUrl);
            responseData.put("totalAmount", totalAmount);
            responseData.put("passengers", passengerNames);
            response.setData(responseData);
            response.setStatusCode(200); // OK
            return new ResponseEntity<>(response, HttpStatus.OK);

        } catch (Exception e) {
            response.setMessage("Đặt vé thất bại: " + e.getMessage());
            response.setStatusCode(500); // Internal Server Error
            return new ResponseEntity<>(response, HttpStatus.INTERNAL_SERVER_ERROR);
        }
    }
    @GetMapping(value = "/pdf/{idVe}", produces = MediaType.APPLICATION_PDF_VALUE)
    public ResponseEntity<byte[]> generateTicketPDF(@PathVariable int idVe) throws IOException, WriterException {
        Optional<Ve> optionalVe = veRepo.findById(idVe);

        if (!optionalVe.isPresent()) {
            return ResponseEntity.notFound().build(); // Trả về 404 nếu không tìm thấy vé
        }

        Ve ve = optionalVe.get();
        Context context = pdfService.createContext(ve);
        String htmlContent = pdfService.templateEngine.process("email/pdf", context);

        byte[] pdfBytes = pdfService.generatePdfFromHtml(htmlContent);

        HttpHeaders headers = new HttpHeaders();
        headers.add("Content-Disposition", "inline; filename=boarding_pass.pdf");

        return ResponseEntity
                .ok()
                .headers(headers)
                .contentType(MediaType.APPLICATION_PDF)
                .body(pdfBytes);
    }



}
