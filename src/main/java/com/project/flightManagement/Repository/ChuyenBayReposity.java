package com.project.flightManagement.Repository;

import com.project.flightManagement.Model.ChuyenBay;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

import java.util.List;

public interface ChuyenBayReposity extends JpaRepository<ChuyenBay, Integer> {

}
