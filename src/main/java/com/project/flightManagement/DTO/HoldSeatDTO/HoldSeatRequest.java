package com.project.flightManagement.DTO.HoldSeatDTO;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class HoldSeatRequest {
    private Integer seatId;
    private Integer idVe;// ID của ghế được giữ
    private Integer flightId; // ID chuyến bay
    private Integer userId; // ID người dùng giữ ghế
}