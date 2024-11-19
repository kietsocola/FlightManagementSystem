package com.project.flightManagement.DTO;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class HanhKhach_HangHoaDTO {
    private String tenHangHoa;
    private String tenHanhKhach;
    private double soTien;
}
