package com.project.flightManagement.DTO.TuyenBayDTO;

import com.project.flightManagement.DTO.SanBayDTO.SanBayDTO;
import com.project.flightManagement.DTO.SanBayDTO.SanBay_VeDTO;
import com.project.flightManagement.Enum.ActiveEnum;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.time.LocalTime;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class TuyenBay_VeDTO {
    private int idTuyenBay;
    private SanBay_VeDTO SanBayBatDau;
    private SanBay_VeDTO SanBayKetThuc;
    private LocalTime thoiGianChuyenBay;
    private int khoangCach;
    private ActiveEnum status;
}
