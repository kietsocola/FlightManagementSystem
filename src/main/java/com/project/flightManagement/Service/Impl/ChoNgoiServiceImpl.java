package com.project.flightManagement.Service.Impl;

import com.project.flightManagement.DTO.ChoNgoiDTO.ChoNgoiDTO;
import com.project.flightManagement.Mapper.ChoNgoiMapper;
import com.project.flightManagement.Model.ChoNgoi;
import com.project.flightManagement.Model.MayBay;
import com.project.flightManagement.Repository.ChoNgoiRepository;
import com.project.flightManagement.Service.ChoNgoiService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.Collections;
import java.util.List;
import java.util.stream.Collectors;
import java.util.stream.StreamSupport;

@Service
public class ChoNgoiServiceImpl implements ChoNgoiService {
    @Autowired
    ChoNgoiRepository repo;
    @Override
    public List<ChoNgoiDTO> getAllChoNgoiByIdChuyenBayandHangVe(int idCB, int hangVe) {
        List<ChoNgoi> choNgoiList = repo.findByMayBay_ChuyenBayList_IdChuyenBayAndHangVe_IdHangVe(idCB, hangVe);
        return choNgoiList.stream()
                .map(ChoNgoiMapper::toDTO)
                .collect(Collectors.toList());
    }
    @Override
    public Iterable<ChoNgoiDTO> getAllChoNgoi() {
        try{
            Iterable<ChoNgoi> listnv =  repo.findAll();
            Iterable<ChoNgoiDTO> listnvDTO = StreamSupport.stream(listnv.spliterator(),false)
                    .map(ChoNgoiMapper::toDTO)
                    .toList();
            return listnvDTO;
        }catch (Exception e){
            System.err.println("error occurred while fetching customers :" + e.getMessage());
            return Collections.emptyList();
        }
    }

    @Override
    public Iterable<ChoNgoiDTO> getChoNgoiByMayBay(MayBay mayBay) {
        try{
            Iterable<ChoNgoi> listnv =  repo.findByMayBay(mayBay);
            Iterable<ChoNgoiDTO> listnvDTO = StreamSupport.stream(listnv.spliterator(),false)
                    .map(ChoNgoiMapper::toDTO)
                    .toList();
            return listnvDTO;
        }catch (Exception e){
            System.err.println("error occurred while fetching customers :" + e.getMessage());
            return Collections.emptyList();
        }
    }
}
