package com.project.flightManagement.Service;

import java.util.List;
import java.util.Optional;

import com.project.flightManagement.DTO.TuyenBayDTO.TuyenBayDTO;
import com.project.flightManagement.Model.TuyenBay;

public interface TuyenBayService {

    public Iterable<TuyenBayDTO> getAllTuyenBay();

    public Optional<TuyenBayDTO> getTuyenBayByIdTuyenBay(int id);

    public Optional<TuyenBayDTO> addNewTuyenBay(TuyenBayDTO tuyenBayDTO);

    public Optional<TuyenBayDTO> updateTuyenBay(Integer idTuyenBay, TuyenBayDTO tuyenBayDTO);

    public Iterable<TuyenBayDTO> getAllTuyenBaySorted(String sortBy, String direction);

    public List<TuyenBayDTO> findBySanBayBatDau(String keyword);

    public String deleteTuyenBay(int id);

}
