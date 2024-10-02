package com.project.flightManagement.Service;

import com.project.flightManagement.DTO.AuthDTO.LoginDTO;
import com.project.flightManagement.DTO.AuthDTO.SignupDTO;
import com.project.flightManagement.Model.TaiKhoan;
import org.springframework.stereotype.Service;

import java.util.Optional;

public interface TaiKhoanService {
    boolean createTaiKhoan(SignupDTO signupDTO);
    boolean existsTaiKhoanByTenDangNhap(String userName);
    boolean checkLogin(LoginDTO loginDTO);
    Optional<TaiKhoan> getTaiKhoanByTenDangNhap(String userName);
}
