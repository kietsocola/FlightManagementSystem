package com.project.flightManagement.Service;

import com.project.flightManagement.DTO.VeDTO.VeDTO;
import org.springframework.data.domain.Page;

public interface VeService {
    Page<VeDTO> getAllVe(int page, int size);
    Page<VeDTO> getAllVeByIdChuyenBay(int idChuyenBay, int page, int size);
    boolean updateVe(int idVe, VeDTO veDTO);
}
