package com.project.flightManagement.Repository;
import com.project.flightManagement.Model.ChoNgoi;
import com.project.flightManagement.Model.MayBay;
import jdk.dynalink.linker.LinkerServices;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface ChoNgoiRepository extends JpaRepository<ChoNgoi, Integer> {
    public List<ChoNgoi> findByMayBay_ChuyenBayList_IdChuyenBayAndHangVe_IdHangVe(int chuyenBayId, int hangVeId);
    public List<ChoNgoi> findChoNgoiByMayBay(MayBay mayBay);
}