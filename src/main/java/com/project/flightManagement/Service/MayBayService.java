package com.project.flightManagement.Service;

import com.project.flightManagement.DTO.HangBayDTO.HangBayDTO;
import com.project.flightManagement.DTO.MayBayDTO.MayBayDTO;
import com.project.flightManagement.Model.HangBay;
import com.project.flightManagement.Model.MayBay;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Service
public interface MayBayService {
    public Optional<MayBayDTO> getMayBayById(int id);
    public Optional<MayBayDTO> getMayBayByIcaoMayBay(String icaoMayBay);
    public Optional<MayBayDTO> getMayBayBySoHieu(String soHieu);
    public Optional<MayBayDTO> addNewMayBay(MayBayDTO mayBayDTO);
    public Optional<MayBayDTO> updateMayBay(MayBayDTO mayBayDTO);
    public Iterable<MayBayDTO> getAllMayBay();
    public List<MayBayDTO> findMayBayByHangBay(HangBay hangBay);
    public List<MayBayDTO> findMayBayByTenMayBay(String keyword);
    public Iterable<MayBayDTO> getAllMayBaySorted(String sortBy);

}
