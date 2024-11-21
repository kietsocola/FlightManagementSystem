package com.project.flightManagement.Controller;


import com.project.flightManagement.DTO.TuyenBayDTO.TuyenBayDTO;
import com.project.flightManagement.Enum.ActiveEnum;
import com.project.flightManagement.Model.SanBay;
import com.project.flightManagement.Model.TuyenBay;
import com.project.flightManagement.Payload.ResponseData;
import com.project.flightManagement.Repository.SanBayRepository;
import com.project.flightManagement.Repository.TuyenBayRepository;
import com.project.flightManagement.Service.TuyenBayService;
import jakarta.persistence.EntityNotFoundException;
import jakarta.validation.Valid;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.dao.DataIntegrityViolationException;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.*;

import java.util.*;


@RestController
@CrossOrigin("http://localhost:5173")
public class TuyenBayController {
    @Autowired
    private TuyenBayService tuyenBayService;
    private ResponseData response = new ResponseData();
    @Autowired
    private SanBayRepository sanBayRepository;
    @Autowired
    private TuyenBayRepository tbRepo;

    @GetMapping("/getAllRoutes")
    public ResponseEntity<ResponseData> getAllTuyenBay() {
        ResponseData response = new ResponseData();
        Iterable<TuyenBayDTO> listTuyenBayDTO = tuyenBayService.getAllTuyenBay();
        if (listTuyenBayDTO.iterator().hasNext()) {
            response.setMessage("Get list of routes success!!");
            response.setData(listTuyenBayDTO);
            response.setStatusCode(200);
            return new ResponseEntity<>(response, HttpStatus.OK);
        } else {
            response.setMessage("No routes found!!");
            response.setData(null);
            response.setStatusCode(204);
            return new ResponseEntity<>(HttpStatus.NO_CONTENT);
        }
    }

    @GetMapping("/getAllRoutesSorted")
    public ResponseEntity<ResponseData> getAllTuyenBaySorted(@RequestParam(defaultValue = "idTuyenBay") String sortBy,
                                                             @RequestParam(defaultValue = "asc") String order) {
        Iterable<TuyenBayDTO> listTuyenBayDTO = tuyenBayService.getAllTuyenBaySorted(sortBy, order);
        if (listTuyenBayDTO.iterator().hasNext()) {
            response.setMessage("Get sorted routes success!!");
            response.setData(listTuyenBayDTO);
            response.setStatusCode(200);
            return new ResponseEntity<>(response, HttpStatus.OK);
        } else {
            response.setMessage("No routes found!!");
            response.setData(null);
            response.setStatusCode(204);
            return new ResponseEntity<>(HttpStatus.NO_CONTENT);
        }
    }

    @GetMapping("/getRouteById/{idTB}")
    public ResponseEntity<ResponseData> getTuyenBayByIdTuyenBay(@PathVariable int idTB) {
        ResponseData response = new ResponseData(); // Khởi tạo đối tượng response
        Optional<TuyenBayDTO> tuyenBayDTO = tuyenBayService.getTuyenBayByIdTuyenBay(idTB);
        if (tuyenBayDTO.isPresent()) {
            response.setMessage("Get route by ID success!!");
            response.setData(tuyenBayDTO.get()); // Chỉ lấy đối tượng DTO
            response.setStatusCode(200);
            return new ResponseEntity<>(response, HttpStatus.OK);
        } else {
            response.setMessage("Route not found!!");
            response.setData(null);
            response.setStatusCode(404);
            return new ResponseEntity<>(response, HttpStatus.NOT_FOUND);
        }
    }


