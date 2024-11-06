package com.project.flightManagement.DTO.ChucVuDTO;

import com.project.flightManagement.Enum.ActiveEnum;
import jakarta.persistence.*;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@AllArgsConstructor

public class ChucVuDTO {

    private int idChucVu ;

    @NotBlank(message =  "Không được để tên trống")
    private String ten ;

    @NotBlank(message =  "Không được để mô tả trống")
    private String moTa;

    @NotNull(message =  "Chọn trạng thái")
    private ActiveEnum trangThaiActive;
}