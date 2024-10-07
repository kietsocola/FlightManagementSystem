package com.project.flightManagement.Repository;

import java.util.Optional;
import java.util.List;

import com.project.flightManagement.Model.TuyenBay;
import org.springframework.data.jpa.repository.JpaRepository;

public interface TuyenBayRepository extends JpaRepository<TuyenBay, Integer> {
    Optional<TuyenBay> findById(int id);
    List<TuyenBay> findBySanBayBatDau_Id(int idSanBay);
    List<TuyenBay> findBySanBayKetThuc_Id(int idSanBay);

}
