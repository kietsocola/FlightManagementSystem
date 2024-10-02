package com.project.flightManagement.Repository;

import com.project.flightManagement.Model.KhachHang;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface KhachHangRepository extends JpaRepository<KhachHang, Integer> {
    KhachHang findByEmail(String email);

    KhachHang findBySoDienThoai(String sodienthoai);

    KhachHang findByCccd(String cccd);

    List<KhachHang> findByHoTen(String keyword);
}
