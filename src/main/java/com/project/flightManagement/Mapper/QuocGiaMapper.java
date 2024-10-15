package com.project.flightManagement.Mapper;

import com.project.flightManagement.DTO.QuocGiaDTO.QuocGiaDTO;
import com.project.flightManagement.Model.QuocGia;

public class QuocGiaMapper {
    public static QuocGia toEntity(QuocGiaDTO quocGiaDTO) {
        QuocGia quocGia = new QuocGia();
        quocGia.setIdQuocGia(quocGiaDTO.getIdQuocGia());
        quocGia.setTenQuocGia(quocGiaDTO.getTenQuocGia());
        quocGia.setTrangThaiActive(quocGiaDTO.getTrangThaiActive());
        return quocGia;
    }
    public static QuocGiaDTO toDTO(QuocGia quocGia) {
        QuocGiaDTO quocGiaDTO = new QuocGiaDTO();
        quocGiaDTO.setIdQuocGia(quocGia.getIdQuocGia());
        quocGiaDTO.setTenQuocGia(quocGia.getTenQuocGia());
        quocGiaDTO.setTrangThaiActive(quocGia.getTrangThaiActive());
        return quocGiaDTO;
    }
}