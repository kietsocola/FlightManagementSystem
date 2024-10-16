package com.project.flightManagement.DTO.ChuyenBayDTO;


import com.project.flightManagement.DTO.CongDTO.CongDTO;
import com.project.flightManagement.DTO.MayBayDTO.MayBayDTO;
import com.project.flightManagement.DTO.TuyenBayDTO.TuyenBayDTO;
import com.project.flightManagement.Enum.ActiveEnum;
import com.project.flightManagement.Enum.ChuyenBayEnum;
import com.project.flightManagement.Model.Cong;
import com.project.flightManagement.Model.MayBay;
import com.project.flightManagement.Model.TuyenBay;
import jakarta.validation.constraints.Min;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.sql.Time;
import java.time.LocalDateTime;
import java.util.Date;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class ChuyenBayDTO {

    @NotNull(message = "ID chuyến bay không được để trống")
    private int idChuyenBay;

    @NotNull(message = "ID tuyến bay không được để trống")
    private TuyenBayDTO tuyenBay;

    @NotNull(message = "ID máy bay không được để trống")
    private MayBayDTO mayBay;

    @NotNull(message = "ID cổng không được để trống")
    private CongDTO cong;

    @NotNull(message = "Thời gian bắt đầu dự tính không được để trống")
    private LocalDateTime thoiGianBatDauDuTinh;

    @NotNull(message = "Thời gian kết thúc dự tính không được để trống")
    private LocalDateTime thoiGianKetThucDuTinh;

    @NotBlank(message = "IATA chuyến bay không được để trống")
    private String iataChuyenBay;

    @NotBlank(message = "ICAO chuyến bay không được để trống")
    private String icaoChuyenBay;

    private LocalDateTime thoiGianBatDauThucTe;
    private LocalDateTime thoiGianKetThucThucTe;

    @Min(value = 0, message = "Delay không được nhỏ hơn 0")
    private int delay;

    @NotNull(message = "Ngày bay không được để trống")
    private Date ngayBay;

    @Min(value = 1, message = "Số ghế phải lớn hơn 0")
    private int soGhe;

    @NotNull(message = "Trạng thái chuyến bay không được để trống")
    private ChuyenBayEnum trangThai;

    @NotNull(message = "Trạng thái hoạt động không được để trống")
    private ActiveEnum trangThaiActive;
}

