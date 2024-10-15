package com.project.flightManagement.DTO.SanBayDTO;

import com.project.flightManagement.Enum.ActiveEnum;
import com.project.flightManagement.Model.ThanhPho;
import jakarta.validation.Valid;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.Pattern;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class SanBayDTO {
    private int idSanBay;
    @NotBlank(message = "Không bỏ trống tên sân bay")
    private String tenSanBay;
    @NotBlank(message = "Không bỏ trống mã IATA sân bay")
    @Pattern(regexp = "^[A-Z]{3}$", message = "Mã IATA phải có 3 kí tự chữ in hoa")
    private String iataSanBay;
    @NotBlank(message = "Không được bỏ trống mã ICAO sân bay")
    @Pattern(regexp = "^[A-Z]{4}$", message = "Mã ICAO phải có 4 kí tự chữ in hoa")
    private String icaoSanBay;
    private String diaChi;
    @Valid
    private ThanhPho thanhPho;
    private ActiveEnum trangThaiActive;
}