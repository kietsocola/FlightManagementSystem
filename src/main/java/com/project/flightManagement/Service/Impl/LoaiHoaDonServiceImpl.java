package com.project.flightManagement.Service.Impl;

import com.project.flightManagement.DTO.LoaiHoaDonDTO.LoaiHoaDonDTO;
import com.project.flightManagement.Mapper.LoaiHoaDonMapper;
import com.project.flightManagement.Model.LoaiHoaDon;
import com.project.flightManagement.Repository.LoaiHoaDonReposity;
import com.project.flightManagement.Service.LoaiHoaDonService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.Collections;
import java.util.Optional;
import java.util.stream.StreamSupport;

@Service
public class LoaiHoaDonServiceImpl implements LoaiHoaDonService {
    @Autowired
    private LoaiHoaDonReposity loaiHdRepo;

    @Override
    public Optional<LoaiHoaDonDTO> addLoaiHoaDon(LoaiHoaDonDTO loaiHoaDonDTO) {
        Optional<LoaiHoaDon> existingLoaiHD = loaiHdRepo.findById(loaiHoaDonDTO.getIdLoaiHD());
        if (!existingLoaiHD.isPresent()) {
            LoaiHoaDon loaiHD = LoaiHoaDonMapper.toEntity(loaiHoaDonDTO);
            LoaiHoaDon updatedLoaiHD = loaiHdRepo.save(loaiHD);
            return Optional.of(LoaiHoaDonMapper.toDTO(updatedLoaiHD));
        } else {
            System.err.println("Loai Hoa Don exist!");
            return null;
        }
    }

    @Override
    public Optional<LoaiHoaDonDTO> updateLoaiHoaDon(LoaiHoaDonDTO loaiHoaDonDTO) {
        Optional<LoaiHoaDon> existingLoaiHD = loaiHdRepo.findById(loaiHoaDonDTO.getIdLoaiHD());
        if (existingLoaiHD.isPresent()) {
            LoaiHoaDon hd = LoaiHoaDonMapper.toEntity(loaiHoaDonDTO);
            LoaiHoaDon updatedLoaiHD = loaiHdRepo.save(hd);
            return Optional.of(LoaiHoaDonMapper.toDTO(updatedLoaiHD));
        }else {
            System.err.println("Loai Hoa Don does not existing!");
            return Optional.empty();
        }
    }

    @Override
    public Iterable<LoaiHoaDonDTO> getAllLoaiHoaDon() {
        try {
            Iterable<LoaiHoaDon> listLoaiHD = loaiHdRepo.findAll();
            Iterable<LoaiHoaDonDTO> listLoaiHDDTO = StreamSupport.stream(listLoaiHD.spliterator(), false)
                    .map(LoaiHoaDonMapper::toDTO)
                    .toList();
            return listLoaiHDDTO;
        } catch (Exception e) {
            System.err.println("Error occurred while fetching Loai Hoa Don: " + e.getMessage());
            return Collections.emptyList();
        }
    }

    @Override
    public Optional<LoaiHoaDonDTO> getLoaiHoaDonById(int idLoaiHD) {
        try {
            Optional<LoaiHoaDon> loaiHoaDon = loaiHdRepo.findById(idLoaiHD);
            Optional<LoaiHoaDonDTO> loaiHoaDonDTO = loaiHoaDon.map(LoaiHoaDonMapper::toDTO);
            return loaiHoaDonDTO;
        } catch (Exception e) {
            System.err.println("Error occurred while get Loai Hoa Don by ID");
            return null;
        }
    }
}
