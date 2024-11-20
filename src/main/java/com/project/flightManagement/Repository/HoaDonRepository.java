package com.project.flightManagement.Repository;

import com.project.flightManagement.Model.ChiTietHoaDon;
import com.project.flightManagement.Model.HoaDon;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import java.time.LocalDateTime;
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

    @Query("SELECT SUM(hd.tongTien) FROM HoaDon hd WHERE MONTH(hd.thoiGianLap) = :month AND YEAR(hd.thoiGianLap) = :year")
    Double findRevenueByMonth(@Param("month") int month, @Param("year") int year);

    @Query("SELECT SUM(hd.tongTien) FROM HoaDon hd WHERE QUARTER(hd.thoiGianLap) = :quarter AND YEAR(hd.thoiGianLap) = :year")
    Double findRevenueByQuarter(@Param("quarter") int quarter, @Param("year") int year);

    @Query("SELECT SUM(hd.tongTien) FROM HoaDon hd WHERE YEAR(hd.thoiGianLap) = :year")
    Double findRevenueByYear(@Param("year") int year);

    @Query("SELECT SUM(hd.tongTien) FROM HoaDon hd WHERE hd.thoiGianLap BETWEEN :startDate AND :endDate")
    Double findRevenueBetweenDates(@Param("startDate") LocalDateTime startDate, @Param("endDate") LocalDateTime endDate);

    // Lấy danh sách các năm có trong hóa đơn
    @Query("SELECT DISTINCT YEAR(h.thoiGianLap) FROM HoaDon h ORDER BY YEAR(h.thoiGianLap) DESC")
    List<Integer> findDistinctYears();
}