    @PostMapping("/addNewRoute")
    public ResponseEntity<ResponseData> addNewTuyenBay(@Valid @RequestBody TuyenBayDTO tuyenBayDTO, BindingResult bindingResult) {
        ResponseData response = new ResponseData();

        // Handle validation errors
        if (bindingResult.hasErrors()) {
            Map<String, String> errors = new HashMap<>();
            bindingResult.getFieldErrors().forEach(error ->
                    errors.put(error.getField(), error.getDefaultMessage()) // Lấy thông báo lỗi cụ thể từ validation
            );
            response.setData(errors);
            response.setStatusCode(400);
            response.setMessage("Validation failed for input data.");
            return new ResponseEntity<>(response, HttpStatus.BAD_REQUEST);
        }

        // Check if departure and arrival airports are the same
        if (tuyenBayDTO.getIdSanBayBatDau()== tuyenBayDTO.getIdSanBayKetThuc()) {
            response.setMessage("Departure and arrival airports cannot be the same.");
            response.setData(null);
            response.setStatusCode(400);
            return new ResponseEntity<>(response, HttpStatus.BAD_REQUEST);
        }

        // Check if departure and arrival airports exist
        Optional<SanBay> existingSanBayBatDau = sanBayRepository.findById(tuyenBayDTO.getIdSanBayBatDau());
        Optional<SanBay> existingSanBayKetThuc = sanBayRepository.findById(tuyenBayDTO.getIdSanBayKetThuc());

        if (existingSanBayBatDau.isEmpty()) {
            response.setMessage("Departure airport does not exist.");
            response.setData(null);
            response.setStatusCode(400);
            return new ResponseEntity<>(response, HttpStatus.BAD_REQUEST);
        }

        if (existingSanBayKetThuc.isEmpty()) {
            response.setMessage("Arrival airport does not exist.");
            response.setData(null);
            response.setStatusCode(400);
            return new ResponseEntity<>(response, HttpStatus.BAD_REQUEST);
        }

        // Check if route already exists
        SanBay sanBayBatDau = existingSanBayBatDau.get();
        SanBay sanBayKetThuc = existingSanBayKetThuc.get();
        Optional<TuyenBay> existingTuyenBay = tbRepo.findBySanBayBatDauAndSanBayKetThuc(sanBayBatDau, sanBayKetThuc);

        if (existingTuyenBay.isPresent()) {
            response.setMessage("Route already exists.");
            response.setData(null);
            response.setStatusCode(400);
            return new ResponseEntity<>(response, HttpStatus.BAD_REQUEST);
        }

        // Add new route
        try {
            Optional<TuyenBayDTO> savedRoute = tuyenBayService.addNewTuyenBay(tuyenBayDTO);

            if (savedRoute.isPresent()) {
                response.setMessage("Route saved successfully!");
                response.setData(savedRoute.get());
                response.setStatusCode(201);
                return new ResponseEntity<>(response, HttpStatus.CREATED);
            } else {
                response.setMessage("Route saving failed due to unknown reasons.");
                response.setData(null);
                response.setStatusCode(500);
                return new ResponseEntity<>(response, HttpStatus.INTERNAL_SERVER_ERROR);
            }
        } catch (Exception e) {
            response.setMessage("Error saving route: " + e.getMessage());
            response.setData(null);
            response.setStatusCode(500);
            return new ResponseEntity<>(response, HttpStatus.INTERNAL_SERVER_ERROR);
        }
    }


    @PutMapping("/updateRoute/{idTuyenBay}")
    public ResponseEntity<ResponseData> updateTuyenBay(
            @PathVariable("idTuyenBay") Integer idTuyenBay,
            @Valid @RequestBody TuyenBayDTO tuyenBayDTO,
            BindingResult bindingResult) {
        ResponseData response = new ResponseData();

        // Xử lý lỗi validation
        if (bindingResult.hasErrors()) {
            Map<String, String> errors = new HashMap<>();
            bindingResult.getFieldErrors().forEach(error ->
                    errors.put(error.getField(), error.getDefaultMessage())
            );
            response.setData(errors);
            response.setMessage("Validation failed for the provided data.");
            response.setStatusCode(400);
            return new ResponseEntity<>(response, HttpStatus.BAD_REQUEST);
        }

        try {
            // Kiểm tra sân bay đi và đến
            if (tuyenBayDTO.getIdSanBayBatDau()== tuyenBayDTO.getIdSanBayKetThuc()) {
                response.setMessage("Departure and arrival airports cannot be the same.");
                response.setData(null);
                response.setStatusCode(400);
                return new ResponseEntity<>(response, HttpStatus.BAD_REQUEST);
            }

            Optional<SanBay> sanBayBatDau = sanBayRepository.findById(tuyenBayDTO.getIdSanBayBatDau());
            Optional<SanBay> sanBayKetThuc = sanBayRepository.findById(tuyenBayDTO.getIdSanBayKetThuc());

            if (sanBayBatDau.isEmpty()) {
                response.setMessage("Departure airport with ID " + tuyenBayDTO.getIdSanBayBatDau() + " does not exist.");
                response.setData(null);
                response.setStatusCode(400);
                return new ResponseEntity<>(response, HttpStatus.BAD_REQUEST);
            }

            if (sanBayKetThuc.isEmpty()) {
                response.setMessage("Arrival airport with ID " + tuyenBayDTO.getIdSanBayKetThuc() + " does not exist.");
                response.setData(null);
                response.setStatusCode(400);
                return new ResponseEntity<>(response, HttpStatus.BAD_REQUEST);
            }

            // Kiểm tra trùng lặp tuyến bay
            SanBay sanBayStart = sanBayBatDau.get();
            SanBay sanBayEnd = sanBayKetThuc.get();
            Optional<TuyenBay> existingRoute = tbRepo.findBySanBayBatDauAndSanBayKetThuc(sanBayStart, sanBayEnd);

            if (existingRoute.isPresent()) {
                response.setMessage("Route already exists.");
                response.setData(null);
                response.setStatusCode(400);
                return new ResponseEntity<>(response, HttpStatus.BAD_REQUEST);
            }

            // Thực hiện cập nhật tuyến bay
            Optional<TuyenBayDTO> updatedRoute = tuyenBayService.updateTuyenBay(idTuyenBay, tuyenBayDTO);
            if (updatedRoute.isPresent()) {
                response.setMessage("Route updated successfully!");
                response.setData(updatedRoute.get());
                response.setStatusCode(200);
                return new ResponseEntity<>(response, HttpStatus.OK);
            } else {
                response.setMessage("Route with ID " + idTuyenBay + " not found.");
                response.setData(null);
                response.setStatusCode(404);
                return new ResponseEntity<>(response, HttpStatus.NOT_FOUND);
            }
        } catch (Exception e) {
            response.setMessage("Error updating route: " + e.getMessage());
            response.setData(null);
            response.setStatusCode(500);
            return new ResponseEntity<>(response, HttpStatus.INTERNAL_SERVER_ERROR);
        }
    }


