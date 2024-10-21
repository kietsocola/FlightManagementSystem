package com.project.flightManagement.DTO.TaiKhoanDTO;

import com.fasterxml.jackson.annotation.JsonInclude;
import com.project.flightManagement.DTO.KhachHangDTO.KhachHangBasicDTO;
import com.project.flightManagement.DTO.NhanVienDTO.NhanVienDTO;
import com.project.flightManagement.DTO.QuyenDTO.QuyenBasicDTO;
import com.project.flightManagement.Enum.ActiveEnum;
import com.project.flightManagement.Model.NhanVien;
import com.project.flightManagement.Model.Quyen;
import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.time.LocalDateTime;

@Data
@NoArgsConstructor
@AllArgsConstructor
//@JsonInclude(JsonInclude.Include.NON_NULL)
public class TaiKhoanDTO {
    private int idTaiKhoan;
    private String tenDangNhap;
    private String matKhau;
    private QuyenBasicDTO quyen;
    private KhachHangBasicDTO khachHang;
    private NhanVienDTO nhanVien;
    private LocalDateTime thoiGianTao;
    private ActiveEnum trangThaiActive;
}
