package com.project.flightManagement.Service.Impl;

import com.project.flightManagement.DTO.ChiTietQuyenDTO.ChiTietQuyenCreateDTO;
import com.project.flightManagement.Enum.ActiveEnum;
import com.project.flightManagement.Mapper.ChiTietQuyenMapper;
import com.project.flightManagement.Model.ChiTietQuyen;
import com.project.flightManagement.Repository.ChiTietQuyenRepository;
import com.project.flightManagement.Service.ChiTietQuyenService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Lazy;
import org.springframework.stereotype.Service;

@Service
public class ChiTietQuyenServiceImpl implements ChiTietQuyenService {
    @Autowired
    private ChiTietQuyenRepository chiTietQuyenRepository;
    @Autowired
    @Lazy
    private ChiTietQuyenMapper chiTietQuyenMapper;
    @Override
    public boolean createChiTietQuyen(ChiTietQuyenCreateDTO chiTietQuyenCreateDTO) {
        ChiTietQuyen chiTietQuyen = chiTietQuyenMapper.toChiTietQuyen(chiTietQuyenCreateDTO);
        chiTietQuyen.setTrangThaiActive(ActiveEnum.ACTIVE);
        ChiTietQuyen chiTietQuyenNew = chiTietQuyenRepository.save(chiTietQuyen);
        if(chiTietQuyenNew!=null) {
            return true;
        }
        return false;
    }
}
