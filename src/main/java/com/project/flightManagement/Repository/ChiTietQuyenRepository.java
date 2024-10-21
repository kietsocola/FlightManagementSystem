package com.project.flightManagement.Repository;

import com.project.flightManagement.Model.ChiTietQuyen;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.transaction.annotation.Transactional;

public interface ChiTietQuyenRepository extends JpaRepository<ChiTietQuyen, Integer> {
    @Transactional
    @Modifying
    @Query("delete from ChiTietQuyen c")
    void deleteFirstBy();
}
