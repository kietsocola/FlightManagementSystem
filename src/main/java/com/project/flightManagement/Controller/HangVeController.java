package com.project.flightManagement.Controller;

import com.project.flightManagement.DTO.HangVeDTO.HangVeDTO;
import com.project.flightManagement.Payload.ResponseData;
import com.project.flightManagement.Service.HangVeService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@CrossOrigin(origins = "http://localhost:5173")
@RequestMapping("/admin/hangve")
public class HangVeController {
    @Autowired
    HangVeService hangVeService;

    private ResponseData responseData = new ResponseData();
    @GetMapping("/getAllHangVe")
    public ResponseEntity<ResponseData> getAllHangVe(){
        try {
            Iterable<HangVeDTO> listHV = hangVeService.getAllHangVe();
            if(listHV.iterator().hasNext()) {
                responseData.setMessage("Get list HangVe success!!");
                responseData.setStatusCode(200);
                responseData.setData(listHV);
                return new ResponseEntity<>(responseData, HttpStatus.OK);
            } else {
                responseData.setMessage("Can not get any HangVe!!");
                responseData.setStatusCode(404);
                responseData.setData(listHV);
                return new ResponseEntity<>(responseData, HttpStatus.NOT_FOUND);
            }
        } catch (Exception e) {
            responseData.setMessage("Get list HangVe fail!!");
            responseData.setStatusCode(500);
            responseData.setMessage(e.getMessage());
            return new ResponseEntity<>(responseData, HttpStatus.INTERNAL_SERVER_ERROR);
        }
    }
}
