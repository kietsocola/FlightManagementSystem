package com.project.flightManagement.Mapper;

import com.project.flightManagement.DTO.ThanhPhoDTO.ThanhPhoDTO;
import com.project.flightManagement.Model.ThanhPho;

public class ThanhPhoMapper {
    public static ThanhPho toEntity(ThanhPhoDTO dto) {
        ThanhPho entity = new ThanhPho();
        entity.setIdThanhPho(dto.getIdThanhPho());
        entity.setTenThanhPho(dto.getTenThanhPho());
        entity.setQuocGia(dto.getQuocGia());
        entity.setTrangThaiActive(dto.getTrangThaiActive());
        return entity;
    }
    public static ThanhPhoDTO toDTO(ThanhPho entity) {
        ThanhPhoDTO dto = new ThanhPhoDTO();
        dto.setIdThanhPho(entity.getIdThanhPho());
        dto.setTenThanhPho(entity.getTenThanhPho());
        dto.setQuocGia(entity.getQuocGia());
        dto.setTrangThaiActive(entity.getTrangThaiActive());
        return dto;
    }
}
