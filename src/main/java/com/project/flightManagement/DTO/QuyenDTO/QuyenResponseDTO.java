package com.project.flightManagement.DTO.QuyenDTO;

import com.project.flightManagement.DTO.ChiTietQuyenDTO.ChiTietQuyenDTO;
import com.project.flightManagement.Enum.ActiveEnum;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.security.Permission;
import java.util.List;
@Data
@NoArgsConstructor
@AllArgsConstructor
public class QuyenResponseDTO {
    private int idQuyen;
    private String tenQuyen;
    private List<ChiTietQuyenDTO> chiTietQuyenDTOList;
    private ActiveEnum trangThaiActive;
}
