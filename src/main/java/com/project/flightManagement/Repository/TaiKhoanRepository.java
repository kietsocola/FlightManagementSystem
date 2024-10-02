package com.project.flightManagement.Repository;

import com.project.flightManagement.Model.TaiKhoan;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.repository.query.Param;

import java.util.Optional;

public interface TaiKhoanRepository extends JpaRepository<TaiKhoan, Integer> {
    boolean existsTaiKhoanByTenDangNhap(String userName);
    Optional<TaiKhoan> findTaiKhoanByTenDangNhap(String userName);
}
