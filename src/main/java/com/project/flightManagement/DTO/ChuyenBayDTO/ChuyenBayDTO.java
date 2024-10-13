package com.project.flightManagement.DTO.ChuyenBayDTO;

import com.project.flightManagement.Enum.ActiveEnum;
import com.project.flightManagement.Enum.ChuyenBayEnum;
import com.project.flightManagement.Model.Cong;
import com.project.flightManagement.Model.DanhGia;
import com.project.flightManagement.Model.MayBay;
import com.project.flightManagement.Model.TuyenBay;
import jakarta.persistence.*;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.sql.Timestamp;
import java.time.LocalDateTime;
import java.util.Date;
import java.util.List;


@Data
@NoArgsConstructor
@AllArgsConstructor
public class ChuyenBayDTO {

    private int idChuyenBay;

    @NotNull(message = "Không được để trống")
    private TuyenBay tuyenBay;

    @NotNull(message = "Không được để trống")
    private MayBay mayBay;

    @NotNull(message = "Không được để trống")
    private Cong cong;

    @NotNull(message = "Không được để trống")
    private LocalDateTime thoiGianBatDauDuTinh;

    @NotNull(message = "Không được để trống")
    private LocalDateTime thoiGianKetThucDuTinh;

    @NotBlank(message = "Không được để trống")
    private String iataChuyenBay;

    @NotBlank(message = "Không được để trống")
    private String icaoChuyenBay;

    @NotNull(message = "Không được để trống")
    private LocalDateTime thoiGianBatDauThucTe;

    @NotNull(message = "Không được để trống")
    private LocalDateTime thoiGianKetThucThucTe;

    @NotBlank(message = "Không được để trống")
    private int delay;

    @NotNull(message = "Không được để trống")
    private Date ngayBay;

    @NotBlank(message = "Không được để trống")
    private int soGhe;

    @NotNull(message = "Không được để trống")
    private ChuyenBayEnum trangThai;

    @NotNull(message = "Không được để trống")
    private ActiveEnum trangThaiActive;


}

