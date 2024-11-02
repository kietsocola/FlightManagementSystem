package com.project.flightManagement.Controller;

import com.project.flightManagement.DTO.HanhKhachDTO.HanhKhachDTO;
import com.project.flightManagement.Enum.VeEnum;
import com.project.flightManagement.Model.Ve;
import com.project.flightManagement.Payload.ResponseData;
import com.project.flightManagement.Repository.VeRepository;
import com.project.flightManagement.Service.KhachHangService;
import jakarta.servlet.http.HttpServletRequest;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.*;

@RestController
@RequestMapping("/payment")
public class PaymentController {
    @Autowired
    private final VeRepository veRepo;
    @Autowired
    private KhachHangService khService;
    public PaymentController(VeRepository veRepo) {
        this.veRepo = veRepo;
    }

    @GetMapping("/vnpayReturn")
    public ResponseEntity<ResponseData> vnpayReturn(HttpServletRequest request) {
        Map<String, String> params = new HashMap<>();
        request.getParameterMap().forEach((key, value) -> params.put(key, value[0]));

        ResponseData response = new ResponseData();

        String responseCode = params.get("vnp_ResponseCode");
        String vnp_OrderInfo = params.get("vnp_OrderInfo"); // Lấy giá trị từ tham số trả về


        try {
            // Kiểm tra kết quả thanh toán từ VNPay
            if ("00".equals(responseCode)) {
                // Thanh toán thành công
                // lấy thông tin hành khách từ session khi triển khai
//                HanhKhachDTO hanhKhach = (HanhKhachDTO) request.getSession().getAttribute("hanhKhach");
                System.out.println(vnp_OrderInfo);
                String[] parts = vnp_OrderInfo.split("\\+"); // Chia chuỗi thành mảng
                List<String> ticketIds = new ArrayList<>();

                for (String part : parts) {
                    System.out.println(part);
                    if (part.matches("\\d+")) { // Kiểm tra nếu phần tử là số
                        ticketIds.add(part); // Thêm phần tử vào danh sách ID vé
                    }
                }
                int totalPoints = 0;
// ticketIds giờ chứa các phần tử là số (ID vé)
                for (String ticketId : ticketIds) {
                    Optional<Ve> optionalVe = veRepo.findById(Integer.parseInt(ticketId));
                    if (optionalVe.isPresent()) {
                        Ve ve = optionalVe.get();
                        ve.setTrangThai(VeEnum.BOOKED); // Cập nhật trạng thái vé
                        veRepo.save(ve);
                        // Cập nhật điểm thưởng hoặc các thao tác khác nếu cần
                        int points = (int) (ve.getGiaVe() * 5 / 100);
                        totalPoints += points;
                    } else {
                    response.setMessage("Vé không tồn tại." + "ticketId: "+ticketId);
                    response.setStatusCode(404); // Not Found
                }}
                boolean rs = khService.updatePoint(1, totalPoints, false);
                response.setMessage("Thanh toán thành công cho các vé: " + String.join(", ", ticketIds) + ". Điểm cộng: " + rs);
                response.setData(ticketIds);
                response.setStatusCode(200);
            } else {
                response.setMessage("Thanh toán thất bại: " + responseCode);
                response.setStatusCode(400); // Bad Request
            }

        } catch (Exception e) {
            response.setMessage("Có lỗi xảy ra: " + e.getMessage());
            response.setStatusCode(500); // Internal Server Error
        }

        return new ResponseEntity<>(response, HttpStatus.OK);
    }
}
