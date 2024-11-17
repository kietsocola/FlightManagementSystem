package com.project.flightManagement.Service;

import com.project.flightManagement.DTO.HanhKhachDTO.HanhKhachCreateDTO;
import com.project.flightManagement.DTO.HanhKhachDTO.HanhKhachUpdateDTO;
import com.project.flightManagement.Model.HanhKhach;

import java.time.LocalDate;
import java.time.LocalDateTime;
import java.util.List;
import java.util.Map;
import java.util.Optional;

public interface HanhKhachService {
    boolean existHanhKhachByIdHanhKhach(int idHanhKhach);

//    boolean createHanhKhach(HanhKhach hanhKhach);
    HanhKhach createHanhKhach(HanhKhachCreateDTO hanhKhachCreateDTO);
    Optional<HanhKhach> getHanhKhachById(int idHanhKhach);

    HanhKhach updateHanhKhach(HanhKhachUpdateDTO hanhKhachUpdateDTO);

    HanhKhach saveNewHanhKhachWhenBooking(HanhKhach hk);

    public List<Map<String, Object>> getPassengerStatistics(LocalDateTime startDate, LocalDateTime endDate, String groupByType);

    Optional<HanhKhach> findByCccd(String cccd);
}
