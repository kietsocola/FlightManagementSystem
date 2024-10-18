package com.project.flightManagement.Repository;

import java.util.List;

import com.project.flightManagement.Model.SanBay;
import com.project.flightManagement.Model.TuyenBay;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

public interface TuyenBayRepository extends JpaRepository<TuyenBay, Integer> {
    TuyenBay findBySanBayBatDau(SanBay idSanBay);

    TuyenBay findBySanBayKetThuc(SanBay idSanBay);

    @Query("SELECT tb FROM TuyenBay tb WHERE " +
            "LOWER(tb.sanBayBatDau) LIKE LOWER(CONCAT('%', :keyword, '%')) OR " +
            "LOWER(tb.sanBayKetThuc) LIKE LOWER(CONCAT('%', :keyword, '%'))")
    List<TuyenBay> findByKeywordContainingIgnoreCase(@Param("keyword") String keyword);


}


