package com.project.flightManagement.DTO.ChiTietQuyenDTO;

import com.project.flightManagement.Enum.ChiTietQuyenActionEnum;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class ChiTietQuyenCreateDTO {
    private int idQuyen;
    private int idChucNang;
    private ChiTietQuyenActionEnum hanhDong;
}
