package com.project.flightManagement.DTO.HoaDonDTO;

import com.project.flightManagement.DTO.ChiTietHoaDonDTO.ChiTietHoaDonDTO;
import com.project.flightManagement.Enum.HoaDonEnum;
import com.project.flightManagement.Model.*;
import jakarta.validation.constraints.Min;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.time.LocalDateTime;
import java.util.List;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class HoaDonDTO {
    private int idHoaDon;

    private KhachHang khachHang;

    private NhanVien nhanVien;

    @Min(message = "Số lượng vé phải lớn hơn 0", value = 0)
    private int soLuongVe;

    private double tongTien;
    private LoaiHoaDon loaiHoaDon;
    private PhuongThucThanhToan phuongThucThanhToan;
//    private List<ChiTietHoaDonDTO> chiTietHoaDonList;
    private LocalDateTime thoiGianLap;
    private HoaDonEnum status;
    private boolean danhGia;
}
