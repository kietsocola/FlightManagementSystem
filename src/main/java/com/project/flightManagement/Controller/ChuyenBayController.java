package com.project.flightManagement.Controller;

import com.project.flightManagement.DTO.ChuyenBayDTO.ChuyenBayDTO;
import com.project.flightManagement.Model.ChuyenBay;
import com.project.flightManagement.Payload.ResponseData;
import com.project.flightManagement.Service.ChuyenBayService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.format.annotation.DateTimeFormat;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import java.util.*;

@RestController
@RequestMapping("/api/chuyenbay")
public class ChuyenBayController {

    @Autowired
    private ChuyenBayService chuyenBayService;

    @GetMapping("/search")
    public ResponseEntity<ResponseData> searchFlights(
            @RequestParam String departureLocation,
            @RequestParam String arrivalLocation,
            @RequestParam @DateTimeFormat(iso = DateTimeFormat.ISO.DATE) Date departureDate,
            @RequestParam(required = false) @DateTimeFormat(iso = DateTimeFormat.ISO.DATE) Date returnDate,
            @RequestParam int numberOfTickets) {
        ResponseData response = new ResponseData();
        List<ChuyenBayDTO> flightsDi = chuyenBayService.searchFlights(departureLocation, arrivalLocation, departureDate, numberOfTickets);
        List<ChuyenBayDTO> flightsVe = chuyenBayService.searchFlights(arrivalLocation, departureLocation, returnDate, numberOfTickets);

        Map<String, Object> flightMap = new HashMap<>();

// Kiểm tra chuyến đi
        if (flightsDi != null && !flightsDi.isEmpty()) {
            flightMap.put("chuyenbaydi", Map.of("status", "found", "data", flightsDi));
        } else {
            flightMap.put("chuyenbaydi", Map.of("status", "not_found", "data", null));
        }

// Kiểm tra chuyến về
        if (flightsVe != null && !flightsVe.isEmpty()) {
            flightMap.put("chuyenbayve", Map.of("status", "found", "data", flightsVe));
        } else {
            flightMap.put("chuyenbayve", Map.of("status", "not_found", "data", null));
        }

// Xử lý phản hồi tổng thể
        if (!"not_found".equals(((Map<String, Object>) flightMap.get("chuyenbaydi")).get("status"))
                || !"not_found".equals(((Map<String, Object>) flightMap.get("chuyenbayve")).get("status"))) {
            response.setMessage("Flight search completed with partial or full results.");
            response.setData(flightMap);
            response.setStatusCode(200);
            return new ResponseEntity<>(response, HttpStatus.OK);
        } else {
            response.setMessage("No flights found for both departure and return routes.");
            response.setData(flightMap); // Trả về map dù không tìm thấy
            response.setStatusCode(404);
            return new ResponseEntity<>(response, HttpStatus.NOT_FOUND);
        }

    }
}
