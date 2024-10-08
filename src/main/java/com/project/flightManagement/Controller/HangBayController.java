package com.project.flightManagement.Controller;

import com.project.flightManagement.DTO.HangBayDTO.HangBayDTO;
import com.project.flightManagement.DTO.MayBayDTO.MayBayDTO;
import com.project.flightManagement.Payload.ResponseData;
import com.project.flightManagement.Service.HangBayService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;
import java.util.Optional;

@RestController
@CrossOrigin(origins = "http://localhost:5173")
public class HangBayController {
    @Autowired
    private HangBayService hangBayService;
    private ResponseData responseData = new ResponseData();

    @GetMapping("/getAllAirline")
    public ResponseEntity<ResponseData> getAllAirline() {
        Iterable<HangBayDTO> listHB = hangBayService.getAllHangBay();
        if(listHB.iterator().hasNext()) {
            responseData.setMessage("Get all airline success!!");
            responseData.setData(listHB);
            responseData.setStatusCode(200);
            return new ResponseEntity<>(responseData, HttpStatus.OK);
        } else {
            responseData.setMessage("Get all airline failed!!");
            responseData.setData(null);
            responseData.setStatusCode(404);
            return new ResponseEntity<>(responseData, HttpStatus.NOT_FOUND);
        }
    }
    @GetMapping("/getAirline/{idHangBay}")
    public ResponseEntity<ResponseData> getAirline(@PathVariable int idHangBay) {
        Optional<HangBayDTO> hangBayDTO = hangBayService.getHangBayById(idHangBay);
        if(hangBayDTO.isPresent()) {
            responseData.setMessage("Get airline success!!");
            responseData.setData(hangBayDTO.get());
            responseData.setStatusCode(200);
            return new ResponseEntity<>(responseData, HttpStatus.OK);
        } else {
            responseData.setMessage("Get airline failed!!");
            responseData.setData(null);
            responseData.setStatusCode(404);
            return new ResponseEntity<>(responseData, HttpStatus.NOT_FOUND);
        }
    }
}
