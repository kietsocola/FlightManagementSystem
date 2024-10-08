package com.project.flightManagement.Controller;

import com.project.flightManagement.DTO.AuthDTO.LoginDTO;
import com.project.flightManagement.DTO.QuyenDTO.QuyenCreateDTO;
import com.project.flightManagement.Payload.ResponseData;
import com.project.flightManagement.Service.QuyenService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;

@Controller
@RequestMapping("/quyen")
public class QuyenController {
    @Autowired
    private QuyenService quyenService;
    @PostMapping("/create")
    public ResponseEntity<ResponseData> createQuyen(@RequestBody QuyenCreateDTO quyenCreateDTO) {
        ResponseData responseData = new ResponseData();
        try {
            boolean isSusscess = quyenService.createQuyen(quyenCreateDTO);
            // Kiểm tra đăng nhập
            if (isSusscess) {
                responseData.setMessage("created role success");
                responseData.setData("");
                return new ResponseEntity<>(responseData, HttpStatus.OK);
            } else {
                responseData.setMessage("create role failed");
                return new ResponseEntity<>(responseData, HttpStatus.BAD_REQUEST);
            }
        } catch (Exception e) {
            responseData.setMessage("create role failed: " + e.getMessage());
            return new ResponseEntity<>(responseData, HttpStatus.INTERNAL_SERVER_ERROR);
        }
    }

}
