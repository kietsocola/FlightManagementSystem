package com.project.flightManagement.Mapper;

import com.project.flightManagement.DTO.TuyenBayDTO.TuyenBayDTO;
import com.project.flightManagement.DTO.TuyenBayDTO.TuyenBay_VeDTO;
import com.project.flightManagement.Model.TuyenBay;
import java.sql.Timestamp;
import java.time.LocalTime;

public class TuyenBayMapper {

    public static TuyenBayDTO toDTO(TuyenBay tb) {
        TuyenBayDTO tbDTO = new TuyenBayDTO();

        tbDTO.setIdTuyenBay(tb.getIdTuyenBay());
        tbDTO.setSanBayBatDau(tb.getSanBayBatDau());
        tbDTO.setSanBayKetThuc(tb.getSanBayKetThuc());

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
        tb.setSanBayBatDau(tbDTO.getSanBayBatDau());
        tb.setSanBayKetThuc(tbDTO.getSanBayKetThuc());

        if (tbDTO.getThoiGianChuyenBay() != null) {
            Timestamp thoiGianChuyenBay = Timestamp
                    .valueOf(tbDTO.getThoiGianChuyenBay().atDate(java.time.LocalDate.now()));
            tb.setThoiGianChuyenBay(thoiGianChuyenBay);
        }

        tb.setKhoangCach(tbDTO.getKhoangCach());
        tb.setTrangThaiActive(tbDTO.getStatus());

        return tb;
    }

    public static TuyenBay_VeDTO toTuyenBay_VeDTO(TuyenBay tb) {
        TuyenBay_VeDTO tbDTO = new TuyenBay_VeDTO();

        tbDTO.setIdTuyenBay(tb.getIdTuyenBay());
        tbDTO.setSanBayBatDau(SanBayMapper.toSanBay_veDTO(tb.getSanBayBatDau()));
        tbDTO.setSanBayKetThuc(SanBayMapper.toSanBay_veDTO(tb.getSanBayKetThuc()));

        if (tb.getThoiGianChuyenBay() != null) {
            LocalTime thoiGianChuyenBay = tb.getThoiGianChuyenBay().toLocalDateTime().toLocalTime();
            tbDTO.setThoiGianChuyenBay(thoiGianChuyenBay);
        }

        tbDTO.setKhoangCach(tb.getKhoangCach());
        tbDTO.setStatus(tb.getTrangThaiActive());

        return tbDTO;
    }
}
