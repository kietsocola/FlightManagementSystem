package com.project.flightManagement.Mapper;

import com.project.flightManagement.DTO.ChoNgoiDTO.ChoNgoiDTO;
import com.project.flightManagement.Model.ChoNgoi;
import com.project.flightManagement.Model.HangVe;
import com.project.flightManagement.Model.MayBay;

public class ChoNgoiMapper {

    // Chuyển từ ChoNgoi entity sang ChoNgoiDTO
    public static ChoNgoiDTO toDTO(ChoNgoi choNgoi) {
        if (choNgoi == null) {
            return null;
        }

        return new ChoNgoiDTO(
                choNgoi.getIdChoNgoi(),
                choNgoi.getHangVe().getIdHangVe(),  // Lấy id từ đối tượng HangVe
                choNgoi.getMayBay().getIdMayBay(),  // Lấy id từ đối tượng MayBay
                choNgoi.getViTri(),
                choNgoi.getTrangThaiActive()
        );
    }

    // Chuyển từ ChoNgoiDTO sang ChoNgoi entity
    public static ChoNgoi toEntity(ChoNgoiDTO choNgoiDTO, HangVe hangVe, MayBay mayBay) {
        if (choNgoiDTO == null) {
            return null;
        }

        ChoNgoi choNgoi = new ChoNgoi();
        choNgoi.setIdChoNgoi(choNgoiDTO.getIdChoNgoi());
        choNgoi.setHangVe(hangVe);  // Set đối tượng HangVe
        choNgoi.setMayBay(mayBay);  // Set đối tượng MayBay
        choNgoi.setViTri(choNgoiDTO.getViTri());
        choNgoi.setTrangThaiActive(choNgoiDTO.getTrangThaiActive());

        return choNgoi;
    }
}
