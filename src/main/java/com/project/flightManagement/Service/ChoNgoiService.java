package com.project.flightManagement.Service;

import com.project.flightManagement.DTO.ChoNgoiDTO.ChoNgoiDTO;
import com.project.flightManagement.DTO.MayBayDTO.MayBayDTO;
import com.project.flightManagement.Model.ChoNgoi;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Service
public interface ChoNgoiService {
    public Iterable<ChoNgoiDTO> getAllChoNgoi();
    public Iterable<ChoNgoiDTO> getChoNgoiByMayBay(MayBayDTO mayBayDTO);
    public Optional<ChoNgoiDTO> addNewChoNgoi(ChoNgoiDTO choNgoiDTO);
    public void deleteChoNgoiByMayBay(MayBayDTO mayBayDTO);
    public Optional<ChoNgoiDTO> getChoNgoiById(int id);
    public List<ChoNgoiDTO> getAllChoNgoiByIdChuyenBayandHangVe(int idCB, int hangVe);
    public Optional<ChoNgoiDTO> blockChoNgoi(int id);
    public Optional<ChoNgoiDTO> unblockChoNgoi(int id);

    Optional<ChoNgoi> getChoNgoiEntityById(int idChoNgoi);

}
