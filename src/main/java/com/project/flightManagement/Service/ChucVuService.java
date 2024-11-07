package com.project.flightManagement.Service;

import com.project.flightManagement.DTO.ChucVuDTO.ChucVuDTO;
import com.project.flightManagement.Enum.ActiveEnum;
import com.project.flightManagement.Model.ChucVu;

import java.util.Optional;

public interface ChucVuService {

    public Optional<ChucVuDTO> addChucVu(ChucVuDTO chucVuDTO);

    public Iterable<ChucVuDTO> getAllChucVu();
    public Optional<ChucVuDTO> getChucVuByTen(String ten);
    public Optional<ChucVuDTO> getChucVuById(int id);

    public Iterable<ChucVuDTO> searchChucVu(String ten, ActiveEnum trangThaiActive);

}