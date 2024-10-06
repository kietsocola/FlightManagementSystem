package com.project.flightManagement.Service.Impl;

import com.project.flightManagement.DTO.HangBayDTO.HangBayDTO;
import com.project.flightManagement.DTO.MayBayDTO.MayBayDTO;
import com.project.flightManagement.Mapper.HangBayMapper;
import com.project.flightManagement.Mapper.MayBayMapper;
import com.project.flightManagement.Model.HangBay;
import com.project.flightManagement.Model.MayBay;
import com.project.flightManagement.Repository.HangBayRepository;
import com.project.flightManagement.Service.HangBayService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.Collections;
import java.util.List;
import java.util.Optional;
import java.util.stream.StreamSupport;

@Service
public class HangBayServicelmpl implements HangBayService {
    @Autowired
    private HangBayRepository hbRepo;

    @Override
    public Optional<HangBayDTO> getHangBayById(int id){
        try {
            Optional<HangBay> hb = hbRepo.findById(id);
            Optional<HangBayDTO> hbDTO = hb.map(HangBayMapper::toDTO);
            return hbDTO;
        } catch (Exception e) {
            System.err.println("Error occurred while get plane: " + e.getMessage());
            return null;
        }
    }
    @Override
    public Iterable<HangBayDTO> getAllHangBay(){
        try {
            Iterable<HangBay> hb = hbRepo.findAll();
            Iterable<HangBayDTO> hbDTO = StreamSupport.stream(hb.spliterator(),false).map(HangBayMapper::toDTO).toList();
            return hbDTO;
        } catch (Exception e) {
            System.err.println("Error occurred while fetching airline: " + e.getMessage());
            return Collections.emptyList();
        }
    }
}
