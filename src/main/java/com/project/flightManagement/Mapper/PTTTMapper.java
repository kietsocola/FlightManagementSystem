package com.project.flightManagement.Mapper;

import com.project.flightManagement.DTO.PTTTDTO.PTTTDTO;
import com.project.flightManagement.Model.PhuongThucThanhToan;

public class PTTTMapper {
    public static PTTTDTO toDTO(PhuongThucThanhToan pttt) {
        PTTTDTO ptttDTO = new PTTTDTO();
        ptttDTO.setIdPTTT(pttt.getIdPhuongThucTT());
        ptttDTO.setTenPTTT(pttt.getTenPhuongThucTT());
        ptttDTO.setMoTa(pttt.getMoTa());
        ptttDTO.setStatus(pttt.getTrangThaiActive());

        return ptttDTO;
    }

    public static PhuongThucThanhToan toEntity(PTTTDTO ptttDTO) {
        PhuongThucThanhToan pttt = new PhuongThucThanhToan();
        pttt.setIdPhuongThucTT(ptttDTO.getIdPTTT());
        pttt.setTenPhuongThucTT(ptttDTO.getTenPTTT());
        pttt.setMoTa(ptttDTO.getMoTa());
        pttt.setTrangThaiActive(ptttDTO.getStatus());

        return pttt;
    }

}
