package com.project.flightManagement.Service;

import com.project.flightManagement.DTO.VeDTO.VeCreateDTO;
import com.project.flightManagement.DTO.VeDTO.VeDTO;
import com.project.flightManagement.DTO.VeDTO.VeUpdateDTO;
import com.project.flightManagement.DTO.VeDTO.VeUpdateHanhKhachDTO;
import com.project.flightManagement.Model.Ve;
import org.springframework.data.domain.Page;

import java.util.Optional;

public interface VeService {
    Page<VeDTO> getAllVe(int page, int size);
    Page<VeDTO> getAllVeByIdChuyenBay(int idChuyenBay, int page, int size);
    boolean updateVe(int idVe, VeUpdateDTO veUpdateDTO);

    VeDTO getVeById(int idVe);

    boolean createVe(VeCreateDTO veCreateDTO);
    boolean updateHanhKhachVe(VeUpdateHanhKhachDTO veUpdateHanhKhachDTO);

    void createAutoVeByIdMayBay(int idMayBay);

}
