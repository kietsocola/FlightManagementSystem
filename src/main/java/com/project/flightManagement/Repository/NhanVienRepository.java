package com.project.flightManagement.Repository;

import com.project.flightManagement.Model.NhanVien;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

import java.util.List;

public interface NhanVienRepository extends JpaRepository<NhanVien, Integer> {
    List<NhanVien> findByEmailContaining(String Email);
    List<NhanVien> findByHoTenContaining(String Hoten);
    List<NhanVien> findByCccdContaining(String cccd);
    List<NhanVien> findBySoDienThoaiContaining(String soDienThoai);

    @Query("SELECT n FROM NhanVien n WHERE n.idNhanVien BETWEEN ?1 AND ?2")
    List<NhanVien> findByContentBetween(String start, String end);
}
