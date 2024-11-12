package com.project.flightManagement.Controller;

import com.project.flightManagement.DTO.DanhGiaDTO.DanhGiaDTO;
import com.project.flightManagement.DTO.KhachHangDTO.KhachHangDTO;
import com.project.flightManagement.Enum.ActiveEnum;
import com.project.flightManagement.Model.KhachHang;
import com.project.flightManagement.Payload.ResponseData;
import com.project.flightManagement.Service.DanhGiaService;
import com.project.flightManagement.Service.KhachHangService;
import jakarta.validation.Valid;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.*;

import java.time.LocalDate;
import java.time.LocalDateTime;
import java.time.LocalTime;
import java.time.format.DateTimeFormatter;
import java.time.format.DateTimeParseException;
import java.util.*;

@RestController
@RequestMapping("/admin/danhgia")
@CrossOrigin(origins = "http://localhost:5173")
public class DanhGiaController {
    @Autowired
    private DanhGiaService danhGiaService;
    @Autowired
    private KhachHangService khachHangService;
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
    @GetMapping("/getReviewByNameOfCustomer")
    public ResponseEntity<ResponseData> getDanhGiaByTenKhachHang(@RequestParam String tenKhachHang) {
        Iterable<DanhGiaDTO> listDG = danhGiaService.getDanhGiaByTenKhachHang(tenKhachHang);
        if(listDG != null && listDG.iterator().hasNext()) {
            response.setData(listDG);
            response.setMessage("Get list reviews by name of customer success!!");
            response.setStatusCode(200);
            return new ResponseEntity<>(response, HttpStatus.OK);
        } else {
            response.setStatusCode(500);
            response.setMessage("Cant found reviews by name of customer!!");
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
    public ResponseEntity<ResponseData> getDanhGiaByStartTimeAndEndTime(@RequestParam(required = false) String startTime, @RequestParam(required = false) String endTime) {
        LocalDateTime start, end;
        DateTimeFormatter formatter = DateTimeFormatter.ofPattern("yyyy-MM-dd HH:mm:ss.SSSSSS");
        DateTimeFormatter dateOnlyFormatter = DateTimeFormatter.ofPattern("yyyy-MM-dd");
        if (startTime != "" && endTime != "") {
            try {
                // Kiểm tra nếu `startTime` chỉ có định dạng ngày (yyyy-MM-dd)
                if (startTime.length() == 10) {
                    LocalDate date = LocalDate.parse(startTime, dateOnlyFormatter);
                    start = date.atStartOfDay(); // yyyy-MM-dd 00:00:00
                } else {
                    start = LocalDateTime.parse(startTime, formatter);
                }

                // Kiểm tra nếu `endTime` chỉ có định dạng ngày (yyyy-MM-dd)
                if (endTime.length() == 10) {
                    LocalDate date = LocalDate.parse(endTime, dateOnlyFormatter);
                    end = date.atTime(LocalTime.MAX); // yyyy-MM-dd 23:59:59.999999
                } else {
                    end = LocalDateTime.parse(endTime, formatter);
                }

            } catch (DateTimeParseException e) {
                response.setStatusCode(501);
                response.setMessage("Invalid date-time format!!");
                response.setData(null);
                return new ResponseEntity<>(response, HttpStatus.BAD_REQUEST);
            }

            Iterable<DanhGiaDTO> listDG = danhGiaService.getDanhGiaByStartTimeAndEndTime(start, end);
            if (listDG != null && listDG.iterator().hasNext()) {
                response.setData(listDG);
                response.setMessage("Get list reviews by starttime and endtime success!!");
                response.setStatusCode(200);
                return new ResponseEntity<>(response, HttpStatus.OK);
            } else {
                response.setStatusCode(500);
                response.setMessage("Cannot find reviews by starttime and endtime");
                response.setData(null);
                return new ResponseEntity<>(response, HttpStatus.BAD_REQUEST);
            }
        } else if (startTime != "" && endTime == "") {
            try {
                // Kiểm tra nếu `startTime` chỉ có định dạng ngày (yyyy-MM-dd)
                if (startTime.length() == 10) {
                    LocalDate date = LocalDate.parse(startTime, dateOnlyFormatter);
                    start = date.atStartOfDay(); // yyyy-MM-dd 00:00:00
                } else {
                    start = LocalDateTime.parse(startTime, formatter);
                }

                // Kiểm tra nếu `endTime` chỉ có định dạng ngày (yyyy-MM-dd)

            } catch (DateTimeParseException e) {
                response.setStatusCode(501);
                response.setMessage("Invalid date-time format!!");
                response.setData(null);
                return new ResponseEntity<>(response, HttpStatus.BAD_REQUEST);
            }

            Iterable<DanhGiaDTO> listDG = danhGiaService.getDanhGiaByStartTime(start);
            if (listDG != null && listDG.iterator().hasNext()) {
                response.setData(listDG);
                response.setMessage("Get list reviews by starttime success!!");
                response.setStatusCode(200);
                return new ResponseEntity<>(response, HttpStatus.OK);
            } else {
                response.setStatusCode(500);
                response.setMessage("Cannot find reviews by starttime");
                response.setData(null);
                return new ResponseEntity<>(response, HttpStatus.BAD_REQUEST);
            }
        } else {
            try {
                // Kiểm tra nếu `startTime` chỉ có định dạng ngày (yyyy-MM-dd)

                // Kiểm tra nếu `endTime` chỉ có định dạng ngày (yyyy-MM-dd)
                if (endTime.length() == 10) {
                    LocalDate date = LocalDate.parse(endTime, dateOnlyFormatter);
                    end = date.atTime(LocalTime.MAX); // yyyy-MM-dd 23:59:59.999999
                } else {
                    end = LocalDateTime.parse(endTime, formatter);
                }

            } catch (DateTimeParseException e) {
                response.setStatusCode(501);
                response.setMessage("Invalid date-time format!!");
                response.setData(null);
                return new ResponseEntity<>(response, HttpStatus.BAD_REQUEST);
            }

            Iterable<DanhGiaDTO> listDG = danhGiaService.getDanhGiaByEndTime(end);
            if (listDG != null && listDG.iterator().hasNext()) {
                response.setData(listDG);
                response.setMessage("Get list reviews by endtime success!!");
                response.setStatusCode(200);
                return new ResponseEntity<>(response, HttpStatus.OK);
            } else {
                response.setStatusCode(500);
                response.setMessage("Cannot find reviews by endtime");
                response.setData(null);
                return new ResponseEntity<>(response, HttpStatus.BAD_REQUEST);
            }
        }

    }

    @PutMapping("/blockReview/{idDanhGia}")
    public ResponseEntity<ResponseData> blockDanhGia(@PathVariable int idDanhGia) {
        Optional<DanhGiaDTO> danhGiaDTO = danhGiaService.getDanhGiaByID(idDanhGia);
        if(danhGiaDTO.isPresent()) {
            if(danhGiaDTO.get().getTrangThaiActive() == ActiveEnum.ACTIVE) {
                if(danhGiaService.blockDanhGia(idDanhGia)) {
                    response.setData(true);
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
                    response.setData(true);
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
    @PostMapping("/addNewReview")
    public ResponseEntity<ResponseData> addNewDanhGia(@Valid @RequestBody DanhGiaDTO danhGiaDTO, BindingResult bindingResult) {
        if(bindingResult.hasErrors()){
            Map<String, String> fieldErrors = new HashMap<>();
            bindingResult.getFieldErrors().forEach(error ->
                    fieldErrors.put(error.getField(), error.getDefaultMessage()));
            response.setStatusCode(400);
            response.setData(fieldErrors);
            response.setMessage("There are some fields invalid");
            return new ResponseEntity<>(response, HttpStatus.BAD_REQUEST);
        }
        if (danhGiaDTO != null) {
            if(danhGiaService.addNewDanhGia(danhGiaDTO)) {
                response.setData(danhGiaDTO);
                response.setMessage("Add new review success!!");
                response.setStatusCode(200);
                return new ResponseEntity<>(response, HttpStatus.OK);
            } else {
                response.setStatusCode(500);
                response.setMessage("Add new review failed!!");
                response.setData(null);
                return new ResponseEntity<>(response, HttpStatus.BAD_REQUEST);
            }
        } else {
            response.setStatusCode(501);
            response.setMessage("Can not add review!!");
            response.setData(null);
            return new ResponseEntity<>(response, HttpStatus.BAD_REQUEST);
        }
    }
    @PostMapping("/addCMT")
    public ResponseEntity<DanhGiaDTO> addDanhGia(@RequestBody DanhGiaDTO danhGiaDTO) {
        DanhGiaDTO result = danhGiaService.addComment(danhGiaDTO);
        return ResponseEntity.ok(result);
    }
    @GetMapping("/replies/{parentId}")
    public ResponseEntity<List<DanhGiaDTO>> getReplies(@PathVariable int parentId) {
        List<DanhGiaDTO> replies = danhGiaService.getDanhGiaByParentComment(parentId);
        return ResponseEntity.ok(replies);
    }
}
