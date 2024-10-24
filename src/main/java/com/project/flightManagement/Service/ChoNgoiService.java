package com.project.flightManagement.Service;

import com.project.flightManagement.DTO.ChoNgoiDTO.ChoNgoiDTO;
import com.project.flightManagement.DTO.MayBayDTO.MayBayDTO;
import com.project.flightManagement.Model.ChoNgoi;
import org.springframework.stereotype.Service;

import java.util.Optional;

@Service
public interface ChoNgoiService {
    public Iterable<ChoNgoiDTO> getAllChoNgoi();
    public Iterable<ChoNgoiDTO> getChoNgoiByMayBay(MayBayDTO mayBayDTO);
    public Optional<ChoNgoiDTO> addNewChoNgoi(ChoNgoiDTO choNgoiDTO);
    public Optional<ChoNgoiDTO> deleteChoNgoi(ChoNgoiDTO choNgoiDTO);
}
