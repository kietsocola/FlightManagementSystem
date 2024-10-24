package com.project.flightManagement.Repository;

import com.project.flightManagement.DTO.MayBayDTO.MayBayDTO;
import com.project.flightManagement.Model.ChoNgoi;
import com.project.flightManagement.Model.MayBay;
import org.springframework.data.jpa.repository.JpaRepository;

public interface ChoNgoiRepository extends JpaRepository<ChoNgoi, Integer> {
    public Iterable<ChoNgoi> getChoNgoiByMayBay(MayBay mayBay);

}
