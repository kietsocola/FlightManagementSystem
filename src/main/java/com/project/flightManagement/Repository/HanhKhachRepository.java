package com.project.flightManagement.Repository;

import com.project.flightManagement.Model.HanhKhach;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface HanhKhachRepository extends JpaRepository<HanhKhach, Integer> {
    @Override
    boolean existsById(Integer idHanhKhach);
}
