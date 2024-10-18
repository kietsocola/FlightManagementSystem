package com.project.flightManagement.Mapper;

import com.project.flightManagement.DTO.LoaiQuyDinhDTO.LoaiQuyDinhDTO;
import com.project.flightManagement.Model.LoaiQuyDinh;

public class LoaiQuyDinhMapper {

    // Chuyển từ Entity sang DTO
    public static LoaiQuyDinhDTO toDTO(LoaiQuyDinh loaiQuyDinh) {
        if (loaiQuyDinh == null) {
            return null;
        }

        LoaiQuyDinhDTO loaiQuyDinhDTO = new LoaiQuyDinhDTO();
        loaiQuyDinhDTO.setIdLoaiQuyDinh(loaiQuyDinh.getIdLoaiQuyDinh());
        loaiQuyDinhDTO.setTenLoaiQuyDinh(loaiQuyDinh.getTenLoaiQuyDinh());
        loaiQuyDinhDTO.setMoTaLoaiQuyDinh(loaiQuyDinh.getMoTaLoaiQuyDinh());
        loaiQuyDinhDTO.setTrangThaiActive(loaiQuyDinh.getTrangThaiActive());

        return loaiQuyDinhDTO;
    }

    // Chuyển từ DTO sang Entity
    public static LoaiQuyDinh toEntity(LoaiQuyDinhDTO loaiQuyDinhDTO) {
        if (loaiQuyDinhDTO == null) {
            return null;
        }
        LoaiQuyDinh loaiQuyDinh = new LoaiQuyDinh();
        loaiQuyDinh.setIdLoaiQuyDinh(loaiQuyDinhDTO.getIdLoaiQuyDinh());
        loaiQuyDinh.setTenLoaiQuyDinh(loaiQuyDinhDTO.getTenLoaiQuyDinh());
        loaiQuyDinh.setMoTaLoaiQuyDinh(loaiQuyDinhDTO.getMoTaLoaiQuyDinh());
        loaiQuyDinh.setTrangThaiActive(loaiQuyDinhDTO.getTrangThaiActive());

        return loaiQuyDinh;
    }
}

