package com.project.flightManagement.Service;

import com.project.flightManagement.DTO.QuocGiaDTO.QuocGiaDTO;
import com.project.flightManagement.Model.QuocGia;
import org.springframework.stereotype.Service;

import java.util.Optional;
@Service
public interface QuocGiaService {
    public Optional<QuocGiaDTO> getQuocGiaById(int id);
    public Iterable<QuocGiaDTO> getAllQuocGia();
}
