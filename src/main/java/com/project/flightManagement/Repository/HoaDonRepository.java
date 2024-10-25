package com.project.flightManagement.Repository;

import com.project.flightManagement.Model.ChiTietHoaDon;
import com.project.flightManagement.Model.HoaDon;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import java.util.List;

public interface HoaDonRepository extends JpaRepository<HoaDon, Integer> {
    @Query("SELECT hd FROM HoaDon hd WHERE " +
            "LOWER(hd.khachHang.hoTen) LIKE LOWER(CONCAT('%', :keyWord, '%')) OR " +
            "LOWER(hd.nhanVien.hoTen) LIKE LOWER(CONCAT('%', :keyWord, '%')) OR " +
            "LOWER(hd.loaiHoaDon.tenLoaiHoaDon) LIKE LOWER(CONCAT('%', :keyWord, '%')) OR " +
            "LOWER(hd.phuongThucTT.tenPhuongThucTT) LIKE LOWER(CONCAT('%', :keyWord, '%')) OR " +
            "CAST(hd.soLuongVe AS string) LIKE CONCAT('%', :keyWord, '%') OR " +
            "CAST(hd.tongTien AS string) LIKE CONCAT('%', :keyWord, '%')")
    List<HoaDon> getHoaDonByKeyWord(@Param("keyWord") String keyWord);

    @Query("SELECT hd from HoaDon hd WHERE " +
            "hd.nhanVien.idNhanVien = :idNV")
    List<HoaDon> findHoaDonByNhanVien(int idNV);

    @Query("SELECT hd from HoaDon hd WHERE " +
            "hd.khachHang.idKhachHang = :idKH")
    List<HoaDon> findHoaDonByKhachHang(int idKH);

    @Query("SELECT hd from HoaDon hd WHERE " +
            "hd.phuongThucTT.idPhuongThucTT = :idPTTT")
    List<HoaDon> findHoaDonByPTTT(int idPTTT);
    @Query("SELECT hd from HoaDon hd WHERE " +
            "hd.loaiHoaDon.idLoaiHoaDon = :idLoaiHD")
    List<HoaDon> findHoaDonByLoaiHD(int idLoaiHD);
}
