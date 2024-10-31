package com.project.flightManagement.Service;

import com.project.flightManagement.DTO.QuyenDTO.QuyenBasicDTO;
import com.project.flightManagement.DTO.QuyenDTO.QuyenCreateDTO;
import com.project.flightManagement.DTO.QuyenDTO.QuyenResponseDTO;
import org.springframework.data.domain.Page;

import java.util.Optional;

public interface QuyenService {
    Optional<QuyenBasicDTO> getQuyenByIdQuyen(int idQuyen);
    boolean createQuyen(QuyenCreateDTO quyenCreateDTO);

    Page<QuyenResponseDTO> getAllQuyen(int page, int size);
    QuyenResponseDTO getQuyenDetailByIdQuyen(int idQuyen);

    void updateQuyen(int idQuyen, QuyenCreateDTO quyenCreateDTO);

    Page<QuyenResponseDTO> searchQuyenByName(String name, int page, int size);

    boolean existsQuyenByTenQuyen(String tenQuyen);

    boolean existsByTenQuyenAndNotIdQuyenNot(String tenQuyen, int idQuyen);

}
