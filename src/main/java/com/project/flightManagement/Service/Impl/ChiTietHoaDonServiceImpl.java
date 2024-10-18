package com.project.flightManagement.Service.Impl;

import com.project.flightManagement.DTO.ChiTietHoaDonDTO.ChiTietHoaDonDTO;
import com.project.flightManagement.Mapper.ChiTietHoaDonMapper;
import com.project.flightManagement.Model.ChiTietHoaDon;
import com.project.flightManagement.Repository.ChiTietHoaDonRepository;
import com.project.flightManagement.Service.ChiTietHoaDonService;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.Optional;
import java.util.stream.StreamSupport;

@Service
public class ChiTietHoaDonServiceImpl implements ChiTietHoaDonService {

    @Autowired
    ChiTietHoaDonRepository chiTietHoaDonReposity;

    @Override
    public Iterable<ChiTietHoaDonDTO> getAllChiTietHoaDon() {
        try {
            Iterable<ChiTietHoaDon> listChiTietHoaDon = chiTietHoaDonReposity.findAll();
            Iterable<ChiTietHoaDonDTO> listChiTietHoaDonDTO = StreamSupport.stream(listChiTietHoaDon.spliterator(), false).map(ChiTietHoaDonMapper::toDTO).toList();
            return listChiTietHoaDonDTO;
        } catch (Exception e) {
            System.err.println("Lỗi khi lấy được danh sách chi tiết hóa đơn: " + e);
            return null;
        }
    }

    @Override
    public Optional<ChiTietHoaDonDTO> getChiTietHoaDonByID(int idChiTietHoaDon) {
        try {
            Optional<ChiTietHoaDon> chiTietHoaDon = chiTietHoaDonReposity.findById(idChiTietHoaDon);
            Optional<ChiTietHoaDonDTO> chiTietHoaDonDTO = chiTietHoaDon.map(ChiTietHoaDonMapper::toDTO);
            return chiTietHoaDonDTO;
        } catch (Exception e) {
            System.err.println("Lỗi khi lấy chi tiết hóa đơn theo ID: " + e);
            return null;
        }
    }

    @Override
    public Optional<ChiTietHoaDonDTO> addChiTietHoaDon(ChiTietHoaDonDTO chiTietHoaDonDTO) {
        Optional<ChiTietHoaDon> existingCTHD = chiTietHoaDonReposity.findById(chiTietHoaDonDTO.getIdChiTietHoaDon());
        if (!existingCTHD.isPresent()) {
            ChiTietHoaDon chiTietHoaDon = ChiTietHoaDonMapper.toEntity(chiTietHoaDonDTO);
            ChiTietHoaDon savedCTHD = chiTietHoaDonReposity.save(chiTietHoaDon);
            return Optional.of(ChiTietHoaDonMapper.toDTO(savedCTHD));
        } else {
            System.err.println("Chi tiết hóa đơn đã tồn tại!");
            return null;
        }
    }

    @Override
    public Optional<ChiTietHoaDonDTO> updateChiTietHoaDon(ChiTietHoaDonDTO chiTietHoaDonDTO) {
        Optional<ChiTietHoaDon> existingCTHD = chiTietHoaDonReposity.findById(chiTietHoaDonDTO.getIdChiTietHoaDon());
        if (existingCTHD.isPresent()) {
            ChiTietHoaDon chiTietHoaDon = ChiTietHoaDonMapper.toEntity(chiTietHoaDonDTO);
            ChiTietHoaDon updatedCTHD = chiTietHoaDonReposity.save(chiTietHoaDon);
            return Optional.of(ChiTietHoaDonMapper.toDTO(updatedCTHD));
        } else {
            System.err.println("Không tồn tại chi tiết hóa đơn!");
            return null;
        }
    }
}
