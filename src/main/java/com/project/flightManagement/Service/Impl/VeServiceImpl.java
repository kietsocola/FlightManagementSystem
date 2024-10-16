package com.project.flightManagement.Service.Impl;

import com.project.flightManagement.DTO.VeDTO.VeDTO;
import com.project.flightManagement.Mapper.LoaiVeMapper;
import com.project.flightManagement.Mapper.VeMapper;
import com.project.flightManagement.Model.LoaiVe;
import com.project.flightManagement.Model.Ve;
import com.project.flightManagement.Repository.VeRepository;
import com.project.flightManagement.Service.VeService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;

@Service
public class VeServiceImpl implements VeService {
    @Autowired
    private VeRepository veRepository;
    @Autowired
    private VeMapper veMapper;
    @Override
    public Page<VeDTO> getAllVe(int page, int size) {
        Pageable pageable = PageRequest.of(page, size);
        Page<Ve> vePage = veRepository.findAll(pageable);
        return vePage.map(veMapper::toDto);
    }

    @Override
    public Page<VeDTO> getAllVeByIdChuyenBay(int idChuyenBay, int page, int size) {
        Pageable pageable = PageRequest.of(page, size);
        Page<Ve> vePage = veRepository.findByChuyenBay_IdChuyenBay(idChuyenBay, pageable);
        return vePage.map(veMapper::toDto);
    }
    @Override
    public boolean updateVe(int idVe, VeDTO veDTO) {
        try {
            // Retrieve the existing Ve entity
            Ve existingVe = veRepository.findById(idVe)
                    .orElseThrow(() -> new RuntimeException("Ve not found with id: " + idVe));

            // Update fields of the existing entity with values from the DTO
            existingVe.setGiaVe(veDTO.getGiaVe());
            existingVe.setTrangThaiActive(veDTO.getTrangThaiActive());
            existingVe.setTrangThai(veDTO.getTrangThai());
           veRepository.save(existingVe);
            return true;
        }catch (Exception e) {
                System.out.println(e);
                return false;
        }

    }

}
