package com.project.flightManagement.Controller;

import com.project.flightManagement.DTO.KhachHangDTO.KhachHangBasicDTO;
import com.project.flightManagement.Payload.ResponseData;
import com.project.flightManagement.Service.KhachHangService;
import jakarta.persistence.EntityNotFoundException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;

@Controller
@RequestMapping("/khachhang")
public class KhachHangController {
    @Autowired
    private KhachHangService khachHangService;

    @GetMapping("/{idKhachHang}")
    public ResponseEntity<?> getKhachHangByIdKhachHang(@PathVariable int idKhachHang) {
        ResponseData responseData = new ResponseData();
        try {
            KhachHangBasicDTO khachHang = khachHangService.getKhachHangByIdKhachHang(idKhachHang);
            responseData.setStatusCode(200);
            responseData.setMessage("Thong tin cua khach hang");
            responseData.setData(khachHang);
            return new ResponseEntity<>(responseData, HttpStatus.OK);
        } catch (EntityNotFoundException e) {
            responseData.setStatusCode(404);
            responseData.setData("");
            responseData.setMessage("Khách hàng với ID " + idKhachHang + " không tồn tại.");
            return new ResponseEntity<>(responseData, HttpStatus.NOT_FOUND);
        } catch (Exception e) {
            responseData.setStatusCode(500);
            responseData.setData("");
            responseData.setMessage("Đã xảy ra lỗi khi lấy thông tin khách hàng.");
            return new ResponseEntity<>(responseData, HttpStatus.BAD_REQUEST);
        }
    }

}
