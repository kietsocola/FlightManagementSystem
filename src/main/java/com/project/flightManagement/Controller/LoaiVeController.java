package com.project.flightManagement.Controller;

import com.project.flightManagement.DTO.LoaiVeDTO.LoaiVeDTO;
import com.project.flightManagement.DTO.QuyenDTO.QuyenResponseDTO;
import com.project.flightManagement.Payload.ResponseData;
import com.project.flightManagement.Service.LoaiVeService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.rest.webmvc.ResourceNotFoundException;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/loaive")
public class LoaiVeController {
    @Autowired
    private LoaiVeService loaiVeService;

    @GetMapping
    public ResponseEntity<?> getAllLoaiVe(@RequestParam(defaultValue = "0") int page,
                                         @RequestParam(defaultValue = "10") int size) {
        ResponseData responseData = new ResponseData();

        // Lấy danh sách đánh giá
        Page<LoaiVeDTO> loaiVeDTOPage = loaiVeService.getAllLoaiVe(page, size);

        // Kiểm tra nếu danh sách đánh giá trống
        if (loaiVeDTOPage.isEmpty()) {
            responseData.setStatusCode(204);
            responseData.setMessage("No loai ve found.");
            return ResponseEntity.status(HttpStatus.NO_CONTENT).body(responseData);
        }
        responseData.setStatusCode(200);
        responseData.setData(loaiVeDTOPage);
        responseData.setMessage("Successfully retrieved all loai ve.");
        return ResponseEntity.ok(responseData);
    }
    @GetMapping("/{idLoaiVe}")
    public ResponseEntity<ResponseData> getLoaiVeByIdLoaiVe(@PathVariable int idLoaiVe) {
        ResponseData responseData = new ResponseData();
        try {
            LoaiVeDTO loaiVeDTO = loaiVeService.getLoaiVeById(idLoaiVe);
            responseData.setStatusCode(200);
            responseData.setMessage("Lấy thông tin loai ve thành công");
            responseData.setData(loaiVeDTO);
            return new ResponseEntity<>(responseData, HttpStatus.OK);
        } catch (ResourceNotFoundException e) {
            responseData.setStatusCode(404);
            responseData.setMessage(e.getMessage());
            return new ResponseEntity<>(responseData, HttpStatus.NOT_FOUND);
        } catch (Exception e) {
            responseData.setStatusCode(500);
            responseData.setMessage("Có lỗi xảy ra: " + e.getMessage());
            return new ResponseEntity<>(responseData, HttpStatus.INTERNAL_SERVER_ERROR);
        }
    }


}
