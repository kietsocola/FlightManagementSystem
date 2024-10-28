package com.project.flightManagement.Mapper;

import com.project.flightManagement.DTO.TuyenBayDTO.TuyenBayDTO;
import com.project.flightManagement.Model.TuyenBay;
import java.sql.Timestamp;
import java.time.LocalTime;

public class TuyenBayMapper {

    public static TuyenBayDTO toDTO(TuyenBay tb) {
        TuyenBayDTO tbDTO = new TuyenBayDTO();

        tbDTO.setIdTuyenBay(tb.getIdTuyenBay());
        tbDTO.setSanBayBatDau(tb.getSanBayBatDau());
        tbDTO.setSanBayKetThuc(tb.getSanBayKetThuc());
        tbDTO.setThoiGianChuyenBay(tb.getThoiGianChuyenBay());

        tbDTO.setKhoangCach(tb.getKhoangCach());
        tbDTO.setStatus(tb.getTrangThaiActive());

        return tbDTO;
    }

    public static TuyenBay toEntity(TuyenBayDTO tbDTO) {
        TuyenBay tb = new TuyenBay();

        tb.setIdTuyenBay(tbDTO.getIdTuyenBay());
        tb.setSanBayBatDau(tbDTO.getSanBayBatDau());
        tb.setSanBayKetThuc(tbDTO.getSanBayKetThuc());
        tb.setThoiGianChuyenBay(tbDTO.getThoiGianChuyenBay());
        tb.setKhoangCach(tbDTO.getKhoangCach());
        tb.setTrangThaiActive(tbDTO.getStatus());

        return tb;
    }
}
