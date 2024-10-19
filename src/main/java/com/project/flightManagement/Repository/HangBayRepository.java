package com.project.flightManagement.Repository;

import com.project.flightManagement.Model.HangBay;
import com.project.flightManagement.Model.MayBay;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import java.util.List;

public interface HangBayRepository  extends JpaRepository<HangBay, Integer> {
    @Query("SELECT k FROM HangBay k WHERE " +
            "LOWER(k.tenHangBay) LIKE LOWER(CONCAT('%', :keyword, '%')) OR " +
            "LOWER(k.icaoHangBay) LIKE LOWER(CONCAT('%', :keyword, '%')) OR " +
            "LOWER(k.iataHangBay) LIKE LOWER(CONCAT('%', :keyword, '%'))")
    List<HangBay> findByKeywordContainingIgnoreCase(@Param("keyword") String keyword);
}
