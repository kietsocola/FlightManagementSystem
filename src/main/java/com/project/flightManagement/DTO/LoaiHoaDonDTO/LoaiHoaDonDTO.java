package com.project.flightManagement.DTO.LoaiHoaDonDTO;

import com.project.flightManagement.Enum.ActiveEnum;
import jakarta.validation.constraints.NotBlank;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class LoaiHoaDonDTO {
    private int idLoaiHD;

    private String moTa;

    @NotBlank(message = "Tên loại hóa đơn không được bỏ trống!")
    private String tenLoaiHD;

    private ActiveEnum status;
}
