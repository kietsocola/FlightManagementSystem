package com.project.flightManagement.Service.Impl;

import com.project.flightManagement.DTO.ChuyenBayDTO.ChuyenBayDTO;
import com.project.flightManagement.DTO.ChuyenBayDTO.ChuyenBayDTO;
import com.project.flightManagement.Mapper.ChuyenBayMapper;
import com.project.flightManagement.Mapper.ChuyenBayMapper;
import com.project.flightManagement.Model.ChuyenBay;
import com.project.flightManagement.Model.ChuyenBay;
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
        ChuyenBay cb = ChuyenBayMapper.toEntity(cbDTO);
        ChuyenBay savecb = repo.save(cb);
        return Optional.of(ChuyenBayMapper. toDTO(savecb));
    }

    @Override
    public Optional<ChuyenBayDTO> updateChuyenBay(ChuyenBayDTO cbDTO) {
        ChuyenBay cb = ChuyenBayMapper.toEntity(cbDTO);
        ChuyenBay updatecb = repo.save(cb);
        return Optional.of(ChuyenBayMapper.toDTO(updatecb));
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

    @Override
    public Optional<ChuyenBayDTO> getChuyenBayById(int id) {
        Optional<ChuyenBay> cb = repo.findById(id);
        return cb.map(ChuyenBayMapper::toDTO);
    }
    
    
}
