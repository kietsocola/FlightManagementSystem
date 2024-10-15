package com.project.flightManagement.Mapper;

import com.project.flightManagement.DTO.SanBayDTO.SanBayDTO;
import com.project.flightManagement.Model.SanBay;

public class SanBayMapper {
    public static SanBay toEntity(SanBayDTO sanBayDTO) {
        SanBay sanBay = new SanBay();
        sanBay.setIdSanBay(sanBayDTO.getIdSanBay());
        sanBay.setTenSanBay(sanBayDTO.getTenSanBay());
        sanBay.setIataSanBay(sanBayDTO.getIataSanBay());
        sanBay.setIcaoSanBay(sanBayDTO.getIcaoSanBay());
        sanBay.setDiaChi(sanBayDTO.getDiaChi());
        sanBay.setThanhPho(sanBayDTO.getThanhPho());
        sanBay.setTrangThaiActive(sanBayDTO.getTrangThaiActive());
        return sanBay;
    }
    public static SanBayDTO toDTO(SanBay sanBay) {
        SanBayDTO sanBayDTO = new SanBayDTO();
        sanBayDTO.setIdSanBay(sanBay.getIdSanBay());
        sanBayDTO.setTenSanBay(sanBay.getTenSanBay());
        sanBayDTO.setIataSanBay(sanBay.getIataSanBay());
        sanBayDTO.setIcaoSanBay(sanBay.getIcaoSanBay());
        sanBayDTO.setDiaChi(sanBay.getDiaChi());
        sanBayDTO.setThanhPho(sanBay.getThanhPho());
        sanBayDTO.setTrangThaiActive(sanBay.getTrangThaiActive());
        return sanBayDTO;
    }
}