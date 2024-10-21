package com.project.flightManagement.Mapper;

import com.project.flightManagement.DTO.RefreshTokenDTO.RefreshTokenDTO;
import com.project.flightManagement.Model.RefreshToken;
import com.project.flightManagement.Model.TaiKhoan;
import org.springframework.stereotype.Component;

@Component
public class RefreshTokenMapper {
    public RefreshToken toRefreshToken(RefreshTokenDTO refreshTokenDTO) {
        RefreshToken refreshToken = new RefreshToken();
        refreshToken.setIdRefreshToken(refreshTokenDTO.getIdRefreshToken());
        refreshToken.setExpiryDate(refreshTokenDTO.getExpiryDate());
        TaiKhoan taiKhoan = new TaiKhoan();
        taiKhoan.setIdTaiKhoan(refreshTokenDTO.getIdTaiKhoan());
        refreshToken.setTaiKhoan(taiKhoan);
        refreshToken.setTrangThaiActive(refreshTokenDTO.getTrangThaiActive());
        return refreshToken;
    }
}
