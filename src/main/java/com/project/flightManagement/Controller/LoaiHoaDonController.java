package com.project.flightManagement.Controller;

import com.project.flightManagement.DTO.HoaDonDTO.HoaDonDTO;
import com.project.flightManagement.DTO.LoaiHoaDonDTO.LoaiHoaDonDTO;
import com.project.flightManagement.Payload.ResponseData;
import com.project.flightManagement.Service.LoaiHoaDonService;

import jakarta.validation.Valid;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.*;

import java.util.HashMap;
import java.util.Map;
import java.util.Optional;

@RestController
@CrossOrigin(origins = "http://localhost:5173")
public class LoaiHoaDonController {
    @Autowired
    private LoaiHoaDonService loaiHoaDonService;
    private ResponseData response = new ResponseData();

    @GetMapping("/getAllLoaiHD")
    public ResponseEntity<ResponseData> getAllLoaiHD() {
        Iterable<LoaiHoaDonDTO> listLoaiHDDTO = loaiHoaDonService.getAllLoaiHoaDon();
        if (listLoaiHDDTO.iterator().hasNext()) {
            System.out.println(listLoaiHDDTO);
            response.setData(listLoaiHDDTO);
            response.setMessage("Lấy danh sách loại hóa đơn thành công!");
            response.setStatusCode(200);

            return new ResponseEntity<>(response, HttpStatus.OK);
        } else {
            response.setMessage("Lấy danh sách hóa đơn không thành công");
            response.setData(null);
            response.setStatusCode(204);

            return new ResponseEntity<>(response, HttpStatus.NO_CONTENT);
        }
    }

    @GetMapping("/getLoaiHDByID/{idLoaiHD}")
    public ResponseEntity<ResponseData> getHoaDonByID(@PathVariable("idLoaiHD") Integer idLoaiHD) {
        Optional<LoaiHoaDonDTO> loaiHoaDonDTO = loaiHoaDonService.getLoaiHoaDonById(idLoaiHD);
        if (loaiHoaDonDTO.isPresent()) {
            response.setMessage("Lấy loại hóa đơn theo ID thành công!");
            response.setData(loaiHoaDonDTO);
            response.setStatusCode(200);

            return new ResponseEntity<>(response, HttpStatus.OK);
        } else {
            response.setMessage("Không tồn tại loại hóa đơn theo ID!");
            response.setData(null);
            response.setStatusCode(204);

            return new ResponseEntity<>(response, HttpStatus.NO_CONTENT);
        }
    }

    @PostMapping("/addLoaiHoaDon")
    public ResponseEntity<ResponseData> addLoaiHoaDon(@Valid @RequestBody LoaiHoaDonDTO loaiHoaDonDTO, BindingResult bindingResult) {
        if (bindingResult.hasErrors()) {
            Map<String, String> fieldErrors =  new HashMap<>();
            bindingResult.getFieldErrors().forEach(error -> fieldErrors.put(error.getField(), error.getDefaultMessage()));
            response.setStatusCode(400);
            response.setData(fieldErrors);
            response.setMessage("Có một số trường thông tin không hợp lệ!");
            return new ResponseEntity<>(response, HttpStatus.BAD_REQUEST);
        }

        if (loaiHoaDonDTO != null && (loaiHoaDonDTO.getTenLoaiHD() != null)) {
            Optional<LoaiHoaDonDTO> existingByTen = loaiHoaDonService.getLoaiHDByTen(loaiHoaDonDTO.getTenLoaiHD());
            if (existingByTen.isPresent()) {
                response.setData(null);
                response.setMessage("Đã tồn tại loại hóa đơn với tên: " + loaiHoaDonDTO.getTenLoaiHD());
                response.setStatusCode(409);

                return new ResponseEntity<>(response, HttpStatus.CONFLICT);
            } else {
                Optional<LoaiHoaDonDTO> savedLoaiHD = loaiHoaDonService.addLoaiHoaDon(loaiHoaDonDTO);
                if (savedLoaiHD.isPresent()) {
                    response.setMessage("Lưu loại hóa đơn mới thành công!");
                    response.setData(savedLoaiHD);
                    response.setStatusCode(201);

                    return new ResponseEntity<>(response, HttpStatus.CREATED);
                } else {
                    response.setMessage("Lưu loại hóa đơn mới không thành công!");
                    response.setData(null);
                    response.setStatusCode(500);

                    return new ResponseEntity<>(response, HttpStatus.INTERNAL_SERVER_ERROR);
                }
            }
        } else {
            response.setMessage("Thông tin của loại hóa đơn không hợp lệ!");
            response.setData(null);
            response.setStatusCode(400);

            return new ResponseEntity<>(response, HttpStatus.BAD_REQUEST);
        }
    }

