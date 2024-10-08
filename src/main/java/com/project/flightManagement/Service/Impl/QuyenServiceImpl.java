package com.project.flightManagement.Service.Impl;

import com.project.flightManagement.DTO.ChiTietQuyenDTO.ChiTietQuyenCreateDTO;
import com.project.flightManagement.DTO.ChiTietQuyenDTO.ChiTietQuyenDTO;
import com.project.flightManagement.DTO.QuyenDTO.QuyenBasicDTO;
import com.project.flightManagement.DTO.QuyenDTO.QuyenCreateDTO;
import com.project.flightManagement.Enum.ActiveEnum;
import com.project.flightManagement.Mapper.QuyenMapper;
import com.project.flightManagement.Model.Quyen;
import com.project.flightManagement.Repository.ChiTietQuyenRepository;
import com.project.flightManagement.Repository.QuyenRepository;
import com.project.flightManagement.Service.ChiTietQuyenService;
import com.project.flightManagement.Service.QuyenService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;
@Service
public class QuyenServiceImpl implements QuyenService {
    @Autowired
    private QuyenRepository quyenRepository;
    @Autowired
    private QuyenMapper quyenMapper;
    @Autowired
    private ChiTietQuyenService chiTietQuyenService;
    @Autowired
    private ChiTietQuyenRepository chiTietQuyenRepository;

    @Override
    public Optional<QuyenBasicDTO> getQuyenByIdQuyen(int idQuyen) {
        Optional<Quyen> quyenOptional = quyenRepository.findQuyenByIdQuyen(idQuyen);
        if(quyenOptional.isEmpty()) {
            return null;
        }
        Quyen quyen = quyenOptional.get();
        return Optional.ofNullable(quyenMapper.toQuyenBasicDTO(quyen));
    }

    @Override
    public boolean createQuyen(QuyenCreateDTO quyenCreateDTO) {
        Quyen quyen = quyenMapper.toQuyenFromQuyenCreateDTO(quyenCreateDTO);
        quyen.setTrangThaiActive(ActiveEnum.ACTIVE);
        Quyen quyenNew = quyenRepository.save(quyen); // insert xong vo bang quyen
        List<ChiTietQuyenDTO> chiTietQuyenDTOList = quyenCreateDTO.getChiTietQuyenDTO();

        List<ChiTietQuyenCreateDTO> chiTietQuyenCreateDTOList = new ArrayList<>();   // khi tao moi chi tiet quyen
        for(ChiTietQuyenDTO chiTietQuyenDTO : chiTietQuyenDTOList) { // can phai co id cua quyen
            ChiTietQuyenCreateDTO chiTietQuyenCreateDTO = new ChiTietQuyenCreateDTO(); // nen phai bo sung them id quyen
            chiTietQuyenCreateDTO.setIdQuyen(quyenNew.getIdQuyen());
            System.out.println("id chuc nag:" + chiTietQuyenDTO.getIdChucNang());
            chiTietQuyenCreateDTO.setIdChucNang(chiTietQuyenDTO.getIdChucNang());
            System.out.println("Sau: " + chiTietQuyenCreateDTO.getIdChucNang());
            chiTietQuyenCreateDTO.setHanhDong(chiTietQuyenDTO.getHanhDong());
            chiTietQuyenCreateDTOList.add(chiTietQuyenCreateDTO);
        }

        for(ChiTietQuyenCreateDTO chiTietQuyenCreateDTO : chiTietQuyenCreateDTOList) {
            System.out.println("id CHuc Nang: foe2: " + chiTietQuyenCreateDTO.getIdChucNang());
            chiTietQuyenService.createChiTietQuyen(chiTietQuyenCreateDTO);

        }

        if (quyenNew != null) {
            return  true;
        }
        return false;
    }
}
