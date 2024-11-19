package com.project.flightManagement.Controller;

import com.project.flightManagement.DTO.AuthDTO.*;
import com.project.flightManagement.DTO.InvalidToken.InvalidTokenDTO;
import com.project.flightManagement.DTO.RefreshTokenDTO.RefreshTokenDTO;
import com.project.flightManagement.Enum.ActiveEnum;
import com.project.flightManagement.Model.Email;
import com.project.flightManagement.Model.TaiKhoan;
import com.project.flightManagement.Payload.ResponseData;
import com.project.flightManagement.Security.JwtTokenProvider;
import com.project.flightManagement.Service.*;
import io.jsonwebtoken.ExpiredJwtException;
import io.jsonwebtoken.JwtException;
import io.jsonwebtoken.security.SignatureException;
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
    private RefreshTokenService refreshTokenService;
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


    private static final int MAX_FAILED_ATTEMPTS = 5;

    @PostMapping("/login")
    public ResponseEntity<ResponseData> login(@RequestBody LoginDTO loginDTO) {
        // 999 bi khoa
        ResponseData responseData = new ResponseData();
        try {

            int checkLogin = taiKhoanService.checkLogin(loginDTO);
            // 0 thanh cong
            // 1 sai mat khau
            // 2 khong tim thay tk
            if (checkLogin == -1) {
                responseData.setStatusCode(999);
                responseData.setMessage("Đăng nhập thất bại: Tai khoan da bi khoa");
                return new ResponseEntity<>(responseData, HttpStatus.OK);
            }
            else if (checkLogin == 0) {
                // Tạo access token và refresh token
                String accessToken = jwtTokenProvider.generateToken(loginDTO.getUserName());
                String refreshToken = jwtTokenProvider.generateRefreshToken(loginDTO.getUserName());
//                System.out.println("refreshToken ~ login: " + refreshToken);

                // Cấu hình cookie
                ResponseCookie responseCookie = ResponseCookie.from("refreshToken", refreshToken)
                        .httpOnly(true)  // Chỉ dùng Http, không thể truy cập từ JavaScript
                        .secure(true)    // Chỉ gửi cookie qua HTTPS
                        .path("/")       // Đường dẫn của API refresh token
                        .maxAge(7 * 24 * 60 * 60) // Thời gian sống 7 ngày
                        .build();

                // Lưu refresh token vào cơ sở dữ liệu
                RefreshTokenDTO refreshTokenDTO = new RefreshTokenDTO(
                        jwtTokenProvider.getIdTokenFromJwtToken(refreshToken),
                        jwtTokenProvider.getExpirationTimeTokenFromJwtToken(refreshToken),
                        taiKhoanService.getTaiKhoanByTenDangNhap(loginDTO.getUserName()).get().getIdTaiKhoan(),
                        ActiveEnum.ACTIVE
                );
                boolean isSaveSuccess = refreshTokenService.saveRefreshTokenIntoDatabase(refreshTokenDTO);

                // Trả về phản hồi thành công
                responseData.setStatusCode(200);
                responseData.setMessage("Đăng nhập thành công");
                responseData.setData(accessToken);
                resetFailedAttempts(loginDTO.getUserName());
                return ResponseEntity.ok()
                        .header(HttpHeaders.SET_COOKIE, responseCookie.toString())
                        .body(responseData);
            } else if (checkLogin == 1) {
                increaseFailedAttempts(loginDTO.getUserName());
                responseData.setStatusCode(401);
                responseData.setMessage("Đăng nhập thất bại: Sai thông tin đăng nhập");
                return new ResponseEntity<>(responseData, HttpStatus.OK);
            } else {
                responseData.setStatusCode(401);
                responseData.setMessage("Đăng nhập thất bại: Sai thông tin đăng nhập");
                return new ResponseEntity<>(responseData, HttpStatus.OK);
            }
        } catch (Exception e) {
            responseData.setStatusCode(500);
            responseData.setMessage("Đăng nhập thất bại: " + e.getMessage());
            return new ResponseEntity<>(responseData, HttpStatus.INTERNAL_SERVER_ERROR);
        }
    }

    private void increaseFailedAttempts(String userName) {
        Optional<TaiKhoan> taiKhoanOptional = taiKhoanService.getTaiKhoanByTenDangNhap(userName);

        TaiKhoan taiKhoan = taiKhoanOptional.get();
        int newFailAttempts = taiKhoan.getSoLanNhapSai() + 1;
        taiKhoan.setSoLanNhapSai(newFailAttempts);

        if (newFailAttempts >= MAX_FAILED_ATTEMPTS) {
            taiKhoan.setTrangThaiActive(ActiveEnum.IN_ACTIVE);
        }
        taiKhoanService.saveTaiKhoan(taiKhoan);
    }

    private void resetFailedAttempts(String userName) {
        Optional<TaiKhoan> taiKhoanOptional = taiKhoanService.getTaiKhoanByTenDangNhap(userName);

        TaiKhoan taiKhoan = taiKhoanOptional.get();
        taiKhoan.setSoLanNhapSai(0);
        taiKhoanService.saveTaiKhoan(taiKhoan);
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
            if (khachHangService.existsKhachHangByCccd(signupDTO.getCccd())) {
                errorMap.put("phone", "Số điện thoại đã tồn tại");
                isError = true;
            }

            // Kiểm tra nếu mật khẩu không khớp
            if (!signupDTO.getPassword().equals(signupDTO.getRePassword())) {
                errorMap.put("password", "Mật khẩu và xác nhận mật khẩu không khớp");
                isError = true;
            }

            // Nếu có lỗi, trả về thông báo với danh sách lỗi
            if (isError) {
                responseData.setStatusCode(400);
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
            String refreshToken = getRefreshTokenFromRequest(request);

            try {
                String idRefreshToken = jwtTokenProvider.getIdTokenFromJwtToken(refreshToken);
                // Vô hiệu hóa refresh token
                refreshTokenService.deactivateToken(idRefreshToken);
                // Lưu lại refresh token đã bị vô hiệu hóa
                invalidTokenService.saveInvalidTokenIntoDatabase(
                        new InvalidTokenDTO(idRefreshToken, expirationTime)
                );
            } catch (ExpiredJwtException e) {
                // Token hết hạn thì bỏ qua lỗi này và tiếp tục đăng xuất
                System.out.println("Refresh token đã hết hạn, vẫn tiếp tục đăng xuất.");
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
            // Nếu có lỗi không mong muốn khác xảy ra, trả về lỗi 500
            responseData.setStatusCode(500);
            responseData.setMessage("Đăng xuất thất bại: " + e.getMessage());
            responseData.setData("");
            return new ResponseEntity<>(responseData, HttpStatus.OK);
        }
    }

    @PostMapping("/refresh_token")
    public ResponseEntity<?> refreshToken(HttpServletRequest request) {
        String refreshToken = getRefreshTokenFromRequest(request);
        ResponseData responseData = new ResponseData();

        if (refreshToken != null) {
            String idRefreshToken = null;
            try {
                // Lấy ID từ refresh token
                idRefreshToken = jwtTokenProvider.getIdTokenFromJwtToken(refreshToken);
            } catch (ExpiredJwtException e) {
                // Token đã hết hạn
                responseData.setStatusCode(999);
                responseData.setMessage("Refresh token đã hết hạn");
                responseData.setData("");
                return new ResponseEntity<>(responseData, HttpStatus.UNAUTHORIZED);
            } catch (SignatureException e) {
                // Lỗi chữ ký không khớp (Signature does not match)
                responseData.setStatusCode(999);
                responseData.setMessage("Lỗi chữ ký JWT không hợp lệ");
                responseData.setData("");
                return new ResponseEntity<>(responseData, HttpStatus.UNAUTHORIZED);
            } catch (JwtException e) {
                // Bắt các lỗi JWT khác (bao gồm signature, format lỗi, không hợp lệ, v.v.)
                responseData.setStatusCode(999);
                responseData.setMessage("Token không hợp lệ: " + e.getMessage());
                responseData.setData("");
                return new ResponseEntity<>(responseData, HttpStatus.UNAUTHORIZED);
            }

            // Kiểm tra token có hợp lệ và active hay không
            if (jwtTokenProvider.validateJwtToken(refreshToken) && refreshTokenService.isTokenActive(idRefreshToken)) {
                // Lấy username từ token và tạo access token mới
                String username = jwtTokenProvider.getUserNameFromJwtToken(refreshToken);
                String newAccessToken = jwtTokenProvider.generateToken(username);
                responseData.setStatusCode(200); // Nội bộ báo thành công
                responseData.setMessage("Tạo mới access token thành công");
                responseData.setData(newAccessToken);
                return new ResponseEntity<>(responseData, HttpStatus.OK); // Trả về 200 OK
            } else {
                // Token không hợp lệ hoặc đã bị vô hiệu hóa
                responseData.setStatusCode(999);
                responseData.setMessage("Refresh token không hợp lệ hoặc đã bị vô hiệu hóa");
                responseData.setData("");
                return new ResponseEntity<>(responseData, HttpStatus.UNAUTHORIZED);
            }
        } else {
            // Không tìm thấy refresh token
            responseData.setStatusCode(999);
            responseData.setMessage("Không tìm thấy refresh token");
            responseData.setData("");
            return new ResponseEntity<>(responseData, HttpStatus.OK);
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
        emailService.sendHtmlEMail(emailSend, resetLink, taiKhoanOptional.get().getTenDangNhap());
        responseData.setStatusCode(200);
        responseData.setMessage("Reset password link has been sent to your email");
        responseData.setData("");
        return new ResponseEntity<>(responseData, HttpStatus.OK);
    }

    @PostMapping("/reset_password")
    public ResponseEntity<?> resetPassword(@Valid @RequestBody ResetPasswordDTO resetPasswordDTO) {

        ResponseData responseData = new ResponseData();
        String refreshPasswordToken = resetPasswordDTO.getRefreshPasswordToken();
        if (!isValidJwtFormat(refreshPasswordToken)) {
            responseData.setStatusCode(400);
            responseData.setMessage("Token không hợp lệ");
            responseData.setData("");
            return new ResponseEntity<>(responseData, HttpStatus.BAD_REQUEST);
        }
        String idRefreshPasswordToken = jwtTokenProvider.getIdTokenFromExpiredJwtToken(refreshPasswordToken);

        // Kiểm tra token có hợp lệ không
        if (refreshPasswordToken == null || !jwtTokenProvider.validateJwtToken(refreshPasswordToken) || invalidTokenService.existsByIdToken(refreshPasswordToken)) {
            responseData.setStatusCode(400);
            responseData.setMessage("Token không hợp lệ");
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
            responseData.setMessage("Token đã hết hạn");
            responseData.setData("");
            return new ResponseEntity<>(responseData, HttpStatus.BAD_REQUEST);
        }

        // Tìm tài khoản dựa trên email
        Optional<TaiKhoan> taiKhoanOptional = taiKhoanService.getTaiKhoanByEmail(email);
        if (taiKhoanOptional.isEmpty()) {
            responseData.setStatusCode(404);
            responseData.setMessage("Không tìm thấy người dùng");
            responseData.setData("");
            return new ResponseEntity<>(responseData, HttpStatus.NOT_FOUND);
        }

        // Lấy tài khoản và cập nhật mật khẩu mới
        TaiKhoan taiKhoan = taiKhoanOptional.get();
        if (!resetPasswordDTO.getNewPassword().equals(resetPasswordDTO.getReNewPassword())) {
            responseData.setStatusCode(400);
            responseData.setMessage("Mật khẩu nhập lại không khớp");
            responseData.setData("");
            return new ResponseEntity<>(responseData, HttpStatus.CONFLICT);
        }
        taiKhoan.setMatKhau(resetPasswordDTO.getNewPassword());
        taiKhoan.setSoLanNhapSai(0);
        taiKhoan.setTrangThaiActive(ActiveEnum.ACTIVE);

        // Cập nhật tài khoản với mật khẩu mới
        boolean isSuccess = taiKhoanService.updateTaiKhoan_RefreshPassword(taiKhoan);

        // Loại bỏ token
        if (isSuccess) {
            InvalidTokenDTO invalidTokenDTO = new InvalidTokenDTO(idRefreshPasswordToken, expirationDate);
            invalidTokenService.saveInvalidTokenIntoDatabase(invalidTokenDTO);
            responseData.setStatusCode(200);
            responseData.setMessage("Đặt lại mật khẩu thành công");
            responseData.setData("");
            return new ResponseEntity<>(responseData, HttpStatus.OK);
        } else {
            responseData.setStatusCode(500);
            responseData.setMessage("Đặt lại mật khẩu thất bại");
            responseData.setData("");
            return new ResponseEntity<>(responseData, HttpStatus.INTERNAL_SERVER_ERROR);
        }
    }

    private String getRefreshTokenFromRequest(HttpServletRequest httpServletRequest) {
        String refreshToken = null;

        // Lấy refresh token từ cookie
        Cookie[] cookies = httpServletRequest.getCookies();
        if (cookies != null) {
            for (Cookie cookie : cookies) {
                if (cookie.getName().equals("refreshToken")) {
                    refreshToken = cookie.getValue();
                    System.out.println("refreshToken: " + refreshToken);
                }
            }
        }
        return refreshToken;
    }

    private boolean isValidJwtFormat(String token) {
        // Kiểm tra token không null và có đúng định dạng JWT
        return token != null && token.split("\\.").length == 3;
    }
}