    @PutMapping("/blockRoute/{idTuyenBay}")
    public ResponseEntity<ResponseData> blockTuyenBay(@PathVariable int idTuyenBay){
        Optional<TuyenBayDTO> existingTB = tuyenBayService.getTuyenBayByIdTuyenBay(idTuyenBay);
        if(existingTB.isPresent()){
            if(existingTB.get().getStatus() == ActiveEnum.ACTIVE){
                Optional<TuyenBayDTO> blockTB = tuyenBayService.blockTuyenBay(existingTB.get().getIdTuyenBay());
                if(blockTB.isPresent()){
                    response.setMessage("Block route successfully!!");
                    response.setData(blockTB.get());
                    response.setStatusCode(200); // OK
                    return new ResponseEntity<>(response, HttpStatus.OK);
                } else {
                    // Xử lý lỗi khi cập nhật không thành công
                    response.setMessage("Block route unsuccessfully!!");
                    response.setData(null);
                    response.setStatusCode(500); // Internal Server Error
                    return new ResponseEntity<>(response, HttpStatus.INTERNAL_SERVER_ERROR);
                }
            } else {
                Optional<TuyenBayDTO> blockHH = tuyenBayService.unblockTuyenBay(existingTB.get().getIdTuyenBay());
                if(blockHH.isPresent()){
                    response.setMessage("Block route successfully!!");
                    response.setData(blockHH.get());
                    response.setStatusCode(200); // OK
                    return new ResponseEntity<>(response, HttpStatus.OK);
                } else {
                    // Xử lý lỗi khi cập nhật không thành công
                    response.setMessage("Block route unsuccessfully!!");
                    response.setData(null);
                    response.setStatusCode(500); // Internal Server Error
                    return new ResponseEntity<>(response, HttpStatus.INTERNAL_SERVER_ERROR);
                }
            }
        } else {
            response.setMessage("Airport not found!!");
            response.setData(null);
            response.setStatusCode(404); // Not Found
            return new ResponseEntity<>(response, HttpStatus.NOT_FOUND);
        }
    }



    @GetMapping("/top-routes")
    public ResponseEntity<ResponseData> getTopRoutes(@RequestParam String period) {
        // Lấy danh sách top 5 tuyến bay từ service
        Map<String, List<Map<String, Object>>> result = tuyenBayService.getTop5FlightRoutesByTimePeriod(period);

        // Tạo phản hồi
        ResponseData response = new ResponseData();
        response.setMessage("Get top 5 frequency routes successfully!!");
        response.setData(result);
        response.setStatusCode(200); // OK

        // Trả về ResponseEntity với mã trạng thái 200 và dữ liệu phản hồi
        return ResponseEntity.ok(response);
    }


}
