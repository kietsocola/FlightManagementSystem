package com.project.flightManagement.Service.Impl;

import com.project.flightManagement.DTO.ThanhPhoDTO.ThanhPhoDTO;
import com.project.flightManagement.Mapper.SanBayMapper;
import com.project.flightManagement.Mapper.ThanhPhoMapper;
import com.project.flightManagement.Model.QuocGia;
import com.project.flightManagement.Model.SanBay;
import com.project.flightManagement.Model.ThanhPho;
import com.project.flightManagement.Repository.ThanhPhoRepository;
import com.project.flightManagement.Service.ThanhPhoService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.Collections;
import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;
import java.util.stream.StreamSupport;

@Service
public class ThanhPhoServicelmpl implements ThanhPhoService {
    @Autowired
    private ThanhPhoRepository ThanhPhoRepository;
    private ThanhPhoRepository thanhPhoRepository;

    @Override
    public Optional<ThanhPhoDTO> getThanhPhoById(int id){
        try{
            Optional<ThanhPho> ThanhPho = ThanhPhoRepository.findById(id);
            Optional<ThanhPhoDTO> ThanhPhoDTO = ThanhPho.map(ThanhPhoMapper::toDTO);
            return ThanhPhoDTO;
        } catch (Exception e) {
            System.err.println("Error occurred while get city: " + e.getMessage());
            return null;
        }
    }

    @Override
    public Iterable<ThanhPhoDTO> getAllThanhPho(){
        try{
            Iterable<ThanhPho> ThanhPhoList = ThanhPhoRepository.findAll();
            Iterable<ThanhPhoDTO> ThanhPhoDTOList = StreamSupport.stream(ThanhPhoList.spliterator(), false).map(ThanhPhoMapper::toDTO).toList();
            return ThanhPhoDTOList;
        } catch (Exception e) {
            System.err.println("Error occurred while fetching city: " + e.getMessage());
            return Collections.emptyList();
        }
    }
    @Override
    public Iterable<ThanhPhoDTO> getAllThanhPhoByQuocGia(QuocGia quocGia) {
        List<ThanhPho> thanhPhoList = ThanhPhoRepository.findByQuocGia(quocGia);
        return thanhPhoList.stream().map(ThanhPhoMapper::toDTO).collect(Collectors.toList());
    }
}
