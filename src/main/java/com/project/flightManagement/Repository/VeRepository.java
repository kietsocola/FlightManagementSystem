package com.project.flightManagement.Repository;

import com.project.flightManagement.Model.Ve;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.time.LocalDate;
import java.util.List;

@Repository
public interface VeRepository extends JpaRepository<Ve, Integer> {
        Page<Ve> findAll(Pageable pageable);

        Page<Ve> findByChuyenBay_IdChuyenBay(int idChuyenBay, Pageable pageable);

        List<Ve> findByChuyenBay_IdChuyenBay(int idChuyenBay);

        boolean existsByHanhKhach_IdHanhKhach(int idHanhKhach);
        @Query("SELECT v FROM Ve v " +
                "LEFT JOIN v.chuyenBay cb " +
                "LEFT JOIN v.hanhKhach hk " +
                "WHERE (:maVe IS NULL OR LOWER(v.maVe) LIKE LOWER(CONCAT('%', :maVe, '%'))) " +
                "AND (:startDate IS NULL OR cb.ngayBay BETWEEN :startDate AND :endDate) " +
                "AND (:cccd IS NULL OR (hk IS NULL OR LOWER(hk.cccd) LIKE LOWER(CONCAT('%', :cccd, '%'))))")
        Page<Ve> searchVeMaVaAndDateBay(
                @Param("maVe") String maVe,
                @Param("startDate") LocalDate startDate,
                @Param("endDate") LocalDate endDate,
                @Param("cccd") String cccd,
                Pageable pageable);
        List<Ve> findByChuyenBay_IdChuyenBayAndHangVe_IdHangVe(int idChuyenBay, int idHangVe);



        Page<Ve> findByMaVeContainingIgnoreCase(String maVe, Pageable pageable);

}
