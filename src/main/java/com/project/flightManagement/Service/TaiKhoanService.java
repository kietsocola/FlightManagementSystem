package com.project.flightManagement.Service;

import com.project.flightManagement.DTO.AuthDTO.LoginDTO;
import com.project.flightManagement.DTO.AuthDTO.SignupDTO;
import com.project.flightManagement.DTO.TaiKhoanDTO.TaiKhoanDTO;
import com.project.flightManagement.DTO.TaiKhoanDTO.TaiKhoanResponseDTO;
import com.project.flightManagement.DTO.TaiKhoanDTO.TaiKhoanUpdateNguoiDungDTO;
import com.project.flightManagement.Model.TaiKhoan;
import org.springframework.data.domain.Page;

import java.util.Optional;

public interface TaiKhoanService {
    boolean createTaiKhoan(SignupDTO signupDTO);
    boolean existsTaiKhoanByTenDangNhap(String userName);
    boolean checkLogin(LoginDTO loginDTO);
    Optional<TaiKhoan> getTaiKhoanByTenDangNhap(String userName);
    TaiKhoanResponseDTO getTaiKhoanByIdTaiKhoan(int idTaiKhoan);
    Page<TaiKhoanDTO> getAllTaiKhoan(int page, int size);

    String createPasswordResetToken(String email);
    Optional<TaiKhoan> getTaiKhoanByEmail(String email);

    boolean updateTaiKhoan(String userName, TaiKhoanUpdateNguoiDungDTO taiKhoanUpdateNguoiDungDTO);

    boolean updateTaiKhoan_RefreshPassword(TaiKhoan taiKhoan);
    Iterable<TaiKhoanDTO> findByKeyword(String keyword);
    Iterable<TaiKhoanDTO> getAllTaiKhoanSorted(String field, String order);
    Optional<TaiKhoanDTO> updateTaiKhoan(TaiKhoanDTO tkDTO);
    Optional<TaiKhoanDTO> addNewTaiKhoan(TaiKhoanDTO tkDTO);
    boolean checkExistTenDangNhap(TaiKhoanDTO tkDTO);

}
