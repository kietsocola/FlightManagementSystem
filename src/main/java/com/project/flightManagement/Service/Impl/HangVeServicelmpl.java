package com.project.flightManagement.Service.Impl;

import com.project.flightManagement.DTO.HangVeDTO.HangVeDTO;
import com.project.flightManagement.Mapper.HangVeMapper;
import com.project.flightManagement.Model.HangVe;
import com.project.flightManagement.Repository.HangVeRepository;
import com.project.flightManagement.Service.HangBayService;
import com.project.flightManagement.Service.HangVeService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.Optional;
import java.util.stream.Collectors;
import java.util.stream.StreamSupport;

@Service
public class HangVeServicelmpl implements HangVeService {
    @Autowired
    private HangVeRepository hvRepo;
    @Override
    public Iterable<HangVeDTO> getAllHangVe() {
        try {
            Iterable<HangVe> hvList = hvRepo.findAll();
            Iterable<HangVeDTO> hvDTOList = StreamSupport.stream(hvList.spliterator(), false).map(HangVeMapper::toDTO).collect(Collectors.toList());
            return hvDTOList;
        } catch (Exception e) {
            return null;
        }
    }
    @Override
    public Optional<HangVeDTO> getHangVeById(int id) {
        try {
            Optional<HangVe> hv = hvRepo.findById(id);
            return hv.map(HangVeMapper::toDTO);
        } catch (Exception e) {
            System.out.println("Get HangVe by id failed");
            return Optional.empty();
        }
    }
}