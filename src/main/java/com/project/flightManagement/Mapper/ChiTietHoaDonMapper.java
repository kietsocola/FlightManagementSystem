package com.project.flightManagement.Mapper;

import com.project.flightManagement.DTO.ChiTietHoaDonDTO.ChiTietHoaDonDTO;
import com.project.flightManagement.Model.ChiTietHoaDon;

public class ChiTietHoaDonMapper {
    public static ChiTietHoaDonDTO toDTO(ChiTietHoaDon chiTietHoaDon) {
        ChiTietHoaDonDTO chiTietHoaDonDTO = new ChiTietHoaDonDTO();

        chiTietHoaDonDTO.setIdChiTietHoaDon(chiTietHoaDon.getIdChiTietHoaDon());
        chiTietHoaDonDTO.setHoaDon(chiTietHoaDon.getHoaDon());
        chiTietHoaDonDTO.setHangHoa(chiTietHoaDon.getHangHoa());
        chiTietHoaDonDTO.setVe(chiTietHoaDon.getVe());
        chiTietHoaDonDTO.setSoTien(chiTietHoaDon.getSoTien());

        return chiTietHoaDonDTO;
    }

    public static ChiTietHoaDon toEntity(ChiTietHoaDonDTO chiTietHoaDonDTO) {
        ChiTietHoaDon chiTietHoaDon = new ChiTietHoaDon();

        chiTietHoaDon.setIdChiTietHoaDon(chiTietHoaDonDTO.getIdChiTietHoaDon());
        chiTietHoaDon.setHangHoa(chiTietHoaDonDTO.getHangHoa());
        chiTietHoaDon.setHoaDon(chiTietHoaDonDTO.getHoaDon());
        chiTietHoaDon.setSoTien(chiTietHoaDonDTO.getSoTien());
        chiTietHoaDon.setVe(chiTietHoaDonDTO.getVe());

        return chiTietHoaDon;
    }
}
