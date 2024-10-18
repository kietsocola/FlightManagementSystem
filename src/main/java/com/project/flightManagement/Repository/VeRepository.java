package com.project.flightManagement.Repository;

import com.project.flightManagement.Model.Ve;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface VeRepository extends JpaRepository<Ve, Integer> {
}
