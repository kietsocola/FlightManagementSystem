package com.project.flightManagement.Controller;

import com.project.flightManagement.DTO.AuthDTO.LoginDTO;
import com.project.flightManagement.DTO.QuyenDTO.QuyenCreateDTO;
import com.project.flightManagement.DTO.QuyenDTO.QuyenResponseDTO;
import com.project.flightManagement.DTO.TaiKhoanDTO.TaiKhoanDTO;
import com.project.flightManagement.Payload.ResponseData;
import com.project.flightManagement.Service.QuyenService;
import jakarta.transaction.Transactional;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.rest.webmvc.ResourceNotFoundException;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.*;

@Controller
@RequestMapping("/quyen")
public class QuyenController {
    @Autowired
    private QuyenService quyenService;

    @GetMapping
    public ResponseEntity<?> getAllTaiKhoan(@RequestParam(defaultValue = "0") int page,
                                            @RequestParam(defaultValue = "10") int size) {
        ResponseData responseData = new ResponseData();

        // Lấy danh sách đánh giá
        Page<QuyenResponseDTO> quyenResponseDTOPage = quyenService.getAllQuyen(page, size);

        // Kiểm tra nếu danh sách đánh giá trống
        if (quyenResponseDTOPage.isEmpty()) {
            responseData.setMessage("No quyen found.");
            return ResponseEntity.status(HttpStatus.NO_CONTENT).body(responseData);
        }

        // Nếu có dữ liệu
        responseData.setData(quyenResponseDTOPage);
        responseData.setMessage("Successfully retrieved all quyen.");
        return ResponseEntity.ok(responseData);
    }

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

    @GetMapping("/{idQuyen}")
    public ResponseEntity<ResponseData> getQuyenDetail(@PathVariable int idQuyen) {
        ResponseData responseData = new ResponseData();
        try {
            QuyenResponseDTO quyenResponseDTO = quyenService.getQuyenDetailByIdQuyen(idQuyen);

            // Kiểm tra đăng nhập
            if (quyenResponseDTO != null) {
                responseData.setMessage("Lấy thông tin chi tiết quyền thành công");
                responseData.setData(quyenResponseDTO);
                return new ResponseEntity<>(responseData, HttpStatus.OK);
            } else {
                responseData.setMessage("Không tìm thấy quyền với ID: " + idQuyen);
                return new ResponseEntity<>(responseData, HttpStatus.NOT_FOUND);
            }
        } catch (Exception e) {
            responseData.setMessage("Có lỗi xảy ra: " + e.getMessage());
            return new ResponseEntity<>(responseData, HttpStatus.INTERNAL_SERVER_ERROR);
        }
    }
    @PostMapping("/update/{idQuyen}")
    public ResponseEntity<ResponseData> updateQuyen(@PathVariable int idQuyen, @RequestBody QuyenCreateDTO quyenCreateDTO) {
        ResponseData responseData = new ResponseData();
        try {
            quyenService.updateQuyen(idQuyen, quyenCreateDTO);
            responseData.setMessage("Cập nhật quyền thành công");
            responseData.setData("");
            return new ResponseEntity<>(responseData, HttpStatus.OK);
        } catch (IllegalArgumentException e) {
            responseData.setMessage(e.getMessage());
            return new ResponseEntity<>(responseData, HttpStatus.BAD_REQUEST);
        } catch (ResourceNotFoundException e) {
            responseData.setMessage(e.getMessage());
            return new ResponseEntity<>(responseData, HttpStatus.NOT_FOUND);
        } catch (Exception e) {
            responseData.setMessage("Có lỗi xảy ra: " + e.getMessage());
            return new ResponseEntity<>(responseData, HttpStatus.INTERNAL_SERVER_ERROR);
        }
    }

}
