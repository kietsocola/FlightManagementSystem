package com.project.flightManagement.Mapper;

import com.project.flightManagement.DTO.HangVeDTO.HangVeDTO;
import com.project.flightManagement.Model.HangVe;

public class HangVeMapper {
    public static HangVeDTO toHangVeDTO(HangVe hangVe) {
        HangVeDTO hangVeDTO = new HangVeDTO();
        hangVeDTO.setIdHangVe(hangVe.getIdHangVe());
        hangVeDTO.setTenHangVe(hangVe.getTenHangVe());
        hangVeDTO.setMoTa(hangVe.getMoTa());
        hangVeDTO.setTrangThaiActive(hangVe.getTrangThaiActive());
        return hangVeDTO;
    }
}
