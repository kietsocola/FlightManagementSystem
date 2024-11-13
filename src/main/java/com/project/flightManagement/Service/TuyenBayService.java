package com.project.flightManagement.Service;

import java.util.List;
import java.util.Optional;

import com.project.flightManagement.DTO.TuyenBayDTO.TuyenBayDTO;

public interface TuyenBayService {

    public Iterable<TuyenBayDTO> getAllTuyenBay();

    public Optional<TuyenBayDTO> getTuyenBayByIdTuyenBay(int id);

    public Optional<TuyenBayDTO> addNewTuyenBay(TuyenBayDTO tuyenBayDTO);

    public Optional<TuyenBayDTO> updateTuyenBay(Integer idTuyenBay, TuyenBayDTO tuyenBayDTO);

    public Iterable<TuyenBayDTO> getAllTuyenBaySorted(String sortBy, String direction);

    public Optional<TuyenBayDTO> blockTuyenBay(int id);

    public Optional<TuyenBayDTO> unblockTuyenBay(int id);

}
