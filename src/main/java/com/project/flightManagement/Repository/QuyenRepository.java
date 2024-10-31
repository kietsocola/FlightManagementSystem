package com.project.flightManagement.Repository;

import com.project.flightManagement.Model.Quyen;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.Optional;

public interface QuyenRepository extends JpaRepository<Quyen, Integer> {
    Optional<Quyen> findQuyenByIdQuyen(int idQuyen);
    Optional<Quyen> findQuyenByTenQuyen(String tenQuyen);
    Page<Quyen> findAll(Pageable pageable);
    Page<Quyen> findByTenQuyenContainingIgnoreCase(String name, Pageable pageable);

    boolean existsByTenQuyen(String tenQuyen);

    boolean existsByTenQuyenAndIdQuyenNot(String tenQuyen, int idQuyen);

}
