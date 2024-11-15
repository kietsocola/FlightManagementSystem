package com.project.flightManagement.Repository;

import com.project.flightManagement.Model.HanhKhach;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.time.LocalDate;
import java.time.LocalDateTime;
import java.util.List;

@Repository
public interface HanhKhachRepository extends JpaRepository<HanhKhach, Integer> {
    @Override
    boolean existsById(Integer idHanhKhach);

    @Query("SELECT " +
            "CASE " +
            "WHEN (YEAR(CURRENT_DATE) - YEAR(CAST(h.ngaySinh AS DATE))) BETWEEN 0 AND 10 THEN '0-10' " +
            "WHEN (YEAR(CURRENT_DATE) - YEAR(CAST(h.ngaySinh AS DATE))) BETWEEN 11 AND 20 THEN '11-20' " +
            "WHEN (YEAR(CURRENT_DATE) - YEAR(CAST(h.ngaySinh AS DATE))) BETWEEN 21 AND 30 THEN '21-30' " +
            "WHEN (YEAR(CURRENT_DATE) - YEAR(CAST(h.ngaySinh AS DATE))) BETWEEN 31 AND 40 THEN '31-40' " +
            "WHEN (YEAR(CURRENT_DATE) - YEAR(CAST(h.ngaySinh AS DATE))) BETWEEN 41 AND 50 THEN '41-50' " +
            "WHEN (YEAR(CURRENT_DATE) - YEAR(CAST(h.ngaySinh AS DATE))) BETWEEN 51 AND 60 THEN '51-60' " +
            "WHEN (YEAR(CURRENT_DATE) - YEAR(CAST(h.ngaySinh AS DATE))) BETWEEN 61 AND 70 THEN '61-70' " +
            "WHEN (YEAR(CURRENT_DATE) - YEAR(CAST(h.ngaySinh AS DATE))) BETWEEN 71 AND 80 THEN '71-80' " +
            "WHEN (YEAR(CURRENT_DATE) - YEAR(CAST(h.ngaySinh AS DATE))) BETWEEN 81 AND 90 THEN '81-90' " +
            "ELSE '91-100' " +
            "END AS ageGroup, " +
            "CASE " +
            "WHEN :groupByType = 'MONTH' THEN MONTH(hd.thoiGianLap) " +
            "WHEN :groupByType = 'QUARTER' THEN (MONTH(hd.thoiGianLap) - 1) / 3 + 1 " +
            "WHEN :groupByType = 'YEAR' THEN YEAR(hd.thoiGianLap) " +
            "END AS timeGroup, " +
            "COUNT(v) " +
            "FROM Ve v " +
            "JOIN v.hanhKhach h " +
            "JOIN ChiTietHoaDon cthd ON cthd.ve.idVe = v.idVe " +
            "JOIN cthd.hoaDon hd " +
            "WHERE hd.thoiGianLap BETWEEN :startDate AND :endDate " +
            "AND hd.status = 'PAID' " +
            "GROUP BY ageGroup, timeGroup " +
            "ORDER BY timeGroup, ageGroup")
    List<Object[]> findPassengerCountByGroup(@Param("startDate") LocalDateTime startDate,
                                             @Param("endDate") LocalDateTime endDate,
                                             @Param("groupByType") String groupByType);

}
