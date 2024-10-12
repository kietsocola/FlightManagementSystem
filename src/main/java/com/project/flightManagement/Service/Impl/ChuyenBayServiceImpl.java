package com.project.flightManagement.Service.Impl;

import com.project.flightManagement.DTO.ChuyenBayDTO.ChuyenBayDTO;
import com.project.flightManagement.Mapper.ChuyenBayMapper;
import com.project.flightManagement.Model.ChuyenBay;
import com.project.flightManagement.Repository.ChuyenBayRepository;
import com.project.flightManagement.Service.ChuyenBayService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.Collections;
import java.util.Date;
import java.util.List;
import java.util.stream.Collectors;

@Service
public class ChuyenBayServiceImpl implements ChuyenBayService {
    @Autowired
    private ChuyenBayRepository chuyenBayRepository;
    @Override
    public List<ChuyenBayDTO> searchFlights(String departureCity, String arrivalCity, Date departureDate, int numberOfTickets) {

        List<ChuyenBay> cb = chuyenBayRepository.findFlightsOneWay(departureCity, arrivalCity, departureDate, numberOfTickets);
        if(cb!=null) return cb.stream().map(ChuyenBayMapper::toChuyenBayDTO).collect(Collectors.toList());
        return Collections.emptyList();
    }
}
