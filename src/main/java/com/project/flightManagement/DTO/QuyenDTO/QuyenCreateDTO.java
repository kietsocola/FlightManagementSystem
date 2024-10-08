package com.project.flightManagement.DTO.QuyenDTO;

import com.project.flightManagement.DTO.ChiTietQuyenDTO.ChiTietQuyenDTO;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.List;
@Data
@NoArgsConstructor
@AllArgsConstructor
public class QuyenCreateDTO {
    private String tenQuyen;
    private List<ChiTietQuyenDTO> chiTietQuyenDTO;
}
