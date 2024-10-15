package com.project.flightManagement.Repository;

import com.project.flightManagement.Model.HangHoa;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface HangHoaRepository extends JpaRepository<HangHoa, Integer> {
    @Query("SELECT hh FROM HangHoa hh WHERE " +
            "LOWER(hh.tenHangHoa) LIKE LOWER(CONCAT('%', :keyword, '%')) " )
    List<HangHoa> findByKeywordContainingIgnoreCase(@Param("keyword") String keyword);
}

