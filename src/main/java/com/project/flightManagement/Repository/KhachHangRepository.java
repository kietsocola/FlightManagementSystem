package com.project.flightManagement.Repository;

import com.project.flightManagement.Model.KhachHang;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import java.time.LocalDate;
import java.util.List;
import java.util.Optional;

public interface KhachHangRepository extends JpaRepository<KhachHang, Integer> {
    KhachHang findByEmail(String email);

    KhachHang findBySoDienThoai(String sodienthoai);
    boolean existsKhachHangByEmail(String email);
    boolean existsKhachHangByCccd(String cccd);
    boolean existsKhachHangBySoDienThoai(String phone);
    KhachHang findByCccd(String cccd);

    @Query("SELECT k FROM KhachHang k WHERE " +
            "LOWER(k.hoTen) LIKE LOWER(CONCAT('%', :keyword, '%')) OR " +
            "LOWER(k.soDienThoai) LIKE LOWER(CONCAT('%', :keyword, '%')) OR " +
            "LOWER(k.email) LIKE LOWER(CONCAT('%', :keyword, '%')) OR " +
            "LOWER(k.cccd) LIKE LOWER(CONCAT('%', :keyword, '%'))")
    List<KhachHang> findByKeywordContainingIgnoreCase(@Param("keyword") String keyword);
    Optional<KhachHang> findKhachHangByIdKhachHang(int idKhachHang);
    @Query("SELECT k FROM KhachHang k " +
            "LEFT JOIN k.taiKhoan t " +  // Giả sử rằng bạn đã định nghĩa mối quan hệ giữa KhachHang và TaiKhoan
            "WHERE t IS NULL")
    List<KhachHang> findKhachHangChuaCoTaiKhoan();

    long countByNgayTaoBetween(LocalDate startDate, LocalDate endDate);
    List<KhachHang> findByNgayTaoBetween(LocalDate startDate, LocalDate endDate);
}
