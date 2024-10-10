package com.project.flightManagement.Controller;

import com.project.flightManagement.DTO.TaiKhoanDTO.TaiKhoanDTO;
import com.project.flightManagement.DTO.TaiKhoanDTO.TaiKhoanUpdateNguoiDungDTO;
import com.project.flightManagement.Model.TaiKhoan;
import com.project.flightManagement.Payload.ResponseData;
import com.project.flightManagement.Service.TaiKhoanService;
import jakarta.persistence.EntityNotFoundException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.Authentication;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.*;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

@Controller
@RequestMapping("/taikhoan")
public class TaiKhoanController {
    @Autowired
    private PasswordEncoder passwordEncoder;
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
    @GetMapping("/me")
    public ResponseEntity<?> getMyInfo(Authentication authentication) {
        ResponseData responseData = new ResponseData();
        String userName = authentication.getName();

        try {
            Optional<TaiKhoan> taiKhoanOptional = taiKhoanService.getTaiKhoanByTenDangNhap(userName);
            // Lấy thông tin tài khoản theo id
            TaiKhoanDTO taiKhoan = taiKhoanService.getTaiKhoanByIdTaiKhoan(taiKhoanOptional.get().getIdTaiKhoan());
            if (taiKhoan == null) {
                responseData.setStatusCode(404);
                responseData.setData("");
                responseData.setMessage("Tài khoản với ID " + userName + " không tồn tại.");
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
            responseData.setMessage("Đã xảy ra lỗi khi lấy thông tin tài khoản: " + e);
            return new ResponseEntity<>(responseData, HttpStatus.INTERNAL_SERVER_ERROR);
        }
    }
    @PutMapping("/update_password")
    public ResponseEntity<?> updatePassword(
            Authentication authentication,
            @RequestBody TaiKhoanUpdateNguoiDungDTO taiKhoanUpdateNguoiDungDTO) {
        ResponseData responseData = new ResponseData();
        List<String> errorList = new ArrayList<>();
        boolean isError = false;

        // Lấy tên đăng nhập từ Authentication
        String userName = authentication.getName();

        // Kiểm tra mật khẩu cũ có chính xác không
        Optional<TaiKhoan> optionalTaiKhoan = taiKhoanService.getTaiKhoanByTenDangNhap(userName);
        if (optionalTaiKhoan.isEmpty()) {
            responseData.setMessage("Người dùng không tồn tại.");
            responseData.setData("");
            return new ResponseEntity<>(responseData, HttpStatus.NOT_FOUND);
        }

        TaiKhoan taiKhoan = optionalTaiKhoan.get();
        if (!passwordEncoder.matches(taiKhoanUpdateNguoiDungDTO.getMatKhauCu(), taiKhoan.getMatKhau())) {
            errorList.add("Mật khẩu cũ không chính xác");
            isError = true;
        }

        // Kiểm tra mật khẩu mới và re_password có khớp không
        if (!taiKhoanUpdateNguoiDungDTO.getMatKhau().equals(taiKhoanUpdateNguoiDungDTO.getReMatKhau())) {
            errorList.add("Mật khẩu mới không khớp");
            isError = true;
        }
        if(isError) {
            responseData.setStatusCode(409);
            responseData.setMessage("Đổi mật không thành công");
            responseData.setData(errorList);
            return new ResponseEntity<>(responseData, HttpStatus.CONFLICT);
        }

        // Nếu mọi thứ đều ổn, gọi Service để cập nhật mật khẩu
        taiKhoanService.updateTaiKhoan(userName ,taiKhoanUpdateNguoiDungDTO);
        responseData.setStatusCode(200);
        responseData.setMessage("Đổi mật khẩu thành công!");
        responseData.setData("");
        return new ResponseEntity<>(responseData, HttpStatus.OK);
    }
}
