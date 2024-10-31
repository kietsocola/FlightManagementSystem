package com.project.flightManagement.Service.Impl;

import com.project.flightManagement.DTO.ChiTietQuyenDTO.ChiTietQuyenCreateDTO;
import com.project.flightManagement.DTO.ChiTietQuyenDTO.ChiTietQuyenDTO;
import com.project.flightManagement.DTO.QuyenDTO.QuyenBasicDTO;
import com.project.flightManagement.DTO.QuyenDTO.QuyenCreateDTO;
import com.project.flightManagement.DTO.QuyenDTO.QuyenResponseDTO;
import com.project.flightManagement.Enum.ActiveEnum;
import com.project.flightManagement.Mapper.ChiTietQuyenMapper;
import com.project.flightManagement.Mapper.QuyenMapper;
import com.project.flightManagement.Model.ChiTietQuyen;
import com.project.flightManagement.Model.Quyen;
import com.project.flightManagement.Repository.ChiTietQuyenRepository;
import com.project.flightManagement.Repository.QuyenRepository;
import com.project.flightManagement.Service.ChiTietQuyenService;
import com.project.flightManagement.Service.QuyenService;
import jakarta.transaction.Transactional;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Lazy;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.rest.webmvc.ResourceNotFoundException;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

@Service
public class QuyenServiceImpl implements QuyenService {
    @Autowired
    private QuyenRepository quyenRepository;
    @Autowired
    private QuyenMapper quyenMapper;
    @Autowired
    private ChiTietQuyenService chiTietQuyenService;

    @Autowired
    @Lazy
    private ChiTietQuyenMapper chiTietQuyenMapper;
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

    @Override
    @Transactional
    public Page<QuyenResponseDTO> getAllQuyen(int page, int size) {
        Pageable pageable = PageRequest.of(page, size);
        Page<Quyen> quyenPage = quyenRepository.findAll(pageable);
        return quyenPage.map(quyenMapper::toQuyenResponseDTO);
    }

    @Override
    @Transactional
    public QuyenResponseDTO getQuyenDetailByIdQuyen(int idQuyen) {
        Optional<Quyen> quyenOptional = quyenRepository.findQuyenByIdQuyen(idQuyen);
        if(quyenOptional.isEmpty()) {
            return null;
        }
        Quyen quyen = quyenOptional.get();
        QuyenResponseDTO quyenResponseDTO = quyenMapper.toQuyenResponseDTO(quyen);
        return quyenResponseDTO;
    }
    @Override
    @Transactional
    public void updateQuyen(int idQuyen, QuyenCreateDTO quyenCreateDTO) {
        // Kiểm tra quyền có tồn tại không
        Quyen quyen = quyenRepository.findQuyenByIdQuyen(idQuyen)
                .orElseThrow(() -> new ResourceNotFoundException("Quyen not found with id " + idQuyen));

        // Kiểm tra tên quyền có bị trùng không (ngoại trừ quyền hiện tại)
        Optional<Quyen> existingQuyen = quyenRepository.findQuyenByTenQuyen(quyenCreateDTO.getTenQuyen());
        if (existingQuyen.isPresent() && existingQuyen.get().getIdQuyen() != idQuyen) {
            throw new IllegalArgumentException("Tên quyền đã tồn tại.");
        }
        quyen.setTenQuyen(quyenCreateDTO.getTenQuyen());
        quyen.setTrangThaiActive(quyenCreateDTO.getActiveEnum());

        deleteAllChiTietQuyenByIdQuyen(idQuyen);

        // Ánh xạ từ chiTietQuyenDTOList sang chiTietQuyenList
        List<ChiTietQuyenDTO> chiTietQuyenDTOList = quyenCreateDTO.getChiTietQuyenDTO();
        List<ChiTietQuyenCreateDTO> chiTietQuyenCreateDTOList = new ArrayList<>();   // khi tao moi chi tiet quyen
        for(ChiTietQuyenDTO chiTietQuyenDTO : chiTietQuyenDTOList) { // can phai co id cua quyen
            ChiTietQuyenCreateDTO chiTietQuyenCreateDTO = new ChiTietQuyenCreateDTO(); // nen phai bo sung them id quyen
            chiTietQuyenCreateDTO.setIdQuyen(idQuyen);
            chiTietQuyenCreateDTO.setIdChucNang(chiTietQuyenDTO.getIdChucNang());
            chiTietQuyenCreateDTO.setHanhDong(chiTietQuyenDTO.getHanhDong());
            chiTietQuyenCreateDTOList.add(chiTietQuyenCreateDTO);
        }

        for(ChiTietQuyenCreateDTO chiTietQuyenCreateDTO : chiTietQuyenCreateDTOList) {
            chiTietQuyenService.createChiTietQuyen(chiTietQuyenCreateDTO);
        }

        quyenRepository.save(quyen);
    }

    private void deleteAllChiTietQuyenByIdQuyen(int idQuyen) {
        Optional<Quyen> quyenOptional = quyenRepository.findQuyenByIdQuyen(idQuyen);
        if(quyenOptional.isPresent()) {
            Quyen quyen = quyenOptional.get();
            for(ChiTietQuyen chiTietQuyen : quyen.getChiTietQuyenList()) {
                chiTietQuyenRepository.delete(chiTietQuyen);
            }
        }
    }

    @Override
    public Page<QuyenResponseDTO> searchQuyenByName(String tenQuyen, int page, int size) {
        // Chuyển đổi tên sang định dạng để tìm kiếm (có thể áp dụng các quy tắc tìm kiếm khác nhau)

        Page<Quyen> quyenPage = quyenRepository.findByTenQuyenContainingIgnoreCase(tenQuyen, PageRequest.of(page, size));
        return quyenPage.map(quyenMapper::toQuyenResponseDTO);
    }

    @Override
    public boolean existsQuyenByTenQuyen(String tenQuyen) {
        return quyenRepository.existsByTenQuyen(tenQuyen);
    }

    @Override
    public boolean existsByTenQuyenAndNotIdQuyenNot(String tenQuyen, int idQuyen) {
        return quyenRepository.existsByTenQuyenAndIdQuyenNot(tenQuyen, idQuyen);
    }
}
