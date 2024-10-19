package com.project.flightManagement.Service.Impl;

import com.project.flightManagement.DTO.QuyDinhDTO.QuyDinhDTO;
import com.project.flightManagement.Mapper.QuyDinhMapper;
import com.project.flightManagement.Model.QuyDinh;
import com.project.flightManagement.Repository.QuyDinhRepository;
import com.project.flightManagement.Service.QuyDinhService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.Collections;
import java.util.Optional;
import java.util.stream.StreamSupport;

@Service
public class QuyDinhServiceImpl implements QuyDinhService {

    @Autowired
    private QuyDinhRepository repo;

    @Override
    public Optional<QuyDinhDTO> addQuyDinh(QuyDinhDTO quyDinhDTO) {
        QuyDinh qd = QuyDinhMapper.toEntity(quyDinhDTO);
        QuyDinh saveqd = repo.save(qd);
        return Optional.of(QuyDinhMapper. toDTO(saveqd));
    }

    @Override
    public Iterable<QuyDinhDTO> getAllQuyDinh() {
        try{
            Iterable<QuyDinh> lisqd =  repo.findAll();
            Iterable<QuyDinhDTO> listqdDTO = StreamSupport.stream(lisqd.spliterator(),false)
                    .map(QuyDinhMapper::toDTO)
                    .toList();
            return listqdDTO;
        }catch (Exception e){
            System.err.println("error occurred while fetching customers :" + e.getMessage());
            return Collections.emptyList();
        }
    }

    @Override
    public Optional<QuyDinhDTO> getQuyDinhById(int id) {
        Optional<QuyDinh> qd = repo.findById(id);
        return qd.map(QuyDinhMapper::toDTO);
    }
}
