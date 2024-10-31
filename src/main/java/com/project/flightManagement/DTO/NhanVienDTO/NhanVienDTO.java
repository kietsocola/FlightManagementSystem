package com.project.flightManagement.DTO.NhanVienDTO;

import com.project.flightManagement.DTO.ChuyenBayDTO.ChuyenBayDTO;
import com.project.flightManagement.Enum.ActiveEnum;
import com.project.flightManagement.Enum.ChucVuEnum;
import com.project.flightManagement.Enum.GioiTinhEnum;
import com.project.flightManagement.Model.ChucVu;
import com.project.flightManagement.Model.ChuyenBay;
import com.project.flightManagement.Model.QuyDinh;
import com.project.flightManagement.Model.TaiKhoan;
import jakarta.persistence.*;
import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Pattern;
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
    @Pattern(regexp = "^[\\p{L}]+( [\\p{L}]+)*$", message = "Họ tên chỉ chứa chữ cái,  1 khoảng trắng giữa các từ")
    private String hoTen;

    @NotNull(message = "Ngày sinh không được bỏ trống")
    private Date ngaySinh;

    @Email(message = "Email không hợp lệ")
    @NotBlank(message = "Email không được bỏ trống")
    private String email;

    @NotBlank(message = "Số điện thoại không được bỏ trống")
    @Pattern(regexp = "^0[0-9]{9}$", message = "Số điện thoại chỉ gồm 10 số , bắt đầu bằng số 0")
    private String soDienThoai;

    @NotBlank(message = "CCCD không được bỏ trống")
    @Pattern(regexp = "^\\d{12}$", message = "Căn cước công dân gồm 12 số")
    private String cccd;

    @NotNull(message = "Gioi tinh không được bỏ trống")
    private GioiTinhEnum gioiTinhEnum;

    @NotNull(message = "Trang thai không được bỏ trống")
    private ActiveEnum trangThaiActive;

    @NotNull(message = "Chuc vu không được bỏ trống")
    private ChucVu chucVu;

    private ChuyenBay chuyenBay;
}
