package com.project.flightManagement.Mapper;

import com.project.flightManagement.DTO.HangVeDTO.HangVeDTO;
import com.project.flightManagement.Model.HangVe;

public class HangVeMapper {
    public static HangVe toEntity(HangVeDTO hangVeDTO) {
        HangVe hv = new HangVe();
        hv.setIdHangVe(hangVeDTO.getIdHangVe());
        hv.setMoTa(hangVeDTO.getMoTa());
        hv.setTenHangVe(hangVeDTO.getTenHangVe());
        hv.setTrangThaiActive(hangVeDTO.getTrangThaiActive());
        return hv;
    }
    public static HangVeDTO toDTO(HangVe hangVe) {
        HangVeDTO hvdto = new HangVeDTO();
        hvdto.setIdHangVe(hangVe.getIdHangVe());
        hvdto.setMoTa(hangVe.getMoTa());
        hvdto.setTenHangVe(hangVe.getTenHangVe());
        hvdto.setTrangThaiActive(hangVe.getTrangThaiActive());
        return hvdto;
    }
}
