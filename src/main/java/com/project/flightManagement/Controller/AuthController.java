package com.project.flightManagement.Controller;

import com.project.flightManagement.DTO.AuthDTO.LoginDTO;
import com.project.flightManagement.DTO.AuthDTO.SignupDTO;
import com.project.flightManagement.Payload.ResponseData;
import com.project.flightManagement.Sercurity.JwtTokenProvider;
import com.project.flightManagement.Service.KhachHangService;
import com.project.flightManagement.Service.TaiKhoanService;
import jakarta.validation.Valid;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;

@Controller
@RequestMapping("/auth")
public class AuthController {
    @Autowired
    private TaiKhoanService taiKhoanService;
    @Autowired
    private KhachHangService khachHangService;
    @Autowired
    private JwtTokenProvider jwtTokenProvider;
    @PostMapping("/login")
    public ResponseEntity<ResponseData> login(@RequestBody LoginDTO loginDTO) {
        ResponseData responseData = new ResponseData();
        try {
            // Kiểm tra đăng nhập
            if (taiKhoanService.checkLogin(loginDTO)) {
                responseData.setMessage("Login success");
                responseData.setData(jwtTokenProvider.generateToken(loginDTO.getUserName()));
                return new ResponseEntity<>(responseData, HttpStatus.OK);
            } else {
                responseData.setMessage("Login failed: Invalid credentials");
                return new ResponseEntity<>(responseData, HttpStatus.UNAUTHORIZED);
            }
        } catch (Exception e) {
            responseData.setMessage("Login failed: " + e.getMessage());
            return new ResponseEntity<>(responseData, HttpStatus.INTERNAL_SERVER_ERROR);
        }
    }

    @PostMapping("/signup")
    public ResponseEntity<ResponseData> signup(@Valid @RequestBody SignupDTO signupDTO) {
        ResponseData responseData = new ResponseData();
        try {
            // Kiểm tra nếu tên đăng nhập đã tồn tại
            if (taiKhoanService.existsTaiKhoanByTenDangNhap(signupDTO.getUserName())) {
                responseData.setMessage("Signup failed: Username already exists");
                return new ResponseEntity<>(responseData, HttpStatus.CONFLICT);
            }

            // Kiểm tra nếu email đã tồn tại
            if (khachHangService.existsKhachHangByEmail(signupDTO.getEmail())) {
                responseData.setMessage("Signup failed: Email already exists");
                return new ResponseEntity<>(responseData, HttpStatus.CONFLICT);
            }
            // Kiểm tra nếu cccd đã tồn tại
            if (khachHangService.existsKhachHangByCccd(signupDTO.getCccd())) {
                responseData.setMessage("Signup failed: Cccd already exists");
                return new ResponseEntity<>(responseData, HttpStatus.CONFLICT);
            }
            if(!signupDTO.getPassword().equals(signupDTO.getRePassword())) {
                responseData.setMessage("Signup failed: Not match between password and re_password");
                return new ResponseEntity<>(responseData, HttpStatus.CONFLICT);
            }

            // Tạo tài khoản mới
            boolean created = taiKhoanService.createTaiKhoan(signupDTO);
            if (created) {
                responseData.setMessage("Signup success");
                return new ResponseEntity<>(responseData, HttpStatus.OK);
            } else {
                responseData.setMessage("Signup failed: Unable to create account");
                return new ResponseEntity<>(responseData, HttpStatus.INTERNAL_SERVER_ERROR);
            }
        } catch (Exception e) {
            responseData.setMessage("Signup failed: " + e.getMessage());
            return new ResponseEntity<>(responseData, HttpStatus.INTERNAL_SERVER_ERROR);
        }
    }

}
