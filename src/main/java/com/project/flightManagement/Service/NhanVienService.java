package com.project.flightManagement.Service;

import com.jayway.jsonpath.Option;
import com.project.flightManagement.DTO.NhanVienDTO.NhanVienDTO;
import com.project.flightManagement.Model.NhanVien;

import java.util.List;
import java.util.Optional;

public interface NhanVienService {

    public Optional<NhanVienDTO> addNhanVien(NhanVienDTO nvDTO);
    public Optional<NhanVienDTO> updateNhanVien(NhanVienDTO nvDTO);

    public Iterable<NhanVienDTO> getAllNhanVien();
    public List<NhanVienDTO> getAllNhanVienSorted(String sort);

    public Optional<NhanVienDTO> getNhanVienByIdNhanVien(int idNhanVien);
    public List<NhanVienDTO> getNhanVienByhoTen(String hoTen);
    public Optional<NhanVienDTO> getNhanVienByEmail(String email);
    public Optional<NhanVienDTO> getNhanVienBySDT(String SDT);
    public Optional<NhanVienDTO> getNhanVienByCCCD(String cccd);


}
