package com.project.flightManagement.Repository;

import com.project.flightManagement.Model.NhanVien;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface NhanVienRepository extends JpaRepository<NhanVien, Integer> {
    NhanVien findByEmail(String Email);
    List<NhanVien> findByHoTen(String Hoten);
    NhanVien findByCccd(String cccd);
    NhanVien findBySoDienThoai(String soDienThoai);
}