    @PutMapping("/updateLoaiHoaDon/{idLoaiHD}")
    public ResponseEntity<ResponseData> updateLoaiHD(@PathVariable("idLoaiHD") Integer idLoaiHD, @Valid @RequestBody LoaiHoaDonDTO loaiHoaDonDTO, BindingResult bindingResult) {
        ResponseData responseData = new ResponseData();

        if (bindingResult.hasErrors()) {
            Map<String, String> fieldErrors = new HashMap<>();
            bindingResult.getFieldErrors().forEach(error -> fieldErrors.put(error.getField(), error.getDefaultMessage()));
            responseData.setStatusCode(400);
            responseData.setData(fieldErrors);
            responseData.setMessage("Có một số trường thông tin không hợp lệ!");
            return new ResponseEntity<>(responseData, HttpStatus.BAD_REQUEST);
        }
        if (loaiHoaDonDTO != null && (loaiHoaDonDTO.getTenLoaiHD() != null)) {
            Optional<LoaiHoaDonDTO> existingLoaiHD = loaiHoaDonService.getLoaiHoaDonById(idLoaiHD);
            if (existingLoaiHD.isPresent()) {
                loaiHoaDonDTO.setIdLoaiHD(idLoaiHD);
                Optional<LoaiHoaDonDTO> updatedLoaiHD = loaiHoaDonService.updateLoaiHoaDon(loaiHoaDonDTO);
                if (updatedLoaiHD.isPresent()) {
                    responseData.setMessage("Cập nhật thông tin loại hóa đơn thành công!");
                    responseData.setData(updatedLoaiHD);
                    responseData.setStatusCode(200);

                    return new ResponseEntity<>(responseData, HttpStatus.OK);
                } else {
                    System.out.println(updatedLoaiHD);
                    responseData.setMessage("Cập nhật thông tin loại hóa đơn không thành công!");
                    responseData.setData(null);
                    responseData.setStatusCode(500);
                    return new ResponseEntity<>(responseData, HttpStatus.INTERNAL_SERVER_ERROR);
                }
            } else {
                responseData.setMessage("Loại hóa đơn không tồn tại!");
                responseData.setData(null);
                responseData.setStatusCode(404);

                return new ResponseEntity<>(responseData, HttpStatus.NOT_FOUND);
            }

        } else {
            // Dữ liệu không hợp lệ
            responseData.setMessage("Dữ liệu loại hóa đơn không hợp lệ!");
            responseData.setData(null);
            responseData.setStatusCode(400); // Bad Request
            return new ResponseEntity<>(responseData, HttpStatus.BAD_REQUEST);
        }
    }

    @GetMapping("/getAllLoaiHDSorted")
    public ResponseEntity<ResponseData> getAllLoaiHDSorted(@RequestParam(defaultValue = "idLoaiHoaDon") String sortBy, @RequestParam(defaultValue = "asc") String order) {
        Iterable<LoaiHoaDonDTO> listLoaiHDDTO = loaiHoaDonService.getAllLoaiHDSorted(sortBy, order);
        if (listLoaiHDDTO.iterator().hasNext()) {
            response.setMessage("get list loai hoa don success!!");
            response.setData(listLoaiHDDTO);
            response.setStatusCode(200);
            return new ResponseEntity<>(response, HttpStatus.OK);
        } else {
            response.setMessage("get list loai hoa don unsuccess!!");
            response.setData(null);
            response.setStatusCode(204);
            return new ResponseEntity<>(HttpStatus.NO_CONTENT);
        }
    }

    @GetMapping("/getLoaiHDByKeyWord")
    public ResponseEntity<ResponseData> getLoaiHDByKeyWord(@RequestParam String keyWord) {
        Iterable<LoaiHoaDonDTO> listLoaiHoaDonDTO = loaiHoaDonService.getLoaiHDByKeyWord(keyWord);
        if (listLoaiHoaDonDTO.iterator().hasNext()) {
            System.out.println("Searching for 1: " + keyWord);
            response.setMessage("get list bill type success!!");
            response.setData(listLoaiHoaDonDTO);
            response.setStatusCode(200);
            return new ResponseEntity<>(response, HttpStatus.OK);
        } else {
            System.out.println("Searching for 2: " + keyWord);
            response.setMessage("get list bill type unsuccess!!");
            response.setData(listLoaiHoaDonDTO);
            response.setStatusCode(404);
            return new ResponseEntity<>(response, HttpStatus.NOT_FOUND);
        }
    }
}
