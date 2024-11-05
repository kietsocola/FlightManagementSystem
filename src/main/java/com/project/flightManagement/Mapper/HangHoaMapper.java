package com.project.flightManagement.Mapper;

import com.project.flightManagement.DTO.HangHoaDTO.HangHoaDTO;
import com.project.flightManagement.Model.HangHoa;
import com.project.flightManagement.Model.LoaiHangHoa;
import org.springframework.stereotype.Component;

@Component

public class HangHoaMapper {

    public static HangHoaDTO toDTO(HangHoa hangHoa) {
        if (hangHoa == null) {
            return null;
        }

        HangHoaDTO dto = new HangHoaDTO();
        dto.setIdHangHoa(hangHoa.getIdHangHoa());
        dto.setMaHangHoa(hangHoa.getMaHangHoa());
        dto.setTenHangHoa(hangHoa.getTenHangHoa());
        dto.setTaiTrong(hangHoa.getTaiTrong());
        dto.setGiaPhatSinh(hangHoa.getGiaPhatSinh());
        dto.setIdLoaiHangHoa(hangHoa.getLoaiHangHoa().getIdLoaiHangHoa());
        dto.setTrangThaiActive(hangHoa.getTrangThaiActive());
        return dto;
    }

    public static HangHoa toEntity(HangHoaDTO dto) {
        if (dto == null) {
            return null;
        }

        HangHoa hangHoa = new HangHoa();
        hangHoa.setIdHangHoa(dto.getIdHangHoa());
        hangHoa.setMaHangHoa(dto.getMaHangHoa());
        hangHoa.setTenHangHoa(dto.getTenHangHoa());
        hangHoa.setTaiTrong(dto.getTaiTrong());
        hangHoa.setGiaPhatSinh(dto.getGiaPhatSinh());
        hangHoa.setTrangThaiActive(dto.getTrangThaiActive());
        return hangHoa;
    }
}
