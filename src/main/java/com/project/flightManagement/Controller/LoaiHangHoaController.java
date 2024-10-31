package com.project.flightManagement.Controller;

import com.project.flightManagement.DTO.LoaiHangHoaDTO.LoaiHangHoaDTO;
import com.project.flightManagement.Payload.ResponseData;
import com.project.flightManagement.Service.LoaiHangHoaService;
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
@RequestMapping("/api/loaiHangHoa")
public class LoaiHangHoaController {

    @Autowired
    private LoaiHangHoaService loaiHangHoaService;

    @GetMapping("/all")
    public ResponseEntity<ResponseData> getAllLoaiHangHoa() {
        ResponseData response = new ResponseData();
        Iterable<LoaiHangHoaDTO> list = loaiHangHoaService.getAllLoaiHangHoa();
        response.setMessage("Fetched list successfully!");
        response.setData(list);
        response.setStatusCode(200);
        return new ResponseEntity<>(response, HttpStatus.OK);
    }

    @GetMapping("/{id}")
    public ResponseEntity<ResponseData> getLoaiHangHoaById(@PathVariable int id) {
        Optional<LoaiHangHoaDTO> loaiHangHoa = loaiHangHoaService.getLoaiHangHoaById(id);
        ResponseData response = new ResponseData();
        if (loaiHangHoa.isPresent()) {
            response.setMessage("Fetched item successfully!");
            response.setData(loaiHangHoa.get());
            response.setStatusCode(200);
            return new ResponseEntity<>(response, HttpStatus.OK);
        } else {
            response.setMessage("Item not found!");
            response.setData(null);
            response.setStatusCode(404);
            return new ResponseEntity<>(response, HttpStatus.NOT_FOUND);
        }
    }

    @PostMapping("/add")
    public ResponseEntity<ResponseData> addNewLoaiHangHoa(@Valid @RequestBody LoaiHangHoaDTO loaiHangHoaDTO, BindingResult bindingResult) {
        ResponseData response = new ResponseData();
        if (bindingResult.hasErrors()) {
            Map<String, String> errors = new HashMap<>();
            bindingResult.getFieldErrors().forEach(error ->
                    errors.put(error.getField(), error.getDefaultMessage())
            );
            response.setData(errors);
            response.setStatusCode(400);
            return new ResponseEntity<>(response, HttpStatus.BAD_REQUEST);
        }

        Optional<LoaiHangHoaDTO> savedItem = loaiHangHoaService.addNewLoaiHangHoa(loaiHangHoaDTO);
        if (savedItem.isPresent()) {
            response.setMessage("Added item successfully!");
            response.setData(savedItem.get());
            response.setStatusCode(201);
            return new ResponseEntity<>(response, HttpStatus.CREATED);
        } else {
            response.setMessage("Error adding item!");
            response.setData(null);
            response.setStatusCode(500);
            return new ResponseEntity<>(response, HttpStatus.INTERNAL_SERVER_ERROR);
        }
    }

    @PutMapping("/update/{id}")
    public ResponseEntity<ResponseData> updateLoaiHangHoa(@PathVariable int id, @Valid @RequestBody LoaiHangHoaDTO loaiHangHoaDTO, BindingResult bindingResult) {
        ResponseData response = new ResponseData();
        if (bindingResult.hasErrors()) {
            Map<String, String> errors = new HashMap<>();
            bindingResult.getFieldErrors().forEach(error ->
                    errors.put(error.getField(), error.getDefaultMessage())
            );
            response.setData(errors);
            response.setStatusCode(400);
            return new ResponseEntity<>(response, HttpStatus.BAD_REQUEST);
        }

        Optional<LoaiHangHoaDTO> updatedItem = loaiHangHoaService.updateLoaiHangHoa(id, loaiHangHoaDTO);
        if (updatedItem.isPresent()) {
            response.setMessage("Updated item successfully!");
            response.setData(updatedItem.get());
            response.setStatusCode(200);
            return new ResponseEntity<>(response, HttpStatus.OK);
        } else {
            response.setMessage("Item not found!");
            response.setData(null);
            response.setStatusCode(404);
            return new ResponseEntity<>(response, HttpStatus.NOT_FOUND);
        }
    }

    @DeleteMapping("/delete/{id}")
    public ResponseEntity<ResponseData> deleteLoaiHangHoa(@PathVariable int id) {
        ResponseData response = new ResponseData();
        try {
            loaiHangHoaService.deleteLoaiHangHoa(id);
            response.setMessage("Deleted item successfully!");
            response.setData(null);
            response.setStatusCode(200);
            return new ResponseEntity<>(response, HttpStatus.OK);
        } catch (Exception e) {
            response.setMessage("Error deleting item!");
            response.setData(null);
            response.setStatusCode(500);
            return new ResponseEntity<>(response, HttpStatus.INTERNAL_SERVER_ERROR);
        }
    }
}
