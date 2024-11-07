package com.project.flightManagement.DTO.DanhGiaDTO;

import com.project.flightManagement.Enum.ActiveEnum;
import com.project.flightManagement.Enum.DanhGiaEnum;
import com.project.flightManagement.Model.HangBay;
import com.project.flightManagement.Model.HanhKhach;
import com.project.flightManagement.Model.KhachHang;
import jakarta.validation.constraints.NotBlank;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.time.LocalDateTime;
@Data
@AllArgsConstructor
@NoArgsConstructor
public class DanhGiaDTO {
    private int idDanhGia;
    @NotBlank(message = "Không để trống hãng bay")
    private HangBay hangBay;
    private KhachHang khachHang;
    @NotBlank(message = "Không để trống đánh giá sao")
    private DanhGiaEnum sao;
    private String noiDung;
    private LocalDateTime thoiGianTao;
    private ActiveEnum trangThaiActive;
}
