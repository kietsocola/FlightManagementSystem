package com.project.flightManagement.Repository;

import com.project.flightManagement.Model.SanBay;
import com.project.flightManagement.Model.ThanhPho;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;


import java.util.List;
import java.util.Optional;


public interface SanBayRepository extends JpaRepository<SanBay, Integer> {


    @Query("SELECT k FROM SanBay k WHERE " +
            "LOWER(k.tenSanBay) LIKE LOWER(CONCAT('%', :keyword, '%')) OR " +
            "LOWER(k.diaChi) LIKE LOWER(CONCAT('%', :keyword, '%')) OR " +
            "LOWER(k.thanhPho.tenThanhPho) LIKE LOWER(CONCAT('%', :keyword, '%')) OR " +
            "LOWER(k.iataSanBay) LIKE LOWER(CONCAT('%', :keyword, '%')) OR " +
            "LOWER(k.icaoSanBay) LIKE LOWER(CONCAT('%', :keyword, '%'))")
    List<SanBay> findByKeywordContainingIgnoreCase(@Param("keyword") String keyWord);

    List<SanBay> findSanBayByThanhPho(ThanhPho thanhPho);
    SanBay findSanBayByIcaoSanBay(String icaoSanBay);
    SanBay findSanBayByIataSanBay(String iataSanBay);
}