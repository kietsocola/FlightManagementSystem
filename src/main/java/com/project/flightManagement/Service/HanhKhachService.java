package com.project.flightManagement.Service;

import com.project.flightManagement.DTO.HanhKhachDTO.HanhKhachCreateDTO;
import com.project.flightManagement.DTO.HanhKhachDTO.HanhKhachUpdateDTO;
import com.project.flightManagement.Model.HanhKhach;

import java.util.Optional;

public interface HanhKhachService {
    boolean existHanhKhachByIdHanhKhach(int idHanhKhach);

//    boolean createHanhKhach(HanhKhach hanhKhach);
    HanhKhach createHanhKhach(HanhKhachCreateDTO hanhKhachCreateDTO);
    Optional<HanhKhach> getHanhKhachById(int idHanhKhach);

    HanhKhach updateHanhKhach(HanhKhachUpdateDTO hanhKhachUpdateDTO);
}
