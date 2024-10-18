package com.project.flightManagement.Controller;

import com.project.flightManagement.DTO.ChiTietHoaDonDTO.ChiTietHoaDonDTO;
import com.project.flightManagement.Payload.ResponseData;
import com.project.flightManagement.Service.ChiTietHoaDonService;
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
@RequestMapping("admin/chitiethoadon")
public class ChiTietHoaDonController {
    @Autowired
    ChiTietHoaDonService chiTietHoaDonService;

    ResponseData response = new ResponseData();

    @GetMapping("/getAllChiTietHoaDon")
    public ResponseEntity<ResponseData> getAllChiTietHoaDon() {
        Iterable<ChiTietHoaDonDTO> listCTHDDTO = chiTietHoaDonService.getAllChiTietHoaDon();
        if (listCTHDDTO.iterator().hasNext()) {
            response.setMessage("Lấy thành công danh sách chi tiết hóa đơn!");
            response.setData(listCTHDDTO);
            response.setStatusCode(200);

            return new ResponseEntity<>(response, HttpStatus.OK);
        } else {
            response.setMessage("Lấy danh sách chi tiết hóa đơn không thành công!");
            response.setData(null);
            response.setStatusCode(200);

            return new ResponseEntity<>(response, HttpStatus.NO_CONTENT);
        }
    }

    @GetMapping("/getChiTietHoaDonByID/{idChiTietHoaDon}")
    public ResponseEntity<ResponseData> getChiTietHoaDonByID(@PathVariable("idChiTietHoaDon") Integer idCTHD) {
        Optional<ChiTietHoaDonDTO> chiTietHoaDonDTO = chiTietHoaDonService.getChiTietHoaDonByID(idCTHD);
        if (chiTietHoaDonDTO.isPresent()) {
            response.setMessage("Lấy thành công chi tiết hóa đơn!");
            response.setData(chiTietHoaDonDTO);
            response.setStatusCode(200);

            return new ResponseEntity<>(response, HttpStatus.OK);
        } else {
            response.setMessage("Lấy chi tiết hóa đơn không thành công!");
            response.setData(null);
            response.setStatusCode(200);

            return new ResponseEntity<>(response, HttpStatus.NO_CONTENT);
        }
    }

    @PostMapping("/addChiTietHoaDon")
    public ResponseEntity<ResponseData> addChiTietHoaDon(@Valid @RequestBody ChiTietHoaDonDTO chiTietHoaDonDTO, BindingResult bindingResult) {
        if (bindingResult.hasErrors()) {
            Map<String, String> fieldErrors =  new HashMap<>();
            bindingResult.getFieldErrors().forEach(error -> fieldErrors.put(error.getField(), error.getDefaultMessage()));
            response.setStatusCode(400);
            response.setData(fieldErrors);
            response.setMessage("There are some fields invalid!");
            return new ResponseEntity<>(response, HttpStatus.BAD_REQUEST);
        }

        if (chiTietHoaDonDTO != null && (chiTietHoaDonDTO.getHoaDon() != null && chiTietHoaDonDTO.getVe() != null && chiTietHoaDonDTO.getHangHoa() != null)) {
            Optional<ChiTietHoaDonDTO> savedCTHD = chiTietHoaDonService.addChiTietHoaDon(chiTietHoaDonDTO);
            if (savedCTHD.isPresent()) {
                response.setMessage("Save Hoa Don successfully!");
                response.setData(savedCTHD.get());
                response.setStatusCode(200);
                return new ResponseEntity<>(response, HttpStatus.CREATED);
            } else {
                response.setMessage("Save Hoa Don unsuccessfully!");
                response.setData(null);
                response.setStatusCode(500);
                return new ResponseEntity<>(response, HttpStatus.INTERNAL_SERVER_ERROR);
            }
        } else {
            response.setMessage("Invalid Chi Tiet Hoa Don data!");
            response.setData(chiTietHoaDonDTO);
            response.setStatusCode(400);
            return new ResponseEntity<>(response, HttpStatus.BAD_REQUEST);
        }
    }

    @PutMapping("/updateChiTietHoaDon/{idChiTietHoaDon}")
    public ResponseEntity<ResponseData> updateChiTietHoaDon(@PathVariable("idChiTietHoaDon") Integer idCTHD, @Valid @RequestBody ChiTietHoaDonDTO chiTietHoaDonDTO, BindingResult bindingResult) {
        if (bindingResult.hasErrors()) {
            Map<String, String> fieldErrors = new HashMap<>();
            bindingResult.getFieldErrors().forEach(error -> fieldErrors.put(error.getField(), error.getDefaultMessage()));
            response.setStatusCode(400);
            response.setData(fieldErrors);
            response.setMessage("There are some fields invalid!");
            return new ResponseEntity<>(response, HttpStatus.BAD_REQUEST);
        }

        if (chiTietHoaDonDTO != null && (chiTietHoaDonDTO.getHangHoa() != null && chiTietHoaDonDTO.getHoaDon() != null && chiTietHoaDonDTO.getVe() != null)) {
            chiTietHoaDonDTO.setIdChiTietHoaDon(idCTHD);
            Optional<ChiTietHoaDonDTO> existingCTHD = chiTietHoaDonService.getChiTietHoaDonByID(chiTietHoaDonDTO.getIdChiTietHoaDon());
            if (existingCTHD.isPresent()) {
                Optional<ChiTietHoaDonDTO> updatedCTHD = chiTietHoaDonService.updateChiTietHoaDon(chiTietHoaDonDTO);
                if (updatedCTHD.isPresent()) {
                    response.setMessage("Cập nhật chi tiết hóa đơn thành công!");
                    response.setData(chiTietHoaDonDTO);
                    response.setStatusCode(200);

                    return new ResponseEntity<>(response, HttpStatus.OK);
                } else {
                    response.setMessage("Cập nhật chi tiết hóa đơn thất bại!");
                    response.setData(null);
                    response.setStatusCode(500);

                    return new ResponseEntity<>(response, HttpStatus.INTERNAL_SERVER_ERROR);
                }
            } else {
                response.setMessage("Không tìm thấy Chi tiết hóa đơn!");
                response.setData(null);
                response.setStatusCode(404);

                return new ResponseEntity<>(response, HttpStatus.NOT_FOUND);
            }

        } else {
            // Dữ liệu không hợp lệ
            response.setMessage("Dữ liệu chi tiết hóa đơn không hợp lệ!");
            response.setData(null);
            response.setStatusCode(400); // Bad Request
            return new ResponseEntity<>(response, HttpStatus.BAD_REQUEST);
        }

    }
}
