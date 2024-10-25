package com.project.flightManagement.Service.Impl;

import com.project.flightManagement.DTO.HanhKhachDTO.HanhKhachCreateDTO;
import com.project.flightManagement.DTO.HanhKhachDTO.HanhKhachUpdateDTO;
import com.project.flightManagement.Mapper.HanhKhachMapper;
import com.project.flightManagement.Mapper.KhachHangMapper;
import com.project.flightManagement.Model.HanhKhach;
import com.project.flightManagement.Repository.HanhKhachRepository;
import com.project.flightManagement.Service.HanhKhachService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.Optional;

@Service
public class HanhKhachServiceImpl implements HanhKhachService {
    @Autowired
    private HanhKhachMapper hanhKhachMapper;
    @Autowired
    private HanhKhachRepository hanhKhachRepository;
    @Override
    public boolean existHanhKhachByIdHanhKhach(int idHanhKhach) {
        return hanhKhachRepository.existsById(idHanhKhach);
    }

    @Override
    public HanhKhach createHanhKhach(HanhKhachCreateDTO hanhKhachCreateDTO) {
        HanhKhach hanhKhach = hanhKhachMapper.toHanhKhach(hanhKhachCreateDTO);
        return hanhKhachRepository.save(hanhKhach);
    }

    @Override
    public Optional<HanhKhach> getHanhKhachById(int idHanhKhach) {
        return hanhKhachRepository.findById(idHanhKhach);
    }

    @Override
    public HanhKhach updateHanhKhach(HanhKhachUpdateDTO hanhKhachUpdateDTO) {
        HanhKhach hanhKhach = HanhKhachMapper.toHanhKhachFromHanhKhachUpdateDTO(hanhKhachUpdateDTO);
        return hanhKhachRepository.save(hanhKhach);
    }
}
