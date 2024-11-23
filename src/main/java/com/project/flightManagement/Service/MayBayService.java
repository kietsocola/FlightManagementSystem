package com.project.flightManagement.Service;

import com.nimbusds.jose.util.Pair;
import com.project.flightManagement.DTO.HangBayDTO.HangBayDTO;
import com.project.flightManagement.DTO.MayBayDTO.MayBayDTO;
import com.project.flightManagement.Model.HangBay;
import com.project.flightManagement.Model.MayBay;
import com.project.flightManagement.Model.SanBay;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;
import java.util.List;
import java.util.Map;
import java.util.Optional;

@Service
public interface MayBayService {
    public Optional<MayBayDTO> getMayBayById(int id);
    public Optional<MayBayDTO> getMayBayBySoHieu(String soHieu);
    public Optional<MayBayDTO> addNewMayBay(MayBayDTO mayBayDTO);
    public Optional<MayBayDTO> updateMayBay(MayBayDTO mayBayDTO);
    public Iterable<MayBayDTO> getAllMayBay();
    public List<MayBayDTO> findMayBayByHangBay(HangBay hangBay);
    public List<MayBayDTO> findMayBayByTenMayBay(String keyword);
    public Iterable<MayBayDTO> getAllMayBaySorted(String sortBy, String order);
    public Optional<MayBayDTO> blockMayBay(int id);
    public Optional<MayBayDTO> unblockMayBay(int id);
    public List<MayBayDTO> findMayBayBySanBay(SanBay sanBay);
    String getHoursOfPlane(int id);
    List<Pair<String, Double>> calculateHoursOfPlane(String period, int month, int quarter, int year);
    List<Pair<String, Double>> getTop5PlaneHasHighestFlightHours(int month, int year);
    int getTotalPlane();
}
