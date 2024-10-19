package com.project.flightManagement.Service;

import com.project.flightManagement.DTO.TaiKhoanDTO.TaiKhoanDTO;
import com.project.flightManagement.Model.TaiKhoan;
import org.springframework.stereotype.Service;

import java.util.Optional;

@Service
public interface TaiKhoanService {
    public  Optional<TaiKhoanDTO> addTaiKhoan(TaiKhoanDTO taiKhoanDTO);
    public  Optional<TaiKhoanDTO> updateTaiKhoan(TaiKhoanDTO taiKhoanDTO);
    public  Iterable<TaiKhoanDTO> getAllTaiKhoan();
    public Optional<TaiKhoanDTO> getTaiKhoanByTenDN(String tenDN);
    public  Optional<TaiKhoanDTO> getTaiKhoanByIdTK(int idTK);

}
