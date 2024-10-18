package com.project.flightManagement.Service.Impl;

import com.project.flightManagement.DTO.LoaiQuyDinhDTO.LoaiQuyDinhDTO;
import com.project.flightManagement.DTO.QuyDinhDTO.QuyDinhDTO;
import com.project.flightManagement.Mapper.LoaiQuyDinhMapper;
import com.project.flightManagement.Mapper.QuyDinhMapper;
import com.project.flightManagement.Model.LoaiQuyDinh;
import com.project.flightManagement.Model.QuyDinh;
import com.project.flightManagement.Repository.LoaiQuyDinhRepository;
import com.project.flightManagement.Service.LoaiQuyDinhService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.Collections;
import java.util.Optional;
import java.util.stream.StreamSupport;

@Service
public class LoaiQuyDinhImpl implements LoaiQuyDinhService {

    @Autowired
    private LoaiQuyDinhRepository repo;

    @Override
    public Optional<LoaiQuyDinhDTO> add(LoaiQuyDinhDTO loaiQuyDinhDTO) {
        LoaiQuyDinh lqd = LoaiQuyDinhMapper.toEntity(loaiQuyDinhDTO);
        LoaiQuyDinh savelqd = repo.save(lqd);
        return Optional.of(LoaiQuyDinhMapper. toDTO(savelqd));
    }

    @Override
    public Optional<LoaiQuyDinhDTO> update(LoaiQuyDinhDTO loaiQuyDinhDTO) {
        return Optional.empty();
    }

    @Override
    public Iterable<LoaiQuyDinhDTO> getall() {
        try{
            Iterable<LoaiQuyDinh> lislqd =  repo.findAll();
            Iterable<LoaiQuyDinhDTO> listlqdDTO = StreamSupport.stream(lislqd.spliterator(),false)
                    .map(LoaiQuyDinhMapper::toDTO)
                    .toList();
            return listlqdDTO;
        }catch (Exception e){
            System.err.println("error occurred while fetching customers :" + e.getMessage());
            return Collections.emptyList();
        }
    }

    @Override
    public Optional<LoaiQuyDinhDTO> getById(int idLoaiQuyDinh) {
        Optional<LoaiQuyDinh> lqd = repo.findById(idLoaiQuyDinh);
        return lqd.map(LoaiQuyDinhMapper::toDTO);
    }
}
