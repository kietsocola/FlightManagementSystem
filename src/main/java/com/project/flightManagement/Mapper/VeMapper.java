package com.project.flightManagement.Mapper;

import com.project.flightManagement.DTO.VeDTO.VeDTO;
import com.project.flightManagement.Model.Ve;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

@Component
public class VeMapper {
    @Autowired
    private LoaiVeMapper loaiVeMapper;
    public VeDTO toDto(Ve ve) {
        if (ve == null) {
            return null;
        }
        VeDTO veDTO = new VeDTO();
        veDTO.setIdVe(ve.getIdVe());
        veDTO.setMaVe(ve.getMaVe());
        veDTO.setIdChuyenBay(ve.getChuyenBay().getIdChuyenBay());
        veDTO.setGiaVe(ve.getGiaVe());
        veDTO.setIdChoNgoi(ve.getChoNgoi().getIdChoNgoi());
        if(ve.getHanhKhach() != null) {
            veDTO.setIdHanhKhach(ve.getHanhKhach().getIdHanhKhach());
        }
        veDTO.setLoaiVe(loaiVeMapper.toDto(ve.getLoaiVe()));
        veDTO.setTrangThaiActive(ve.getTrangThaiActive());
        veDTO.setTrangThai(ve.getTrangThai());

        return veDTO;
    }
    // Chuyển đổi từ VeDTO sang Ve entity
    public Ve toEntity(VeDTO veDTO) {
        if (veDTO == null) {
            return null;
        }

        Ve ve = new Ve();
        ve.setIdVe(veDTO.getIdVe());
        ve.setMaVe(veDTO.getMaVe());
//        ve.setChuyenBay(veDTO.getChuyenBay());
        ve.setGiaVe(veDTO.getGiaVe());
//        ve.setChoNgoi(veDTO.getChoNgoi());
//        ve.setHanhKhach(veDTO.getHanhKhach());
        ve.setLoaiVe(loaiVeMapper.toEntity(veDTO.getLoaiVe()));
        ve.setTrangThaiActive(veDTO.getTrangThaiActive());
        ve.setTrangThai(veDTO.getTrangThai());

        return ve;
    }

}
