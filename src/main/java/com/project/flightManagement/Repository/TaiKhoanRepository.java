package com.project.flightManagement.Repository;

import com.project.flightManagement.Model.TaiKhoan;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import java.util.List;
import java.util.Optional;

public interface TaiKhoanRepository extends JpaRepository<TaiKhoan, Integer> {
    boolean existsTaiKhoanByTenDangNhap(String userName);
    Optional<TaiKhoan> findTaiKhoanByTenDangNhap(String userName);
    Optional<TaiKhoan> findTaiKhoanByIdTaiKhoan(int idTaiKhoan);

    Optional<TaiKhoan> findByKhachHang_Email(String email);
    Page<TaiKhoan> findAll(Pageable pageable);
    @Query("SELECT k FROM TaiKhoan k WHERE " +
            "LOWER(k.tenDangNhap) LIKE LOWER(CONCAT('%', :keyword, '%')) OR " +
            "LOWER(k.khachHang.hoTen) LIKE LOWER(CONCAT('%', :keyword, '%')) OR " +
            "LOWER(k.quyen.tenQuyen) LIKE LOWER(CONCAT('%', :keyword, '%')) OR " +
            "LOWER(k.khachHang.email) LIKE LOWER(CONCAT('%', :keyword, '%')) OR " +
            "LOWER(k.khachHang.cccd) LIKE LOWER(CONCAT('%', :keyword, '%'))")
    List<TaiKhoan> findByKeywordContainingIgnoreCase(@Param("keyword") String keyword);
}
