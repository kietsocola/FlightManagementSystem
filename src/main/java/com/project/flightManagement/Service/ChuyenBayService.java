package com.project.flightManagement.Service;

import com.project.flightManagement.DTO.ChuyenBayDTO.ChuyenBayDTO;
import com.project.flightManagement.Enum.ChuyenBayEnum;
import com.project.flightManagement.Model.ChuyenBay;
import com.project.flightManagement.Model.MayBay;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;
import java.util.Date;
import java.util.List;
import java.util.Map;
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


    public List<ChuyenBayDTO> getFilterChuyenBay(ChuyenBayEnum trangThai, LocalDateTime thoiGianBatDau , LocalDateTime thoiGianKetThuc);
    public Iterable<ChuyenBayDTO> getChuyenBayByYear(int nam) ;
    public Iterable<ChuyenBayDTO> getChuyenBayByYearAndMonth(int year , int month) ;
    public Iterable<ChuyenBayDTO> filterChuyenBayByQuyAndNam(int year , int month) ;
    public List<ChuyenBayDTO> getChuyenBayByMayBay(MayBay mb);
    public String getHoursOfFlight(int idChuyenBay);
}
