package com.project.flightManagement.Mapper;

import com.project.flightManagement.DTO.TuyenBayDTO.TuyenBayDTO;
import com.project.flightManagement.Model.TuyenBay;
import java.sql.Timestamp;
import java.time.LocalTime;

public class TuyenBayMapper {

    public static TuyenBayDTO toDTO(TuyenBay tb) {
        TuyenBayDTO tbDTO = new TuyenBayDTO();

        tbDTO.setIdTuyenBay(tb.getIdTuyenBay());
        tbDTO.setIdSanBayBatDau(tb.getSanBayBatDau());
        tbDTO.setIdSanBayKetThuc(tb.getSanBayKetThuc());

        if (tb.getThoiGianChuyenBay() != null) {
            LocalTime thoiGianChuyenBay = tb.getThoiGianChuyenBay().toLocalDateTime().toLocalTime();
            tbDTO.setThoiGianChuyenBay(thoiGianChuyenBay);
        }

        tbDTO.setKhoangCach(tb.getKhoangCach());
        tbDTO.setStatus(tb.getTrangThaiActive());

        return tbDTO;
    }

    public static TuyenBay toEntity(TuyenBayDTO tbDTO) {
        TuyenBay tb = new TuyenBay();

        tb.setIdTuyenBay(tbDTO.getIdTuyenBay());
        tb.setSanBayBatDau(tbDTO.getIdSanBayBatDau());
        tb.setSanBayKetThuc(tbDTO.getIdSanBayKetThuc());

        if (tbDTO.getThoiGianChuyenBay() != null) {
            Timestamp thoiGianChuyenBay = Timestamp
                    .valueOf(tbDTO.getThoiGianChuyenBay().atDate(java.time.LocalDate.now()));
            tb.setThoiGianChuyenBay(thoiGianChuyenBay);
        }

        tb.setKhoangCach(tbDTO.getKhoangCach());
        tb.setTrangThaiActive(tbDTO.getStatus());

        return tb;
    }
}
