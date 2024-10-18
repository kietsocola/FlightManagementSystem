package com.project.flightManagement.DTO.ChiTietHoaDonDTO;

import com.project.flightManagement.Model.HangHoa;
import com.project.flightManagement.Model.HoaDon;
import com.project.flightManagement.Model.Ve;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class ChiTietHoaDonDTO {
    private int idChiTietHoaDon;
    private HoaDon hoaDon;
    private HangHoa hangHoa;
    private Ve ve;
    private double soTien;
}
