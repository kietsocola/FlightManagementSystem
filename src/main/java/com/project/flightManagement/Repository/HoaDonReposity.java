package com.project.flightManagement.Repository;

import com.project.flightManagement.DTO.HoaDonDTO.HoaDonDTO;
import com.project.flightManagement.Model.HoaDon;
import org.springframework.data.jpa.repository.JpaRepository;

public interface HoaDonReposity extends JpaRepository<HoaDon, Integer> {
//    Iterable<HoaDon> findHoaDonByNhanVien(int idNV);
//    Iterable<HoaDon> findHoaDonByKhachHang(int idKH);
//    Iterable<HoaDon> findHoaDonByPTTT(int idPTTT);
//
//    Iterable<HoaDon> findHoaDonByLoaiHD(int idLoaiHD);
}
