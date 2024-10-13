package com.project.flightManagement.Service;

import com.project.flightManagement.DTO.ChiTietHoaDonDTO.ChiTietHoaDonDTO;
import org.springframework.stereotype.Service;

import java.util.Optional;

@Service
public interface ChiTietHoaDonService {
    public Iterable<ChiTietHoaDonDTO> getAllChiTietHoaDon();
    public Optional<ChiTietHoaDonDTO> getChiTietHoaDonByID(int idChiTietHoaDon);
    public Optional<ChiTietHoaDonDTO> addChiTietHoaDon(ChiTietHoaDonDTO chiTietHoaDonDTO);
    public Optional<ChiTietHoaDonDTO> updateChiTietHoaDon(ChiTietHoaDonDTO chiTietHoaDonDTO);

}
