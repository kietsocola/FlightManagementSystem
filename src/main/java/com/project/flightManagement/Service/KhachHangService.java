package com.project.flightManagement.Service;

import com.project.flightManagement.DTO.KhachHangDTO.KhachHangDTO;
import com.project.flightManagement.DTO.KhachHangDTO.KhachHangBasicDTO;
import com.project.flightManagement.DTO.KhachHangDTO.KhachHangCreateDTO;
import com.project.flightManagement.Model.KhachHang;
import org.springframework.stereotype.Service;

import java.time.LocalDate;
import java.util.List;
import java.util.Map;
import java.util.Optional;

public interface KhachHangService {
    public Iterable<KhachHangDTO> getAllKhachHang();
    public Optional<KhachHangDTO> getKhachHangByIdKhachHang(int idKH);
    public Optional<KhachHangDTO> addNewKhachHang(KhachHangDTO khDTO);
    public Optional<KhachHangDTO> updateKhachHang(KhachHangDTO khDTO);

    public Optional<KhachHangDTO> getKhachHangByEmail(String email);

    public Optional<KhachHangDTO> getKhachHangBySDT(String sodienthoai);

    public Optional<KhachHangDTO> getKhachHangByCccd(String cccd);
    public Iterable<KhachHangDTO> getAllKhachHangSorted(String sortBy, String direction);
    public List<KhachHangDTO> findByHoTen(String keyword);
    KhachHang createKhachHang(KhachHangCreateDTO khachHangCreateDTO);
    boolean existsKhachHangByEmail(String email);
    boolean existsKhachHangByCccd(String cccd);
    boolean existsKhachHangByPhone(String phone);
    KhachHangBasicDTO getKhachHangByIdKhachHang_BASIC(int idKhachHang);
    boolean updatePoint(int idKH, int point, boolean isUse);
    List<KhachHangDTO> getKhachHangChuaCoTaiKhoan();
    public Map<String, Double> calculateGrowthRate(String period);
    public long tinhTongSoKhachHang();
    public List<KhachHangDTO> findKhachHangByNgayTaoBetween(LocalDate startDate, LocalDate endDate);
}
