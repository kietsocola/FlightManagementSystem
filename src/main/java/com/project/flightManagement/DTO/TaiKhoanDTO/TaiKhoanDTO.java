package com.project.flightManagement.DTO.TaiKhoanDTO;

import com.fasterxml.jackson.annotation.JsonFormat;
import com.fasterxml.jackson.annotation.JsonInclude;
import com.project.flightManagement.DTO.KhachHangDTO.KhachHangBasicDTO;
import com.project.flightManagement.DTO.NhanVienDTO.NhanVienDTO;
import com.project.flightManagement.DTO.QuyenDTO.QuyenBasicDTO;
import com.project.flightManagement.Enum.ActiveEnum;
import com.project.flightManagement.Model.NhanVien;
import com.project.flightManagement.Model.Quyen;
import jakarta.persistence.*;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.time.LocalDate;
import java.time.LocalDateTime;

@Data
@NoArgsConstructor
@AllArgsConstructor
//@JsonInclude(JsonInclude.Include.NON_NULL)
public class TaiKhoanDTO {
    private int idTaiKhoan;
    @NotBlank(message = "Tên đăng nhập không được bỏ trống!")
    private String tenDangNhap;

    @NotBlank(message = "Mật khẩu không được bỏ trống!")
    private String matKhau;

    @NotNull(message = "Quyền không được bỏ trống!")
    private QuyenBasicDTO quyen;

    private KhachHangBasicDTO khachHang;

    private NhanVienDTO nhanVien;
    @JsonFormat(shape = JsonFormat.Shape.STRING, pattern = "yyyy-MM-dd")
    private LocalDate thoiGianTao;
    private ActiveEnum trangThaiActive;
}
