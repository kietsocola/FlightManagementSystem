package com.project.flightManagement.Service;

import com.project.flightManagement.DTO.ChoNgoiDTO.ChoNgoiDTO;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public interface ChoNgoiService {
    public List<ChoNgoiDTO> getAllChoNgoiByIdChuyenBayandHangVe(int idCB, int hangVe);
}
