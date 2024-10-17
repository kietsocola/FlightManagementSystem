package com.project.flightManagement.DTO.MayBayDTO;

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
public class MayBayDTO {
    private int idMayBay;
    @NotBlank(message = "Không được bỏ trống tên máy bay")
    private String tenMayBay;
    @Valid
    private HangBay hangBay;
    @Pattern(regexp = "^[A-Z0-9]{4}$", message = "Mã ICAO phải có 6 kí tự chữ in hoa(3 kí tự đầu in hoa, 3 kí tự sau chữ số)")
    private String icaoMayBay;
    @Pattern(regexp = "^[A-Z]{6,10}", message = "Số hàng ghế thường ít nhất phải có 6 kí tự in hoa")
    private String soHangGheThuong;
    @Pattern(regexp = "^[A-Z]{4,10}", message = "Số hàng ghế VIP ít nhất phải có 4 kí tự in hoa")
    private String soHangGheVip;
    @Min(value = 1, message = "Số cột ghế thường phải là số nguyên dương")
    private int soCotGheThuong;
    @Min(value = 1, message = "Số cột ghế VIP phải là số nguyên dương")
    private int soCotGheVip;
    @NotBlank(message = "Không được bỏ trống số hiệu")
    @Pattern(regexp = "^[A-Z]{1,2}-[A-Z0-9]{3,5}$", message = "Số hiệu phải có 6 kí tự[Mã quốc gia(1-2 kí tự chữ in hoa và số) - Mã số hiệu(1-3 kí tự chữ in hoa và số)]")
    private String soHieu;
    private int namSanXuat;
    private ActiveEnum trangThaiActive;
}
