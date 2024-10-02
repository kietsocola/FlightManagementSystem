package com.project.flightManagement.Service;

import com.project.flightManagement.DTO.KhachHangDTO.KhachHangDTO;
import com.project.flightManagement.Model.KhachHang;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Service
public interface KhachHangService {
    public Iterable<KhachHangDTO> getAllKhachHang();
    public Optional<KhachHangDTO> getKhachHangByIdKhachHang(int idKH);
    public Optional<KhachHangDTO> addNewKhachHang(KhachHangDTO khDTO);
    public Optional<KhachHangDTO> updateKhachHang(KhachHangDTO khDTO);

    public Optional<KhachHangDTO> getKhachHangByEmail(String email);

    public Optional<KhachHangDTO> getKhachHangBySDT(String sodienthoai);

    public Optional<KhachHangDTO> getKhachHangByCccd(String cccd);
    public Iterable<KhachHangDTO> getAllKhachHangSorted(String sortBy);
    public List<KhachHangDTO> findByHoTen(String keyword);
}
