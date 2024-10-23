package com.project.flightManagement.DTO.LoaiVeDTO;

import com.project.flightManagement.Enum.ActiveEnum;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
@Data
@NoArgsConstructor
@AllArgsConstructor
public class LoaiVeDTO {
    private int idLoaiVe;
    private String tenLoaiVe;
    private String moTa;
    private ActiveEnum trangThaiActive;
}
