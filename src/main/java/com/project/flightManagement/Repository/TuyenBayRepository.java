package com.project.flightManagement.Repository;

import java.time.LocalDate;
import java.util.List;
import java.util.Optional;

import com.project.flightManagement.Model.SanBay;
import com.project.flightManagement.Model.TuyenBay;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

public interface TuyenBayRepository extends JpaRepository<TuyenBay, Integer> {
    Optional<TuyenBay> findBySanBayBatDauAndSanBayKetThuc(SanBay sanBayBatDau, SanBay sanBayKetThuc);

    @Query("SELECT tb FROM TuyenBay tb WHERE " +
            "LOWER(tb.sanBayBatDau) LIKE LOWER(CONCAT('%', :keyword, '%')) OR " +
            "LOWER(tb.sanBayKetThuc) LIKE LOWER(CONCAT('%', :keyword, '%'))")
    List<TuyenBay> findByKeywordContainingIgnoreCase(@Param("keyword") String keyword);

    @Query(value = """
    SELECT
        tp_bat_dau.ten_thanh_pho AS thanhPhoBatDau,
        tp_ket_thuc.ten_thanh_pho AS thanhPhoKetThuc,
        COUNT(cb.id_chuyen_bay) AS soChuyenBay
    FROM
        ChuyenBay cb
    JOIN
        TuyenBay tb ON cb.id_tuyen_bay = tb.id_tuyen_bay
    JOIN
        SanBay sb_bat_dau ON tb.san_bay_bat_dau = sb_bat_dau.id_san_bay
    JOIN
        SanBay sb_ket_thuc ON tb.san_bay_ket_thuc = sb_ket_thuc.id_san_bay
    JOIN
        ThanhPho tp_bat_dau ON sb_bat_dau.id_thanh_pho = tp_bat_dau.id_thanh_pho
    JOIN
        ThanhPho tp_ket_thuc ON sb_ket_thuc.id_thanh_pho = tp_ket_thuc.id_thanh_pho
    WHERE
        cb.ngay_bay BETWEEN :startDate AND :endDate
    GROUP BY
        tp_bat_dau.ten_thanh_pho, tp_ket_thuc.ten_thanh_pho
    ORDER BY
        soChuyenBay DESC
    LIMIT 5
    """, nativeQuery = true)
    List<Object[]> getTop5FlightRoutesByFrequency(
            @Param("startDate") LocalDate startDate,
            @Param("endDate") LocalDate endDate);

}


