package com.project.flightManagement.Repository;

import com.project.flightManagement.Model.ChucVu;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.Optional;

public interface ChucVuRepositoty extends JpaRepository<ChucVu,Integer> {
    public Optional<ChucVu> findByTen(String ten) ;
}
