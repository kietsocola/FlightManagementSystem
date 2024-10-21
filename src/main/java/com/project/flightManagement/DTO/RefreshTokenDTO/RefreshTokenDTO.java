package com.project.flightManagement.DTO.RefreshTokenDTO;

import com.project.flightManagement.Enum.ActiveEnum;
import jakarta.persistence.Column;
import jakarta.persistence.EnumType;
import jakarta.persistence.Enumerated;
import jakarta.persistence.Id;
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
