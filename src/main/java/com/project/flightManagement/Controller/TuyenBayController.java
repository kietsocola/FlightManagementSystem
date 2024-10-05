package com.project.flightManagement.Controller;

import com.project.flightManagement.DTO.TuyenBayDTO.TuyenBayDTO;
import com.project.flightManagement.Payload.ResponseData;
import com.project.flightManagement.Service.TuyenBayService;
import jakarta.validation.Valid;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.*;

import java.util.HashMap;
import java.util.Map;
import java.util.Optional;

@Controller
@RestController
@CrossOrigin("http://localhost:5173")
public class TuyenBayController {
    @Autowired
    private TuyenBayService tuyenBayService;
    private ResponseData response = new ResponseData();

    @GetMapping("/getAllRoutes")
    public ResponseEntity<ResponseData> getAllTuyenBay() {
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
    public ResponseEntity<ResponseData> getAllTuyenBaySorted(@RequestParam(defaultValue = "idTuyenBay") String sortBy) {
        Iterable<TuyenBayDTO> listTuyenBayDTO = tuyenBayService.getAllTuyenBaySorted(sortBy);
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

    @GetMapping("/getRouteById")
    public ResponseEntity<ResponseData> getTuyenBayById(@RequestParam(defaultValue = "idTuyenBay") int idTuyenBay) {
        Optional<TuyenBayDTO> tuyenBayDTO = tuyenBayService.getTuyenBayById(idTuyenBay);
        if (tuyenBayDTO.isPresent()) {
            response.setMessage("Get route by ID success!!");
            response.setData(tuyenBayDTO);
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
            response.setData(updatedRoute.get());
            response.setStatusCode(200);
            return new ResponseEntity<>(response, HttpStatus.OK);
        } else {
            response.setMessage("Route not found!!");
            response.setData(null);
            response.setStatusCode(404);
            return new ResponseEntity<>(response, HttpStatus.NOT_FOUND);
        }
    }

    @GetMapping("/findRoutesByStartAirport")
    public ResponseEntity<ResponseData> findRoutesByStartAirport(@RequestParam("idSanBayBatDau") int idSanBayBatDau) {
        Iterable<TuyenBayDTO> listTuyenBayDTO = tuyenBayService.getAllTuyenBayIdSanBayBatDau(idSanBayBatDau);
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

    @GetMapping("/findRoutesByEndAirport")
    public ResponseEntity<ResponseData> findRoutesByEndAirport(@RequestParam("idSanBayKetThuc") int idSanBayKetThuc) {
        Iterable<TuyenBayDTO> listTuyenBayDTO = tuyenBayService.getAllTuyenBayIdSanBayKetThuc(idSanBayKetThuc);
        if (listTuyenBayDTO.iterator().hasNext()) {
            response.setMessage("Get routes by end airport success!!");
            response.setData(listTuyenBayDTO);
            response.setStatusCode(200);
            return new ResponseEntity<>(response, HttpStatus.OK);
        } else {
            response.setMessage("No routes found for the end airport!!");
            response.setData(null);
            response.setStatusCode(404);
            return new ResponseEntity<>(response, HttpStatus.NOT_FOUND);
        }
    }
}
