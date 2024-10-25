package com.project.flightManagement.Repository;

import com.project.flightManagement.Model.Ve;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface VeRepository extends JpaRepository<Ve, Integer> {
    Page<Ve> findAll(Pageable pageable);
    Page<Ve> findByChuyenBay_IdChuyenBay(int idChuyenBay, Pageable pageable);

    boolean existsByHanhKhach_IdHanhKhach(int idHanhKhach);
}
