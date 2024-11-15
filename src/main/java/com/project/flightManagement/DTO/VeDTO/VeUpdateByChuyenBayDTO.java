package com.project.flightManagement.DTO.VeDTO;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class VeUpdateByChuyenBayDTO {
    private int idChuyenBay;
    private double giaVeThuong;
    private double giaVeThuongGia;
}
