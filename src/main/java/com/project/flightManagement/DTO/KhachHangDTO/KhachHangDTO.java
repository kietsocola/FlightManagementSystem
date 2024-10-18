package com.project.flightManagement.DTO.KhachHangDTO;

import com.project.flightManagement.Enum.ActiveEnum;
import com.project.flightManagement.Enum.GioiTinhEnum;
import jakarta.validation.constraints.*;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.sql.Date;
@Data
@NoArgsConstructor
@AllArgsConstructor
public class KhachHangDTO {
    private int idKhachHang;
    @NotBlank(message = "Tên không được bỏ trống")
    private String hoTen;
    @NotNull(message = "Ngày sinh không được bỏ trống")
    private java.sql.Date ngaySinh;
    @NotBlank(message = "Số điện thoại không được bỏ trống")
    @Pattern(regexp = "^0[0-9]{9}$", message = "Số điện thoại phải bắt đầu bằng 0 và có 10 chữ số")
    private String soDienThoai;
    @Email(message = "Email không hợp lệ")
    @NotBlank(message = "Email không được bỏ trống")
    private String email;
    @Pattern(regexp = "^[0-9]{9}$|^[0-9]{12}$", message = "CCCD phải có 9 hoặc 12 chữ số")
    @NotBlank(message = "Căn cước công dân không được bỏ trống")
    private String cccd;
    private GioiTinhEnum gioiTinhEnum;
    private ActiveEnum trangThaiActive;
}