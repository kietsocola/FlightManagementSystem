package com.project.flightManagement.Service;

import com.project.flightManagement.DTO.LoaiHangHoaDTO.LoaiHangHoaDTO;

import java.util.Optional;

public interface LoaiHangHoaService {
    Iterable<LoaiHangHoaDTO> getAllLoaiHangHoa();
    Optional<LoaiHangHoaDTO> getLoaiHangHoaById(int id);
    Optional<LoaiHangHoaDTO> addNewLoaiHangHoa(LoaiHangHoaDTO loaiHangHoaDTO);
    Optional<LoaiHangHoaDTO> updateLoaiHangHoa(int id, LoaiHangHoaDTO loaiHangHoaDTO);
    String deleteLoaiHangHoa(int id);
}
