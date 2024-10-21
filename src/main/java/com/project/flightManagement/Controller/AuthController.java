package com.project.flightManagement.Controller;

import com.project.flightManagement.DTO.AuthDTO.LoginDTO;
import com.project.flightManagement.DTO.AuthDTO.LogoutDTO;
import com.project.flightManagement.DTO.AuthDTO.SignupDTO;
import com.project.flightManagement.DTO.InvalidToken.InvalidTokenDTO;
import com.project.flightManagement.DTO.RefreshTokenDTO.RefreshTokenDTO;
import com.project.flightManagement.Enum.ActiveEnum;
import com.project.flightManagement.Model.RefreshToken;
import com.project.flightManagement.Payload.ResponseData;
import com.project.flightManagement.Security.JwtTokenProvider;
import com.project.flightManagement.Service.InvalidTokenService;
import com.project.flightManagement.Service.KhachHangService;
import com.project.flightManagement.Service.RefreshTokenService;
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
    private RefreshTokenService refreshTokenService;

    @PostMapping("/login")
    public ResponseEntity<ResponseData> login(@RequestBody LoginDTO loginDTO) {
        ResponseData responseData = new ResponseData();
        try {
            // Kiểm tra đăng nhập
            if (taiKhoanService.checkLogin(loginDTO)) {
                // Tạo access token và refresh token
                String accessToken = jwtTokenProvider.generateToken(loginDTO.getUserName());
                String refreshToken = jwtTokenProvider.generateRefreshToken(loginDTO.getUserName());

                System.out.println("re token: " + refreshToken);
                // Lưu refresh token vào cơ sở dữ liệu
                RefreshTokenDTO refreshTokenDTO = new RefreshTokenDTO(
                        jwtTokenProvider.getIdTokenFromJwtToken(refreshToken),
                        jwtTokenProvider.getExpirationTimeTokenFromJwtToken(refreshToken),
                        taiKhoanService.getTaiKhoanByTenDangNhap(loginDTO.getUserName()).get().getIdTaiKhoan(),
                        ActiveEnum.ACTIVE
                );
                refreshTokenService.saveRefreshTokenIntoDatabase(refreshTokenDTO);
                System.out.println("da luu re token: " + refreshToken);

                // Thiết lập phản hồi thành công
                responseData.setStatusCode(200);  // Mã trạng thái tùy chỉnh
                responseData.setMessage("Login success");
                responseData.setData(accessToken);
                return new ResponseEntity<>(responseData, HttpStatus.OK);  // HTTP 200 OK
            } else {
                // Đăng nhập thất bại: Sai thông tin xác thực
                responseData.setStatusCode(401);  // Mã trạng thái tùy chỉnh
                responseData.setMessage("Login failed: Invalid credentials");
                return new ResponseEntity<>(responseData, HttpStatus.UNAUTHORIZED);  // HTTP 401 Unauthorized
            }
        } catch (Exception e) {
            // Lỗi máy chủ
            responseData.setStatusCode(500);  // Mã trạng thái tùy chỉnh
            responseData.setMessage("Login failed: " + e.getMessage());
            return new ResponseEntity<>(responseData, HttpStatus.INTERNAL_SERVER_ERROR);  // HTTP 500 Internal Server Error
        }
    }

    @PostMapping("/signup")
    public ResponseEntity<ResponseData> signup(@Valid @RequestBody SignupDTO signupDTO) {
        ResponseData responseData = new ResponseData();
        Map<String, String> errorMap = new HashMap<>(); // Gán label cho từng lỗi
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
                responseData.setStatusCode(409); // Mã tùy chỉnh, thể hiện lỗi trùng lặp
                responseData.setMessage("Đăng ký không thành công");
                responseData.setData(errorMap); // Gán Map chứa các lỗi vào response
                return new ResponseEntity<>(responseData, HttpStatus.CONFLICT);
            }

            // Tạo tài khoản mới
            boolean created = taiKhoanService.createTaiKhoan(signupDTO);
            if (created) {
                responseData.setStatusCode(200);  // Mã tùy chỉnh cho thành công
                responseData.setMessage("Đăng ký thành công");
                return new ResponseEntity<>(responseData, HttpStatus.OK);
            } else {
                responseData.setStatusCode(500);  // Mã tùy chỉnh cho lỗi server
                responseData.setMessage("Đăng ký không thành công: Không thể tạo tài khoản");
                return new ResponseEntity<>(responseData, HttpStatus.INTERNAL_SERVER_ERROR);
            }

        } catch (Exception e) {
            // Xử lý lỗi ngoại lệ
            responseData.setStatusCode(500);  // Mã tùy chỉnh cho lỗi server
            responseData.setMessage("Đăng ký không thành công: " + e.getMessage());
            return new ResponseEntity<>(responseData, HttpStatus.INTERNAL_SERVER_ERROR);
        }
    }
    @PostMapping("/logout")
    public ResponseEntity<ResponseData> logout(HttpServletRequest request, @RequestBody LogoutDTO logoutDTO) {
        ResponseData responseData = new ResponseData();
        try {
            // Lấy idToken từ token đăng xuất
            String idToken = jwtTokenProvider.getIdTokenFromJwtToken(logoutDTO.getToken());
            String refreshToken = getRefreshTokenFromRequest(request);
            String idRefreshToken = jwtTokenProvider.getIdTokenFromJwtToken(refreshToken);

            // Lấy thời gian hết hạn của token
            Date expirationTime = jwtTokenProvider.getExpirationTimeTokenFromJwtToken(logoutDTO.getToken());

            // Lưu token vào danh sách token không hợp lệ
            InvalidTokenDTO invalidTokenDTO = new InvalidTokenDTO(idToken, expirationTime);
            invalidTokenService.saveInvalidTokenIntoDatabase(invalidTokenDTO);

            // Vô hiệu hóa refresh token
            refreshTokenService.deactivateToken(idRefreshToken);

            // Phản hồi đăng xuất thành công
            responseData.setStatusCode(200);
            responseData.setMessage("Đăng xuất thành công");
            responseData.setData("");
            return new ResponseEntity<>(responseData, HttpStatus.OK);

        } catch (Exception e) {
            // Xử lý lỗi khi có ngoại lệ
            responseData.setStatusCode(500);
            responseData.setMessage("Đăng xuất thất bại: " + e.getMessage());
            responseData.setData("");
            return new ResponseEntity<>(responseData, HttpStatus.INTERNAL_SERVER_ERROR);
        }
    }

    @PostMapping("/refresh_token")
    public ResponseEntity<ResponseData> refreshToken(HttpServletRequest request) {
        String refreshToken = getRefreshTokenFromRequest(request);
        System.out.println("re token_api_refresh:" + refreshToken);
        ResponseData responseData = new ResponseData();

        try {
            // Kiểm tra refresh token có hợp lệ
            if (refreshToken != null && jwtTokenProvider.validateJwtToken(refreshToken)) {
                String username = jwtTokenProvider.getUserNameFromJwtToken(refreshToken);
                String newAccessToken = jwtTokenProvider.generateToken(username);

                responseData.setStatusCode(200);
                responseData.setMessage("Tạo mới access token thành công");
                responseData.setData(newAccessToken);
                return new ResponseEntity<>(responseData, HttpStatus.OK);
            } else {
                // Token không hợp lệ hoặc hết hạn
                responseData.setStatusCode(401);
                responseData.setMessage("Refresh token không hợp lệ hoặc đã hết hạn");
                responseData.setData("");
                return new ResponseEntity<>(responseData, HttpStatus.UNAUTHORIZED);
            }
        } catch (Exception e) {
            // Xử lý lỗi khi có ngoại lệ
            responseData.setStatusCode(500);
            responseData.setMessage("Có lỗi xảy ra: " + e.getMessage());
            responseData.setData("");
            return new ResponseEntity<>(responseData, HttpStatus.INTERNAL_SERVER_ERROR);
        }
    }
    private String getRefreshTokenFromRequest(HttpServletRequest request) {
        String refreshToken = null;

        ResponseData responseData = new ResponseData();

        // Lấy refresh token từ cookie
        Cookie[] cookies = request.getCookies();
        if (cookies != null) {
            for (Cookie cookie : cookies) {
                if (cookie.getName().equals("refreshToken")) {
                    refreshToken = cookie.getValue();
                }
            }
        }
        return refreshToken;
    }
}
