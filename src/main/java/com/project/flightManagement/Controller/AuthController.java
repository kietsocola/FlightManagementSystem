package com.project.flightManagement.Controller;

import com.project.flightManagement.DTO.AuthDTO.*;
import com.project.flightManagement.DTO.InvalidToken.InvalidTokenDTO;
import com.project.flightManagement.Model.Email;
import com.project.flightManagement.Model.TaiKhoan;
import com.project.flightManagement.Payload.ResponseData;
import com.project.flightManagement.Security.JwtTokenProvider;
import com.project.flightManagement.Service.EmailService;
import com.project.flightManagement.Service.InvalidTokenService;
import com.project.flightManagement.Service.KhachHangService;
import com.project.flightManagement.Service.TaiKhoanService;
import jakarta.servlet.http.Cookie;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.validation.Valid;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseCookie;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;

import java.time.LocalDateTime;
import java.time.ZoneId;
import java.util.*;

@Controller
@RequestMapping("/auth")
public class AuthController {
    @Autowired
    private TaiKhoanService taiKhoanService;
    @Autowired
    private KhachHangService khachHangService;
    @Autowired
    private JwtTokenProvider jwtTokenProvider;
    @Autowired
    private InvalidTokenService invalidTokenService;
    @Autowired
    private EmailService emailService;

    @PostMapping("/login")
    public ResponseEntity<ResponseData> login(@RequestBody LoginDTO loginDTO) {
        ResponseData responseData = new ResponseData();
        try {
            // Kiểm tra đăng nhập
            if (taiKhoanService.checkLogin(loginDTO)) {

                // Tạo access token và refresh token
                String accessToken = jwtTokenProvider.generateToken(loginDTO.getUserName());
                String refreshToken = jwtTokenProvider.generateRefreshToken(loginDTO.getUserName());
                System.out.println("refreshToken ~ login" + refreshToken);
                ResponseCookie responseCookie = ResponseCookie.from("refreshToken", refreshToken)
                        .httpOnly(true)// Chỉ dùng Http, không thể truy cập từ JavaScript
                        .secure(true)  // Chỉ gửi cookie qua HTTPS
                        .path("/")// Đường dẫn của API refresh token
                        .maxAge(7 * 24 * 60 * 60)
                        .build();

                responseData.setStatusCode(200);
                responseData.setMessage("Login success");
                responseData.setData(accessToken);
                return ResponseEntity.ok()
                        .header(HttpHeaders.SET_COOKIE, responseCookie.toString())
                        .body(responseData);
            } else {
                responseData.setStatusCode(401);
                responseData.setMessage("Login failed: Invalid credentials");
                return new ResponseEntity<>(responseData, HttpStatus.UNAUTHORIZED);
            }
        } catch (Exception e) {
            responseData.setStatusCode(500);
            responseData.setMessage("Login failed: " + e.getMessage());
            return new ResponseEntity<>(responseData, HttpStatus.INTERNAL_SERVER_ERROR);
        }
    }

