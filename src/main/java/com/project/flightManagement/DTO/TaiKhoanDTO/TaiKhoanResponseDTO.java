package com.project.flightManagement.DTO.TaiKhoanDTO;

import com.project.flightManagement.DTO.KhachHangDTO.KhachHangBasicDTO;
import com.project.flightManagement.DTO.NhanVienDTO.NhanVienDTO;
import com.project.flightManagement.DTO.QuyenDTO.QuyenBasicDTO;
import com.project.flightManagement.DTO.QuyenDTO.QuyenResponseDTO;
import com.project.flightManagement.Enum.ActiveEnum;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.time.LocalDate;
import java.time.LocalDateTime;
@Data
@NoArgsConstructor
@AllArgsConstructor
public class TaiKhoanResponseDTO {
    private int idTaiKhoan;
    private String tenDangNhap;
    private QuyenResponseDTO quyen;
    private KhachHangBasicDTO khachHang;
    private NhanVienDTO nhanVien;
    private LocalDate thoiGianTao;
    private ActiveEnum trangThaiActive;
}
