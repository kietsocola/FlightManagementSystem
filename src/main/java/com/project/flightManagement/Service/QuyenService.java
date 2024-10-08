package com.project.flightManagement.Service;

import com.project.flightManagement.DTO.QuyenDTO.QuyenBasicDTO;
import com.project.flightManagement.DTO.QuyenDTO.QuyenCreateDTO;

import java.util.Optional;

public interface QuyenService {
    Optional<QuyenBasicDTO> getQuyenByIdQuyen(int idQuyen);
    boolean createQuyen(QuyenCreateDTO quyenCreateDTO);


}
