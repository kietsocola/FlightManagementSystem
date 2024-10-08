package com.project.flightManagement.Repository;

import com.project.flightManagement.Model.ChucNang;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.Optional;

public interface ChucNangRepository extends JpaRepository<ChucNang, Integer> {

    Optional<ChucNang> findChucNangByIdChucNang(int idChucNang);
}
