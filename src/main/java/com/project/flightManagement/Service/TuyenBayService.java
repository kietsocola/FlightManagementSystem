package com.project.flightManagement.Service;

import java.util.List;
import java.util.Optional;

import com.project.flightManagement.DTO.TuyenBayDTO.TuyenBayDTO;

public interface TuyenBayService {

     Iterable<TuyenBayDTO> getAllTuyenBay();

    Optional<TuyenBayDTO> getTuyenBayById(int id);

    Optional<TuyenBayDTO> addNewTuyenBay(TuyenBayDTO tuyenBayDTO);

    Optional<TuyenBayDTO> updateTuyenBay(TuyenBayDTO tuyenBayDTO);

    Iterable<TuyenBayDTO> getAllTuyenBaySorted(String sortBy);

    List<TuyenBayDTO> getAllTuyenBayIdSanBayBatDau(int idSanBayBatDau);

    List<TuyenBayDTO> getAllTuyenBayIdSanBayKetThuc(int idSanBayKetThuc);

}
