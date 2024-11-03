package com.project.flightManagement.Mapper;

import com.project.flightManagement.DTO.ChoNgoiDTO.ChoNgoiDTO;
import com.project.flightManagement.DTO.ChoNgoiDTO.ChoNgoi_VeDTO;
import com.project.flightManagement.Model.ChoNgoi;
import com.project.flightManagement.Model.Ve;

public class ChoNgoiMapper {
    public static ChoNgoiDTO toDTO(ChoNgoi choNgoi) {
        ChoNgoiDTO choNgoiDTO = new ChoNgoiDTO();
        choNgoiDTO.setIdChoNgoi(choNgoi.getIdChoNgoi());
        choNgoiDTO.setMayBay(choNgoi.getMayBay());
        choNgoiDTO.setHangVe(choNgoi.getHangVe());
        choNgoiDTO.setRowIndex(choNgoi.getRowIndex());
        choNgoiDTO.setColumnIndex(choNgoi.getColumnIndex());
        choNgoiDTO.setTrangThaiActive(choNgoi.getTrangThaiActive());
        return choNgoiDTO;
    }
    public static ChoNgoi_VeDTO toDTO(Ve ve) {
        ChoNgoi_VeDTO choNgoiDTO = new ChoNgoi_VeDTO();
        choNgoiDTO.setIdChoNgoi(ve.getChoNgoi().getIdChoNgoi());
        choNgoiDTO.setMayBay(ve.getChoNgoi().getMayBay());
        choNgoiDTO.setHangVe(ve.getChoNgoi().getHangVe());
        choNgoiDTO.setRowIndex(ve.getChoNgoi().getRowIndex());
        choNgoiDTO.setColumnIndex(ve.getChoNgoi().getColumnIndex());
        choNgoiDTO.setTrangThaiActive(ve.getChoNgoi().getTrangThaiActive());
        choNgoiDTO.setIdVe(ve.getIdVe());
        return choNgoiDTO;
    }
    public static ChoNgoi toEntity(ChoNgoiDTO choNgoiDTO) {
        ChoNgoi choNgoi = new ChoNgoi();
        choNgoi.setIdChoNgoi(choNgoiDTO.getIdChoNgoi());
        choNgoi.setMayBay(choNgoiDTO.getMayBay());
        choNgoi.setHangVe(choNgoiDTO.getHangVe());
        choNgoi.setRowIndex(choNgoiDTO.getRowIndex());
        choNgoi.setColumnIndex(choNgoiDTO.getColumnIndex());
        choNgoi.setTrangThaiActive(choNgoiDTO.getTrangThaiActive());
        return choNgoi;
    }
}
