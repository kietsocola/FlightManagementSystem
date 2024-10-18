package com.project.flightManagement.Controller;

import com.project.flightManagement.DTO.ChoNgoiDTO.ChoNgoiDTO;
import com.project.flightManagement.DTO.NhanVienDTO.NhanVienDTO;
import com.project.flightManagement.Model.ChoNgoi;
import com.project.flightManagement.Model.MayBay;
import com.project.flightManagement.Payload.ResponseData;
import com.project.flightManagement.Service.ChoNgoiService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/admin/chongoi")
@CrossOrigin(origins = "http://localhost:5173")


public class ChoNgoiController {
    @Autowired
    private ChoNgoiService choNgoiService;
    private ResponseData response = new ResponseData();


    @GetMapping("/getallchongoi")
    public ResponseEntity<ResponseData> getallchongoi(){
        Iterable<ChoNgoiDTO> listDTO = choNgoiService.getAllChoNgoi();
        if(listDTO.iterator().hasNext()){
            response.setMessage("get list Cho Ngoi success!!");
            response.setData(listDTO);
            response.setStatusCode(200);
            return new ResponseEntity<>(response, HttpStatus.OK);
        } else {
            response.setMessage("get list Cho Ngoi unsuccess!!");
            response.setData(null);
            response.setStatusCode(204);
            return new ResponseEntity<>(HttpStatus.NO_CONTENT);
        }
    }

    @GetMapping("/getchongoibymaybay/{mayBay}")
    public ResponseEntity<ResponseData> getchongoibymaybay(@PathVariable MayBay mayBay){
        Iterable<ChoNgoiDTO> listDTO = choNgoiService.getChoNgoiByMayBay(mayBay);
        if(listDTO.iterator().hasNext()){
            response.setMessage("get list Cho Ngoi success!!");
            response.setData(listDTO);
            response.setStatusCode(200);
            return new ResponseEntity<>(response, HttpStatus.OK);
        } else {
            response.setMessage("get list Cho Ngoi unsuccess!!");
            response.setData(null);
            response.setStatusCode(204);
            return new ResponseEntity<>(HttpStatus.NO_CONTENT);
        }
    }
}
