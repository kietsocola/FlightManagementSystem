package com.project.flightManagement.Mapper;

import com.project.flightManagement.DTO.ChuyenBayDTO.ChuyenBayDTO;
import com.project.flightManagement.DTO.ChuyenBayDTO.ChuyenBay_VeDTO;
import com.project.flightManagement.Model.ChuyenBay;
import com.project.flightManagement.Model.TuyenBay;
import org.springframework.stereotype.Component;

@Component
public class ChuyenBayMapper {

    public static ChuyenBayDTO toDTO(ChuyenBay chuyenBay) {
        ChuyenBayDTO dto = new ChuyenBayDTO();
        dto.setIdChuyenBay(chuyenBay.getIdChuyenBay());
        dto.setTuyenBay(TuyenBayMapper.toDTO(chuyenBay.getTuyenBay()));
        dto.setMayBay(MayBayMapper.toDTO(chuyenBay.getMayBay()));
        dto.setCong(CongMapper.toDTO(chuyenBay.getCong()));
        dto.setThoiGianBatDauDuTinh(chuyenBay.getThoiGianBatDauDuTinh());
        dto.setThoiGianKetThucDuTinh(chuyenBay.getThoiGianKetThucDuTinh());
        dto.setIataChuyenBay(chuyenBay.getIataChuyenBay());
        dto.setIcaoChuyenBay(chuyenBay.getIcaoChuyenBay());
        dto.setThoiGianBatDauThucTe(chuyenBay.getThoiGianBatDauThucTe());
        dto.setThoiGianKetThucThucTe(chuyenBay.getThoiGianKetThucThucTe());
        dto.setDelay(chuyenBay.getDelay());
        dto.setNgayBay(chuyenBay.getNgayBay());
        dto.setSoGhe(chuyenBay.getSoGhe());
        dto.setTrangThai(chuyenBay.getTrangThai());
        dto.setTrangThaiActive(chuyenBay.getTrangThaiActive());

        return dto;
    }

    public static ChuyenBay toEntity(ChuyenBayDTO dto) {
        ChuyenBay chuyenBay = new ChuyenBay();
        chuyenBay.setIdChuyenBay(dto.getIdChuyenBay());
        chuyenBay.setTuyenBay(TuyenBayMapper.toEntity(dto.getTuyenBay()));
        chuyenBay.setMayBay(MayBayMapper.toEntity(dto.getMayBay()));
        chuyenBay.setCong(CongMapper.toEntity(dto.getCong()));
        chuyenBay.setThoiGianBatDauDuTinh(dto.getThoiGianBatDauDuTinh());
        chuyenBay.setThoiGianKetThucDuTinh(dto.getThoiGianKetThucDuTinh());
        chuyenBay.setIataChuyenBay(dto.getIataChuyenBay());
        chuyenBay.setIcaoChuyenBay(dto.getIcaoChuyenBay());
        chuyenBay.setThoiGianBatDauThucTe(dto.getThoiGianBatDauThucTe());
        chuyenBay.setThoiGianKetThucThucTe(dto.getThoiGianKetThucThucTe());
        chuyenBay.setDelay(dto.getDelay());
        chuyenBay.setNgayBay(dto.getNgayBay());
        chuyenBay.setSoGhe(dto.getSoGhe());
        chuyenBay.setTrangThai(dto.getTrangThai());
        chuyenBay.setTrangThaiActive(dto.getTrangThaiActive());

        return chuyenBay;
    }

    public ChuyenBay_VeDTO toChuyenBay_VeDTO (ChuyenBay chuyenBay) {
        ChuyenBay_VeDTO dto = new ChuyenBay_VeDTO();
        dto.setIdChuyenBay(chuyenBay.getIdChuyenBay());
        dto.setTuyenBay(TuyenBayMapper.toTuyenBay_VeDTO(chuyenBay.getTuyenBay()));
        dto.setMayBay(MayBayMapper.toMayBay_VeDTO(chuyenBay.getMayBay()));
        dto.setCong(CongMapper.toCong_VeDTO(chuyenBay.getCong()));
        dto.setThoiGianBatDauDuTinh(chuyenBay.getThoiGianBatDauDuTinh());
        dto.setThoiGianKetThucDuTinh(chuyenBay.getThoiGianKetThucDuTinh());
        dto.setIataChuyenBay(chuyenBay.getIataChuyenBay());
        dto.setIcaoChuyenBay(chuyenBay.getIcaoChuyenBay());
        dto.setThoiGianBatDauThucTe(chuyenBay.getThoiGianBatDauThucTe());
        dto.setThoiGianKetThucThucTe(chuyenBay.getThoiGianKetThucThucTe());
        dto.setDelay(chuyenBay.getDelay());
        dto.setNgayBay(chuyenBay.getNgayBay());
        dto.setTrangThai(chuyenBay.getTrangThai());
        dto.setTrangThaiActive(chuyenBay.getTrangThaiActive());
        return dto;
    }

}
