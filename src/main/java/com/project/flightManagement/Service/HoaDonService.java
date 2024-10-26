package com.project.flightManagement.Service;

import com.project.flightManagement.DTO.ChiTietHoaDonDTO.ChiTietHoaDonDTO;
import com.project.flightManagement.DTO.HoaDonDTO.HoaDonDTO;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Service
public interface HoaDonService {
    public Optional<HoaDonDTO> addHoaDon(HoaDonDTO hoaDonDTO);
    public Optional<HoaDonDTO> updateHoaDon(HoaDonDTO hoaDonDTO);
    public Iterable<HoaDonDTO> getAllHoaDon();
    public Optional<HoaDonDTO> getHoaDonById(int idHD);
    public List<ChiTietHoaDonDTO> getChiTietHoaDon(int idHD);
    public List<HoaDonDTO> getHoaDonByKeyWord(String keyWord);
    public Iterable<HoaDonDTO> getAllHoaDonSorted(String sortBy, String direction);
    public List<HoaDonDTO> getHoaDonByNV(int idNV);
    public List<HoaDonDTO> getHoaDonByKH(int idKH);
    public List<HoaDonDTO> getHoaDonByPTTT(int idPTTT);
    public List<HoaDonDTO> getHoaDonByLoaiHD(int idLoaiHD);

}
