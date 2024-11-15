package com.project.flightManagement.Repository;

import com.project.flightManagement.Enum.ChuyenBayEnum;
import com.project.flightManagement.Model.ChuyenBay;
import com.project.flightManagement.Model.MayBay;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import java.time.LocalDateTime;
import java.util.Date;
import java.util.List;

public interface ChuyenBayRepository extends JpaRepository<ChuyenBay, Integer> {
    @Query("SELECT c FROM ChuyenBay c WHERE c.mayBay.idMayBay = :mayBayId")
    List<ChuyenBay> findByMayBay(@Param("mayBayId") int mayBayId);

    @Query("SELECT cb FROM ChuyenBay cb WHERE (:trangThai IS NULL OR cb.trangThai = :trangThai) " +
            "AND (:thoiGianBatDau IS NULL OR cb.thoiGianBatDauDuTinh >= :thoiGianBatDau) " +
            "AND (:thoiGianKetThuc IS NULL OR cb.thoiGianBatDauDuTinh <= :thoiGianKetThuc)")
    List<ChuyenBay> filterChuyenBay(
            @Param("trangThai") ChuyenBayEnum trangThai,
            @Param("thoiGianBatDau") LocalDateTime thoiGianBatDau,
            @Param("thoiGianKetThuc") LocalDateTime thoiGianKetThuc);

    @Query("SELECT cb FROM ChuyenBay cb WHERE YEAR(cb.ngayBay) = :nam")
    List<ChuyenBay> findChuyenBaysByYear(@Param("nam") int nam);

    @Query("SELECT cb FROM ChuyenBay cb WHERE YEAR(cb.ngayBay) = :year AND MONTH(cb.ngayBay) = :month")
    List<ChuyenBay> findChuyenBayByYearAndMonth(int year, int month);


    // Tìm chuyến bay một chiều
    @Query("SELECT c FROM ChuyenBay c " +
            "JOIN c.tuyenBay t " +
            "JOIN t.sanBayBatDau sbd " +
            "JOIN sbd.thanhPho cityDi " +
            "JOIN t.sanBayKetThuc sbdn " +
            "JOIN sbdn.thanhPho cityDen " +
            "WHERE cityDi.tenThanhPho = :departureCity " +
            "AND cityDen.tenThanhPho = :arrivalCity " +
            "AND c.ngayBay = :departureDate " +
            "AND c.soGhe >= :numberOfTickets")
    List<ChuyenBay> findFlightsOneWay(@Param("departureCity") String departureCity,
                                      @Param("arrivalCity") String arrivalCity,
                                      @Param("departureDate") Date departureDate,
                                      @Param("numberOfTickets") int numberOfTickets);


}
