package com.project.flightManagement.DTO;

import com.project.flightManagement.Enum.ActiveEnum;
import com.project.flightManagement.Enum.GioiTinhEnum;
import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.Pattern;
import jakarta.validation.constraints.Size;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class HanhKhachDTO {

    private Integer idHanhKhach;
    private Integer idVe;

    @NotBlank(message = "Họ tên không được để trống")
    @Size(min = 2, message = "Họ tên phải có ít nhất 2 ký tự")
    private String hoTen;

    @NotBlank(message = "Ngày sinh không được để trống")
    private String ngaySinh;

    @NotBlank(message = "Số điện thoại không được để trống")
    @Pattern(regexp = "^(\\+84|0)\\d{9,10}$", message = "Số điện thoại không hợp lệ")
    private String soDienThoai;

    @NotBlank(message = "Email không được để trống")
    @Email(message = "Email không đúng định dạng")
    private String email;

    @Pattern(regexp = "^[0-9]{9}$|^[0-9]{12}$", message = "CCCD phải có 9 hoặc 12 chữ số")
    private String cccd;
    private String hoChieu;

    private GioiTinhEnum gioiTinhEnum;
    private ActiveEnum trangThaiActive;
}
