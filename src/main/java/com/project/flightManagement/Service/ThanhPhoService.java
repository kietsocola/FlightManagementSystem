package com.project.flightManagement.Service;

import com.project.flightManagement.DTO.ThanhPhoDTO.ThanhPhoDTO;
import com.project.flightManagement.Model.QuocGia;
import org.springframework.stereotype.Service;

import java.util.Optional;


@Service
public interface ThanhPhoService {
    public Optional<ThanhPhoDTO> getThanhPhoById(int id);
    public Iterable<ThanhPhoDTO> getAllThanhPho();
    public Iterable<ThanhPhoDTO> getAllThanhPhoByQuocGia(QuocGia quocGia);
}
