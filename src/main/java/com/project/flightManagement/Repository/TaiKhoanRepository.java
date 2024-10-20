package com.project.flightManagement.Repository;

import com.project.flightManagement.Model.TaiKhoan;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

import java.util.Optional;

public interface TaiKhoanRepository extends JpaRepository<TaiKhoan, Integer> {

    @Query("SELECT tk from TaiKhoan tk where tk.tenDangNhap = :tendangnhap")
    Optional<TaiKhoan> findByTenDangNhap(String tendangnhap);

}