    @PostMapping("/signup")
    public ResponseEntity<ResponseData> signup(@Valid @RequestBody SignupDTO signupDTO) {
        ResponseData responseData = new ResponseData();
        Map<String, String> errorMap = new HashMap<>(); // Thay đổi List thành Map để gán label cho từng lỗi
        boolean isError = false;

        try {
            // Kiểm tra nếu tên đăng nhập đã tồn tại
            if (taiKhoanService.existsTaiKhoanByTenDangNhap(signupDTO.getUserName())) {
                errorMap.put("username", "Tên đăng nhập đã tồn tại");
                isError = true;
            }

            // Kiểm tra nếu email đã tồn tại
            if (khachHangService.existsKhachHangByEmail(signupDTO.getEmail())) {
                errorMap.put("email", "Email đã tồn tại");
                isError = true;
            }

            // Kiểm tra nếu CCCD đã tồn tại
            if (khachHangService.existsKhachHangByCccd(signupDTO.getCccd())) {
                errorMap.put("cccd", "CCCD đã tồn tại");
                isError = true;
            }

            // Kiểm tra nếu mật khẩu không khớp
            if (!signupDTO.getPassword().equals(signupDTO.getRePassword())) {
                errorMap.put("password", "Mật khẩu và xác nhận mật khẩu không khớp");
                isError = true;
            }

            // Nếu có lỗi, trả về thông báo với danh sách lỗi
            if (isError) {
                responseData.setStatusCode(200);
                responseData.setMessage("Đăng ký không thành công");
                responseData.setData(errorMap); // Gán Map chứa các lỗi vào response
                return new ResponseEntity<>(responseData, HttpStatus.CONFLICT);
            }

            // Tạo tài khoản mới
            boolean created = taiKhoanService.createTaiKhoan(signupDTO);
            if (created) {
                responseData.setStatusCode(200);
                responseData.setMessage("Đăng ký thành công");
                return new ResponseEntity<>(responseData, HttpStatus.OK);
            } else {
                responseData.setStatusCode(500);
                responseData.setMessage("Đăng ký không thành công: Không thể tạo tài khoản");
                return new ResponseEntity<>(responseData, HttpStatus.INTERNAL_SERVER_ERROR);
            }

        } catch (Exception e) {
            responseData.setStatusCode(500);
            responseData.setMessage("Đăng ký không thành công: " + e.getMessage());
            return new ResponseEntity<>(responseData, HttpStatus.INTERNAL_SERVER_ERROR);
        }
    }
    @PostMapping("/logout")
    public ResponseEntity<?> logout(HttpServletRequest request, @RequestBody LogoutDTO logoutDTO) {
        ResponseData responseData = new ResponseData();
        try {
            String accessToken = logoutDTO.getToken();
            String idToken = jwtTokenProvider.getIdTokenFromExpiredJwtToken(accessToken);
            Date expirationTime = jwtTokenProvider.getExpirationTimeFromExpiredJwtToken(accessToken);
            InvalidTokenDTO invalidTokenDTO = new InvalidTokenDTO(idToken, expirationTime);
            invalidTokenService.saveInvalidTokenIntoDatabase(invalidTokenDTO);

            // Xử lý refresh token từ cookie
            String refreshToken = "";
            Cookie[] cookies = request.getCookies();
            if (cookies != null) {
                for (Cookie cookie : cookies) {
                    if (cookie.getName().equals("refreshToken")) {
                        refreshToken = cookie.getValue();
                        System.out.println("refreshToken: " + refreshToken);
                        break; // Tìm thấy refresh token thì dừng vòng lặp
                    }
                }
            }

            // Nếu refresh token không rỗng và hợp lệ, lưu vào cơ sở dữ liệu
            if (!refreshToken.isEmpty()) {
                invalidTokenService.saveInvalidTokenIntoDatabase(
                        new InvalidTokenDTO(idToken, expirationTime)
                );
            }

            // Xóa cookie refresh token
            ResponseCookie deleteCookie = ResponseCookie.from("refreshToken", null)
                    .httpOnly(true)
                    .secure(true)
                    .path("/")
                    .maxAge(0) // Xóa cookie
                    .build();

            // Trả về phản hồi thành công
            responseData.setStatusCode(200);
            responseData.setMessage("Đăng xuất thành công");
            responseData.setData("");
            return ResponseEntity.ok()
                    .header(HttpHeaders.SET_COOKIE, deleteCookie.toString())
                    .body(responseData);
        } catch (Exception e) {
            responseData.setStatusCode(500);
            responseData.setMessage("Đăng xuất thất bại: " + e.getMessage());
            responseData.setData("");
            return new ResponseEntity<>(responseData, HttpStatus.INTERNAL_SERVER_ERROR);
        }
    }

    @PostMapping("/refresh_token")
    public ResponseEntity<?> refreshToken(HttpServletRequest request) {
        String refreshToken = null;

        ResponseData responseData = new ResponseData();

        // Lấy refresh token từ cookie
        Cookie[] cookies = request.getCookies();
        if (cookies != null) {
            for (Cookie cookie : cookies) {
                if (cookie.getName().equals("refreshToken")) {
                    refreshToken = cookie.getValue();
                    System.out.println("refreshToken: " + refreshToken);
                }
            }
        }

        // Kiểm tra refresh token có hợp lệ không
        if (refreshToken != null && jwtTokenProvider.validateJwtToken(refreshToken) && !invalidTokenService.existsByIdToken(refreshToken)) {
            String username = jwtTokenProvider.getUserNameFromJwtToken(refreshToken);
            String newAccessToken = jwtTokenProvider.generateToken(username);
            responseData.setStatusCode(200);
            responseData.setMessage("tao moi access token thanh cong");
            responseData.setData(newAccessToken);
            return new ResponseEntity<>(responseData, HttpStatus.OK);
        } else {
            responseData.setStatusCode(999);
            responseData.setMessage("Refresh token is invalid or expired");
            responseData.setData("");
            return new ResponseEntity<>(responseData, HttpStatus.UNAUTHORIZED);
        }
    }


