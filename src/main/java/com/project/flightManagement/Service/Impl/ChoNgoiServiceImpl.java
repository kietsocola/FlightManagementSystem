package com.project.flightManagement.Service.Impl;

import com.project.flightManagement.DTO.ChoNgoiDTO.ChoNgoiDTO;
import com.project.flightManagement.Mapper.ChoNgoiMapper;
import com.project.flightManagement.Model.ChoNgoi;
import com.project.flightManagement.Repository.ChoNgoiRepository;
import com.project.flightManagement.Service.ChoNgoiService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.stream.Collectors;

@Service
public class ChoNgoiServiceImpl implements ChoNgoiService {
    @Autowired
    ChoNgoiRepository choNgoiRepo;
    @Override
    public List<ChoNgoiDTO> getAllChoNgoiByIdChuyenBayandHangVe(int idCB, int hangVe) {
        List<ChoNgoi> choNgoiList = choNgoiRepo.findByMayBay_ChuyenBayList_IdChuyenBayAndHangVe_IdHangVe(idCB, hangVe);
        return choNgoiList.stream()
                .map(ChoNgoiMapper::toDTO)
                .collect(Collectors.toList());
    }
}
