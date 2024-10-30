package com.project.flightManagement.Service.Impl;

import com.project.flightManagement.DTO.ChoNgoiDTO.ChoNgoiDTO;
import com.project.flightManagement.DTO.MayBayDTO.MayBayDTO;
import com.project.flightManagement.Enum.ActiveEnum;
import com.project.flightManagement.Mapper.ChoNgoiMapper;
import com.project.flightManagement.Mapper.MayBayMapper;
import com.project.flightManagement.Model.ChoNgoi;
import com.project.flightManagement.Model.MayBay;
import com.project.flightManagement.Repository.ChoNgoiRepository;
import com.project.flightManagement.Service.ChoNgoiService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.stereotype.Service;

import java.util.Collections;
import java.util.List;
import java.util.Optional;
import java.util.stream.Collector;
import java.util.stream.Collectors;
import java.util.stream.StreamSupport;
@Service
public class ChoNgoiServicelmpl implements ChoNgoiService {
    @Autowired
    private ChoNgoiRepository cnRepo;

    @Override
    public Iterable<ChoNgoiDTO> getAllChoNgoi() {
        try {
            Iterable<ChoNgoi> choNgoiList = cnRepo.findAll();
            Iterable<ChoNgoiDTO> choNgoiDTOList = StreamSupport.stream(choNgoiList.spliterator(), false).map(ChoNgoiMapper::toDTO).collect(Collectors.toList());
            return choNgoiDTOList;
        } catch (Exception e) {
            System.out.println("Get all seat failed");
            return null;
        }
    }

//    @Override
//    public Optional<ChoNgoiDTO> addNewChoNgoi(ChoNgoiDTO choNgoiDTO) {
//        try {
//            ChoNgoi cn = ChoNgoiMapper.toEntity(choNgoiDTO);
//            ChoNgoi savedCn = cnRepo.save(cn);
//            System.out.println("add seat success");
//            System.out.println(choNgoiDTO.toString());
//            return Optional.of(ChoNgoiMapper.toDTO(savedCn));
//        } catch (Exception e) {
//            System.out.println("Add new seat failed!!");
//            System.out.println(choNgoiDTO.toString());
//            return Optional.empty();
//        }
//    }
    @Override
    public Optional<ChoNgoiDTO> addNewChoNgoi(ChoNgoiDTO choNgoiDTO) {
        try {
            ChoNgoi cn = ChoNgoiMapper.toEntity(choNgoiDTO);
            ChoNgoi savedCn = cnRepo.save(cn);
            System.out.println("Add seat success");
            return Optional.of(ChoNgoiMapper.toDTO(savedCn));
        } catch (Exception e) {
            System.out.println("Add new seat failed: " + e.getMessage());
            return Optional.empty();
        }
    }
    @Override
    public void deleteChoNgoiByMayBay(MayBayDTO mayBayDTO) {
        try {
            // Chuyển đổi MayBayDTO thành MayBay entity
            MayBay mayBay = MayBayMapper.toEntity(mayBayDTO);

            // Lấy danh sách ghế liên quan tới máy bay
            List<ChoNgoi> choNgoiList = cnRepo.findChoNgoiByMayBay(mayBay);

            // Xóa từng ghế trong danh sách
            if (!choNgoiList.isEmpty()) {
                cnRepo.deleteAll(choNgoiList);
                System.out.println("Deleted seats successfully for plane ID: " + mayBay.getIdMayBay());
            } else {
                System.out.println("No seats found for plane ID: " + mayBay.getIdMayBay());
            }
        } catch (Exception e) {
            System.out.println("Delete seats failed: " + e.getMessage());
        }
    }
    @Override
    public Optional<ChoNgoiDTO> getChoNgoiById(int id) {
        try {
            Optional<ChoNgoi> cn = cnRepo.findById(id);
            return cn.map(ChoNgoiMapper::toDTO);
        } catch (Exception e) {
            System.out.println("Get seat by id failed!!");
            return Optional.empty();
        }
    }
    @Override
    public List<ChoNgoiDTO> getAllChoNgoiByIdChuyenBayandHangVe(int idCB, int hangVe) {
        List<ChoNgoi> choNgoiList = cnRepo.findByMayBay_ChuyenBayList_IdChuyenBayAndHangVe_IdHangVe(idCB, hangVe);
        return choNgoiList.stream()
                .map(ChoNgoiMapper::toDTO)
                .collect(Collectors.toList());
    }
    @Override
    public Iterable<ChoNgoiDTO> getChoNgoiByMayBay(MayBayDTO mayBayDTO) {
        try{
            Iterable<ChoNgoi> listnv =  cnRepo.findChoNgoiByMayBay(MayBayMapper.toEntity(mayBayDTO));
            Iterable<ChoNgoiDTO> listnvDTO = StreamSupport.stream(listnv.spliterator(),false)
                    .map(ChoNgoiMapper::toDTO)
                    .toList();
            return listnvDTO;
        }catch (Exception e){
            System.err.println("error occurred while fetching customers :" + e.getMessage());
            return Collections.emptyList();
        }
    }
    //    @Override
//    public Optional<ChoNgoiDTO> blockChoNgoi(int idChoNgoi) {
//        Optional<ChoNgoi> existingCN = cnRepo.findById(idChoNgoi);
//        if (existingCN.isPresent()) {
//            ChoNgoi cn = existingCN.get();
//            cn.setTrangThaiActive(ActiveEnum.IN_ACTIVE);
//            ChoNgoi savedCn = cnRepo.save(cn);
//            return Optional.of(ChoNgoiMapper.toDTO(savedCn));
//        } else {
//            System.out.println("Not found seat!!");
//            return Optional.empty();
//        }
//    }
//    @Override
//    public Optional<ChoNgoiDTO> unblockChoNgoi(int idChoNgoi) {
//        Optional<ChoNgoi> existingCN = cnRepo.findById(idChoNgoi);
//        if (existingCN.isPresent()) {
//            ChoNgoi cn = existingCN.get();
//            cn.setTrangThaiActive(ActiveEnum.ACTIVE);
//            ChoNgoi savedCn = cnRepo.save(cn);
//            return Optional.of(ChoNgoiMapper.toDTO(savedCn));
//        } else {
//            System.out.println("Not found seat!!");
//            return Optional.empty();
//        }
//    }
    @Override
    public Optional<ChoNgoiDTO> blockChoNgoi(int id) {
        Optional<ChoNgoi> existingCN = cnRepo.findById(id);
        if (existingCN.isPresent()) {
            ChoNgoi cn = existingCN.get();
            cn.setTrangThaiActive(ActiveEnum.IN_ACTIVE);
            cnRepo.save(cn);
            return Optional.of(ChoNgoiMapper.toDTO(cn));
        } else {
            return Optional.empty();
        }
    }
    @Override
    public Optional<ChoNgoiDTO> unblockChoNgoi(int id) {
        Optional<ChoNgoi> existingCN = cnRepo.findById(id);
        if (existingCN.isPresent()) {
            ChoNgoi cn = existingCN.get();
            cn.setTrangThaiActive(ActiveEnum.ACTIVE);
            cnRepo.save(cn);
            return Optional.of(ChoNgoiMapper.toDTO(cn));
        } else {
            return Optional.empty();
        }
    }

    @Override
    public Optional<ChoNgoi> getChoNgoiEntityById(int idChoNgoi) {
        Optional<ChoNgoi> choNgoiOptional = cnRepo.findById(idChoNgoi);
        return choNgoiOptional;
    }

}
