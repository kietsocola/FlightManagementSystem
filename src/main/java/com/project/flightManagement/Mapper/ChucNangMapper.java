package com.project.flightManagement.Mapper;

import com.project.flightManagement.DTO.ChucNangDTO.ChucNangDTO;
import com.project.flightManagement.Model.ChucNang;

public class ChucNangMapper {
    public static ChucNangDTO chucNangDTO(ChucNang chucNang) {
        ChucNangDTO chucNangDTO = new ChucNangDTO();
        chucNangDTO.setIdChucNang(chucNang.getIdChucNang());
        chucNangDTO.setTenChucNang(chucNang.getTenChucNang());
        return chucNangDTO;
    }
}

