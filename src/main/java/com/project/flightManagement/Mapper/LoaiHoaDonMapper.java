package com.project.flightManagement.Mapper;

import com.project.flightManagement.DTO.LoaiHoaDonDTO.LoaiHoaDonDTO;
import com.project.flightManagement.Model.LoaiHoaDon;

public class LoaiHoaDonMapper {
    public static LoaiHoaDonDTO toDTO(LoaiHoaDon loaiHD) {
        LoaiHoaDonDTO loaiHoaDonDTO = new LoaiHoaDonDTO();
        loaiHoaDonDTO.setIdLoaiHD(loaiHD.getIdLoaiHoaDon());
        loaiHoaDonDTO.setMoTa(loaiHD.getMoTaLoaiHoaDon());
        loaiHoaDonDTO.setTenLoaiHD(loaiHD.getTenLoaiHoaDon());
        loaiHoaDonDTO.setStatus(loaiHD.getTrangThaiActive());
        return loaiHoaDonDTO;
    }

    public static LoaiHoaDon toEntity(LoaiHoaDonDTO loaiHoaDonDTO) {
        LoaiHoaDon loaiHoaDon = new LoaiHoaDon();
        loaiHoaDon.setIdLoaiHoaDon(loaiHoaDonDTO.getIdLoaiHD());
        loaiHoaDon.setMoTaLoaiHoaDon(loaiHoaDonDTO.getMoTa());
        loaiHoaDon.setTenLoaiHoaDon(loaiHoaDonDTO.getTenLoaiHD());
        loaiHoaDon.setTrangThaiActive(loaiHoaDonDTO.getStatus());
        return loaiHoaDon;
    }
}
