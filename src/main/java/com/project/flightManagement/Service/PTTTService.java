package com.project.flightManagement.Service;

import com.project.flightManagement.DTO.PTTTDTO.PTTTDTO;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Service
public interface PTTTService {
    public Optional<PTTTDTO> addPTTT(PTTTDTO ptttDTO);

    public Optional<PTTTDTO> updatePTTT(PTTTDTO ptttDTO);

    public Iterable<PTTTDTO> getAllPTTT();

    public Optional<PTTTDTO> getPTTTByID(int idPTTT);

    public List<PTTTDTO> findPhuongThucThanhToanByKeyWord(String keyword);

    public Optional<PTTTDTO> getPTTTByTen(String tenPTTT);
    public Iterable<PTTTDTO> getAllPTTTSorted(String sortBy, String direction);
}
