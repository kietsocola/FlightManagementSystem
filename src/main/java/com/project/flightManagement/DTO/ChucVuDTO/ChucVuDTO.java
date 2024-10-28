package com.project.flightManagement.DTO.ChucVuDTO;

import com.project.flightManagement.Enum.ActiveEnum;
import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@AllArgsConstructor

public class ChucVuDTO {

    private int idChucVu ;

    private String ten ;

    private String moTa;

    private ActiveEnum trangThaiActive;
}
