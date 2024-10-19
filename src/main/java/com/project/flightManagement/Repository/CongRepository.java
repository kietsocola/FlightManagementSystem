package com.project.flightManagement.Repository;

import com.project.flightManagement.Model.Cong;
import com.project.flightManagement.Model.SanBay;
import org.springframework.data.jpa.repository.JpaRepository;

public interface CongRepository extends JpaRepository<Cong,Integer> {
        public Iterable<Cong> findBySanBay(SanBay sanBay);
}
