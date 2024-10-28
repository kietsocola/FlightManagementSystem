package com.project.flightManagement.DTO.VeDTO;

import com.project.flightManagement.DTO.ChuyenBayDTO.ChuyenBay_VeDTO;
import com.project.flightManagement.DTO.HangVeDTO.HangVeDTO;
import com.project.flightManagement.DTO.HanhKhachDTO.HanhKhach_VeDTO;
import com.project.flightManagement.DTO.LoaiVeDTO.LoaiVeDTO;
import com.project.flightManagement.Enum.ActiveEnum;
import com.project.flightManagement.Enum.VeEnum;
import jakarta.validation.constraints.DecimalMin;
import jakarta.validation.constraints.NotNull;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class VeCreateDTO {
    @NotNull(message = "idChuyenBay không được để trống.")
    private Integer idChuyenBay;

    @DecimalMin(value = "0.0", message = "Giá vé phải lớn hơn hoặc bằng 0.")
    private double giaVe;

    @NotNull(message = "idHangVe không được để trống.")
    private int idHangVe;

    @NotNull(message = "idChoNgoi không được để trống.")
    private int idChoNgoi;

    @NotNull(message = "idLoaiVe không được để trống.")
    private int idLoaiVe;
}
