package com.project.flightManagement.Service;

import com.project.flightManagement.DTO.ChuyenBayDTO.ChuyenBayDTO;
import com.project.flightManagement.Model.ChuyenBay;
import org.springframework.stereotype.Service;

import java.util.Date;
import java.util.List;
import java.util.Optional;

@Service
public interface ChuyenBayService {

    public Optional<ChuyenBayDTO> addChuyenBay(ChuyenBayDTO cbDTO);
    public Optional<ChuyenBayDTO> updateChuyenBay(ChuyenBayDTO cbDTO);

    public Iterable<ChuyenBayDTO> getAllChuyenBay();
    public List<ChuyenBayDTO> getAllChuyenBaySorted(String sortField , String sordOrder);

    public Optional<ChuyenBayDTO> getChuyenBayById(int  id);
    public List<ChuyenBayDTO> searchFlights(String departureLocation, String arrivalLocation, Date departureDate, int numberOfTickets);

    public ChuyenBay getChuyenBayEntityById(int idChuyenBay);
}
