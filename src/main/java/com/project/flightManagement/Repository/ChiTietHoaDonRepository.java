package com.project.flightManagement.Repository;

import com.project.flightManagement.DTO.HanhKhach_HangHoaDTO;
import com.project.flightManagement.Model.ChiTietHoaDon;
import org.springframework.data.domain.Sort;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import java.util.List;

public interface ChiTietHoaDonRepository extends JpaRepository<ChiTietHoaDon, Integer> {

    @Query("SELECT cthd FROM ChiTietHoaDon cthd WHERE cthd.hoaDon.idHoaDon = :idHoaDon")
    List<ChiTietHoaDon> findChiTietHoaDonByIdHoaDon(int idHoaDon);
    @Query("SELECT cthd FROM ChiTietHoaDon cthd WHERE cthd.hoaDon.idHoaDon = :idHoaDon")
    List<ChiTietHoaDon> getListChiTietHoaDonSorted(@Param("idHoaDon") int idHoaDon, Sort sort);

    @Query("SELECT cthd FROM ChiTietHoaDon cthd WHERE " +
            "cthd.hoaDon.idHoaDon = :idHoaDon AND " +
            "LOWER(cthd.hangHoa.tenHangHoa) LIKE LOWER(CONCAT('%', :keyWord, '%')) OR " +
            "LOWER(cthd.ve.maVe) LIKE LOWER(CONCAT('%', :keyWord, '%')) OR " +
            "CAST(cthd.soTien AS string) LIKE CONCAT('%', :keyWord, '%')")
    List<ChiTietHoaDon> getChiTietHoaDonByKeyWord(@Param("idHoaDon") int idHoaDon, @Param("keyWord") String keyWord);

    @Query("SELECT new com.project.flightManagement.DTO.HanhKhach_HangHoaDTO(hh.tenHangHoa, hk.hoTen, cthd.soTien) " +
            "FROM ChiTietHoaDon cthd " +
            "JOIN cthd.hangHoa hh " +
            "JOIN cthd.ve v " +
            "JOIN v.hanhKhach hk")
    List<HanhKhach_HangHoaDTO> findAllChiTietHoaDonWithHanhKhach();



}
