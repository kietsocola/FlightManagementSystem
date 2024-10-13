package com.project.flightManagement.Mapper;

import com.project.flightManagement.DTO.HangBayDTO.HangBayDTO;
import com.project.flightManagement.Model.HangBay;
import com.project.flightManagement.Repository.HangBayRepository;

public class HangBayMapper {
    public static HangBayDTO toDTO(HangBay hangBay) {
        HangBayDTO hangBayDTO = new HangBayDTO();
        hangBayDTO.setIdHangBay(hangBay.getIdHangBay());
        hangBayDTO.setTenHangBay(hangBay.getTenHangBay());
        hangBayDTO.setIataHangBay(hangBay.getIataHangBay());
        hangBayDTO.setIcaoHangBay(hangBay.getIcaoHangBay());
        hangBayDTO.setTrangThaiActive(hangBay.getTrangThaiActive());
        return hangBayDTO;
    }
    public static HangBay toEntity(HangBayDTO hangBayDTO) {
        HangBay hangBay = new HangBay();
        hangBay.setIdHangBay(hangBayDTO.getIdHangBay());
        hangBay.setTenHangBay(hangBayDTO.getTenHangBay());
        hangBay.setTrangThaiActive(hangBayDTO.getTrangThaiActive());
        hangBay.setIataHangBay(hangBayDTO.getIataHangBay());
        hangBay.setIcaoHangBay(hangBayDTO.getIcaoHangBay());
        return hangBay;
    }
}
