package com.project.flightManagement.Service.Impl;

import com.project.flightManagement.DTO.ChoNgoiDTO.ChoNgoiDTO;
import com.project.flightManagement.DTO.MayBayDTO.MayBayDTO;
import com.project.flightManagement.Mapper.ChoNgoiMapper;
import com.project.flightManagement.Mapper.MayBayMapper;
import com.project.flightManagement.Model.ChoNgoi;
import com.project.flightManagement.Model.MayBay;
import com.project.flightManagement.Repository.ChoNgoiRepository;
import com.project.flightManagement.Service.ChoNgoiService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;
import java.util.stream.StreamSupport;
@Service
public class ChoNgoiServicelmpl implements ChoNgoiService {
    @Autowired
    private ChoNgoiRepository cnRepo;

    @Override
    public Iterable<ChoNgoiDTO> getAllChoNgoi() {
        try {
            Iterable<ChoNgoi> choNgoiList = cnRepo.findAll();
            Iterable<ChoNgoiDTO> choNgoiDTOList = StreamSupport.stream(choNgoiList.spliterator(), false).map(ChoNgoiMapper::toDTO).collect(Collectors.toList());
            return choNgoiDTOList;
        } catch (Exception e) {
            System.out.println("Get all seat failed");
            return null;
        }
    }
    @Override
    public Iterable<ChoNgoiDTO> getChoNgoiByMayBay(MayBayDTO mayBayDTO) {
        try {
            Iterable<ChoNgoi> choNgoiList = cnRepo.getChoNgoiByMayBay(MayBayMapper.toEntity(mayBayDTO));
            Iterable<ChoNgoiDTO> cnDTOList = StreamSupport.stream(choNgoiList.spliterator(), false).map(ChoNgoiMapper::toDTO).collect(Collectors.toList());
            return cnDTOList;
        } catch (Exception e) {
            System.out.println("Get list seat by plane failed!!");
            return null;
        }
    }
    @Override
    public Optional<ChoNgoiDTO> addNewChoNgoi(ChoNgoiDTO choNgoiDTO) {
        try {
            ChoNgoi cn = ChoNgoiMapper.toEntity(choNgoiDTO);
            ChoNgoi savedCn = cnRepo.save(cn);
            return Optional.of(ChoNgoiMapper.toDTO(savedCn));
        } catch (Exception e) {
            System.out.println("Add new seat failed!!");
            return Optional.empty();
        }
    }
    @Override
    public Optional<ChoNgoiDTO> deleteChoNgoi(ChoNgoiDTO choNgoiDTO) {
        try {
            ChoNgoi cn = ChoNgoiMapper.toEntity(choNgoiDTO);
            cnRepo.delete(cn);
            return Optional.of(ChoNgoiMapper.toDTO(cn));
        } catch (Exception e) {
            System.out.println("Delete seat failed!!");
            return Optional.empty();
        }
    }
}
