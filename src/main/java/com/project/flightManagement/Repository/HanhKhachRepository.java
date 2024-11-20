package com.project.flightManagement.Repository;

import com.project.flightManagement.Model.ChiTietHoaDon;
import com.project.flightManagement.Model.HanhKhach;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.time.LocalDate;
import java.time.LocalDateTime;
import java.util.List;
import java.util.Optional;

@Repository
public interface HanhKhachRepository extends JpaRepository<HanhKhach, Integer> {
    @Override
    boolean existsById(Integer idHanhKhach);

    Optional<HanhKhach> findByCccd(String cccd);
}