    @PostMapping("/forgot_password")
    public ResponseEntity<?> forgotPassword(@RequestBody ForgotPasswordDTO forgotPasswordDTO) {
        ResponseData responseData = new ResponseData();
        String email = forgotPasswordDTO.getEmail();
        Optional<TaiKhoan> taiKhoanOptional = taiKhoanService.getTaiKhoanByEmail(email);
        if (taiKhoanOptional.isEmpty()) {
            responseData.setStatusCode(404);
            responseData.setMessage("tai khoang khong co voi " + email);
            responseData.setData("");
            return new ResponseEntity<>(responseData, HttpStatus.NOT_FOUND);
        }

        String refreshTokenPassword = taiKhoanService.createPasswordResetToken(email);
        String resetLink = "http://localhost:5173/reset_password?token=" + refreshTokenPassword;
        Email emailSend = new Email();
        emailSend.setToEmail(email);
        emailSend.setSubject("Reset Your Password");
        emailSend.setMessageBody(resetLink);
        emailService.sendTextEmail(emailSend);
        responseData.setStatusCode(200);
        responseData.setMessage("Reset password link has been sent to your email");
        responseData.setData("");
        return new ResponseEntity<>(responseData, HttpStatus.OK);
    }

    @PostMapping("/reset_password")
    public ResponseEntity<?> resetPassword(@RequestBody ResetPasswordDTO resetPasswordDTO) {

        ResponseData responseData = new ResponseData();
        String refreshPasswordToken = resetPasswordDTO.getRefreshPasswordToken();
        String idRefreshPasswordToken = jwtTokenProvider.getIdTokenFromJwtToken(refreshPasswordToken);

        // Kiểm tra token có hợp lệ không
        if (refreshPasswordToken == null || !jwtTokenProvider.validateJwtToken(refreshPasswordToken) || invalidTokenService.existsByIdToken(refreshPasswordToken)) {
            responseData.setStatusCode(400);
            responseData.setMessage("Invalid token");
            responseData.setData("");
            return new ResponseEntity<>(responseData, HttpStatus.BAD_REQUEST);
        }

        // Lấy email từ token
        String email = jwtTokenProvider.getUserNameFromJwtToken(refreshPasswordToken);

        // Lấy thời gian hết hạn của token và chuyển từ Date sang LocalDateTime
        Date expirationDate = jwtTokenProvider.getExpirationTimeTokenFromJwtToken(refreshPasswordToken);
        LocalDateTime expirationTime = expirationDate.toInstant().atZone(ZoneId.systemDefault()).toLocalDateTime();

        // So sánh thời gian hết hạn với thời gian hiện tại
        if (expirationTime.isBefore(LocalDateTime.now())) {
            responseData.setStatusCode(400);
            responseData.setMessage("Token has expired");
            responseData.setData("");
            return new ResponseEntity<>(responseData, HttpStatus.BAD_REQUEST);
        }

        // Tìm tài khoản dựa trên email
        Optional<TaiKhoan> taiKhoanOptional = taiKhoanService.getTaiKhoanByEmail(email);
        if (taiKhoanOptional.isEmpty()) {
            responseData.setStatusCode(404);
            responseData.setMessage("User not found");
            responseData.setData("");
            return new ResponseEntity<>(responseData, HttpStatus.NOT_FOUND);
        }

        // Lấy tài khoản và cập nhật mật khẩu mới
        TaiKhoan taiKhoan = taiKhoanOptional.get();
        taiKhoan.setMatKhau(resetPasswordDTO.getNewPassword());

        // Cập nhật tài khoản với mật khẩu mới
        boolean isSuccess = taiKhoanService.updateTaiKhoan_RefreshPassword(taiKhoan);
        // Loai bo token
        InvalidTokenDTO invalidTokenDTO = new InvalidTokenDTO(idRefreshPasswordToken, expirationDate);
        invalidTokenService.saveInvalidTokenIntoDatabase(invalidTokenDTO);
        if (isSuccess) {
            responseData.setStatusCode(200);
            responseData.setMessage("Password has been reset successfully");
            responseData.setData("");
            return new ResponseEntity<>(responseData, HttpStatus.OK);
        } else {
            responseData.setStatusCode(500);
            responseData.setMessage("Failed to reset password");
            responseData.setData("");
            return new ResponseEntity<>(responseData, HttpStatus.INTERNAL_SERVER_ERROR);
        }
    }
}
