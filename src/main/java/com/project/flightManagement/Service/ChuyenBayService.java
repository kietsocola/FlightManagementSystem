package com.project.flightManagement.Service;

import com.project.flightManagement.DTO.ChuyenBayDTO.ChuyenBayDTO;
import com.project.flightManagement.DTO.NhanVienDTO.NhanVienDTO;
import com.project.flightManagement.Model.ChuyenBay;
import com.project.flightManagement.Repository.ChuyenBayReposity;
import org.springframework.beans.factory.annotation.Autowired;

import java.util.List;
import java.util.Optional;

public interface ChuyenBayService {

    public Optional<ChuyenBayDTO> addChuyenBay(ChuyenBayDTO cbDTO);
    public Optional<ChuyenBayDTO> updateChuyenBay(ChuyenBayDTO cbDTO);

    public Iterable<ChuyenBayDTO> getAllChuyenBay();
    public List<ChuyenBayDTO> getAllChuyenBaySorted(String sortField , String sordOrder);

    public Optional<ChuyenBayDTO> getChuyenBayById(int  id);
}
