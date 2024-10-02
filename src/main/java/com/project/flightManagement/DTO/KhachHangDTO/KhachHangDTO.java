package com.project.flightManagement.DTO.KhachHangDTO;

import com.project.flightManagement.Enum.ActiveEnum;
import com.project.flightManagement.Enum.GioiTinhEnum;
import jakarta.validation.constraints.*;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.Date;
@Data
@NoArgsConstructor
@AllArgsConstructor
public class KhachHangDTO {
    private int idKhachHang;
    @NotBlank(message = "Tên không được bở trống")
    private String hoTen;
    @NotNull(message = "Ngày sinh không được bỏ trống")
    private Date ngaySinh;
    @NotBlank(message = "Số điện thoại không được bỏ trống")
    private String soDienThoai;
    @Email(message = "Email không hợp lệ")
    @NotBlank(message = "Email không được bỏ trống")
    private String email;
    private String cccd;
    private GioiTinhEnum gioiTinhEnum;
    private ActiveEnum trangThaiActive;
}
