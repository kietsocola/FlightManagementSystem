package com.project.flightManagement.DTO.MayBayDTO;

import com.project.flightManagement.DTO.HangBayDTO.HangBayDTO;
import com.project.flightManagement.Enum.ActiveEnum;
import com.project.flightManagement.Model.HangBay;
import jakarta.validation.Valid;
import jakarta.validation.constraints.Min;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.Pattern;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class MayBay_VeDTO {
    private int idMayBay;
    @NotBlank(message = "Không được bỏ trống tên máy bay")
    private String tenMayBay;
    @Valid
    private HangBayDTO hangBay;
    @Pattern(regexp = "^[A-Z0-9]{4,6}$", message = "Mã ICAO phải có 4-6 kí tự chữ in hoa(1-3 kí tự đầu in hoa, 1-3 kí tự sau chữ số)")
    private String icaoMayBay;
    @NotBlank(message = "Không được bỏ trống số hiệu")
    @Pattern(regexp = "^[A-Z]{1,2}-[A-Z0-9]{3,5}$", message = "Số hiệu phải có 6 kí tự[Mã quốc gia(1-2 kí tự chữ in hoa và số) - Mã số hiệu(1-3 kí tự chữ in hoa và số)]")
    private String soHieu;
    private ActiveEnum trangThaiActive;
}
