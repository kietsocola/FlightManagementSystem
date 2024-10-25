package com.project.flightManagement.Service;

import com.project.flightManagement.DTO.ChoNgoiDTO.ChoNgoiDTO;
import com.project.flightManagement.DTO.MayBayDTO.MayBayDTO;
import com.project.flightManagement.Model.ChoNgoi;
import com.project.flightManagement.Model.MayBay;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Service
public interface ChoNgoiService {
    public Iterable<ChoNgoiDTO> getAllChoNgoi();
    public Iterable<ChoNgoiDTO> getChoNgoiByMayBay(MayBay mayBay);
    public List<ChoNgoiDTO> getAllChoNgoiByIdChuyenBayandHangVe(int idCB, int hangVe);
    public Optional<ChoNgoiDTO> addNewChoNgoi(ChoNgoiDTO choNgoiDTO);
    public void deleteChoNgoi(ChoNgoiDTO choNgoiDTO);
    public Optional<ChoNgoiDTO> getChoNgoiById(int id);
    public Optional<ChoNgoiDTO> blockChoNgoi(int idChoNgoi);
    public Optional<ChoNgoiDTO> unblockChoNgoi(int idChoNgoi);
}
