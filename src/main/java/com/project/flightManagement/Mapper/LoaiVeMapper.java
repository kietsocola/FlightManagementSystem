package com.project.flightManagement.Mapper;

import com.project.flightManagement.DTO.LoaiVeDTO.LoaiVeDTO;
import com.project.flightManagement.Model.LoaiVe;
import org.springframework.stereotype.Component;

@Component
public class LoaiVeMapper {
    public LoaiVeDTO toDto(LoaiVe loaiVe) {
        if (loaiVe == null) {
            return null;
        }

        LoaiVeDTO loaiVeDto = new LoaiVeDTO();
        loaiVeDto.setIdLoaiVe(loaiVe.getIdLoaiVe());
        loaiVeDto.setTenLoaiVe(loaiVe.getTenLoaiVe());
        loaiVeDto.setMoTa(loaiVe.getMoTa());
        loaiVeDto.setTrangThaiActive(loaiVe.getTrangThaiActive());

        return loaiVeDto;
    }

    public LoaiVe toEntity(LoaiVeDTO loaiVeDto) {
        if (loaiVeDto == null) {
            return null;
        }

        LoaiVe loaiVe = new LoaiVe();
        loaiVe.setIdLoaiVe(loaiVeDto.getIdLoaiVe());
        loaiVe.setTenLoaiVe(loaiVeDto.getTenLoaiVe());
        loaiVe.setMoTa(loaiVeDto.getMoTa());
        loaiVe.setTrangThaiActive(loaiVeDto.getTrangThaiActive());
        return loaiVe;
    }

}
