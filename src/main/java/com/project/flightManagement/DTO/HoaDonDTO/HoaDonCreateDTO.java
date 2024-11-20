package com.project.flightManagement.DTO.HoaDonDTO;

import com.project.flightManagement.DTO.ChiTietHoaDonDTO.ChiTietHoaDonDTO;
import com.project.flightManagement.DTO.HangHoaDTO.HangHoaDTO;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.time.LocalDateTime;
import java.util.List;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class HoaDonCreateDTO {

    private HoaDonDTO hoaDonDTO;
    private List<ChiTietHoaDonDTO> chiTietHoaDonDTOList;
    private List<HangHoaDTO> hangHoaDTOList;
}
