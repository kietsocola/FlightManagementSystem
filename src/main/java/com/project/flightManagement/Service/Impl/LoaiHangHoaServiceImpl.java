package com.project.flightManagement.Service.Impl;

import com.project.flightManagement.DTO.LoaiHangHoaDTO.LoaiHangHoaDTO;
import com.project.flightManagement.Mapper.LoaiHangHoaMapper;
import com.project.flightManagement.Model.LoaiHangHoa;
import com.project.flightManagement.Repository.LoaiHangHoaRepository;
import com.project.flightManagement.Service.LoaiHangHoaService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.Collections;
import java.util.Optional;
import java.util.stream.Collectors;
import java.util.stream.StreamSupport;

@Service
public class LoaiHangHoaServiceImpl implements LoaiHangHoaService {

    @Autowired
    private LoaiHangHoaRepository loaiHangHoaRepository;

    @Override
    public Iterable<LoaiHangHoaDTO> getAllLoaiHangHoa() {
        return StreamSupport.stream(loaiHangHoaRepository.findAll().spliterator(), false)
                .map(LoaiHangHoaMapper::toDTO)
                .collect(Collectors.toList());
    }

    @Override
    public Optional<LoaiHangHoaDTO> getLoaiHangHoaById(int id) {
        return loaiHangHoaRepository.findById(id).map(LoaiHangHoaMapper::toDTO);
    }

    @Override
    public Optional<LoaiHangHoaDTO> addNewLoaiHangHoa(LoaiHangHoaDTO loaiHangHoaDTO) {
        LoaiHangHoa loaiHangHoa = LoaiHangHoaMapper.toEntity(loaiHangHoaDTO);
        return Optional.of(LoaiHangHoaMapper.toDTO(loaiHangHoaRepository.save(loaiHangHoa)));
    }

    @Override
    public Optional<LoaiHangHoaDTO> updateLoaiHangHoa(int id, LoaiHangHoaDTO loaiHangHoaDTO) {
        return loaiHangHoaRepository.findById(id).map(existing -> {
            LoaiHangHoa updated = LoaiHangHoaMapper.toEntity(loaiHangHoaDTO);
            updated.setIdLoaiHangHoa(id);
            return LoaiHangHoaMapper.toDTO(loaiHangHoaRepository.save(updated));
        });
    }

    @Override
    public String deleteLoaiHangHoa(int id) {
        loaiHangHoaRepository.deleteById(id);
        return "Deleted item with ID: " + id;
    }
}
