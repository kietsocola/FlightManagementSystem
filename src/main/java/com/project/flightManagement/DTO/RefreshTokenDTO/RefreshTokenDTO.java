package com.project.flightManagement.DTO.RefreshTokenDTO;

import com.project.flightManagement.Enum.ActiveEnum;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.Date;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class RefreshTokenDTO {
    private String idRefreshToken;
    private Date expiryDate;
    private int idTaiKhoan;
    private ActiveEnum trangThaiActive;
}

