package com.project.flightManagement.DTO.NhanVienDTO;

import com.project.flightManagement.Enum.ActiveEnum;
import com.project.flightManagement.Enum.GioiTinhEnum;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.Date;
@Data
@NoArgsConstructor
@AllArgsConstructor
public class NhanVienCreateDTO {
    private String cccd;
    private String email;
    private GioiTinhEnum gioiTinh;
    private String hoTen;
    private Date ngaySinh;
    private String soDienThoai;
    private int roleId;
    private ActiveEnum trangThai;
}
