package com.project.flightManagement.Repository;

import com.project.flightManagement.Model.Quyen;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.Optional;

public interface QuyenRepository extends JpaRepository<Quyen, Integer> {
    Optional<Quyen> findQuyenByIdQuyen(int idQuyen);
}
