package com.project.flightManagement.Repository;

import com.project.flightManagement.Enum.ActiveEnum;
import com.project.flightManagement.Model.ChucVu;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import java.util.List;
import java.util.Optional;

public interface ChucVuRepository extends JpaRepository<ChucVu ,Integer> {
    public Optional<ChucVu> findByTen(String ten) ;

    @Query("SELECT c FROM ChucVu c " +
            "WHERE (:ten IS NULL OR c.ten LIKE %:ten%) " +
            "AND (:trangThaiActive IS NULL OR c.trangThaiActive = :trangThaiActive)")
    Iterable<ChucVu> findByTenAndTrangThai(
            @Param("ten") String ten,
            @Param("trangThaiActive") ActiveEnum trangThaiActive);
}
