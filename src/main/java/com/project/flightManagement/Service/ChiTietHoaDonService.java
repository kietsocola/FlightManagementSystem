package com.project.flightManagement.Service;

import com.project.flightManagement.DTO.ChiTietHoaDonDTO.ChiTietHoaDonDTO;
import com.project.flightManagement.DTO.HangHoaDTO.HanhKhach_HangHoaDTO;
import org.springframework.stereotype.Service;

import java.util.Optional;
import java.util.List;

@Service
public interface ChiTietHoaDonService {
    public Iterable<ChiTietHoaDonDTO> getAllChiTietHoaDon();
    public Optional<ChiTietHoaDonDTO> getChiTietHoaDonByID(int idChiTietHoaDon);
    public Optional<ChiTietHoaDonDTO> addChiTietHoaDon(ChiTietHoaDonDTO chiTietHoaDonDTO);
    public Optional<ChiTietHoaDonDTO> updateChiTietHoaDon(ChiTietHoaDonDTO chiTietHoaDonDTO);
    public List<ChiTietHoaDonDTO> getListChiTietHoaDonByHoaDon(int idHoaDon);
    public List<ChiTietHoaDonDTO> getListChiTietHoaDonSorted(int idHoaDon, String sortBy, String order);
    public List<ChiTietHoaDonDTO> getListChiTietHoaDonByKeyWord(int idHoaDon, String keyWord);
    public List<HanhKhach_HangHoaDTO> getHangHoa_HanhKhach();

}
