package com.project.flightManagement.Repository;

import com.project.flightManagement.Model.Cong;
import com.project.flightManagement.Model.SanBay;
import org.springframework.data.jpa.repository.JpaRepository;

public interface CongReposity extends JpaRepository<Cong,Integer> {
        public Iterable<Cong> findCongBySanBay(SanBay sanBay);
}
