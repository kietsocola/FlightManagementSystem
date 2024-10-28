package com.project.flightManagement.DTO.VeDTO;

import com.project.flightManagement.Enum.VeEnum;
import jakarta.validation.constraints.NotNull;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class VeUpdateHanhKhachDTO {
    @NotNull(message = "ID vé không được để trống.")
    private int idVe;
    @NotNull(message = "ID hanh khach không được để trống.")
    private int idHanhKhach;
    @NotNull(message = "trang thai không được để trống.")
    private VeEnum trangThai;
}
