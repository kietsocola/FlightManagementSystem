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
    private TuyenBayRepository tuyenBayRepo;

    @Override
    public List<TuyenBayDTO> getAllTuyenBay() {
        try {
            Iterable<TuyenBay> listTuyenBay = tuyenBayRepo.findAll();
            return StreamSupport.stream(listTuyenBay.spliterator(), false)
                    .map(TuyenBayMapper::toDTO)
                    .collect(Collectors.toList());
        } catch (Exception e) {
            System.err.println("Error occurred while fetching routes: " + e);

            return Collections.emptyList();
        }
    }



    @Override
    public Optional<TuyenBayDTO> getTuyenBayById(int id) {
        try {
            Optional<TuyenBay> tuyenBay = tuyenBayRepo.findById(id);
            return tuyenBay.map(TuyenBayMapper::toDTO);
        } catch (Exception e) {
            System.err.println("Error occurred while fetching route by id: " + e.getMessage());
            return Optional.empty();
        }
    }

    @Override
    public Optional<TuyenBayDTO> addNewTuyenBay(TuyenBayDTO tuyenBayDTO) {
        try {
            TuyenBay tuyenBay = TuyenBayMapper.toEntity(tuyenBayDTO);
            TuyenBay savedTuyenBay = tuyenBayRepo.save(tuyenBay);
            return Optional.of(TuyenBayMapper.toDTO(savedTuyenBay));
        } catch (Exception e) {
            System.err.println("Error occurred while adding new route: " + e.getMessage());
            return Optional.empty();
        }
    }

    @Override
    public Optional<TuyenBayDTO> updateTuyenBay(TuyenBayDTO tuyenBayDTO) {
        try {
            if (tuyenBayRepo.existsById(tuyenBayDTO.getIdTuyenBay())) {
                TuyenBay tuyenBay = TuyenBayMapper.toEntity(tuyenBayDTO);
                TuyenBay updatedTuyenBay = tuyenBayRepo.save(tuyenBay);
                return Optional.of(TuyenBayMapper.toDTO(updatedTuyenBay));
            } else {
                System.err.println("Route does not exist!");
                return Optional.empty();
            }
        } catch (Exception e) {
            System.err.println("Error occurred while updating route: " + e.getMessage());
            return Optional.empty();
        }
    }


    @Override
    public Iterable<TuyenBayDTO> getAllTuyenBaySorted(String sortBy) {
        try {
            List<TuyenBay> tuyenBayList = tuyenBayRepo.findAll(Sort.by(Sort.Direction.DESC, sortBy));
            return tuyenBayList.stream().map(TuyenBayMapper::toDTO).collect(Collectors.toList());
        } catch (IllegalArgumentException e) {
            System.err.println("Invalid sorting field: " + sortBy);
            return Collections.emptyList();
        } catch (Exception e) {
            System.err.println("Error occurred while fetching sorted routes: " + e.getMessage());
            return Collections.emptyList();
        }
    }

    @Override
    public List<TuyenBayDTO> getAllTuyenBayIdSanBayBatDau(int idSanBayBatDau) {
        try {
            List<TuyenBay> tuyenBayList = tuyenBayRepo.findBySanBayBatDau_Id(idSanBayBatDau);
            return tuyenBayList.stream().map(TuyenBayMapper::toDTO).collect(Collectors.toList());
        } catch (Exception e) {
            System.err.println("Error occurred while fetching routes by start airport: " + e.getMessage());
            return Collections.emptyList();
        }
    }

    @Override
    public List<TuyenBayDTO> getAllTuyenBayIdSanBayKetThuc(int idSanBayKetThuc) {
        try {
            List<TuyenBay> tuyenBayList = tuyenBayRepo.findBySanBayKetThuc_Id(idSanBayKetThuc);
            return tuyenBayList.stream().map(TuyenBayMapper::toDTO).collect(Collectors.toList());
        } catch (Exception e) {
            System.err.println("Error occurred while fetching routes by end airport: " + e.getMessage());
            return Collections.emptyList();
        }
    }
}
