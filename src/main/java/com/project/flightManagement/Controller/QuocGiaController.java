package com.project.flightManagement.Controller;

import com.project.flightManagement.DTO.QuocGiaDTO.QuocGiaDTO;
import com.project.flightManagement.Payload.ResponseData;
import com.project.flightManagement.Service.QuocGiaService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RestController;

import java.util.Optional;

@RestController
@CrossOrigin(origins = "http://localhost:5173")
public class QuocGiaController {
    @Autowired
    private QuocGiaService quocGiaService;
    ResponseData responseData = new ResponseData();
    @GetMapping("/getNation/{idQuocGia}")
    public ResponseEntity<ResponseData> getNationById(@PathVariable int idQuocGia) {
        Optional<QuocGiaDTO> quocGiaDTO = quocGiaService.getQuocGiaById(idQuocGia);
        if(quocGiaDTO.isPresent()){
            responseData.setMessage("Get nation by ID success!!");
            responseData.setData(quocGiaDTO);
            responseData.setStatusCode(200);
            return new ResponseEntity<>(responseData, HttpStatus.OK);
        } else {
            responseData.setMessage("Nation not found!!");
            responseData.setStatusCode(404);
            responseData.setData(null);
            return new ResponseEntity<>(responseData, HttpStatus.NOT_FOUND);
        }
    }
    @GetMapping("/getAllNation")
    public ResponseEntity<ResponseData> getAllNation() {
        Iterable<QuocGiaDTO> quocGiaDTOList = quocGiaService.getAllQuocGia();
        if(quocGiaDTOList.iterator().hasNext()){
            responseData.setMessage("Get all nation success!!");
            responseData.setData(quocGiaDTOList);
            responseData.setStatusCode(200);
            return new ResponseEntity<>(responseData, HttpStatus.OK);
        } else {
            responseData.setMessage("Get all nation failed!!");
            responseData.setData(null);
            responseData.setStatusCode(404);
            return new ResponseEntity<>(responseData, HttpStatus.NOT_FOUND);
        }
    }
}