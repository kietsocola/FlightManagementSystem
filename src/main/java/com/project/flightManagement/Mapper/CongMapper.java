package com.project.flightManagement.Mapper;

import com.project.flightManagement.DTO.CongDTO.CongDTO;
import com.project.flightManagement.DTO.CongDTO.Cong_VeDTO;
import com.project.flightManagement.Model.Cong;
import com.project.flightManagement.Model.SanBay;

public class CongMapper {

    // Chuyển từ DTO sang Entity
    public static Cong toEntity(CongDTO congDTO) {
        Cong cong = new Cong();
        cong.setIdCong(congDTO.getIdCong());
        cong.setTenCong(congDTO.getTenCong());
        cong.setTrangThaiActive(congDTO.getTrangThaiActive());
        cong.setSanBay(congDTO.getSanBay());

        return cong;
    }

    // Chuyển từ Entity sang DTO
    public static CongDTO toDTO(Cong cong) {
        CongDTO congDTO = new CongDTO();
        congDTO.setIdCong(cong.getIdCong());
        congDTO.setTenCong(cong.getTenCong());
        congDTO.setTrangThaiActive(cong.getTrangThaiActive());
        congDTO.setSanBay(cong.getSanBay());

        return congDTO;
    }

    public static Cong_VeDTO toCong_VeDTO(Cong cong) {
        Cong_VeDTO congDTO = new Cong_VeDTO();
        congDTO.setIdCong(cong.getIdCong());
        congDTO.setTenCong(cong.getTenCong());
        congDTO.setTrangThaiActive(cong.getTrangThaiActive());
        return congDTO;
    }
}
