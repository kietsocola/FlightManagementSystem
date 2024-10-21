package com.project.flightManagement.Service.Impl;

import com.project.flightManagement.Model.ChucNang;
import com.project.flightManagement.Repository.ChucNangRepository;
import com.project.flightManagement.Service.ChucNangService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.Optional;

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
}
