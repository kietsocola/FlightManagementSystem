package com.project.flightManagement.Service.Impl;

import com.project.flightManagement.DTO.DanhGiaDTO.DanhGiaDTO;
import com.project.flightManagement.Enum.ActiveEnum;
import com.project.flightManagement.Mapper.DanhGiaMapper;
import com.project.flightManagement.Model.DanhGia;
import com.project.flightManagement.Model.HangBay;
import com.project.flightManagement.Model.KhachHang;
import com.project.flightManagement.Repository.DanhGiaRepository;
import com.project.flightManagement.Repository.HangBayRepository;
import com.project.flightManagement.Repository.KhachHangRepository;
import com.project.flightManagement.Service.DanhGiaService;
import org.apache.el.stream.Stream;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Sort;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;
import java.util.Collections;
import java.util.Optional;
import java.util.stream.StreamSupport;

@Service
public class DanhGiaServicelmpl implements DanhGiaService {
    @Autowired
    DanhGiaRepository dgRepo;
    @Autowired
    KhachHangRepository khRepo;
    @Autowired
    HangBayRepository hbRepo;


    @Override
    public Optional<DanhGiaDTO> getDanhGiaByID(int id) {
        try {
            Optional<DanhGia> danhGia = dgRepo.findById(id);
            return danhGia.map(DanhGiaMapper::toDTO);
        } catch (Exception e) {
            System.err.println("Error occurred while get review: " + e.getMessage());
            return Optional.empty();
        }
    }
    @Override
    public Iterable<DanhGiaDTO> getAllDanhGia() {
        try {
            String sortByField = "thoiGianTao";
            Iterable<DanhGia> listDG = dgRepo.findAll(Sort.by(Sort.Direction.DESC, sortByField));
            return StreamSupport.stream(listDG.spliterator(), false)
                    .map(DanhGiaMapper::toDTO)
                    .toList();
        } catch (Exception e) {
            e.printStackTrace();
            return Collections.emptyList();
        }
    }

    @Override
    public Iterable<DanhGiaDTO> getDanhGiaByKhachHang(int idKhachHang) {
        try {
            Optional<KhachHang> kh = khRepo.findById(idKhachHang);
            Iterable<DanhGia> listDG = dgRepo.findByKhachHangOrderByThoiGianTaoDesc(kh.get());
            return StreamSupport.stream(listDG.spliterator(), false).map(DanhGiaMapper::toDTO).toList();
        } catch (Exception e) {
            return null;
        }
    }
    @Override
    public Iterable<DanhGiaDTO> getDanhGiaByStartTimeAndEndTime(LocalDateTime startTime, LocalDateTime endTime) {
        try {
            Iterable<DanhGia> listDG = dgRepo.findByThoiGianTaoBetween(startTime, endTime);
            return StreamSupport.stream(listDG.spliterator(), false)
                    .map(DanhGiaMapper::toDTO)
                    .toList();
        } catch (Exception e) {
            e.printStackTrace();
            return Collections.emptyList();
        }
    }
    @Override
    public Iterable<DanhGiaDTO> getDanhGiaByHangBay(int idHangBay) {
        try {
            Optional<HangBay> hb = hbRepo.findById(idHangBay);
            Iterable<DanhGia> listDG = dgRepo.findByHangBay(hb.get());
            return StreamSupport.stream(listDG.spliterator(), false)
                    .map(DanhGiaMapper::toDTO)
                    .toList();
        } catch (Exception e) {
            e.printStackTrace();
            return Collections.emptyList();
        }
    }
    @Override
    public boolean blockDanhGia(int id) {
        Optional<DanhGia> existingDG = dgRepo.findById(id);
        if(existingDG.isPresent()) {
            existingDG.get().setTrangThaiActive(ActiveEnum.IN_ACTIVE);
            DanhGia danhGia = dgRepo.save(existingDG.get());
            return true;
        } else {
            return false;
        }
    }
    @Override
    public boolean unblockDanhGia(int id) {
        Optional<DanhGia> existingDG = dgRepo.findById(id);
        if(existingDG.isPresent()) {
            existingDG.get().setTrangThaiActive(ActiveEnum.ACTIVE);
            DanhGia danhGia = dgRepo.save(existingDG.get());
            return true;
        } else {
            return false;
        }
    }
}
