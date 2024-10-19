package com.project.flightManagement.Service.Impl;

import com.project.flightManagement.DTO.CongDTO.CongDTO;
import com.project.flightManagement.Mapper.CongMapper;
import com.project.flightManagement.Model.Cong;
import com.project.flightManagement.Model.SanBay;
import com.project.flightManagement.Repository.CongRepository;
import com.project.flightManagement.Service.CongService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.Optional;
import java.util.stream.StreamSupport;

@Service
public class CongServiceImpl implements CongService {

    @Autowired
    private CongRepository repo;

    @Override
    public Iterable<CongDTO> getAllCong() {
        Iterable<Cong> dscong = repo.findAll();
        Iterable<CongDTO> listnvDTO = StreamSupport.stream(dscong.spliterator(),false)
                .map(CongMapper::toDTO)
                .toList();
        return listnvDTO;
    }

    @Override
    public Optional<CongDTO> getCongById(int idCong) {
        try{
            Optional<Cong> cong = repo.findById(idCong);
            return cong.map(CongMapper::toDTO);
        }catch (Exception e){
            System.err.println("Error occurred while get customer: " + e.getMessage());
            return Optional.empty();
        }
    }

    @Override
    public Iterable<CongDTO> getCongBySanBay(SanBay sanBay) {
        Iterable<Cong> dscong = repo.findBySanBay(sanBay);
        Iterable<CongDTO> listnvDTO = StreamSupport.stream(dscong.spliterator(),false)
                .map(CongMapper::toDTO)
                .toList();
        return listnvDTO;
    }
}
