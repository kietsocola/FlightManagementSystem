package com.project.flightManagement.Repository;

import com.project.flightManagement.Model.DanhGia;
import com.project.flightManagement.Model.HangBay;
import com.project.flightManagement.Model.KhachHang;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import java.time.LocalDateTime;

public interface DanhGiaRepository extends JpaRepository<DanhGia, Integer> {
    Iterable<DanhGia> findByKhachHangOrderByThoiGianTaoDesc(KhachHang khachHang);
    Iterable<DanhGia> findByHangBay(HangBay hangBay);

    @Query("SELECT d FROM DanhGia d WHERE LOWER(d.khachHang.hoTen) LIKE LOWER(CONCAT('%', :tenKhachHang, '%')) ORDER BY d.thoiGianTao DESC")
    Iterable<DanhGia> findByTenKhachHang(@Param("tenKhachHang") String tenKhachHang);

    @Query("SELECT d FROM DanhGia d WHERE d.thoiGianTao BETWEEN :startTime AND :endTime")
    Iterable<DanhGia> findByThoiGianTaoBetween(@Param("startTime") LocalDateTime startTime, @Param("endTime") LocalDateTime endTime);

    @Query("SELECT d FROM DanhGia d WHERE d.thoiGianTao >= :startTime ORDER BY d.thoiGianTao DESC")
    Iterable<DanhGia> findByThoiGianTaoFromStartTime(@Param("startTime") LocalDateTime startTime);

    @Query("SELECT d FROM DanhGia d WHERE d.thoiGianTao <= :endTime ORDER BY d.thoiGianTao DESC")
    Iterable<DanhGia> findByThoiGianTaoFromEndTime(@Param("endTime") LocalDateTime endTime);
}