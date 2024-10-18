package com.project.flightManagement.Service;

import com.project.flightManagement.DTO.QuyDinhDTO.QuyDinhDTO;
import com.project.flightManagement.Repository.QuyDinhRepository;

import java.util.Optional;

public interface QuyDinhService {

    public Optional<QuyDinhDTO> addQuyDinh(QuyDinhDTO quyDinhDTO);

    public Iterable<QuyDinhDTO> getAllQuyDinh();
    public Optional<QuyDinhDTO> getQuyDinhById(int id);
}
