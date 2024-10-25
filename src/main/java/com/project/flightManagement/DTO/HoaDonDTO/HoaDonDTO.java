package com.project.flightManagement.DTO.HoaDonDTO;

import com.project.flightManagement.Enum.HoaDonEnum;
import com.project.flightManagement.Model.*;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.time.LocalDateTime;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class HoaDonDTO {
    private int idHoaDon;
    private KhachHang khachHang;
    private NhanVien nhanVien;
    private int soLuongVe;
    private double tongTien;
    private LoaiHoaDon loaiHoaDon;
    private PhuongThucThanhToan phuongThucThanhToan;
    private LocalDateTime thoiGianLap;
    private HoaDonEnum status;
}
