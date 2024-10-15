package com.project.flightManagement.Service.Impl;

import com.project.flightManagement.DTO.QuocGiaDTO.QuocGiaDTO;
import com.project.flightManagement.Mapper.QuocGiaMapper;
import com.project.flightManagement.Model.QuocGia;
import com.project.flightManagement.Repository.QuocGiaRepository;
import com.project.flightManagement.Service.QuocGiaService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.Collections;
import java.util.Optional;
import java.util.stream.StreamSupport;

@Service
public class QuocGiaServicelmpl implements QuocGiaService {
    @Autowired
    private QuocGiaRepository quocGiaRepository;
    @Override
    public Optional<QuocGiaDTO> getQuocGiaById(int id){
        try{
            Optional<QuocGia> quocGia = quocGiaRepository.findById(id);
            Optional<QuocGiaDTO> quocGiaDTO = quocGia.map(QuocGiaMapper::toDTO);
            return quocGiaDTO;
        } catch (Exception e) {
            System.err.println("Error occurred while get nation: " + e.getMessage());
            return null;
        }
    }

    @Override
    public Iterable<QuocGiaDTO> getAllQuocGia(){
        try{
            Iterable<QuocGia> quocGiaList = quocGiaRepository.findAll();
            Iterable<QuocGiaDTO> quocGiaDTOList = StreamSupport.stream(quocGiaList.spliterator(), false).map(QuocGiaMapper::toDTO).toList();
            return quocGiaDTOList;
        } catch (Exception e) {
            System.err.println("Error occurred while fetching nation: " + e.getMessage());
            return Collections.emptyList();
        }
    }
}