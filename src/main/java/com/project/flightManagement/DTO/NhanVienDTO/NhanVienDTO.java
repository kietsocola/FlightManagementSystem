package com.project.flightManagement.DTO.NhanVienDTO;

import com.project.flightManagement.Enum.ActiveEnum;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.Date;
@Data
@NoArgsConstructor
@AllArgsConstructor
public class NhanVienDTO {
    private int idNhanVien;
    private String cccd;
    private String email;
    private String gioiTinh;
    private String hoTen;
    private Date ngaySinh;
    private String soDienThoai;
    private ActiveEnum trangthai;

}
