package com.project.flightManagement.Service.Impl;

import com.project.flightManagement.DTO.ChucNangDTO.ChucNangDTO;
import com.project.flightManagement.Mapper.ChucNangMapper;
import com.project.flightManagement.Model.ChucNang;
import com.project.flightManagement.Repository.ChucNangRepository;
import com.project.flightManagement.Service.ChucNangService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

@Service
public class ChucNangServiceImpl implements ChucNangService {
    @Autowired
    private ChucNangRepository chucNangRepository;

    @Override
    public ChucNang getChucNangByIdChucNang(int idChucNang) {
        Optional<ChucNang> chucNangOptional = chucNangRepository.findChucNangByIdChucNang(idChucNang);
        if(chucNangOptional.isEmpty()) {
            return null;
        }
        return chucNangOptional.get();
     }

    @Override
    public List<ChucNangDTO> getAllChucNang() {
        List<ChucNang> chucNangList = chucNangRepository.findAll();
        return chucNangList.stream()
                .map(ChucNangMapper::chucNangDTO)
                .collect(Collectors.toList());
    }

}
