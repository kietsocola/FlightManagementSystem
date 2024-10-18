package com.project.flightManagement.Service;

import com.project.flightManagement.DTO.ChuyenBayDTO.ChuyenBayDTO;
import com.project.flightManagement.DTO.CongDTO.CongDTO;
import com.project.flightManagement.Model.SanBay;
import org.springframework.stereotype.Service;

import java.util.Optional;


public interface CongService {
    public Iterable<CongDTO> getAllCong();
    public Optional<CongDTO> getCongById(int idCong);
    public Iterable<CongDTO> getCongBySanBay(SanBay sanBay);
}
