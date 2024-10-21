package com.project.flightManagement.Service;

import com.project.flightManagement.DTO.SanBayDTO.SanBayDTO;
import com.project.flightManagement.Model.ThanhPho;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;
@Service
public interface SanBayService {
    public Optional<SanBayDTO> getSanBayById(int id);
    public Iterable<SanBayDTO> getAllSanBay();
    public List<SanBayDTO> findSanBayByKeyword(String keyword);
    public List<SanBayDTO> getAllSanBaySorted(String sortBy, String order);
    public List<SanBayDTO> getSanBayByThanhPho(ThanhPho thanhPho);
    public Optional<SanBayDTO> addNewSanBay(SanBayDTO sanBayDTO);
    public Optional<SanBayDTO> updateSanBay(SanBayDTO sanBayDTO);
    public Optional<SanBayDTO> getSanBayByIcaoSanBay(String icaoSanBay);
    public Optional<SanBayDTO> getSanBayByIataSanBay(String iataSanBay);
    public Optional<SanBayDTO> blockSanBay(int id);
    public Optional<SanBayDTO> unblockSanBay(int id);
}
