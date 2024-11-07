package com.project.flightManagement.Mapper;

import com.project.flightManagement.DTO.DanhGiaDTO.DanhGiaDTO;
import com.project.flightManagement.Model.DanhGia;

public class DanhGiaMapper {
    public static DanhGia toEntity(DanhGiaDTO danhGiaDTO) {
        DanhGia danhGia = new DanhGia();
        danhGia.setIdDanhGia(danhGiaDTO.getIdDanhGia());
        danhGia.setKhachHang(danhGiaDTO.getKhachHang());
        danhGia.setSao(danhGiaDTO.getSao());
        danhGia.setHangBay(danhGiaDTO.getHangBay());
        danhGia.setNoiDung(danhGiaDTO.getNoiDung());
        danhGia.setThoiGianTao(danhGiaDTO.getThoiGianTao());
        danhGia.setTrangThaiActive(danhGiaDTO.getTrangThaiActive());
        return danhGia;
    }
    public static DanhGiaDTO toDTO(DanhGia danhGia) {
        DanhGiaDTO danhGiaDTO = new DanhGiaDTO();
        danhGiaDTO.setIdDanhGia(danhGia.getIdDanhGia());
        danhGiaDTO.setHangBay(danhGia.getHangBay());
        danhGiaDTO.setSao(danhGia.getSao());
        danhGiaDTO.setNoiDung(danhGia.getNoiDung());
        danhGiaDTO.setKhachHang(danhGia.getKhachHang());
        danhGiaDTO.setThoiGianTao(danhGia.getThoiGianTao());
        danhGiaDTO.setTrangThaiActive(danhGia.getTrangThaiActive());
        return danhGiaDTO;
    }
}
