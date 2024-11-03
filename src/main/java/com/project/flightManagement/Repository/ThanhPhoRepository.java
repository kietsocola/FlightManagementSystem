package com.project.flightManagement.Repository;

import com.project.flightManagement.Model.QuocGia;
import com.project.flightManagement.Model.ThanhPho;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface ThanhPhoRepository extends JpaRepository<ThanhPho, Integer> {
    List<ThanhPho> findByQuocGia(QuocGia quocGia);
}
