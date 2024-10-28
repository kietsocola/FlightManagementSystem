package com.project.flightManagement.Repository;

import com.project.flightManagement.Model.PhuongThucThanhToan;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import java.util.List;
import java.util.Optional;

public interface PTTTRepository extends JpaRepository<PhuongThucThanhToan, Integer> {

    @Query("SELECT pttt FROM PhuongThucThanhToan pttt WHERE " + "LOWER(pttt.tenPhuongThucTT) LIKE LOWER(CONCAT('%', :keyWord, '%'))")
    List<PhuongThucThanhToan> getPhuongThucThanhToanByKeyWord(@Param("keyWord") String keyWord);

    @Query("SELECT pttt FROM PhuongThucThanhToan pttt WHERE " + "LOWER(pttt.tenPhuongThucTT) = LOWER(:tenPTTT)")
    Optional<PhuongThucThanhToan> getPhuongThucThanhToanByTen(@Param("tenPTTT") String tenPTTT);
}
