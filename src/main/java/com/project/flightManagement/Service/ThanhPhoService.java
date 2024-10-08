package com.project.flightManagement.Service;

import com.project.flightManagement.DTO.ThanhPhoDTO.ThanhPhoDTO;
import org.springframework.stereotype.Service;

import java.util.Optional;


@Service
public interface ThanhPhoService {
    public Optional<ThanhPhoDTO> getThanhPhoById(int id);
    public Iterable<ThanhPhoDTO> getAllThanhPho();
}
