package com.project.flightManagement.Service;

import com.project.flightManagement.DTO.QuyenDTO.QuyenResponseDTO;
import com.project.flightManagement.DTO.VeDTO.*;
import com.project.flightManagement.Enum.VeEnum;
import com.project.flightManagement.Model.Ve;
import org.springframework.data.domain.Page;

import java.time.LocalDate;
import java.util.List;
import java.util.Optional;

public interface VeService {
    Page<VeDTO> getAllVe(int page, int size);
    Page<VeDTO> getAllVeByIdChuyenBay(int idChuyenBay, int page, int size);
    Iterable<VeDTO> getAllByIdChuyenBayByLam(int idChuyenBay);
    boolean updateVe(int idVe, VeUpdateDTO veUpdateDTO);

    VeDTO getVeById(int idVe);

    boolean createVe(VeCreateDTO veCreateDTO);
    boolean updateHanhKhachVe(VeUpdateHanhKhachDTO veUpdateHanhKhachDTO);

    void createAutoVeByIdChuyenBay(int idChuyenBay, double giaVeHangPhoThong, double giaVeHangThuongGia);

    void updateAutoGiaVeByIdChuyenBay(int idChuyenBay, double giaVeHangPhoThong, double giaVeHangThuongGia);

    Page<VeDTO> searchVeMaVaAndDateBay(String maVe, LocalDate startDate, LocalDate endDate, int page, int size);

    Page<VeDTO> searchVeMaVa(String maVe, int page, int size);

    List<VeEnum> getAllVeStatuses();
    List<VeDTO> getAllVeByIdChuyenBayNotPaging(int idChuyenBay);

    GiaVeDTO getAllGiaVe(int idChuyenBay);

    public Iterable<VeDTO> getAllVe();
}
