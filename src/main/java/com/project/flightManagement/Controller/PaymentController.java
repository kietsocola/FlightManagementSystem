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

import java.util.HashMap;
import java.util.Map;
import java.util.Optional;

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
        String[] parts = vnp_OrderInfo.split("\\+"); // Chia chuỗi thành mảng
        String ticketId = parts[parts.length - 1];

        try {
            // Kiểm tra kết quả thanh toán từ VNPay
            if ("00".equals(responseCode)) {
                // Thanh toán thành công
                // lấy thông tin hành khách từ session khi triển khai
//                HanhKhachDTO hanhKhach = (HanhKhachDTO) request.getSession().getAttribute("hanhKhach");
                Optional<Ve> optionalVe = veRepo.findById(Integer.parseInt(ticketId));
                if (optionalVe.isPresent()) {
                    Ve ve = optionalVe.get();
                    ve.setTrangThai(VeEnum.BOOKED); // Cập nhật trạng thái vé
                    veRepo.save(ve);
                    boolean rs = khService.updatePoint(1, (int) (ve.getGiaVe()*5/100), false);
                    response.setMessage("Thanh toán thành công cho vé " + ve.getIdVe() + rs);
                    response.setStatusCode(200); // OK
                } else {
                    response.setMessage("Vé không tồn tại." + "ticketId: "+ticketId);
                    response.setStatusCode(404); // Not Found
                }
            } else {
                response.setMessage("Thanh toán thất bại: " + responseCode + "ticketId: "+ticketId);
                response.setStatusCode(400); // Bad Request
            }

        } catch (Exception e) {
            response.setMessage("Có lỗi xảy ra: " + e.getMessage() + "ticketId: "+ticketId);
            response.setStatusCode(500); // Internal Server Error
        }

        return new ResponseEntity<>(response, HttpStatus.OK);
    }
}
