package com.project.flightManagement.Mapper;

import com.project.flightManagement.DTO.HangHoaDTO.HangHoaDTO;
import com.project.flightManagement.Model.HangHoa;
import com.project.flightManagement.Model.LoaiHangHoa;
import org.springframework.stereotype.Component;

@Component
public class HangHoaMapper {

    public static HangHoaDTO toDTO(HangHoa hangHoa) {
        HangHoaDTO dto = new HangHoaDTO();
        dto.setIdHangHoa(hangHoa.getIdHangHoa());
        dto.setIdLoaiHangHoa(hangHoa.getLoaiHangHoa().getIdLoaiHangHoa()); // Map idLoaiHangHoa
        dto.setTenHangHoa(hangHoa.getTenHangHoa());
        dto.setTaiTrong(hangHoa.getTaiTrong());
        dto.setGiaPhatSinh(hangHoa.getGiaPhatSinh());
        dto.setTrangThaiActive(hangHoa.getTrangThaiActive());
        return dto;
    }

    public static HangHoa toEntity(HangHoaDTO dto) {
        HangHoa hangHoa = new HangHoa();
        hangHoa.setIdHangHoa(dto.getIdHangHoa());

        LoaiHangHoa loaiHangHoa=new LoaiHangHoa();
        loaiHangHoa.setIdLoaiHangHoa(dto.getIdLoaiHangHoa());
        hangHoa.setLoaiHangHoa(loaiHangHoa);

        hangHoa.setTenHangHoa(dto.getTenHangHoa());
        hangHoa.setTaiTrong(dto.getTaiTrong());
        hangHoa.setGiaPhatSinh(dto.getGiaPhatSinh());
        hangHoa.setTrangThaiActive(dto.getTrangThaiActive());
        return hangHoa;
    }
}
