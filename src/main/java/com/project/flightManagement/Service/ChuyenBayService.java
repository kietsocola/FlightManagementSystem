package com.project.flightManagement.Service;

import com.project.flightManagement.DTO.ChuyenBayDTO.ChuyenBayDTO;
import com.project.flightManagement.Model.ChuyenBay;
import org.springframework.stereotype.Service;

import java.util.Date;
import java.util.List;

@Service
public interface ChuyenBayService {
    public List<ChuyenBayDTO> searchFlights(String departureLocation, String arrivalLocation, Date departureDate, int numberOfTickets);
}
