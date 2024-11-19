package com.project.flightManagement.DTO.KhachHangDTO;

import com.project.flightManagement.Enum.GioiTinhEnum;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.sql.Date;
import java.time.LocalDate;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class KhachHangCreateDTO {
    private int id;
    private String cccd;
    private String email;
    private GioiTinhEnum gioiTinh;
    private String hoTen;
    private Date ngaySinh;
    private String soDienThoai;
}
