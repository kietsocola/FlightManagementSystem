package com.project.flightManagement.Service;

import com.project.flightManagement.DTO.ChucNangDTO.ChucNangDTO;
import com.project.flightManagement.Model.ChucNang;

import java.util.List;

public interface ChucNangService {
    ChucNang getChucNangByIdChucNang(int idChucNang);

    List<ChucNangDTO> getAllChucNang();
}
