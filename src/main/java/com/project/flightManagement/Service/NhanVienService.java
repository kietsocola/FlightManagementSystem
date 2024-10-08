package com.project.flightManagement.Service;

import com.project.flightManagement.DTO.NhanVienDTO.NhanVienDTO;

import java.util.List;
import java.util.Optional;

public interface NhanVienService {

    public Optional<NhanVienDTO> addNhanVien(NhanVienDTO nvDTO);
    public Optional<NhanVienDTO> updateNhanVien(NhanVienDTO nvDTO);

    public Iterable<NhanVienDTO> getAllNhanVien();
    public List<NhanVienDTO> getAllNhanVienSorted(String sortField , String sordOrder);

    public Optional<NhanVienDTO> getNhanVienByIdNhanVien(int idNhanVien);
    public List<NhanVienDTO> getNhanVienByhoTen(String hoTen);
    public List<NhanVienDTO> getNhanVienByEmail(String email);
    public List<NhanVienDTO> getNhanVienBySDT(String SDT);
    public List<NhanVienDTO> getNhanVienByCCCD(String cccd);
    public List<NhanVienDTO> getNhanVienBetween(String start , String end);

}
