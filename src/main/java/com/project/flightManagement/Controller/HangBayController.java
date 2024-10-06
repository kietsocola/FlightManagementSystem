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
import org.springframework.web.bind.annotation.RestController;

@RestController
@CrossOrigin("http://localhost:5175")
public class HangBayController {
    @Autowired
    HangBayService hangBayService;
    ResponseData response = new ResponseData();
    @GetMapping("/getAllAirLine")
    public ResponseEntity<ResponseData> getAllAirLine(){
        Iterable< HangBayDTO> listHBDTO = hangBayService.getAllHangBay();
        if(listHBDTO.iterator().hasNext()) {
            response.setMessage("Get all airline success!!");
            response.setData(listHBDTO);
            response.setStatusCode(200);
            return new ResponseEntity<>(response, HttpStatus.OK);
        }else {
            response.setMessage("Get all airline failed!!");
            response.setData(null);
            response.setStatusCode(404);
            return new ResponseEntity<>(response, HttpStatus.NOT_FOUND);
        }
    }
}
