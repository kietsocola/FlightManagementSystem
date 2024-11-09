package com.project.flightManagement.Service;

import com.project.flightManagement.DTO.DanhGiaDTO.DanhGiaDTO;
import com.project.flightManagement.Model.HangBay;
import org.springframework.stereotype.Service;

import java.time.LocalDate;
import java.time.LocalDateTime;
import java.util.Optional;

@Service
public interface DanhGiaService {
    Optional<DanhGiaDTO> getDanhGiaByID(int id);
    Iterable<DanhGiaDTO> getAllDanhGia();
    Iterable<DanhGiaDTO> getDanhGiaByKhachHang(int idKhachHang);
    Iterable<DanhGiaDTO> getDanhGiaByTenKhachHang(String tenKhachHang);
    Iterable<DanhGiaDTO> getDanhGiaByStartTimeAndEndTime(LocalDateTime startTime, LocalDateTime endTime);
    Iterable<DanhGiaDTO> getDanhGiaByStartTime(LocalDateTime startTime);
    Iterable<DanhGiaDTO> getDanhGiaByEndTime(LocalDateTime endTime);
    Iterable<DanhGiaDTO> getDanhGiaByHangBay(int idHangBay);
    boolean blockDanhGia(int id);
    boolean unblockDanhGia(int id);

}
