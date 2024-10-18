package com.project.flightManagement.Controller;

import com.project.flightManagement.DTO.ChiTietHoaDonDTO.ChiTietHoaDonDTO;
import com.project.flightManagement.DTO.HoaDonDTO.HoaDonDTO;

import com.project.flightManagement.Model.ChiTietHoaDon;
import com.project.flightManagement.Payload.ResponseData;
import com.project.flightManagement.Service.HoaDonService;
import jakarta.validation.Valid;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.repository.query.Param;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.*;

import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Optional;

@RestController
@CrossOrigin(origins = "http://localhost:5173")
//@RequestMapping("admin/hoadon")
public class HoaDonController {
    @Autowired
    private HoaDonService hoaDonService;
    private ResponseData response = new ResponseData();

    @GetMapping("/getAllHoaDon")
    public ResponseEntity<ResponseData> getAllHoaDon() {
        Iterable<HoaDonDTO> listHoaDonDTO = hoaDonService.getAllHoaDon();
        if (listHoaDonDTO.iterator().hasNext()) {
            response.setMessage("get list Hoa Don success!");
            response.setData(listHoaDonDTO);
            response.setStatusCode(200);
            return new ResponseEntity<>(response, HttpStatus.OK);
        } else {
            response.setMessage("get list Hoa Don unsuccess!");
            response.setData(null);
            response.setStatusCode(204);
            return new ResponseEntity<>(response, HttpStatus.NO_CONTENT);
        }
    }

    @GetMapping("/getHoaDonById/{idHD}")
    public ResponseEntity<ResponseData> getHoaDonById(@PathVariable("idHD") Integer idHD) {
        Optional<HoaDonDTO> HoaDonDTO = hoaDonService.getHoaDonById(idHD);
        if (HoaDonDTO.isPresent()) {
            response.setMessage("get Hoa Don by ID success!");
            response.setData(HoaDonDTO);
            response.setStatusCode(200);
            return new ResponseEntity<>(response, HttpStatus.OK);
        } else {
            response.setMessage("get Hoa Don by ID unsuccess!");
            response.setData(null);
            response.setStatusCode(200);
            return new ResponseEntity<>(response, HttpStatus.NO_CONTENT);
        }
    }

    @PostMapping("/addHoaDon")
    public ResponseEntity<ResponseData> addHoaDon(@Valid @RequestBody HoaDonDTO hdDTO, BindingResult bindingResult) {
        if (bindingResult.hasErrors()) {
            Map<String, String> fieldErrors =  new HashMap<>();
            bindingResult.getFieldErrors().forEach(error -> fieldErrors.put(error.getField(), error.getDefaultMessage()));
            response.setStatusCode(400);
            response.setData(fieldErrors);
            response.setMessage("There are some fields invalid!");
            return new ResponseEntity<>(response, HttpStatus.BAD_REQUEST);
        }

        if (hdDTO != null && (hdDTO.getKhachHang() != null && hdDTO.getLoaiHoaDon() != null && hdDTO.getNhanVien() != null && hdDTO.getPhuongThucThanhToan() != null)) {
            Optional<HoaDonDTO> savedHoaDon = hoaDonService.addHoaDon(hdDTO);
            if (savedHoaDon.isPresent()) {
                response.setMessage("Save Hoa Don successfully!");
                response.setData(savedHoaDon.get());
                response.setStatusCode(200);
                return new ResponseEntity<>(response, HttpStatus.CREATED);
            } else {
                response.setMessage("Save Hoa Don unsuccessfully!");
                response.setData(null);
                response.setStatusCode(500);
                return new ResponseEntity<>(response, HttpStatus.INTERNAL_SERVER_ERROR);
            }
        } else {
            response.setMessage("Invalid Hoa Don data!");
            response.setData(hdDTO);
            response.setStatusCode(400);
            return new ResponseEntity<>(response, HttpStatus.BAD_REQUEST);
        }
    }

