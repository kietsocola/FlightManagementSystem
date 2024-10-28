package com.project.flightManagement.DTO.TuyenBayDTO;

import com.project.flightManagement.Enum.ActiveEnum;
import com.project.flightManagement.Model.SanBay;
import jakarta.validation.constraints.NotNull;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.sql.Time;
import java.time.LocalTime;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class TuyenBayDTO {

    private int idTuyenBay;

    @NotNull(message = "Không đúng thông tin sân bay khởi hành")
    private SanBay SanBayBatDau;

    @NotNull(message = "Không đúng thông tin sân bay hạ cánh")
    private SanBay SanBayKetThuc;

    @NotNull(message = "Không để trống thời gian của chuyến bay")
    private int thoiGianChuyenBay;

    @NotNull(message = "Không để trống khoảng cách của chuyến bay")
    private int khoangCach;

    @NotNull(message = "Không để trống trạng thái chuyến bay")
    private ActiveEnum status;


}
