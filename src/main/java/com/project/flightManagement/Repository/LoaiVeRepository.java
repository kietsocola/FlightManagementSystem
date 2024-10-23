package com.project.flightManagement.Repository;

import com.project.flightManagement.Model.LoaiVe;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface LoaiVeRepository extends JpaRepository<LoaiVe, Integer> {
    Page<LoaiVe> findAll(Pageable pageable);
}
