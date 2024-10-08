package com.project.flightManagement.Service;

import com.project.flightManagement.DTO.AuthDTO.LoginDTO;
import com.project.flightManagement.DTO.AuthDTO.SignupDTO;
import com.project.flightManagement.DTO.TaiKhoanDTO.TaiKhoanDTO;
import com.project.flightManagement.Model.TaiKhoan;
import org.springframework.data.domain.Page;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

public interface TaiKhoanService {
    boolean createTaiKhoan(SignupDTO signupDTO);
    boolean existsTaiKhoanByTenDangNhap(String userName);
    boolean checkLogin(LoginDTO loginDTO);
    Optional<TaiKhoan> getTaiKhoanByTenDangNhap(String userName);
    TaiKhoanDTO getTaiKhoanByIdTaiKhoan(int idTaiKhoan);
    Page<TaiKhoanDTO> getAllTaiKhoan(int page, int size);
}
