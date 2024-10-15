package com.project.flightManagement.DTO.TuyenBayDTO;

import com.project.flightManagement.Enum.ActiveEnum;
import jakarta.validation.constraints.Min;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Pattern;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class TuyenBayDTO {
    private int idTuyenBay;

    @NotNull(message = "Sân bay bắt đầu không được để trống")
    private int idSanBayBatDau;

    @NotNull(message = "Sân bay kết thúc không được để trống")
    private int idSanBayKetThuc;

    @NotNull(message = "Thời gian chuyến bay không được để trống")
    @Min(value = 1, message = "Thời gian chuyến bay phải lớn hơn 0")
    private int thoiGianChuyenBay;

    @NotNull(message = "Khoảng cách chuyến bay không được để trống")
    @Min(value = 0, message = "Khoảng cách không thể âm")

    private int khoangCach;

    @NotNull(message = "Trạng thái chuyến bay không được để trống")
    private ActiveEnum status;
}
