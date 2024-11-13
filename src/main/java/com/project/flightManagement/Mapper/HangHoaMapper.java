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
        dto.setIdLoaiHangHoa(hangHoa.getLoaiHangHoa().getIdLoaiHangHoa());
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
        LoaiHangHoa loaiHangHoa=new LoaiHangHoa();
        loaiHangHoa.setIdLoaiHangHoa(dto.getIdLoaiHangHoa());
        hangHoa.setLoaiHangHoa(loaiHangHoa);
        hangHoa.setMaHangHoa(dto.getMaHangHoa());
        hangHoa.setTenHangHoa(dto.getTenHangHoa());
        hangHoa.setTaiTrong(dto.getTaiTrong());
        hangHoa.setGiaPhatSinh(dto.getGiaPhatSinh());
        hangHoa.setTrangThaiActive(dto.getTrangThaiActive());
        return hangHoa;
    }

    public static HangHoa toEntity(HangHoaDTO dto, HangHoa existingHangHoa) {
        if (dto == null || existingHangHoa == null) {
            return null;
        }

        existingHangHoa.setTenHangHoa(dto.getTenHangHoa());
        existingHangHoa.setTaiTrong(dto.getTaiTrong());
        existingHangHoa.setGiaPhatSinh(dto.getGiaPhatSinh());
        existingHangHoa.setTrangThaiActive(dto.getTrangThaiActive());

        // Thiết lập loại hàng hóa
        LoaiHangHoa loaiHangHoa = new LoaiHangHoa();
        loaiHangHoa.setIdLoaiHangHoa(dto.getIdLoaiHangHoa());
        existingHangHoa.setLoaiHangHoa(loaiHangHoa);

        return existingHangHoa;
    }
}
