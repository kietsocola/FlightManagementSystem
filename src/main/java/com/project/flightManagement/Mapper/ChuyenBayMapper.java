package com.project.flightManagement.Mapper;

import com.project.flightManagement.DTO.ChuyenBayDTO.ChuyenBayDTO;
import com.project.flightManagement.Enum.ActiveEnum;
import com.project.flightManagement.Enum.ChuyenBayEnum;
import com.project.flightManagement.Model.ChuyenBay;
import com.project.flightManagement.Model.Cong;
import com.project.flightManagement.Model.MayBay;
import com.project.flightManagement.Model.TuyenBay;

public class ChuyenBayMapper {

    public static ChuyenBayDTO toChuyenBayDTO(ChuyenBay chuyenBay) {
        return new ChuyenBayDTO(
                chuyenBay.getIdChuyenBay(),
                chuyenBay.getTuyenBay().getIdTuyenBay(), // Chỉ lấy ID của TuyenBay
                chuyenBay.getMayBay().getIdMayBay(), // Chỉ lấy ID của MayBay
                chuyenBay.getCong().getIdCong(), // Chỉ lấy ID của Cong
                chuyenBay.getThoiGianBatDauDuTinh(),
                chuyenBay.getThoiGianKetThucDuTinh(),
                chuyenBay.getIataChuyenBay(),
                chuyenBay.getIcaoChuyenBay(),
                chuyenBay.getThoiGianBatDauThucTe(),
                chuyenBay.getThoiGianKetThucThucTe(),
                chuyenBay.getDelay(),
                chuyenBay.getNgayBay(),
                chuyenBay.getSoGhe(),
                chuyenBay.getTrangThai().toString(), // Chuyển Enum thành chuỗi
                chuyenBay.getTrangThaiActive().toString() // Chuyển Enum thành chuỗi
        );
    }
    public static ChuyenBay toChuyenBayEntity(ChuyenBayDTO dto, TuyenBay tuyenBay, MayBay mayBay, Cong cong) {
        ChuyenBay chuyenBay = new ChuyenBay();
        chuyenBay.setIdChuyenBay(dto.getIdChuyenBay());
        chuyenBay.setTuyenBay(tuyenBay); // Set đối tượng TuyenBay
        chuyenBay.setMayBay(mayBay); // Set đối tượng MayBay
        chuyenBay.setCong(cong); // Set đối tượng Cong
        chuyenBay.setThoiGianBatDauDuTinh(dto.getThoiGianBatDauDuTinh());
        chuyenBay.setThoiGianKetThucDuTinh(dto.getThoiGianKetThucDuTinh());
        chuyenBay.setIataChuyenBay(dto.getIataChuyenBay());
        chuyenBay.setIcaoChuyenBay(dto.getIcaoChuyenBay());
        chuyenBay.setThoiGianBatDauThucTe(dto.getThoiGianBatDauThucTe());
        chuyenBay.setThoiGianKetThucThucTe(dto.getThoiGianKetThucThucTe());
        chuyenBay.setDelay(dto.getDelay());
        chuyenBay.setNgayBay(dto.getNgayBay());
        chuyenBay.setSoGhe(dto.getSoGhe());
        chuyenBay.setTrangThai(ChuyenBayEnum.valueOf(dto.getTrangThai())); // Chuyển chuỗi thành Enum
        chuyenBay.setTrangThaiActive(ActiveEnum.valueOf(dto.getTrangThaiActive())); // Chuyển chuỗi thành Enum

        return chuyenBay;
    }
}

