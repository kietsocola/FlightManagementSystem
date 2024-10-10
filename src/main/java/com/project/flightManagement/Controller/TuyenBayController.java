package com.project.flightManagement.Controller;

import com.project.flightManagement.DTO.TuyenBayDTO.TuyenBayDTO;
import com.project.flightManagement.Payload.ResponseData;
import com.project.flightManagement.Service.TuyenBayService;
import jakarta.persistence.EntityNotFoundException;
import jakarta.validation.Valid;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.*;

import java.util.HashMap;
import java.util.Map;
import java.util.Optional;


@RestController
@CrossOrigin("http://localhost:5173")
public class TuyenBayController {
    @Autowired
    private TuyenBayService tuyenBayService;
    private ResponseData response = new ResponseData();

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
        if (bindingResult.hasErrors()) {
            Map<String, String> fieldErrors = new HashMap<>();
            bindingResult.getFieldErrors().forEach(error -> fieldErrors.put(error.getField(), error.getDefaultMessage()));
            response.setStatusCode(400);
            response.setData(fieldErrors);
            response.setMessage("There are some fields invalid");
            return new ResponseEntity<>(response, HttpStatus.BAD_REQUEST);
        }

        Optional<TuyenBayDTO> savedRoute = tuyenBayService.addNewTuyenBay(tuyenBayDTO);
        if (savedRoute.isPresent()) {
            response.setMessage("Save route successfully!!");
            response.setData(savedRoute.get());
            response.setStatusCode(201);
            return new ResponseEntity<>(response, HttpStatus.CREATED);
        } else {
            response.setMessage("Save route unsuccessfully!!");
            response.setData(null);
            response.setStatusCode(500);
            return new ResponseEntity<>(response, HttpStatus.INTERNAL_SERVER_ERROR);
        }
    }

    @PutMapping("/updateRoute/{idTuyenBay}")
    public ResponseEntity<ResponseData> updateTuyenBay(@PathVariable("idTuyenBay") Integer idTuyenBay, @Valid @RequestBody TuyenBayDTO tuyenBayDTO, BindingResult bindingResult) {
        ResponseData response = new ResponseData();
        if (bindingResult.hasErrors()) {
            Map<String, String> fieldErrors = new HashMap<>();
            bindingResult.getFieldErrors().forEach(error -> fieldErrors.put(error.getField(), error.getDefaultMessage()));
            response.setStatusCode(400);
            response.setData(fieldErrors);
            response.setMessage("There are some fields invalid");
            return new ResponseEntity<>(response, HttpStatus.BAD_REQUEST);
        }

        Optional<TuyenBayDTO> updatedRoute = tuyenBayService.updateTuyenBay(tuyenBayDTO);
        if (updatedRoute.isPresent()) {
            response.setMessage("Update route successfully!!");
            response.setData(updatedRoute.get());// Trả về thông tin tuến bay đã cập
            response.setStatusCode(200); //ok
            return new ResponseEntity<>(response, HttpStatus.OK);
        } else {
            response.setMessage("Route not found!!");
            response.setData(null);
            response.setStatusCode(404);
            return new ResponseEntity<>(response, HttpStatus.NOT_FOUND);
        }
    }

    @DeleteMapping("/deleteRoute/{idTB}")
    public ResponseEntity<ResponseData> deleteTuyenBay(@PathVariable int idTB) {
        try {
            tuyenBayService.deleteTuyenBay(idTB);
            response.setMessage("Route deleted successfully!!");
            response.setData(null);
            response.setStatusCode(200);
            return new ResponseEntity<>(response, HttpStatus.OK);
        } catch (EntityNotFoundException e) {
            response.setMessage(e.getMessage());
            response.setData(null);
            response.setStatusCode(404);
            return new ResponseEntity<>(response, HttpStatus.NOT_FOUND);
        } catch (Exception e) {
            response.setMessage("Error occurred while deleting the route: " + e.getMessage());
            response.setData(null);
            response.setStatusCode(500);
            return new ResponseEntity<>(response, HttpStatus.INTERNAL_SERVER_ERROR);
        }
    }



    @GetMapping("/findRoutes")
    public ResponseEntity<ResponseData> findRoutesByStartAirport(@RequestParam String keyword) {
        System.out.println("Searching for: " + keyword);
        Iterable<TuyenBayDTO> listTuyenBayDTO = tuyenBayService.findBySanBayBatDau(keyword);
        if (listTuyenBayDTO.iterator().hasNext()) {
            response.setMessage("Get routes by start airport success!!");
            response.setData(listTuyenBayDTO);
            response.setStatusCode(200);
            return new ResponseEntity<>(response, HttpStatus.OK);
        } else {
            response.setMessage("No routes found for the start airport!!");
            response.setData(null);
            response.setStatusCode(404);
            return new ResponseEntity<>(response, HttpStatus.NOT_FOUND);
        }
    }


}
