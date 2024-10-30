package com.project.flightManagement.Repository;

import com.project.flightManagement.Model.ChucVu;
import com.project.flightManagement.Model.NhanVien;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import java.util.List;

public interface NhanVienRepository extends JpaRepository<NhanVien, Integer> {
    List<NhanVien> findByEmailContaining(String Email);
    @Query("SELECT nv FROM NhanVien nv WHERE LOWER(nv.hoTen) LIKE LOWER(CONCAT('%', :hoTen, '%'))")
    List<NhanVien> findByHoTenContainingIgnoreCase(@Param("hoTen") String hoTen);
    List<NhanVien> findByCccdContaining(String cccd);
    List<NhanVien> findBySoDienThoaiContaining(String soDienThoai);

    @Query("SELECT n FROM NhanVien n WHERE n.idNhanVien BETWEEN ?1 AND ?2")
    List<NhanVien> findByContentBetween(String start, String end);

    @Query("SELECT nv FROM NhanVien nv WHERE (:hoTen IS NULL OR nv.hoTen LIKE %:hoTen%) " +
            "AND (:email IS NULL OR nv.email LIKE %:email%) " +
            "AND (:soDienThoai IS NULL OR nv.soDienThoai LIKE %:soDienThoai%) " +
            "AND (:cccd IS NULL OR nv.cccd LIKE %:cccd%) " +
            "AND (:chucVu IS NULL OR nv.chucVu = :chucVu)")
    List<NhanVien> filterNhanVien(@Param("hoTen") String hoTen,
                                  @Param("email") String email,
                                  @Param("soDienThoai") String soDienThoai,
                                  @Param("cccd") String cccd,
                                  @Param("chucVu") ChucVu chucVu);
}
