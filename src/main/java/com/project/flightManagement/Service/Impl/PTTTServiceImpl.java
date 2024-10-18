package com.project.flightManagement.Service.Impl;

import com.project.flightManagement.DTO.PTTTDTO.PTTTDTO;
import com.project.flightManagement.Mapper.PTTTMapper;
import com.project.flightManagement.Model.PhuongThucThanhToan;
import com.project.flightManagement.Repository.PTTTReposity;
import com.project.flightManagement.Service.PTTTService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.Collections;
import java.util.Optional;
import java.util.stream.StreamSupport;

@Service
public class PTTTServiceImpl implements PTTTService {

    @Autowired
    private PTTTReposity ptttRepo;

    @Override
    public Iterable<PTTTDTO> getAllPTTT() {
        try {
            Iterable<PhuongThucThanhToan> listPTTT = ptttRepo.findAll();
            Iterable<PTTTDTO> listPTTTDTO = StreamSupport.stream(listPTTT.spliterator(), false)
                    .map(PTTTMapper::toDTO)
                    .toList();
            return listPTTTDTO;
        } catch (Exception e) {
            System.err.println("Error occurred while fetching Payment Methods: " + e.getMessage());
            return Collections.emptyList();
        }
    }

    @Override
    public Optional<PTTTDTO> getPTTTByID(int idPTTT) {
        try {
            Optional<PhuongThucThanhToan> pttt = ptttRepo.findById(idPTTT);
            Optional<PTTTDTO> ptttDTO = pttt.map(PTTTMapper::toDTO);
            return ptttDTO;
        } catch (Exception e) {
            System.err.println("Error occurred while get Payment Method: " + e);
            return null;
        }
    }

    @Override
    public Optional<PTTTDTO> addPTTT(PTTTDTO ptttDTO) {
        try {
            PhuongThucThanhToan pttt = PTTTMapper.toEntity(ptttDTO);
            PhuongThucThanhToan savedPTTT = ptttRepo.save(pttt);
            return Optional.of(PTTTMapper.toDTO(savedPTTT));
        } catch (Exception e) {
            System.err.println("Error occurred while save Payment method: " + e);
            return null;
        }
    }

    @Override
    public Optional<PTTTDTO> updatePTTT(PTTTDTO ptttDTO) {
        Optional<PhuongThucThanhToan> existingPTTT = ptttRepo.findById(ptttDTO.getIdPTTT());
        if (existingPTTT.isPresent()) {
            PhuongThucThanhToan PTTT = PTTTMapper.toEntity(ptttDTO);
            PhuongThucThanhToan updatedPTTT = ptttRepo.save(PTTT);
            return Optional.of(PTTTMapper.toDTO(updatedPTTT));
        }else {
            System.err.println("Payment method does not existing!!!");
            return Optional.empty();
        }
    }

//    @Override
//    public List<PTTTDTO> findPhuongThucThanhToanByTen(String keyword) {
//        if (khachHangList.isEmpty()) {
//            System.out.println("No customer found with the keyword: " + keyword);
//        } else {
//            KhachHang kh = khachHangList.get(0); // Dùng get(0) thay vì getFirst()
//            System.out.println("Id kh found: " + kh.getHoTen());
//        }
//        return khachHangList.stream()
//                .map(KhachHangMapper::toDTO)
//                .collect(Collectors.toList());
//
//    }

}
