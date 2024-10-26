package com.project.flightManagement.Mapper;

import com.project.flightManagement.DTO.TuyenBayDTO.TuyenBayDTO;
import com.project.flightManagement.DTO.TuyenBayDTO.TuyenBay_VeDTO;
import com.project.flightManagement.Model.TuyenBay;
import com.project.flightManagement.Model.SanBay;

import java.time.LocalTime;


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

    public static TuyenBay_VeDTO toTuyenBay_VeDTO(TuyenBay tb) {
        TuyenBay_VeDTO tbDTO = new TuyenBay_VeDTO();

        tbDTO.setIdTuyenBay(tb.getIdTuyenBay());
        tbDTO.setSanBayBatDau(SanBayMapper.toSanBay_veDTO(tb.getSanBayBatDau()));
        tbDTO.setSanBayKetThuc(SanBayMapper.toSanBay_veDTO(tb.getSanBayKetThuc()));

//có sửa qua tay LOc
        tbDTO.setThoiGianChuyenBay(LocalTime.ofSecondOfDay(tb.getThoiGianChuyenBay()));


        tbDTO.setKhoangCach(tb.getKhoangCach());
        tbDTO.setStatus(tb.getStatus());

        return tbDTO;
    }
}



