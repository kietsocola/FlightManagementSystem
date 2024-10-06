package com.project.flightManagement.Service;

import com.project.flightManagement.DTO.HangBayDTO.HangBayDTO;
import com.project.flightManagement.DTO.MayBayDTO.MayBayDTO;
import org.springframework.stereotype.Service;

import java.util.Optional;
@Service
public interface HangBayService {
    public Optional<HangBayDTO> getHangBayById(int id);
}