    @PutMapping("/updateHoaDon/{idHD}")
    public ResponseEntity<ResponseData> updateHoaDon(@PathVariable("idHD") Integer idHD, @Valid @RequestBody HoaDonDTO hoaDonDTO, BindingResult bindingResult) {
        ResponseData responseData = new ResponseData();

        if (bindingResult.hasErrors()) {
            Map<String, String> fieldErrors = new HashMap<>();
            bindingResult.getFieldErrors().forEach(error -> fieldErrors.put(error.getField(), error.getDefaultMessage()));
            responseData.setStatusCode(400);
            responseData.setData(fieldErrors);
            responseData.setMessage("There are some fields invalid!");
            return new ResponseEntity<>(responseData, HttpStatus.BAD_REQUEST);
        }

        if (hoaDonDTO != null) {
            Optional<HoaDonDTO> existinghd = hoaDonService.getHoaDonById(idHD);

            if (!existinghd.isPresent()) {
                responseData.setMessage("Hoa Don not found");
                responseData.setData(null);
                responseData.setStatusCode(400);
                return new ResponseEntity<>(responseData, HttpStatus.NOT_FOUND);
            } else {
                hoaDonDTO.setIdHoaDon(idHD);

                Optional<HoaDonDTO> updatedhd = hoaDonService.updateHoaDon(hoaDonDTO);
                if (updatedhd.isPresent()) {
                    responseData.setMessage("Update Hoa Don succesfully!");
                    responseData.setData(updatedhd.get());
                    responseData.setStatusCode(200);
                    return new ResponseEntity<>(responseData, HttpStatus.OK);
                } else {
                    System.out.println(updatedhd);
                    responseData.setMessage("Update Hoa Don Unsuccessfully!");
                    responseData.setData(null);
                    responseData.setStatusCode(500);
                    return new ResponseEntity<>(responseData, HttpStatus.INTERNAL_SERVER_ERROR);
                }
            }
        } else {
            // Dữ liệu không hợp lệ
            response.setMessage("Invalid customer data!!");
            response.setData(null);
            response.setStatusCode(400); // Bad Request
            return new ResponseEntity<>(response, HttpStatus.BAD_REQUEST);
        }
    }

    @GetMapping("/getListChiTietHoaDon/{idHD}")
    public ResponseEntity<ResponseData> getListChiTietHoaDon(@PathVariable("idHD") Integer idHD) {
        List<ChiTietHoaDonDTO> chiTietHoaDonList = hoaDonService.getChiTietHoaDon(idHD);
        if (chiTietHoaDonList.iterator().hasNext()) {
            response.setMessage("Lấy thành công danh sách chi tiết hoóa đơn");
            response.setData(chiTietHoaDonList);
            response.setStatusCode(200);

            return new ResponseEntity<>(response, HttpStatus.OK);
        } else {
            response.setMessage("Lấy không thành công danh sách chi tiết hóa đơn");
            response.setData(null);
            response.setStatusCode(204);

            return new ResponseEntity<>(response, HttpStatus.NO_CONTENT);
        }
    }

    @GetMapping("/getHoaDonByKeyWord")
    public ResponseEntity<ResponseData> getHoaDonByKeyWord(@RequestParam String keyWord) {
        Iterable<HoaDonDTO> listHoaDonDTO = hoaDonService.getHoaDonByKeyWord(keyWord);
        if (listHoaDonDTO.iterator().hasNext()) {
            response.setStatusCode(200);
            response.setMessage("Lấy thành công danh sách hoóa đơn");
            response.setData(listHoaDonDTO);
            return new ResponseEntity<>(response, HttpStatus.OK);
        } else {
            response.setData(null);
            response.setStatusCode(204);
            response.setMessage("Lấy không thành công danh sách hóa đơn");
            return new ResponseEntity<>(response, HttpStatus.NO_CONTENT);
        }
    }

    @GetMapping("/getAllHoaDonSorted")
    public ResponseEntity<ResponseData> getAllHoaDonSorted(@RequestParam(defaultValue = "idHoaDon") String sortBy, @RequestParam(defaultValue = "asc") String order) {
        Iterable<HoaDonDTO> hoaDonDTO = hoaDonService.getAllHoaDonSorted(sortBy, order);
        if (hoaDonDTO.iterator().hasNext()) {
            response.setMessage("get list hoa don success!!");
            response.setData(hoaDonDTO);
            response.setStatusCode(200);
            return new ResponseEntity<>(response, HttpStatus.OK);
        } else {
            response.setMessage("get list hoa don unsuccess!!");
            response.setData(null);
            response.setStatusCode(204);
            return new ResponseEntity<>(HttpStatus.NO_CONTENT);
        }
    }

}
