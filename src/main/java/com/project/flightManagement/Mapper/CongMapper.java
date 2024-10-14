package com.project.flightManagement.Mapper;

import com.project.flightManagement.DTO.CongDTO.CongDTO;
import com.project.flightManagement.Model.Cong;
import com.project.flightManagement.Model.SanBay;

public class CongMapper {

    // Chuyển từ DTO sang Entity
    public static Cong toEntity(CongDTO congDTO, SanBay sanBay) {
        Cong cong = new Cong();
        cong.setIdCong(congDTO.getIdCong());
        cong.setTenCong(congDTO.getTenCong());
        cong.setTrangThaiActive(congDTO.getTrangThaiActive());
        cong.setSanBay(sanBay);

        return cong;
    }

    // Chuyển từ Entity sang DTO
    public static CongDTO toDTO(Cong cong) {
        CongDTO congDTO = new CongDTO();
        congDTO.setIdCong(cong.getIdCong());
        congDTO.setTenCong(cong.getTenCong());
        congDTO.setTrangThaiActive(cong.getTrangThaiActive());
        congDTO.setSanBayId(cong.getSanBay().getIdSanBay());

        return congDTO;
    }
}
