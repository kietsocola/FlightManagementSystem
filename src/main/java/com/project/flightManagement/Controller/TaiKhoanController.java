package com.project.flightManagement.Controller;

import com.project.flightManagement.DTO.TaiKhoanDTO.TaiKhoanDTO;
import com.project.flightManagement.Payload.ResponseData;
import com.project.flightManagement.Service.TaiKhoanService;
import jakarta.persistence.EntityNotFoundException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;

@Controller
@RequestMapping("/taikhoan")
public class TaiKhoanController {
    @Autowired
    private TaiKhoanService taiKhoanService;
    @GetMapping // Đây là ánh xạ cho yêu cầu GET đến /review
    public ResponseEntity<?> getAllTaiKhoan(@RequestParam(defaultValue = "0") int page,
                                           @RequestParam(defaultValue = "10") int size) {
        ResponseData responseData = new ResponseData();

        // Lấy danh sách đánh giá
        Page<TaiKhoanDTO> reviewResponseDTOList = taiKhoanService.getAllTaiKhoan(page, size);

        // Kiểm tra nếu danh sách đánh giá trống
        if (reviewResponseDTOList.isEmpty()) {
            responseData.setMessage("No taikhoan found.");
            return ResponseEntity.status(HttpStatus.NO_CONTENT).body(responseData);
        }

        // Nếu có dữ liệu
        responseData.setData(reviewResponseDTOList);
        responseData.setMessage("Successfully retrieved all taikhoan.");
        return ResponseEntity.ok(responseData);
    }

    @GetMapping("/{idTaiKhoan}")
    public ResponseEntity<?> getTaiKhoanByIdTaiKhoan(@PathVariable int idTaiKhoan) {
        ResponseData responseData = new ResponseData();

        try {
            // Lấy thông tin tài khoản theo id
            TaiKhoanDTO taiKhoan = taiKhoanService.getTaiKhoanByIdTaiKhoan(idTaiKhoan);

            // Kiểm tra nếu tài khoản không tồn tại
            if (taiKhoan == null) {
                responseData.setStatusCode(404);
                responseData.setData("");
                responseData.setMessage("Tài khoản với ID " + idTaiKhoan + " không tồn tại.");
                return new ResponseEntity<>(responseData, HttpStatus.NOT_FOUND);
            }

            // Nếu tồn tại tài khoản
            responseData.setStatusCode(200);
            responseData.setMessage("Thông tin tài khoản");
            responseData.setData(taiKhoan);
            return new ResponseEntity<>(responseData, HttpStatus.OK);

        } catch (EntityNotFoundException e) {
            responseData.setStatusCode(404);
            responseData.setData("");
            responseData.setMessage("Tài khoản không tồn tại.");
            return new ResponseEntity<>(responseData, HttpStatus.NOT_FOUND);

        } catch (Exception e) {
            responseData.setStatusCode(500);
            responseData.setData("");
            responseData.setMessage("Đã xảy ra lỗi khi lấy thông tin tài khoản.");
            return new ResponseEntity<>(responseData, HttpStatus.INTERNAL_SERVER_ERROR);
        }
    }

}
