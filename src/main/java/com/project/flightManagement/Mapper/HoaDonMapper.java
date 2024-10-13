package com.project.flightManagement.Mapper;

import com.project.flightManagement.DTO.HoaDonDTO.HoaDonDTO;
import com.project.flightManagement.Model.HoaDon;

public class HoaDonMapper {
    public static HoaDonDTO toDTO(HoaDon hoaDon) {
        HoaDonDTO hdDTO = new HoaDonDTO();
        hdDTO.setIdHoaDon(hoaDon.getIdHoaDon());
        hdDTO.setNhanVien(hoaDon.getNhanVien());
        hdDTO.setKhachHang(hoaDon.getKhachHang());
        hdDTO.setLoaiHoaDon(hoaDon.getLoaiHoaDon());
        hdDTO.setSoLuongVe(hoaDon.getSoLuongVe());
        hdDTO.setTongTien(hoaDon.getTongTien());
        hdDTO.setStatus(hoaDon.getTrangThaiActive());
        hdDTO.setThoiGianLap(hoaDon.getThoiGianLap());
        hdDTO.setPhuongThucThanhToan(hoaDon.getPhuongThucTT());

        return hdDTO;
    }

    public static HoaDon toEntity(HoaDonDTO hdDTO) {
        HoaDon hd = new HoaDon();
        hd.setIdHoaDon(hdDTO.getIdHoaDon());
        hd.setKhachHang(hdDTO.getKhachHang());
        hd.setNhanVien(hdDTO.getNhanVien());
        hd.setLoaiHoaDon(hdDTO.getLoaiHoaDon());
        hd.setSoLuongVe(hdDTO.getSoLuongVe());
        hd.setTongTien(hdDTO.getTongTien());
        hd.setThoiGianLap(hdDTO.getThoiGianLap());
        hd.setPhuongThucTT(hdDTO.getPhuongThucThanhToan());
        hd.setTrangThaiActive(hdDTO.getStatus());

        return hd;
    }
}
