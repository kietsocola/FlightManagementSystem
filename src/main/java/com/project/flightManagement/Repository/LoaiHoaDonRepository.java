package com.project.flightManagement.Repository;

import com.project.flightManagement.Model.LoaiHoaDon;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import java.util.List;

import java.util.Optional;

public interface LoaiHoaDonRepository extends JpaRepository<LoaiHoaDon, Integer> {
    @Query("SELECT loaihd FROM LoaiHoaDon loaihd WHERE LOWER(loaihd.tenLoaiHoaDon) = LOWER(:tenLoaiHD)")
    Optional<LoaiHoaDon> getLoaiHoaDonByTen(@Param("tenLoaiHD") String tenLoaiHD);


    @Query("SELECT loaihd FROM LoaiHoaDon loaihd WHERE " + "LOWER(loaihd.tenLoaiHoaDon) LIKE LOWER(CONCAT('%', :keyWord, '%'))")
    List<LoaiHoaDon> findLoaiHoaDonByKeyWord(@Param("keyWord") String keyWord);
}
