package com.project.flightManagement.Controller;


import com.project.flightManagement.DTO.CongDTO.CongDTO;
import com.project.flightManagement.DTO.NhanVienDTO.NhanVienDTO;
import com.project.flightManagement.Model.SanBay;
import com.project.flightManagement.Payload.ResponseData;
import com.project.flightManagement.Service.CongService;
import com.project.flightManagement.Service.NhanVienService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/admin/cong")
@CrossOrigin(origins = "http://localhost:5173")

public class CongController {
    @Autowired
    private CongService congService;
    private ResponseData response =  new ResponseData();

    @GetMapping("/getallcong")
    public ResponseEntity<ResponseData> getallcong(){
        Iterable<CongDTO> dscongDTO = congService.getAllCong();
        if(dscongDTO.iterator().hasNext()){
            response.setMessage("get list Nhan Viens success!!");
            response.setData(dscongDTO);
            response.setStatusCode(200);
            return new ResponseEntity<>(response, HttpStatus.OK);
        } else {
            response.setMessage("get list Nhan Viens unsuccess!!");
            response.setData(null);
            response.setStatusCode(204);
            return new ResponseEntity<>(HttpStatus.NO_CONTENT);
        }
    }

    @GetMapping("/getcongbysanbay/{sanBay}")
    public ResponseEntity<ResponseData> getcongbysanbay(@PathVariable SanBay sanBay){
        System.out.println("dang thuc hien chuc nang get cong by san bay");
        Iterable<CongDTO> dscongDTO = congService.getCongBySanBay(sanBay);
        if(dscongDTO.iterator().hasNext()){
            response.setMessage("get list Nhan Viens success!!");
            response.setData(dscongDTO);
            response.setStatusCode(200);
            return new ResponseEntity<>(response, HttpStatus.OK);
        } else {
            response.setMessage("get list Nhan Viens unsuccess!!");
            response.setData(null);
            response.setStatusCode(204);
            return new ResponseEntity<>(HttpStatus.NO_CONTENT);
        }
    }

}
