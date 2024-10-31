package com.project.flightManagement.Mapper;

import com.project.flightManagement.DTO.ChucVuDTO.ChucVuDTO;
import com.project.flightManagement.DTO.NhanVienDTO.NhanVienDTO;
import com.project.flightManagement.Model.ChucVu;

public class ChucVuMapper {

    public static ChucVuDTO toDTO(ChucVu chucVu){
        ChucVuDTO chucVuDTO = new ChucVuDTO();
        chucVuDTO.setIdChucVu(chucVu.getIdChucVu());
        chucVuDTO.setTen(chucVu.getTen());
        chucVuDTO.setTrangThaiActive(chucVu.getTrangThaiActive());
        chucVuDTO.setMoTa(chucVu.getMoTa());
        return chucVuDTO;
    }

    public static ChucVu toEntity(ChucVuDTO chucVuDTO){
        ChucVu chucVu = new ChucVu();
        chucVu.setIdChucVu(chucVuDTO.getIdChucVu());
        chucVu.setTen(chucVuDTO.getTen());
        chucVu.setTrangThaiActive(chucVuDTO.getTrangThaiActive());
        chucVu.setMoTa(chucVuDTO.getMoTa());
        return chucVu;
    }
}