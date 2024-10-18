package com.project.flightManagement.Service;

import com.project.flightManagement.DTO.LoaiQuyDinhDTO.LoaiQuyDinhDTO;
import com.project.flightManagement.Model.LoaiQuyDinh;

import java.util.Optional;

public interface LoaiQuyDinhService {

    public Optional<LoaiQuyDinhDTO> add(LoaiQuyDinhDTO loaiQuyDinhDTO);
    public Optional<LoaiQuyDinhDTO> update(LoaiQuyDinhDTO loaiQuyDinhDTO);
    public Iterable<LoaiQuyDinhDTO> getall();

    public Optional<LoaiQuyDinhDTO> getById(int idLoaiQuyDinh);


}
