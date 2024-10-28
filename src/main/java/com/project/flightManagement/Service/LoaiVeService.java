package com.project.flightManagement.Service;

import com.project.flightManagement.DTO.LoaiVeDTO.LoaiVeDTO;
import com.project.flightManagement.Model.LoaiVe;
import org.springframework.data.domain.Page;

import java.util.List;
import java.util.Optional;

public interface LoaiVeService {
    Page<LoaiVeDTO> getAllLoaiVe(int page, int size);
    LoaiVeDTO getLoaiVeById(int id);
    Optional<LoaiVe> getLoaiVeEntityById(int idLoaiVe);
}
