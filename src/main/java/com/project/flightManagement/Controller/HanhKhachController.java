package com.project.flightManagement.Controller;

import com.project.flightManagement.DTO.HanhKhachDTO.HanhKhachCreateDTO;
import com.project.flightManagement.DTO.HanhKhachDTO.HanhKhachDTO;
import com.project.flightManagement.Mapper.HanhKhachMapper;
import com.project.flightManagement.Model.HanhKhach;
import com.project.flightManagement.Payload.ResponseData;
import com.project.flightManagement.Service.HanhKhachService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.*;

import java.time.LocalDate;
import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import java.time.format.DateTimeParseException;

@Controller
@RequestMapping("/hanhkhach")
public class HanhKhachController {
    @Autowired
    private HanhKhachService hanhKhachService;

    @PostMapping
    public ResponseEntity<?> createHanhKhach(@RequestBody HanhKhachCreateDTO hanhKhachCreateDTO) {
        ResponseData responseData = new ResponseData();
        try {
            HanhKhach hanhKhach = hanhKhachService.createHanhKhach(hanhKhachCreateDTO);
            HanhKhachDTO hanhKhachDTO = HanhKhachMapper.toDTO(hanhKhach);
            responseData.setStatusCode(200);
            responseData.setMessage("Tạo mới khách hàng thành công");
            responseData.setData(hanhKhachDTO);
            return new ResponseEntity<>(responseData, HttpStatus.OK);
        } catch (Exception e) {
            responseData.setStatusCode(500);
            responseData.setMessage("Tạo mới khách hàng thất bại");
            responseData.setData("");
            System.out.println("Tạo mới khách hàng thất bại: " + e);
            return new ResponseEntity<>(responseData, HttpStatus.INTERNAL_SERVER_ERROR);
        }
    }

   

}
