package com.project.flightManagement.Controller;

import com.project.flightManagement.DTO.ThanhPhoDTO.ThanhPhoDTO;
import com.project.flightManagement.Payload.ResponseData;
import com.project.flightManagement.Service.ThanhPhoService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.Optional;

@RestController
@CrossOrigin(origins = "http://localhost:5173")
@RequestMapping("/admin/thanhpho")
public class ThanhPhoController {
    @Autowired
    private ThanhPhoService thanhPhoService;
    ResponseData response = new ResponseData();
    @GetMapping("/getCity/{idThanhPho}")
    public ResponseEntity<ResponseData> getThanhPhoById(@PathVariable int idThanhPho) {
        Optional<ThanhPhoDTO> thanhPhoDTO = thanhPhoService.getThanhPhoById(idThanhPho);
        if (thanhPhoDTO.isPresent()) {
            response.setMessage("Get city by id success!!");
            response.setStatusCode(200);
            response.setData(thanhPhoDTO);
            return new ResponseEntity<>(response, HttpStatus.OK);
        } else {
            response.setMessage("City not found!!");
            response.setStatusCode(404);
            response.setData(null);
            return new ResponseEntity<>(response, HttpStatus.NOT_FOUND);
        }
    }
    @GetMapping("/getAllCity")
    public ResponseEntity<ResponseData> getAllThanhPho() {
        Iterable<ThanhPhoDTO> thanhPhoDTOList = thanhPhoService.getAllThanhPho();
        if(thanhPhoDTOList.iterator().hasNext()) {
            response.setMessage("Get all city success!!");
            response.setStatusCode(202);
            response.setData(thanhPhoDTOList);
            return new ResponseEntity<>(response, HttpStatus.OK);
        } else {
            response.setMessage("All city not found!!");
            response.setStatusCode(404);
            response.setData(null);
            return new ResponseEntity<>(response, HttpStatus.NOT_FOUND);
        }
    }
}
