package com.project.flightManagement.Service;

import com.project.flightManagement.DTO.KhachHangDTO.KhachHangCreateDTO;
import com.project.flightManagement.Model.KhachHang;

public interface KhachHangService {
    KhachHang createKhachHang(KhachHangCreateDTO khachHangCreateDTO);
    boolean existsKhachHangByEmail(String email);
    boolean existsKhachHangByCccd(String cccd);
}
