package com.project.flightManagement.Controller;

import com.project.flightManagement.DTO.HanhKhachDTO.HanhKhachCreateDTO;
import com.project.flightManagement.Payload.ResponseData;
import com.project.flightManagement.Service.HanhKhachService;
import com.project.flightManagement.Service.KhachHangService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;

@Controller
@RequestMapping("/hanhkhach")
public class HanhKhachController {
    @Autowired
    private HanhKhachService hanhKhachService;
    @PostMapping
    public ResponseEntity<?> createHanhKhach(@RequestBody HanhKhachCreateDTO hanhKhachCreateDTO) {
        ResponseData responseData = new ResponseData();
        try{
            hanhKhachService.createHanhKhach(hanhKhachCreateDTO);
            responseData.setStatusCode(200);
            responseData.setMessage("Tao moi khach hang thanh cong");
            responseData.setData("");
            return new ResponseEntity<>(responseData, HttpStatus.OK);
        }catch (Exception e) {
            responseData.setStatusCode(500);
            responseData.setMessage("Tao moi khach hang that bai");
            responseData.setData("");
            System.out.println("Tao moi khach hang that bai" + e);
            return new ResponseEntity<>(responseData, HttpStatus.INTERNAL_SERVER_ERROR);
        }
    }
}
