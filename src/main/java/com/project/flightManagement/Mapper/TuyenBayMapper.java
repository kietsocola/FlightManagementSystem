package com.project.flightManagement.Mapper;

import com.project.flightManagement.DTO.TuyenBayDTO.TuyenBayDTO;
import com.project.flightManagement.Model.TuyenBay;
import com.project.flightManagement.Model.SanBay;


public class TuyenBayMapper {

    public static TuyenBayDTO toDTO(TuyenBay tuyenBay) {
        TuyenBayDTO dto = new TuyenBayDTO();
        dto.setIdTuyenBay(tuyenBay.getIdTuyenBay());
        dto.setIdSanBayBatDau(tuyenBay.getSanBayBatDau().getIdSanBay());
        dto.setIdSanBayKetThuc(tuyenBay.getSanBayKetThuc().getIdSanBay());
        dto.setThoiGianChuyenBay(tuyenBay.getThoiGianChuyenBay());
        dto.setKhoangCach(tuyenBay.getKhoangCach());
        dto.setStatus(tuyenBay.getStatus());
        return dto;
    }

    public static TuyenBay toEntity(TuyenBayDTO dto) {
        TuyenBay tuyenBay = new TuyenBay();
        tuyenBay.setIdTuyenBay(dto.getIdTuyenBay());

        // Tạo các đối tượng SanBay để thiết lập mối quan hệ
        SanBay sanBayBatDau = new SanBay();
        sanBayBatDau.setIdSanBay(dto.getIdSanBayBatDau());

        SanBay sanBayKetThuc = new SanBay();
        sanBayKetThuc.setIdSanBay(dto.getIdSanBayKetThuc());

        tuyenBay.setSanBayBatDau(sanBayBatDau);
        tuyenBay.setSanBayKetThuc(sanBayKetThuc);
        tuyenBay.setThoiGianChuyenBay(dto.getThoiGianChuyenBay());
        tuyenBay.setKhoangCach(dto.getKhoangCach());

        tuyenBay.setStatus(dto.getStatus());
        return tuyenBay;
    }
}
