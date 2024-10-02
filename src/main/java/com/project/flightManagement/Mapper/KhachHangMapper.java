package com.project.flightManagement.Mapper;

import com.project.flightManagement.DTO.KhachHangDTO.KhachHangDTO;
import com.project.flightManagement.Model.KhachHang;

public class KhachHangMapper {
    public static KhachHangDTO toDTO(KhachHang kh){
        KhachHangDTO khDTO = new KhachHangDTO();
        khDTO.setIdKhachHang(kh.getIdKhachHang());
        khDTO.setHoTen(kh.getHoTen());
        khDTO.setNgaySinh(kh.getNgaySinh());
        khDTO.setSoDienThoai(kh.getSoDienThoai());
        khDTO.setCccd(kh.getCccd());
        khDTO.setGioiTinhEnum(kh.getGioiTinhEnum());
        khDTO.setTrangThaiActive(kh.getTrangThaiActive());
        khDTO.setEmail(kh.getEmail());
        return khDTO;
    }
    public static KhachHang toEntity(KhachHangDTO khDTO) {
        KhachHang kh = new KhachHang();
        kh.setIdKhachHang(khDTO.getIdKhachHang());
        kh.setHoTen(khDTO.getHoTen());
        kh.setNgaySinh(khDTO.getNgaySinh());
        kh.setSoDienThoai(khDTO.getSoDienThoai());
        kh.setCccd(khDTO.getCccd());
        kh.setGioiTinhEnum(khDTO.getGioiTinhEnum());
        kh.setTrangThaiActive(khDTO.getTrangThaiActive());
        kh.setEmail(khDTO.getEmail());
        return kh;
    }
}
