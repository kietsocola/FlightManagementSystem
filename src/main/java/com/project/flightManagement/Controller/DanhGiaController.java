package com.project.flightManagement.Controller;

import com.project.flightManagement.DTO.DanhGiaDTO.DanhGiaDTO;
import com.project.flightManagement.Enum.ActiveEnum;
import com.project.flightManagement.Payload.ResponseData;
import com.project.flightManagement.Service.DanhGiaService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.time.format.DateTimeParseException;
import java.util.Optional;

@RestController
@RequestMapping("/admin/danhgia")
@CrossOrigin(origins = "http://localhost:5173")
public class DanhGiaController {
    @Autowired
    private DanhGiaService danhGiaService;
    private ResponseData response = new ResponseData();

    @GetMapping("/getReview/{idDanhGia}")
    public ResponseEntity<ResponseData> getDanhGiaByID(@PathVariable int idDanhGia) {
        Optional<DanhGiaDTO> danhGiaDTO = danhGiaService.getDanhGiaByID(idDanhGia);
        if(danhGiaDTO.isPresent()) {
            response.setData(danhGiaDTO);
            response.setMessage("Get review by ID success!!");
            response.setStatusCode(200);
            return new ResponseEntity<>(response, HttpStatus.OK);
        } else {
            response.setStatusCode(404);
            response.setMessage("Not found review");
            response.setData(null);
            return new ResponseEntity<>(response, HttpStatus.NOT_FOUND);
        }
    }

    @GetMapping("/getAllReview")
    public ResponseEntity<ResponseData> getAllDanhGia() {
        Iterable<DanhGiaDTO> listDG = danhGiaService.getAllDanhGia();
        if(listDG != null && listDG.iterator().hasNext()) {
            response.setData(listDG);
            response.setMessage("Get list reviews success!!");
            response.setStatusCode(200);
            return new ResponseEntity<>(response, HttpStatus.OK);
        } else {
            response.setStatusCode(500);
            response.setMessage("Cant found list reviews");
            response.setData(null);
            return new ResponseEntity<>(response, HttpStatus.BAD_REQUEST);
        }
    }
    @GetMapping("/getReviewByCustomer/{idKhachHang}")
    public ResponseEntity<ResponseData> getDanhGiaByKhachHang(@PathVariable int idKhachHang) {
        Iterable<DanhGiaDTO> listDG = danhGiaService.getDanhGiaByKhachHang(idKhachHang);
        if(listDG != null && listDG.iterator().hasNext()) {
            response.setData(listDG);
            response.setMessage("Get list reviews by customer success!!");
            response.setStatusCode(200);
            return new ResponseEntity<>(response, HttpStatus.OK);
        } else {
            response.setStatusCode(500);
            response.setMessage("Cant found reviews by customer");
            response.setData(null);
            return new ResponseEntity<>(response, HttpStatus.BAD_REQUEST);
        }
    }
    @GetMapping("/getReviewByHangBay/{idHangBay}")
    public ResponseEntity<ResponseData> getDanhGiaByHangBay(@PathVariable int idHangBay) {
        Iterable<DanhGiaDTO> listDG = danhGiaService.getDanhGiaByHangBay(idHangBay);
        if(listDG != null && listDG.iterator().hasNext()) {
            response.setData(listDG);
            response.setMessage("Get list reviews by airline success!!");
            response.setStatusCode(200);
            return new ResponseEntity<>(response, HttpStatus.OK);
        } else {
            response.setStatusCode(500);
            response.setMessage("Cant found reviews by airline");
            response.setData(null);
            return new ResponseEntity<>(response, HttpStatus.BAD_REQUEST);
        }
    }
    @GetMapping("/getReviewByStartTimeAndEndTime")
    public ResponseEntity<ResponseData> getDanhGiaByStartTimeAndEndTime(@RequestParam String startTime,@RequestParam String endTime) {
        LocalDateTime start, end;
        DateTimeFormatter formatter = DateTimeFormatter.ofPattern("yyyy-MM-dd HH:mm:ss.SSSSSS");
        try {
            start = LocalDateTime.parse(startTime, formatter);
            end = LocalDateTime.parse(endTime, formatter);
        } catch (DateTimeParseException e) {
            response.setStatusCode(501);
            response.setMessage("Invalid date-time format!!");
            response.setData(null);
            return new ResponseEntity<>(response, HttpStatus.BAD_REQUEST);
        }
        Iterable<DanhGiaDTO> listDG = danhGiaService.getDanhGiaByStartTimeAndEndTime(start, end);
        if(listDG != null && listDG.iterator().hasNext()) {
            response.setData(listDG);
            response.setMessage("Get list reviews by starttime and endtime success!!");
            response.setStatusCode(200);
            return new ResponseEntity<>(response, HttpStatus.OK);
        } else {
            response.setStatusCode(500);
            response.setMessage("Cant found reviews by starttime and endtime");
            response.setData(null);
            return new ResponseEntity<>(response, HttpStatus.BAD_REQUEST);
        }
    }
    @PutMapping("/blockReview/{idDanhGia}")
    public ResponseEntity<ResponseData> blockDanhGia(@PathVariable int idDanhGia) {
        Optional<DanhGiaDTO> danhGiaDTO = danhGiaService.getDanhGiaByID(idDanhGia);
        if(danhGiaDTO.isPresent()) {
            if(danhGiaDTO.get().getTrangThaiActive() == ActiveEnum.ACTIVE) {
                if(danhGiaService.blockDanhGia(idDanhGia)) {
                    response.setData(danhGiaDTO);
                    response.setMessage("Block review success!!");
                    response.setStatusCode(200);
                    return new ResponseEntity<>(response, HttpStatus.OK);
                } else {
                    response.setStatusCode(500);
                    response.setMessage("Block review failed!!");
                    response.setData(null);
                    return new ResponseEntity<>(response, HttpStatus.BAD_REQUEST);
                }
            } else {
                if(danhGiaService.unblockDanhGia(idDanhGia)) {
                    response.setData(danhGiaDTO);
                    response.setMessage("Unblock review success!!");
                    response.setStatusCode(200);
                    return new ResponseEntity<>(response, HttpStatus.OK);
                } else {
                    response.setStatusCode(500);
                    response.setMessage("Unblock review failed!!");
                    response.setData(null);
                    return new ResponseEntity<>(response, HttpStatus.BAD_REQUEST);
                }
            }
        } else {
            response.setStatusCode(404);
            response.setMessage("Cant found reviews");
            response.setData(null);
            return new ResponseEntity<>(response, HttpStatus.NOT_FOUND);
        }
    }
}
