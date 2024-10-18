package com.project.flightManagement.Repository;

import com.project.flightManagement.Model.ChoNgoi;
import com.project.flightManagement.Model.MayBay;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface ChoNgoiRepository extends JpaRepository<ChoNgoi, Integer> {
    List<ChoNgoi> findByMayBay_ChuyenBayList_IdChuyenBayAndHangVe_IdHangVe(int idChuyenBay, int idHangVe);
    List<ChoNgoi> findByMayBay(MayBay mayBay);
}
