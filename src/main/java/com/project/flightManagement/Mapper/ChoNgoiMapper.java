package com.project.flightManagement.Mapper;

import com.project.flightManagement.DTO.ChoNgoiDTO.ChoNgoiDTO;
import com.project.flightManagement.Model.ChoNgoi;

public class ChoNgoiMapper {
    public static ChoNgoiDTO toDTO(ChoNgoi choNgoi) {
        if (choNgoi == null) {
            return null;
        }

        return new ChoNgoiDTO(
                choNgoi.getIdChoNgoi(),
                choNgoi.getHangVe(),
                choNgoi.getMayBay(),
                choNgoi.getRowIndex(),
                choNgoi.getColumnIndex(),
                choNgoi.getTrangThaiActive()
        );
    }

    // Mapping from ChoNgoiDTO to ChoNgoi entity
    public static ChoNgoi toEntity(ChoNgoiDTO choNgoiDTO) {
        if (choNgoiDTO == null) {
            return null;
        }

        ChoNgoi choNgoi = new ChoNgoi();
        choNgoi.setIdChoNgoi(choNgoiDTO.getIdChoNgoi());
        choNgoi.setHangVe(choNgoiDTO.getHangVe());
        choNgoi.setMayBay(choNgoiDTO.getMayBay());
        choNgoi.setRowIndex(choNgoiDTO.getRowIndex());
        choNgoi.setColumnIndex(choNgoiDTO.getColumnIndex());
        choNgoi.setTrangThaiActive(choNgoiDTO.getTrangThaiActive());
        return choNgoi;
    }
}
