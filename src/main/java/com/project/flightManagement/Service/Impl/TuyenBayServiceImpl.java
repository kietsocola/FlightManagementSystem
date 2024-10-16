package com.project.flightManagement.Service.Impl;

import com.project.flightManagement.DTO.TuyenBayDTO.TuyenBayDTO;
import com.project.flightManagement.Mapper.TuyenBayMapper;
import com.project.flightManagement.Model.TuyenBay;
import com.project.flightManagement.Repository.TuyenBayRepository;
import com.project.flightManagement.Service.TuyenBayService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Sort;
import org.springframework.stereotype.Service;

import java.util.Collections;
import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;
import java.util.stream.StreamSupport;

@Service
public class TuyenBayServiceImpl implements TuyenBayService {

    @Autowired
    private TuyenBayRepository tbRepo;

    @Override
    public List<TuyenBayDTO> getAllTuyenBay() {
        try {
            Iterable<TuyenBay> listTB = tbRepo.findAll();
            return StreamSupport.stream(listTB.spliterator(), false)
                    .map(TuyenBayMapper::toDTO)
                    .collect(Collectors.toList());
        } catch (Exception e) {
            System.err.println("Error occurred while fetching routes: " + e);
            return Collections.emptyList();
        }
    }

    @Override
    public Optional<TuyenBayDTO> getTuyenBayByIdTuyenBay(int idTB) {
        try {
            return tbRepo.findById(idTB).map(TuyenBayMapper::toDTO);
        } catch (Exception e) {
            System.err.println("Error occurred while fetching route by ID: " + e.getMessage());
            // Bạn có thể xử lý thêm ở đây
            return Optional.empty();
        }
    }



    @Override
    public Optional<TuyenBayDTO> addNewTuyenBay(TuyenBayDTO tuyenBayDTO) {
        try {
            TuyenBay tb = TuyenBayMapper.toEntity(tuyenBayDTO);
            TuyenBay savedTuyenBay = tbRepo.save(tb);
            return Optional.of(TuyenBayMapper.toDTO(savedTuyenBay));
        } catch (Exception e) {
            System.err.println("Error occurred while adding new route: " + e.getMessage());
            return Optional.empty(); // Trả về Optional.empty() thay vì null
        }
    }

    @Override
    public Optional<TuyenBayDTO> updateTuyenBay(TuyenBayDTO tuyenBayDTO) {
        Optional<TuyenBay> existingTB = tbRepo.findById(tuyenBayDTO.getIdTuyenBay());
        if (existingTB.isPresent()) {
            TuyenBay tuyenBay = TuyenBayMapper.toEntity(tuyenBayDTO);
            TuyenBay updatedTuyenBay = tbRepo.save(tuyenBay);
            return Optional.of(TuyenBayMapper.toDTO(updatedTuyenBay));
        } else {
            System.err.println("Route does not exist!");
            return Optional.empty();
        }
    }

    @Override
    public String deleteTuyenBay(int id) {
        tbRepo.deleteById(id);
        return "Route remove: " + id;
    }



    @Override
    public List<TuyenBayDTO> getAllTuyenBaySorted(String sortBy, String direction) {
        try {
            Sort sort = Sort.by("asc".equalsIgnoreCase(direction) ? Sort.Direction.ASC : Sort.Direction.DESC, sortBy);
            return tbRepo.findAll(sort).stream()
                    .map(TuyenBayMapper::toDTO)
                    .collect(Collectors.toList());
        } catch (IllegalArgumentException e) {
            System.err.println("Invalid sorting field: " + sortBy);
            return Collections.emptyList();
        } catch (Exception e) {
            System.err.println("Error occurred while fetching sorted routes: " + e.getMessage());
            return Collections.emptyList();
        }
    }

    @Override
    public List<TuyenBayDTO> findBySanBayBatDau(String keyword) {
        List<TuyenBay> tuyenBayList = tbRepo.findByKeywordContainingIgnoreCase(keyword);
        if (tuyenBayList.isEmpty()) {
            System.out.println("No route found with the keyword: " + keyword);
            return Collections.emptyList();
        } else {
            System.out.println("Id route found: " + tuyenBayList.get(0).getSanBayBatDau());
            return tuyenBayList.stream()
                    .map(TuyenBayMapper::toDTO)
                    .collect(Collectors.toList());
        }
    }
}