package com.project.flightManagement.Mapper;

import com.project.flightManagement.DTO.NhanVienDTO.NhanVienDTO;
import com.project.flightManagement.Model.NhanVien;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;


public class NhanVienMapper {
    public static NhanVienDTO toDTO(NhanVien nv){
        return new NhanVienDTO(nv.getIdNhanVien(), nv.getHoTen(),nv.getNgaySinh(),nv.getEmail(),nv.getSoDienThoai(),nv.getCccd(),nv.getGioiTinhEnum(),nv.getTrangThaiActive(),nv.getChucVu(),nv.getChuyenBay());
    }

    public static NhanVien toEntity(NhanVienDTO nvDTO){

        NhanVien nv = new NhanVien();
        nv.setIdNhanVien(nvDTO.getIdNhanVien());
        nv.setHoTen(nvDTO.getHoTen());
        nv.setNgaySinh(nvDTO.getNgaySinh());
        nv.setEmail(nvDTO.getEmail());
        nv.setCccd(nvDTO.getCccd());
        nv.setSoDienThoai(nvDTO.getSoDienThoai());
        nv.setGioiTinhEnum(nvDTO.getGioiTinhEnum());
        nv.setTrangThaiActive(nvDTO.getTrangThaiActive());
        nv.setChucVu(nvDTO.getChucVu());
        nv.setChuyenBay(nvDTO.getChuyenBay());
        return nv;
    }
}
