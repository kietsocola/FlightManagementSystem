package com.project.flightManagement.Mapper;

import com.project.flightManagement.DTO.LoaiHangHoaDTO.LoaiHangHoaDTO;
import com.project.flightManagement.Model.LoaiHangHoa;

public class LoaiHangHoaMapper {

    public static LoaiHangHoaDTO toDTO(LoaiHangHoa loaiHangHoa) {
        return new LoaiHangHoaDTO(
                loaiHangHoa.getIdLoaiHangHoa(),
                loaiHangHoa.getTenLoaiHangHoa(),
                loaiHangHoa.getGioiHanKg(),
                loaiHangHoa.getGiaThemMoiKg(),
                loaiHangHoa.getTrangThaiActive()
        );
    }

    public static LoaiHangHoa toEntity(LoaiHangHoaDTO loaiHangHoaDTO) {
        return new LoaiHangHoa(
                loaiHangHoaDTO.getIdLoaiHangHoa(),
                loaiHangHoaDTO.getTenLoaiHangHoa(),
                loaiHangHoaDTO.getGioiHanKg(),
                loaiHangHoaDTO.getGiaThemMoiKg(),
                loaiHangHoaDTO.getTrangThaiActive()
        );
    }
}

