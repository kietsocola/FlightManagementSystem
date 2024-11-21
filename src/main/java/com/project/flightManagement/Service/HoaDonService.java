package com.project.flightManagement.Service;

import com.project.flightManagement.DTO.ChiTietHoaDonDTO.ChiTietHoaDonDTO;
import com.project.flightManagement.DTO.HoaDonDTO.HoaDonDTO;
import org.springframework.stereotype.Service;

import java.time.LocalDate;
import java.util.List;
import java.util.Locale;
import java.util.Map;
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

    public List<HoaDonDTO> getHoaDonByNgaylap(LocalDate ngayLap);

    public boolean markDanhGia(int idHoaDon);

    public Double getRevenueByMonth(int month, int year);

    public Double getRevenueByQuarter(int quarter, int year);

    public Double getRevenueByYear(int year);

    public Double getRevenueBetweenMonths(int startMonth, int startYear, int endMonth, int endYear);

    public Double getRevenueBetweenQuarters(int startQuarter, int startYear, int endQuarter, int endYear);

    public Double getRevenueBetweenYears(int startYear, int endYear);

    public Map<String, Map<String, Long>> getStatistics(String period);

    public Map<Integer, Double> getRevenueForAllYears();

    public List<Integer> getAllYears();
}
