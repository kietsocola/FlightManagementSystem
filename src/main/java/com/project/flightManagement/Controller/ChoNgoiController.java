package com.project.flightManagement.Controller;

import com.project.flightManagement.DTO.ChoNgoiDTO.ChoNgoiDTO;
import com.project.flightManagement.Payload.ResponseData;
import com.project.flightManagement.Service.ChoNgoiService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.GetMapping;

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
}
