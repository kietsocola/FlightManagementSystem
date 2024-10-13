package com.project.flightManagement.Repository;

import com.project.flightManagement.DTO.PTTTDTO.PTTTDTO;
import com.project.flightManagement.Model.PhuongThucThanhToan;
import com.project.flightManagement.Model.TaiKhoan;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.Optional;

public interface PTTTReposity extends JpaRepository<PhuongThucThanhToan, Integer> {

}
