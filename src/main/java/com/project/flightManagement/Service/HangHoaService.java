package com.project.flightManagement.Service;

import com.project.flightManagement.DTO.HangHoaDTO.HangHoaDTO;
import com.project.flightManagement.Model.HanhKhach;

import java.util.List;
import java.util.Optional;

public interface HangHoaService {
    public Iterable<HangHoaDTO> getAllHangHoa();

    public Optional<HangHoaDTO> getHangHoaByIdHangHoa(int id);

    public Optional<HangHoaDTO> addNewHangHoa(HangHoaDTO HangHoaDTO);

    public Optional<HangHoaDTO> updateHangHoa(Integer idHangHoa, HangHoaDTO HangHoaDTO);

    public Iterable<HangHoaDTO> getAllHangHoaSorted(String sortBy, String direction);

    public List<HangHoaDTO> findByTenHangHoa(String keyword);

    public Optional<HangHoaDTO> blockHangHoa(int id);

    public Optional<HangHoaDTO> unblockHangHoa(int id);


}

