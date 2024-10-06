package com.project.flightManagement.DTO.MayBayDTO;

import com.project.flightManagement.Enum.ActiveEnum;
import com.project.flightManagement.Model.HangBay;
import jakarta.validation.constraints.NotBlank;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class MayBayDTO {
    private int idMayBay;
    @NotBlank(message = "Không được bỏ trống tên máy bay")
    private String tenMayBay;
    @NotBlank(message = "Không được bỏ trống hãng máy bay")
    private HangBay hangBay;
    @NotBlank(message = "Không được bỏ trống mã ICAO")
    private String icaoMayBay;
    private int soLuongGhe;
    @NotBlank(message = "Không được bỏ trống số hiệu")
    private String soHieu;
    private int namSanXuat;
    private ActiveEnum trangThaiActive;
}
