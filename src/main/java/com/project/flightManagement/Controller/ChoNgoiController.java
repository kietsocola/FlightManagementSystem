package com.project.flightManagement.Controller;

import com.project.flightManagement.DTO.ChoNgoiDTO.ChoNgoiDTO;
import com.project.flightManagement.DTO.MayBayDTO.MayBayDTO;
import com.project.flightManagement.Payload.ResponseData;
import com.project.flightManagement.Service.ChoNgoiService;
import jakarta.validation.Valid;
import org.aspectj.apache.bcel.classfile.Module;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;

import java.util.HashMap;
import java.util.Map;
import java.util.Optional;

@Controller
@CrossOrigin(origins = "http://localhost:5173")
public class ChoNgoiController {
    @Autowired
    private ChoNgoiService choNgoiService;
    private ResponseData responseData = new ResponseData();
    @GetMapping("/getAllSeat")
    public ResponseEntity<ResponseData> getAllChoNgoi() {
        Iterable<ChoNgoiDTO> choNgoiDTOs = choNgoiService.getAllChoNgoi();
        if(choNgoiDTOs.iterator().hasNext()) {
            responseData.setStatusCode(200);
            responseData.setMessage("Get all seat success!!");
            responseData.setData(choNgoiDTOs);
            return new ResponseEntity<>(responseData, HttpStatus.OK);
        } else {
            responseData.setStatusCode(404);
            responseData.setMessage("Get all seat failed!!");
            responseData.setData(null);
            return new ResponseEntity<>(responseData, HttpStatus.NOT_FOUND);
        }
    }
    @PostMapping("/addSeat")
    public ResponseEntity<ResponseData> addNewChoNgoi(@Valid @RequestBody ChoNgoiDTO choNgoiDTO, BindingResult bindingResult) {
//        try {
//            choNgoiService.addNewChoNgoi(choNgoiDTO);
//            responseData.setData(choNgoiDTO);
//            responseData.setStatusCode(200);
//            responseData.setMessage("Add seat success!!");
//            return new ResponseEntity<>(responseData, HttpStatus.OK);
//        } catch (Exception e) {
//            responseData.setStatusCode(404);
//            responseData.setMessage("Add seat failed!!");
//            responseData.setData(null);
//            return new ResponseEntity<>(responseData, HttpStatus.NOT_FOUND);
//        }
        if(bindingResult.hasErrors()){
            Map<String, String> fieldErrors = new HashMap<>();
            bindingResult.getFieldErrors().forEach(error ->
                    fieldErrors.put(error.getField(), error.getDefaultMessage()));
            responseData.setStatusCode(400);
            responseData.setData(fieldErrors);
            responseData.setMessage("There are some fields invalid");
            return new ResponseEntity<>(responseData, HttpStatus.BAD_REQUEST);
        }
        if(choNgoiDTO != null) {
            Optional<ChoNgoiDTO> savedCN = choNgoiService.addNewChoNgoi(choNgoiDTO);
            if(savedCN.isPresent()) {
                responseData.setStatusCode(200);
                responseData.setMessage("Add ChoNgoi success!");
                responseData.setData(savedCN.get());
                return new ResponseEntity<>(responseData, HttpStatus.OK);
            } else {
                responseData.setStatusCode(404);
                responseData.setMessage("Add ChoNgoi failed!!");
                responseData.setData(null);
                return new ResponseEntity<>(responseData, HttpStatus.NOT_FOUND);
            }
        } else {
            responseData.setStatusCode(400);
            responseData.setMessage("Add ChoNgoi failed!!");
            responseData.setData(null);
            return new ResponseEntity<>(responseData, HttpStatus.BAD_REQUEST);
        }
    }
}
