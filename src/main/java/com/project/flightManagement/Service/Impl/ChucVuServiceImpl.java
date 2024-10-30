package com.project.flightManagement.Service.Impl;

import com.project.flightManagement.DTO.ChucVuDTO.ChucVuDTO;
import com.project.flightManagement.Mapper.ChucVuMapper;
import com.project.flightManagement.Mapper.NhanVienMapper;
import com.project.flightManagement.Model.ChucVu;
import com.project.flightManagement.Model.NhanVien;
import com.project.flightManagement.Repository.ChucVuRepository;
import com.project.flightManagement.Repository.ChucVuRepository;
import com.project.flightManagement.Service.ChucVuService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.Collections;
import java.util.Optional;
import java.util.stream.StreamSupport;

@Service
public class ChucVuServiceImpl implements ChucVuService {

    @Autowired
    ChucVuRepository repo;

    @Override
    public Optional<ChucVuDTO> addChucVu(ChucVuDTO chucVuDTO) {
        ChucVu cv = ChucVuMapper.toEntity(chucVuDTO);
        ChucVu saveCv = repo.save(cv);
        return Optional.of(ChucVuMapper. toDTO(saveCv));
    }

    @Override
    public Iterable<ChucVuDTO> getAllChucVu() {


        try{
            Iterable<ChucVu> listcv =  repo.findAll();
            Iterable<ChucVuDTO> listcvDTO = StreamSupport.stream(listcv.spliterator(),false)
                    .map(ChucVuMapper::toDTO)
                    .toList();
            return listcvDTO;
        }catch (Exception e){
            System.err.println("error occurred while fetching customers :" + e.getMessage());
            return Collections.emptyList();
        }
    }

    @Override
    public Optional<ChucVuDTO> getChucVuByTen(String ten) {
        try{
            Optional<ChucVu> cv = repo.findByTen(ten);
            return cv.map(ChucVuMapper::toDTO);
        }catch (Exception e){
            System.err.println("Error occurred while get customer: " + e.getMessage());
            return Optional.empty();
        }
    }

    @Override
    public Optional<ChucVuDTO> getChucVuById(int id) {
        try{
            Optional<ChucVu> cv = repo.findById(id);
            return cv.map(ChucVuMapper::toDTO);
        }catch (Exception e){
            System.err.println("Error occurred while get customer: " + e.getMessage());
            return Optional.empty();
        }
    }
}