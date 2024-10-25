package com.project.flightManagement.Service;

import com.project.flightManagement.DTO.VeDTO.VeDTO;
import com.project.flightManagement.DTO.VeDTO.VeUpdateDTO;
import com.project.flightManagement.Model.Ve;
import org.springframework.data.domain.Page;

import java.util.Optional;

public interface VeService {
    Page<VeDTO> getAllVe(int page, int size);
    Page<VeDTO> getAllVeByIdChuyenBay(int idChuyenBay, int page, int size);
    boolean updateVe(int idVe, VeUpdateDTO veUpdateDTO);

    Optional<Ve> getVeById(int idVe);
}
