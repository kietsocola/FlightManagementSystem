package com.project.flightManagement.Mapper;

import com.project.flightManagement.DTO.ChiTietQuyenDTO.ChiTietQuyenCreateDTO;
import com.project.flightManagement.DTO.ChiTietQuyenDTO.ChiTietQuyenDTO;
import com.project.flightManagement.DTO.QuyenDTO.QuyenBasicDTO;
import com.project.flightManagement.Model.ChiTietQuyen;
import com.project.flightManagement.Model.ChucNang;
import com.project.flightManagement.Model.Quyen;
import com.project.flightManagement.Service.ChucNangService;
import com.project.flightManagement.Service.QuyenService;
import org.mapstruct.Mapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import java.util.Optional;

@Component
public class ChiTietQuyenMapper {
    @Autowired
    private QuyenService quyenService;
    @Autowired
    private ChucNangService chucNangService;
    @Autowired
    private QuyenMapper quyenMapper;
     public ChiTietQuyen toChiTietQuyen(ChiTietQuyenCreateDTO chiTietQuyenCreateDTO) {
        ChiTietQuyen chiTietQuyen = new ChiTietQuyen();

        Optional<QuyenBasicDTO> quyenBasicDTOOptional = quyenService.getQuyenByIdQuyen(chiTietQuyenCreateDTO.getIdQuyen());
        Quyen quyen = quyenMapper.toQuyen(quyenBasicDTOOptional.get());
        chiTietQuyen.setQuyen(quyen);
        chiTietQuyen.setHanhDong(chiTietQuyenCreateDTO.getHanhDong());

        ChucNang chucNang = chucNangService.getChucNangByIdChucNang(chiTietQuyenCreateDTO.getIdChucNang());
        chiTietQuyen.setChucNang(chucNang);

        chiTietQuyen.setHanhDong(chiTietQuyenCreateDTO.getHanhDong());
        return chiTietQuyen;

    }
    public ChiTietQuyenDTO toChiTietQuyenDTO(ChiTietQuyen chiTietQuyen) {
        return null;
    }
}
