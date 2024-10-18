package com.project.flightManagement.Service;

import com.project.flightManagement.DTO.LoaiHoaDonDTO.LoaiHoaDonDTO;
import org.springframework.stereotype.Service;

import java.util.Optional;

@Service
public interface LoaiHoaDonService {
    Optional<LoaiHoaDonDTO> addLoaiHoaDon(LoaiHoaDonDTO loaiHoaDonDTO);
    Optional<LoaiHoaDonDTO> updateLoaiHoaDon(LoaiHoaDonDTO loaiHoaDonDTO);
    Iterable<LoaiHoaDonDTO> getAllLoaiHoaDon();
    Optional<LoaiHoaDonDTO> getLoaiHoaDonById(int idLoaiHD);

}
