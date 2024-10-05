package com.project.flightManagement.Service;

import java.util.List;
import java.util.Optional;

import com.project.flightManagement.DTO.TuyenBayDTO.TuyenBayDTO;

public interface TuyenBayService {

    public Iterable<TuyenBayDTO> getAllTuyenBay();

    public Optional<TuyenBayDTO> getTuyenBayById(int id);

    public Optional<TuyenBayDTO> addNewTuyenBay(TuyenBayDTO tuyenBayDTO);

    public Optional<TuyenBayDTO> updateTuyenBay(TuyenBayDTO tuyenBayDTO);

    public Iterable<TuyenBayDTO> getAllTuyenBaySorted(String sortBy);

    public List<TuyenBayDTO> getAllTuyenBayIdSanBayBatDau(int idSanBayBatDau);

    public List<TuyenBayDTO> getAllTuyenBayIdSanBayKetThuc(int idSanBayKetThuc);

}
