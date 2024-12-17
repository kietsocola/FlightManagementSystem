package com.project.flightManagement.Controller;

import com.project.flightManagement.DTO.ChucNangDTO.ChucNangDTO;
import com.project.flightManagement.DTO.ChucVuDTO.ChucVuDTO;
import com.project.flightManagement.DTO.LoaiVeDTO.LoaiVeDTO;
import com.project.flightManagement.Payload.ResponseData;
import com.project.flightManagement.Service.ChucNangService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

@RestController
@RequestMapping("/chucnang")
public class ChucNangController {
    @Autowired
    private ChucNangService chucNangService;
    @GetMapping
    public ResponseEntity<ResponseData> getAllChucNang(){
        ResponseData responseData = new ResponseData();

        // Lấy danh sách đánh giá
        List<ChucNangDTO> chucNangDTOList = chucNangService.getAllChucNang();

        // Kiểm tra nếu danh sách đánh giá trống
        if (chucNangDTOList == null) {
            responseData.setStatusCode(204);
            responseData.setMessage("chuc nang not found.");
            return ResponseEntity.status(HttpStatus.NO_CONTENT).body(responseData);
        }
        responseData.setStatusCode(200);
        responseData.setData(chucNangDTOList);
        responseData.setMessage("Successfully retrieved all chucNang.");
        return ResponseEntity.ok(responseData);
    }
}
