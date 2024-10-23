package com.project.flightManagement.Service.Impl;

import com.project.flightManagement.DTO.LoaiVeDTO.LoaiVeDTO;
import com.project.flightManagement.Mapper.LoaiVeMapper;
import com.project.flightManagement.Model.LoaiVe;
import com.project.flightManagement.Repository.LoaiVeRepository;
import com.project.flightManagement.Service.LoaiVeService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.rest.webmvc.ResourceNotFoundException;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.stream.Collectors;

@Service
public class LoaiVeServiceImpl implements LoaiVeService {
    @Autowired
    private LoaiVeRepository loaiVeRepository;
    @Autowired
    private LoaiVeMapper loaiVeMapper;
    @Override
    public Page<LoaiVeDTO> getAllLoaiVe(int page, int size) {
        Pageable pageable = PageRequest.of(page, size);
        Page<LoaiVe> loaiVePage = loaiVeRepository.findAll(pageable);
        return loaiVePage.map(loaiVeMapper::toDto);
    }

    @Override
    public LoaiVeDTO getLoaiVeById(int idLoaiVe) {
        LoaiVe loaiVe = loaiVeRepository.findById(idLoaiVe).orElseThrow(
                () -> new ResourceNotFoundException("Loai ve not found with ID: " + idLoaiVe)
        );
        return loaiVeMapper.toDto(loaiVe);
    }
}
