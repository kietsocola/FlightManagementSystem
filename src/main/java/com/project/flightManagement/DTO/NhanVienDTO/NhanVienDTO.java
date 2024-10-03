package com.project.flightManagement.DTO.NhanVienDTO;

import com.project.flightManagement.Enum.ActiveEnum;
import com.project.flightManagement.Enum.ChucVuEnum;
import com.project.flightManagement.Enum.GioiTinhEnum;
import com.project.flightManagement.Model.QuyDinh;
import com.project.flightManagement.Model.TaiKhoan;
import jakarta.persistence.*;
import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.Date;
import java.util.List;

@Data
@NoArgsConstructor
@AllArgsConstructor


public class NhanVienDTO {

    private int idNhanVien;

    @NotBlank(message = "Tên không được bở trống")
    private String hoTen;

    @NotNull(message = "Ngày sinh không được bỏ trống")
    private Date ngaySinh;


    @Email(message = "Email không hợp lệ")
    @NotBlank(message = "Email không được bỏ trống")
    private String email;

    @NotBlank(message = "Số điện thoại không được bỏ trống")
    private String soDienThoai;
    private String cccd;
    private GioiTinhEnum gioiTinhEnum;
    private ActiveEnum trangThaiActive;
    private ChucVuEnum chucVu;
}
