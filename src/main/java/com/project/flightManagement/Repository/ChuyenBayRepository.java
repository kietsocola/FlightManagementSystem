package com.project.flightManagement.Repository;

import com.project.flightManagement.Model.ChuyenBay;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import java.util.Date;
import java.util.List;

public interface ChuyenBayRepository extends JpaRepository<ChuyenBay, Integer> {

    // Tìm chuyến bay một chiều
    @Query("SELECT c FROM ChuyenBay c " +
            "JOIN c.tuyenBay t " +
            "JOIN t.sanBayBatDau sbd " +
            "JOIN sbd.thanhPho cityDi " +
            "JOIN t.sanBayKetThuc sbdn " +
            "JOIN sbdn.thanhPho cityDen " +
            "WHERE cityDi.tenThanhPho = :departureCity " +
            "AND cityDen.tenThanhPho = :arrivalCity " +
            "AND c.ngayBay = :departureDate " +
            "AND c.soGhe >= :numberOfTickets")
    List<ChuyenBay> findFlightsOneWay(@Param("departureCity") String departureCity,
                                      @Param("arrivalCity") String arrivalCity,
                                      @Param("departureDate") Date departureDate,
                                      @Param("numberOfTickets") int numberOfTickets);

    // Tìm chuyến bay hai chiều
//    @Query("SELECT c FROM ChuyenBay c " +
//            "JOIN c.tuyenBay t " +
//            "JOIN t.sanBayBatDau sbd " +
//            "JOIN sbd.thanhPho cityDi " +
//            "JOIN t.sanBayKetThuc sbdn " +
//            "JOIN sbdn.thanhPho cityDen " +
//            "WHERE cityDi.tenThanhPho = :departureCity " +
//            "AND cityDen.tenThanhPho = :arrivalCity " +
//            "AND c.ngayBay = :departureDate " +
//            "AND c.soGhe >= :numberOfTickets " +
//            "OR (c.ngayBay = :returnDate)")
//    List<ChuyenBay> findFlightsRoundTrip(@Param("departureCity") String departureCity,
//                                         @Param("arrivalCity") String arrivalCity,
//                                         @Param("departureDate") Date departureDate,
//                                         @Param("returnDate") Date returnDate,
//                                         @Param("numberOfTickets") int numberOfTickets);

}
