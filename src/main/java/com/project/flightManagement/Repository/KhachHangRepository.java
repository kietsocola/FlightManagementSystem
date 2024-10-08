package com.project.flightManagement.Repository;

import com.project.flightManagement.Model.KhachHang;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.Optional;

public interface KhachHangRepository extends JpaRepository<KhachHang, Integer> {
    boolean existsKhachHangByEmail(String email);
    boolean existsKhachHangByCccd(String cccd);

    Optional<KhachHang> findKhachHangByIdKhachHang(int idKhachHang);
}
