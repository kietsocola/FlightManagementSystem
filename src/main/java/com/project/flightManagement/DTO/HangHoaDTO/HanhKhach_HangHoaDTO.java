package com.project.flightManagement.DTO.HangHoaDTO;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class HanhKhach_HangHoaDTO {
    private int idHangHoa;
    private String tenHangHoa;
    private Integer idHanhKhach;
    private String tenHanhKhach;
    private String cccd;
    private double giaHangHoa;
}
