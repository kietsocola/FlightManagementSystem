package com.project.flightManagement.Repository;

import com.project.flightManagement.Model.KhachHang;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import java.util.List;

public interface KhachHangRepository extends JpaRepository<KhachHang, Integer> {
    KhachHang findByEmail(String email);

    KhachHang findBySoDienThoai(String sodienthoai);

    KhachHang findByCccd(String cccd);

    @Query("SELECT k FROM KhachHang k WHERE " +
            "LOWER(k.hoTen) LIKE LOWER(CONCAT('%', :keyword, '%')) OR " +
            "LOWER(k.soDienThoai) LIKE LOWER(CONCAT('%', :keyword, '%')) OR " +
            "LOWER(k.email) LIKE LOWER(CONCAT('%', :keyword, '%')) OR " +
            "LOWER(k.cccd) LIKE LOWER(CONCAT('%', :keyword, '%'))")
    List<KhachHang> findByKeywordContainingIgnoreCase(@Param("keyword") String keyword);
}
