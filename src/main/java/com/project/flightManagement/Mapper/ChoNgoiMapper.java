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
                choNgoi.getVe().getIdVe(),
                choNgoi.getRowIndex(),
                choNgoi.getColumnIndex(),
                choNgoi.getVe().getTrangThai()
        );
    }

    // Mapping from ChoNgoiDTO to ChoNgoi entity
    public static ChoNgoi toEntity(ChoNgoiDTO choNgoiDTO) {
        if (choNgoiDTO == null) {
            return null;
        }

        ChoNgoi choNgoi = new ChoNgoi();
        choNgoi.setIdChoNgoi(choNgoiDTO.getIdChoNgoi());
        choNgoi.setRowIndex(choNgoiDTO.getRow());
        choNgoi.setColumnIndex(choNgoiDTO.getColumn());
        choNgoi.getVe().setTrangThai(choNgoiDTO.getTrangThaiChoNgoi());

        return choNgoi;
    }
}
