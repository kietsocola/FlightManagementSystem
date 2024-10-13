package com.project.flightManagement.Service.Impl;

import com.project.flightManagement.DTO.ChuyenBayDTO.ChuyenBayDTO;
import com.project.flightManagement.DTO.NhanVienDTO.NhanVienDTO;
import com.project.flightManagement.Mapper.ChuyenBayMapper;
import com.project.flightManagement.Mapper.NhanVienMapper;
import com.project.flightManagement.Model.ChuyenBay;
import com.project.flightManagement.Model.NhanVien;
import com.project.flightManagement.Repository.ChuyenBayReposity;
import com.project.flightManagement.Service.ChuyenBayService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.Collections;
import java.util.Optional;
import java.util.stream.StreamSupport;



@Service

public class ChuyenBayImpl implements ChuyenBayService {

    @Autowired
    private ChuyenBayReposity repo;

    @Override
    public Optional<ChuyenBayDTO> addChuyenBay(ChuyenBayDTO cbDTO) {
        return Optional.empty();
    }

    @Override
    public Optional<ChuyenBayDTO> updateChuyenBay(ChuyenBayDTO cbDTO) {
        return Optional.empty();
    }

    @Override
    public Iterable<ChuyenBayDTO> getAllChuyenBay() {
        try{
            Iterable<ChuyenBay> listcb =  repo.findAll();
            Iterable<ChuyenBayDTO> listcbDTO = StreamSupport.stream(listcb.spliterator(),false)
                    .map(ChuyenBayMapper::toDTO)
                    .toList();
            return listcbDTO;
        }catch (Exception e){
            System.err.println("error occurred while fetching customers :" + e.getMessage());
            return Collections.emptyList();
        }
    }
}
