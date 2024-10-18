package com.project.flightManagement.Repository;

import com.project.flightManagement.Model.ChiTietHoaDon;
import org.springframework.data.domain.Sort;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

import java.util.List;

public interface ChiTietHoaDonRepository extends JpaRepository<ChiTietHoaDon, Integer> {

    @Query("SELECT cthd FROM ChiTietHoaDon cthd WHERE cthd.hoaDon.idHoaDon = :idHoaDon")
    List<ChiTietHoaDon> findChiTietHoaDonByIdHoaDon(int idHoaDon);
    @Query("SELECT cthd FROM ChiTietHoaDon cthd WHERE cthd.hoaDon.idHoaDon = :idHoaDon")
    List<ChiTietHoaDon> getListChiTietHoaDonSorted(int idHoaDon, Sort sort);
}
