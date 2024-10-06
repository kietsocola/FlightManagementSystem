package com.project.flightManagement.Repository;

import com.project.flightManagement.DTO.HangBayDTO.HangBayDTO;
import com.project.flightManagement.Model.HangBay;
import com.project.flightManagement.Model.MayBay;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;
import java.util.Optional;

public interface MayBayRepository extends JpaRepository<MayBay, Integer> {
    MayBay findByIcaoMayBay(String icaoMayBay);
    MayBay findBySoHieu(String soHieu);
    List<MayBay> findByTenMayBay(String keyword);
    List<MayBay> findByHangBay(HangBay hangBay);

}
