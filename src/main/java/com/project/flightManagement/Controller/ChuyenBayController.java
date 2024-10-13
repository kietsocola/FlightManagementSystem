package com.project.flightManagement.Controller;

import com.project.flightManagement.DTO.ChuyenBayDTO.ChuyenBayDTO;
import com.project.flightManagement.Payload.ResponseData;
import com.project.flightManagement.Service.ChuyenBayService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/admin/chuyenbay")
@CrossOrigin(origins = "http://localhost:5173")

public class ChuyenBayController {

    @Autowired
    private ChuyenBayService cbservice;
    private ResponseData response =  new ResponseData();

    @GetMapping("/getallchuyenbay")
    public ResponseEntity<ResponseData> getAllChuyenBay(){
        Iterable<ChuyenBayDTO> listcb = cbservice.getAllChuyenBay();
        if(listcb.iterator().hasNext()){
            response.setMessage("Tim thay chuyen bay");
            response.setData(listcb);
            response.setStatusCode(200);
            return new ResponseEntity<>(response, HttpStatus.OK);
        }else{
            response.setMessage("get list Chuyen Bay unsuccess!!");
            response.setData(null);
            response.setStatusCode(204);
            return new ResponseEntity<>(HttpStatus.NO_CONTENT);
        }
    }
}
