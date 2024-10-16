package com.project.flightManagement.Controller;

import com.project.flightManagement.DTO.ChoNgoiDTO.ChoNgoiDTO;
import com.project.flightManagement.DTO.HanhKhachDTO.HanhKhachDTO;
import com.project.flightManagement.DTO.HoldSeatDTO.HoldSeatRequest;
import com.project.flightManagement.Enum.VeEnum;
import com.project.flightManagement.Model.Ve;
import com.project.flightManagement.Payload.ResponseData;
import com.project.flightManagement.Repository.VeRepository;
import com.project.flightManagement.Service.ChoNgoiService;
import com.project.flightManagement.Service.HoldSeatService;
import com.project.flightManagement.Service.PaymentService;
import com.project.flightManagement.Service.SocketIOService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Optional;

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
    public ResponseEntity<ResponseData> confirmBooking(@RequestBody HanhKhachDTO hanhKhach) {
        ResponseData response = new ResponseData();

        // Kiểm tra nếu idVe bị null hoặc không hợp lệ
        if (hanhKhach.getIdVe() == null) {
            response.setMessage("Dữ liệu không hợp lệ: idVe không được để trống");
            response.setStatusCode(400); // Bad Request
            return new ResponseEntity<>(response, HttpStatus.BAD_REQUEST);
        }
        Map<String, Object> responseData = new HashMap<>();

        try {
            // Kiểm tra xem vé có tồn tại không
            Optional<Ve> optionalVe = veRepo.findById(hanhKhach.getIdVe());
            if (!optionalVe.isPresent()) {
                response.setMessage("Vé không tồn tại: " + hanhKhach.getIdVe());
                response.setStatusCode(404); // Not Found
                return new ResponseEntity<>(response, HttpStatus.NOT_FOUND);
            }

            Ve ve = optionalVe.get();
            // Kiểm tra trạng thái vé (phải là BOOKED để tiếp tục)
            if (ve.getTrangThai() != VeEnum.HOLD) {
                response.setMessage("Vé không ở trạng thái BOOKED, không thể xác nhận đặt vé");
                response.setStatusCode(400); // Bad Request
                return new ResponseEntity<>(response, HttpStatus.BAD_REQUEST);
            }

            // Xác nhận đặt vé
            boolean isPaymentSuccessful = paymentService.checkPaymentStatus(hanhKhach);
            if (isPaymentSuccessful) {
                // Xác nhận đặt vé khi thanh toán thành công
                holdSeatService.confirmBooking(hanhKhach);
                responseData.put("payment", true);
                // Cập nhật thông báo cho client
                response.setMessage("Đã đặt vé thành công cho vé " + hanhKhach.getIdVe());
                response.setData(responseData);
                response.setStatusCode(200); // OK
                return new ResponseEntity<>(response, HttpStatus.OK);
            } else {
                responseData.put("payment", false);
                // Nếu thanh toán thất bại, thông báo lỗi
                response.setMessage("Thanh toán không thành công, không thể xác nhận vé.");
                response.setData(responseData);
                response.setStatusCode(400); // Bad Request
                return new ResponseEntity<>(response, HttpStatus.BAD_REQUEST);
            }

        } catch (Exception e) {
            responseData.put("booking", false);
            // Bắt các lỗi ngoại lệ và trả về thông báo lỗi
            response.setData(responseData);
            response.setMessage("Đặt vé thất bại cho vé " + hanhKhach.getIdVe() + ": " + e.getMessage());
            response.setStatusCode(400); // Bad Request
            return new ResponseEntity<>(response, HttpStatus.BAD_REQUEST);
        }
    }

}
