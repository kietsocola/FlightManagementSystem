package com.project.flightManagement.Service.Impl;

import com.project.flightManagement.DTO.KhachHangDTO.KhachHangCreateDTO;
import com.project.flightManagement.Mapper.KhachHangMapper;
import com.project.flightManagement.Model.KhachHang;
import com.project.flightManagement.Repository.KhachHangRepository;
import com.project.flightManagement.Service.KhachHangService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class KhachHangServiceImpl implements KhachHangService {
    @Autowired
    private KhachHangRepository khachHangRepository;
    @Autowired
    private KhachHangMapper khachHangMapper;

    @Override
    public KhachHang createKhachHang(KhachHangCreateDTO khachHangCreateDTO) {
        try {
            KhachHang khachHang = khachHangMapper.toKhachHang(khachHangCreateDTO);
            return khachHangRepository.save(khachHang);
        } catch (Exception e) {
            e.printStackTrace();
            return null;
        }
    }

    @Override
    public boolean existsKhachHangByEmail(String email) {
        if(khachHangRepository.existsKhachHangByEmail(email)) {
            return true;
        }
        return false;
    }
    @Override
    public boolean existsKhachHangByCccd(String cccd) {
        if(khachHangRepository.existsKhachHangByCccd(cccd)) {
            return true;
        }
        return false;
    }
}
