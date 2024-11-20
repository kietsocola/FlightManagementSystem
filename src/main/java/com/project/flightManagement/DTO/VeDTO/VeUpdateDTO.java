package com.project.flightManagement.DTO.VeDTO;

import com.project.flightManagement.DTO.ChuyenBayDTO.ChuyenBay_VeDTO;
import com.project.flightManagement.DTO.HanhKhachDTO.HanhKhach_VeDTO;
import com.project.flightManagement.DTO.LoaiVeDTO.LoaiVeDTO;
import com.project.flightManagement.Enum.ActiveEnum;
import com.project.flightManagement.Enum.GioiTinhEnum;
import com.project.flightManagement.Enum.VeEnum;
import jakarta.validation.constraints.*;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.Date;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class VeUpdateDTO {
    @NotNull(message = "ID vé không được để trống.")
    private int idVe;

    @NotBlank(message = "Tên hành khách không được để trống.")
    @Size(min = 2, max = 100, message = "Tên hành khách phải có độ dài từ 2 đến 100 ký tự.")
    private String tenHanhKhach;
    private GioiTinhEnum gioiTinhEnum;
    private String ngaySinh;
//    @NotBlank(message = "Số điện thoại không được bỏ trống")
//    @Pattern(regexp = "^0[0-9]{9}$", message = "Số điện thoại chỉ gồm 10 số , bắt đầu bằng số 0")
//    private String soDienThoai;
//    private String email;
    private String cccd;
    @NotNull(message = "Trạng thái active không được để trống.")
    private ActiveEnum trangThaiActive;


}
