package com.project.flightManagement.Repository;

import java.util.Optional;
import java.time.LocalTime;
import java.util.List;

import com.project.flightManagement.Model.TuyenBay;
import com.project.flightManagement.Enum.ActiveEnum;
import org.springframework.data.jpa.repository.JpaRepository;

public interface TuyenBayRepository extends JpaRepository<TuyenBay, Integer> {
    Optional<TuyenBay> findById(int id);
    List<TuyenBay> findBySanBayBatDau_Id(int idSanBay);
    List<TuyenBay> findBySanBayKetThuc_Id(int idSanBay);
    List<TuyenBay> findByThoiGianChuyenBay(LocalTime thoiGianChuyenBay);
    List<TuyenBay> findByKhoangCach(int khoangCach);
    List<TuyenBay> findByStatus(ActiveEnum status);
}
