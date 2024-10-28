package com.project.flightManagement.DTO.VeDTO;

import com.project.flightManagement.DTO.ChuyenBayDTO.ChuyenBay_VeDTO;
import com.project.flightManagement.DTO.HanhKhachDTO.HanhKhach_VeDTO;
import com.project.flightManagement.DTO.LoaiVeDTO.LoaiVeDTO;
import com.project.flightManagement.Enum.ActiveEnum;
import com.project.flightManagement.Enum.VeEnum;
import jakarta.validation.constraints.DecimalMin;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Size;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class VeUpdateDTO {
    @NotNull(message = "ID vé không được để trống.")
    private int idVe;

    @DecimalMin(value = "0.0", message = "Giá vé phải lớn hơn hoặc bằng 0.")
    private double giaVe;

    @NotBlank(message = "Tên hành khách không được để trống.")
    @Size(min = 2, max = 100, message = "Tên hành khách phải có độ dài từ 2 đến 100 ký tự.")
    private String tenHanhKhach;

    @NotNull(message = "Trạng thái active không được để trống.")
    private ActiveEnum trangThaiActive;

    @NotNull(message = "Trạng thái vé không được để trống.")
    private VeEnum trangThai;

}
