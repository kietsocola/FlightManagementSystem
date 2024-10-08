package com.project.flightManagement.Service;

import com.project.flightManagement.DTO.KhachHangDTO.KhachHangBasicDTO;
import com.project.flightManagement.DTO.KhachHangDTO.KhachHangCreateDTO;
import com.project.flightManagement.Model.KhachHang;

import java.util.Optional;

public interface KhachHangService {
    KhachHang createKhachHang(KhachHangCreateDTO khachHangCreateDTO);
    boolean existsKhachHangByEmail(String email);
    boolean existsKhachHangByCccd(String cccd);
    KhachHangBasicDTO getKhachHangByIdKhachHang(int idKhachHang);
}
