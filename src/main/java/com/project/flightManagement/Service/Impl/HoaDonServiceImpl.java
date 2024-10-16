package com.project.flightManagement.Service.Impl;

import com.project.flightManagement.DTO.HoaDonDTO.HoaDonDTO;
import com.project.flightManagement.Mapper.HoaDonMapper;
import com.project.flightManagement.Model.HoaDon;
import com.project.flightManagement.Repository.HoaDonReposity;
import com.project.flightManagement.Service.HoaDonService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.Collections;
import java.util.Optional;
import java.util.stream.StreamSupport;

@Service
public class HoaDonServiceImpl implements HoaDonService {
    @Autowired
    private HoaDonReposity hdRepo;

    @Override
    public Iterable<HoaDonDTO> getAllHoaDon() {
        try {
            Iterable<HoaDon> listHD = hdRepo.findAll();
            Iterable<HoaDonDTO> listHDDTO = StreamSupport.stream(listHD.spliterator(), false)
                    .map(HoaDonMapper::toDTO)
                    .toList();
            return listHDDTO;
        } catch (Exception e) {
            System.err.println("Error occurred while fetching Payment Methods: " + e.getMessage());
            return Collections.emptyList();
        }
    }

    @Override
    public Optional<HoaDonDTO> getHoaDonById(int idHD) {
        try {
            Optional<HoaDon> hd = hdRepo.findById(idHD);
            Optional<HoaDonDTO> hdDTO = hd.map(HoaDonMapper::toDTO);
            return hdDTO;
        } catch (Exception e) {
            System.err.println("Error occurred while get Hoa Don by ID");
            return null;
        }
    }

    @Override
    public Optional<HoaDonDTO> addHoaDon(HoaDonDTO hoaDonDTO) {
        Optional<HoaDon> existingHD = hdRepo.findById(hoaDonDTO.getIdHoaDon());
        if (!existingHD.isPresent()) {
            HoaDon hd = HoaDonMapper.toEntity(hoaDonDTO);
            HoaDon savedHD = hdRepo.save(hd);
            return Optional.of(HoaDonMapper.toDTO(savedHD));
        } else {
            System.err.println("Hoa Don exist!");
            return null;
        }
    }

    @Override
    public Optional<HoaDonDTO> updateHoaDon(HoaDonDTO hoaDonDTO) {
        Optional<HoaDon> existingHD = hdRepo.findById((hoaDonDTO.getIdHoaDon()));
        if (existingHD.isPresent()) {
            HoaDon hd = HoaDonMapper.toEntity(hoaDonDTO);
            HoaDon updateHoaDon = hdRepo.save(hd);
            return Optional.of(HoaDonMapper.toDTO(updateHoaDon));
        } else {
            System.err.println("Hoa Don does not exist!");
            return Optional.empty();
        }
    }


//    @Override
//    public Iterable<HoaDonDTO> getHoaDonByNV(int idNV) {
//        try {
//            Iterable<HoaDon> listHD = hdRepo.findHoaDonByNhanVien(idNV);
//            Iterable<HoaDonDTO> listHDDTO = StreamSupport.stream(listHD.spliterator(), false)
//                    .map(HoaDonMapper::toDTO)
//                    .toList();
//            return listHDDTO;
//        } catch (Exception e) {
//            System.err.println("Error occurred while get Hoa Don by ID NV");
//            return null;
//        }
//    }
//
//    @Override
//    public Iterable<HoaDonDTO> getHoaDonByKH(int idKH) {
//        try {
//            Iterable<HoaDon> listHD = hdRepo.findHoaDonByKhachHang(idKH);
//            Iterable<HoaDonDTO> listHDDTO = StreamSupport.stream(listHD.spliterator(), false).
//                    map(HoaDonMapper::toDTO).
//                    toList();
//            return listHDDTO;
//        } catch (Exception e) {
//            System.err.println("Error occurred while get Hoa Don by Id KH");
//            return null;
//        }
//    }
//
//    @Override
//    public Iterable<HoaDonDTO> getHoaDonByPTTT(int idPTTT) {
//        try {
//            Iterable<HoaDon> listHD = hdRepo.findHoaDonByPTTT(idPTTT);
//            Iterable<HoaDonDTO> listHDDTO = StreamSupport.stream(listHD.spliterator(), false).
//                    map(HoaDonMapper::toDTO).toList();
//            return listHDDTO;
//        } catch (Exception e) {
//            System.err.println("Error occurred while get Hoa Don by ID PTTT");
//            return null;
//        }
//    }
//
//    @Override
//    public Iterable<HoaDonDTO> getHoaDonByLoaiHD(int idLoaiHD) {
//        try {
//            Iterable<HoaDon> listHD = hdRepo.findHoaDonByLoaiHD(idLoaiHD);
//            Iterable<HoaDonDTO> listHDDTO = StreamSupport.stream(listHD.spliterator(), false).
//                    map(HoaDonMapper::toDTO).toList();
//            return listHDDTO;
//        } catch (Exception e) {
//            System.err.println("Error occurred while get Hoa Don by ID PTTT");
//            return null;
//        }
//    }

}