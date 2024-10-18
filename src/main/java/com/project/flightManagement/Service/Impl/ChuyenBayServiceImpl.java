package com.project.flightManagement.Service.Impl;

import com.project.flightManagement.DTO.ChuyenBayDTO.ChuyenBayDTO;
import com.project.flightManagement.Mapper.ChuyenBayMapper;
import com.project.flightManagement.Model.ChuyenBay;
import com.project.flightManagement.Repository.ChuyenBayRepository;
import com.project.flightManagement.Service.ChuyenBayService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Sort;
import org.springframework.stereotype.Service;

import java.util.Collections;
import java.util.Date;
import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;
import java.util.stream.StreamSupport;

@Service
public class ChuyenBayServiceImpl implements ChuyenBayService {
    @Autowired
    private ChuyenBayRepository repo;
    @Override
    public List<ChuyenBayDTO> searchFlights(String departureCity, String arrivalCity, Date departureDate, int numberOfTickets) {

        List<ChuyenBay> cb = repo.findFlightsOneWay(departureCity, arrivalCity, departureDate, numberOfTickets);
        if(cb!=null) return cb.stream().map(ChuyenBayMapper::toDTO).collect(Collectors.toList());
        return Collections.emptyList();
    }
    @Override
    public Optional<ChuyenBayDTO> addChuyenBay(ChuyenBayDTO cbDTO) {
        ChuyenBay cb = ChuyenBayMapper.toEntity(cbDTO);
        ChuyenBay savecb = repo.save(cb);
        return Optional.of(ChuyenBayMapper. toDTO(savecb));
    }

    @Override
    public Optional<ChuyenBayDTO> updateChuyenBay(ChuyenBayDTO cbDTO) {
        ChuyenBay cb = ChuyenBayMapper.toEntity(cbDTO);
        ChuyenBay updatecb = repo.save(cb);
        return Optional.of(ChuyenBayMapper.toDTO(updatecb));
    }

    @Override
    public Iterable<ChuyenBayDTO> getAllChuyenBay() {
        try{
            Iterable<ChuyenBay> listcb =  repo.findAll();
            Iterable<ChuyenBayDTO> listcbDTO = StreamSupport.stream(listcb.spliterator(),false)
                    .map(ChuyenBayMapper::toDTO)
                    .toList();
            return listcbDTO;
        }catch (Exception e){
            System.err.println("error occurred while fetching customers :" + e.getMessage());
            return Collections.emptyList();
        }
    }

    @Override
    public List<ChuyenBayDTO> getAllChuyenBaySorted(String sortField, String sordOrder) {
        try{
            // Kiểm tra hướng sắp xếp
            Sort.Direction direction = Sort.Direction.ASC; // Mặc định là ASC
            if ("desc".equalsIgnoreCase(sordOrder)) {
                direction = Sort.Direction.DESC; // Thay đổi thành DESC nếu yêu cầu
            }
            List<ChuyenBay> listcb = repo.findAll(Sort.by( direction , sortField));
            List<ChuyenBayDTO> listcbDTO = listcb.stream().map(ChuyenBayMapper::toDTO).collect(Collectors.toList());
            return listcbDTO;
        }catch (IllegalArgumentException e){

            System.err.println("Invalid sorting field: " + sortField);
            return Collections.emptyList();
        }catch (Exception e) {
            System.err.println("An error occurred while fetching sorted chuyen bay: " + e.getMessage());
            return Collections.emptyList();
        }
    }

    @Override
    public Optional<ChuyenBayDTO> getChuyenBayById(int id) {
        Optional<ChuyenBay> cb = repo.findById(id);
        return cb.map(ChuyenBayMapper::toDTO);
    }
}
