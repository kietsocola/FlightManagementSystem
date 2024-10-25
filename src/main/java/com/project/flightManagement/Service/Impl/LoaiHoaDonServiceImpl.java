package com.project.flightManagement.Service.Impl;

import com.project.flightManagement.DTO.LoaiHoaDonDTO.LoaiHoaDonDTO;
import com.project.flightManagement.Mapper.LoaiHoaDonMapper;
import com.project.flightManagement.Model.LoaiHoaDon;
import com.project.flightManagement.Repository.LoaiHoaDonRepository;
import com.project.flightManagement.Service.LoaiHoaDonService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Sort;
import org.springframework.stereotype.Service;

import java.util.Collections;
import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;
import java.util.stream.StreamSupport;

@Service
public class LoaiHoaDonServiceImpl implements LoaiHoaDonService {
    @Autowired
    private LoaiHoaDonRepository loaiHdRepo;

    @Override
    public Optional<LoaiHoaDonDTO> addLoaiHoaDon(LoaiHoaDonDTO loaiHoaDonDTO) {
        Optional<LoaiHoaDon> existingLoaiHD = loaiHdRepo.findById(loaiHoaDonDTO.getIdLoaiHD());
        if (!existingLoaiHD.isPresent()) {
            LoaiHoaDon loaiHD = LoaiHoaDonMapper.toEntity(loaiHoaDonDTO);
            LoaiHoaDon updatedLoaiHD = loaiHdRepo.save(loaiHD);
            return Optional.of(LoaiHoaDonMapper.toDTO(updatedLoaiHD));
        } else {
            System.err.println("Loai Hoa Don exist!");
            return null;
        }
    }

    @Override
    public Optional<LoaiHoaDonDTO> updateLoaiHoaDon(LoaiHoaDonDTO loaiHoaDonDTO) {
        Optional<LoaiHoaDon> existingLoaiHD = loaiHdRepo.findById(loaiHoaDonDTO.getIdLoaiHD());
        if (existingLoaiHD.isPresent()) {
            LoaiHoaDon hd = LoaiHoaDonMapper.toEntity(loaiHoaDonDTO);
            LoaiHoaDon updatedLoaiHD = loaiHdRepo.save(hd);
            return Optional.of(LoaiHoaDonMapper.toDTO(updatedLoaiHD));
        }else {
            System.err.println("Loai Hoa Don does not existing!");
            return Optional.empty();
        }
    }

    @Override
    public Iterable<LoaiHoaDonDTO> getAllLoaiHoaDon() {
        try {
            Iterable<LoaiHoaDon> listLoaiHD = loaiHdRepo.findAll();
            Iterable<LoaiHoaDonDTO> listLoaiHDDTO = StreamSupport.stream(listLoaiHD.spliterator(), false)
                    .map(LoaiHoaDonMapper::toDTO)
                    .toList();
            return listLoaiHDDTO;
        } catch (Exception e) {
            System.err.println("Error occurred while fetching Loai Hoa Don: " + e.getMessage());
            return Collections.emptyList();
        }
    }

    @Override
    public Optional<LoaiHoaDonDTO> getLoaiHoaDonById(int idLoaiHD) {
        try {
            Optional<LoaiHoaDon> loaiHoaDon = loaiHdRepo.findById(idLoaiHD);
            Optional<LoaiHoaDonDTO> loaiHoaDonDTO = loaiHoaDon.map(LoaiHoaDonMapper::toDTO);
            return loaiHoaDonDTO;
        } catch (Exception e) {
            System.err.println("Error occurred while get Loai Hoa Don by ID");
            return null;
        }
    }

    @Override
    public Iterable<LoaiHoaDonDTO> getAllLoaiHDSorted(String sortBy, String direction) {
        try {
            List<LoaiHoaDon> loaiHoaDonList;
            if (direction.equals("asc")) {
                loaiHoaDonList = loaiHdRepo.findAll(Sort.by(Sort.Direction.ASC, sortBy));
            } else {
                loaiHoaDonList = loaiHdRepo.findAll(Sort.by(Sort.Direction.DESC, sortBy));
            }
            List<LoaiHoaDonDTO> loaiHoaDonDTOList = loaiHoaDonList.stream().map(LoaiHoaDonMapper::toDTO).collect(Collectors.toList());
            return  loaiHoaDonDTOList;
        } catch (IllegalArgumentException e) {
            // Xử lý lỗi nếu tham số sortBy không hợp lệ hoặc có lỗi khác liên quan đến tham số
            System.err.println("Invalid sorting field: " + sortBy);
            return Collections.emptyList(); // Trả về danh sách rỗng

        } catch (Exception e) {
            // Xử lý các lỗi không lường trước khác
            System.err.println("An error occurred while fetching sorted bill types: " + e.getMessage());
            return Collections.emptyList(); // Trả về danh sách rỗng nếu có lỗi
        }
    }

    @Override
    public Optional<LoaiHoaDonDTO> getLoaiHDByTen(String tenLoaiHD) {
        try {
            Optional<LoaiHoaDon> loaiHoaDon = loaiHdRepo.getLoaiHoaDonByTen(tenLoaiHD);
            Optional<LoaiHoaDonDTO> loaiHoaDonDTO = loaiHoaDon.map(LoaiHoaDonMapper::toDTO);
            return loaiHoaDonDTO;
        } catch (Exception e) {
            System.err.println("Error: " + e);
            return null;
        }
    }

    @Override
    public List<LoaiHoaDonDTO> getLoaiHDByKeyWord(String keyWord) {
        List<LoaiHoaDon> loaiHoaDonList = loaiHdRepo.findLoaiHoaDonByKeyWord(keyWord);
        if (loaiHoaDonList.isEmpty()) {
            System.out.println("Không tìm thấy loại hóa đơn với từ khóa: " + keyWord);
        } else {
            System.out.println("Tồn tại loại hóa đơn với từ khóa: " + keyWord);
        }
        return loaiHoaDonList.stream().map(LoaiHoaDonMapper::toDTO).collect(Collectors.toList());
    }
}
