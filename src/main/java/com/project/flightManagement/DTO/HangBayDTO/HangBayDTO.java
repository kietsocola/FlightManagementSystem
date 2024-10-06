package com.project.flightManagement.DTO.HangBayDTO;

import com.project.flightManagement.Enum.ActiveEnum;
import jakarta.validation.constraints.NotBlank;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class HangBayDTO {
    private int idHangBay;
    @NotBlank(message = "Không được bỏ trống tên hãng bay")
    private String tenHangBay;
    @NotBlank(message = "Không được bỏ trống mã IATA")
    private String iataHangBay;
    @NotBlank(message = "Không được bỏ trống mã ICAO")
    private String icaoHangBay;
    private ActiveEnum trangThaiActive;
}
