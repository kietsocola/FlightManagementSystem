package com.project.flightManagement.Service;

import com.project.flightManagement.DTO.HangVeDTO.HangVeDTO;
import com.project.flightManagement.Model.HangVe;
import org.springframework.stereotype.Service;

@Service
public interface HangVeService {
    public Iterable<HangVeDTO> getAllHangVe();
}
