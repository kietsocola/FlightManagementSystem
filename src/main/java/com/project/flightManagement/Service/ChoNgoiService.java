package com.project.flightManagement.Service;

import com.project.flightManagement.DTO.ChoNgoiDTO.ChoNgoiDTO;
import com.project.flightManagement.Model.ChoNgoi;
import com.project.flightManagement.Model.MayBay;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public interface ChoNgoiService {
    public Iterable<ChoNgoiDTO> getAllChoNgoi();
    public Iterable<ChoNgoiDTO> getChoNgoiByMayBay(MayBay mayBay);
    public List<ChoNgoiDTO> getAllChoNgoiByIdChuyenBayandHangVe(int idCB, int hangVe);
}
