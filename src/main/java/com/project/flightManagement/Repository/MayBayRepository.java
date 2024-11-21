package com.project.flightManagement.Repository;

import com.project.flightManagement.Model.HangBay;
import com.project.flightManagement.Model.KhachHang;
import com.project.flightManagement.Model.MayBay;
import com.project.flightManagement.Model.SanBay;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import java.time.LocalDate;
import java.util.List;

public interface MayBayRepository extends JpaRepository<MayBay, Integer> {
    MayBay findBySoHieu(String soHieu);
    List<MayBay> findMayBayBySanBay(SanBay sanBay);
    List<MayBay> findByHangBay(HangBay hangBay);
    @Query("SELECT k FROM MayBay k WHERE " +
            "LOWER(k.tenMayBay) LIKE LOWER(CONCAT('%', :keyword, '%')) OR " +
            "LOWER(k.soHieu) LIKE LOWER(CONCAT('%', :keyword, '%')) OR " +
            "LOWER(k.hangBay.tenHangBay) LIKE LOWER(CONCAT('%', :keyword, '%')) OR " +
            "LOWER(k.icaoMayBay) LIKE LOWER(CONCAT('%', :keyword, '%')) OR " +
            "LOWER(k.sanBay.tenSanBay) LIKE LOWER(CONCAT('%', :keyword, '%'))")
    List<MayBay> findByKeywordContainingIgnoreCase(@Param("keyword") String keyword);
}
