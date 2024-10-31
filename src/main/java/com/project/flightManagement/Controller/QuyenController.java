package com.project.flightManagement.Controller;

import com.project.flightManagement.DTO.AuthDTO.LoginDTO;
import com.project.flightManagement.DTO.QuyenDTO.QuyenCreateDTO;
import com.project.flightManagement.DTO.QuyenDTO.QuyenResponseDTO;
import com.project.flightManagement.DTO.TaiKhoanDTO.TaiKhoanDTO;
import com.project.flightManagement.Payload.ResponseData;
import com.project.flightManagement.Service.QuyenService;
import jakarta.transaction.Transactional;
import jakarta.validation.Valid;
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
    public ResponseEntity<?> getAllQuyen(@RequestParam(defaultValue = "0") int page,
                                            @RequestParam(defaultValue = "10") int size) {
        ResponseData responseData = new ResponseData();

        // Lấy danh sách đánh giá
        Page<QuyenResponseDTO> quyenResponseDTOPage = quyenService.getAllQuyen(page, size);

        // Kiểm tra nếu danh sách đánh giá trống
        if (quyenResponseDTOPage.isEmpty()) {
            responseData.setStatusCode(204);
            responseData.setMessage("No quyen found.");
            responseData.setData("");
            return ResponseEntity.status(HttpStatus.NO_CONTENT).body(responseData);
        }

        // Nếu có dữ liệu
        responseData.setStatusCode(200);
        responseData.setData(quyenResponseDTOPage);
        responseData.setMessage("Successfully retrieved all quyen.");
        return ResponseEntity.ok(responseData);
    }

    @PostMapping("/create")
    public ResponseEntity<ResponseData> createQuyen(@Valid @RequestBody QuyenCreateDTO quyenCreateDTO) {
        ResponseData responseData = new ResponseData();
        try {
            // Kiểm tra xem quyền đã tồn tại theo tên hay chưa
            if (quyenService.existsQuyenByTenQuyen(quyenCreateDTO.getTenQuyen())) {
                responseData.setStatusCode(HttpStatus.BAD_REQUEST.value());
                responseData.setMessage("Tạo quyền thất bại: Tên quyền đã tồn tại");
                responseData.setData("");
                return new ResponseEntity<>(responseData, HttpStatus.BAD_REQUEST);
            }

            // Thử tạo quyền
            boolean isSuccess = quyenService.createQuyen(quyenCreateDTO);
            if (isSuccess) {
                responseData.setStatusCode(HttpStatus.CREATED.value()); // Sử dụng 201 Created cho việc tạo thành công
                responseData.setMessage("Tạo quyền thành công");
                responseData.setData(""); // Tùy chọn: bao gồm chi tiết đối tượng đã tạo nếu cần
                return new ResponseEntity<>(responseData, HttpStatus.CREATED);
            } else {
                responseData.setStatusCode(HttpStatus.BAD_REQUEST.value());
                responseData.setMessage("Tạo quyền thất bại: Không thể tạo quyền");
                responseData.setData("");
                return new ResponseEntity<>(responseData, HttpStatus.BAD_REQUEST);
            }
        } catch (Exception e) {
            responseData.setStatusCode(HttpStatus.INTERNAL_SERVER_ERROR.value());
            responseData.setMessage("Tạo quyền thất bại: " + e.getMessage());
            responseData.setData("");
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
                responseData.setStatusCode(200);
                responseData.setMessage("Lấy thông tin chi tiết quyền thành công");
                responseData.setData(quyenResponseDTO);
                return new ResponseEntity<>(responseData, HttpStatus.OK);
            } else {
                responseData.setStatusCode(204);
                responseData.setMessage("Không tìm thấy quyền với ID: " + idQuyen);
                return new ResponseEntity<>(responseData, HttpStatus.NOT_FOUND);
            }
        } catch (Exception e) {
            responseData.setStatusCode(500);
            responseData.setMessage("Có lỗi xảy ra: " + e.getMessage());
            return new ResponseEntity<>(responseData, HttpStatus.INTERNAL_SERVER_ERROR);
        }
    }
    @PostMapping("/update/{idQuyen}")
    public ResponseEntity<ResponseData> updateQuyen(@PathVariable int idQuyen,@Valid @RequestBody QuyenCreateDTO quyenCreateDTO) {
        ResponseData responseData = new ResponseData();
        try {
            // Kiểm tra xem quyền đã tồn tại theo tên hay chưa
            if (quyenService.existsByTenQuyenAndNotIdQuyenNot(quyenCreateDTO.getTenQuyen(), idQuyen)) {
                responseData.setStatusCode(234);
                responseData.setMessage("Cập nhật quyền thất bại: Tên quyền đã tồn tại");
                responseData.setData("");
                return new ResponseEntity<>(responseData, HttpStatus.BAD_REQUEST);
            }
            quyenService.updateQuyen(idQuyen, quyenCreateDTO);
            responseData.setStatusCode(200);
            responseData.setMessage("Cập nhật quyền thành công");
            responseData.setData("");
            return new ResponseEntity<>(responseData, HttpStatus.OK);
        } catch (IllegalArgumentException e) {
            responseData.setStatusCode(400);
            responseData.setMessage(e.getMessage());
            responseData.setData("");
            return new ResponseEntity<>(responseData, HttpStatus.BAD_REQUEST);
        } catch (ResourceNotFoundException e) {
            responseData.setStatusCode(204);
            responseData.setMessage(e.getMessage());
            responseData.setData("");
            return new ResponseEntity<>(responseData, HttpStatus.NOT_FOUND);
        } catch (Exception e) {
            responseData.setStatusCode(500);
            responseData.setMessage("Có lỗi xảy ra: " + e.getMessage());
            responseData.setData("");
            return new ResponseEntity<>(responseData, HttpStatus.INTERNAL_SERVER_ERROR);
        }
    }

    @GetMapping("/search")
    public ResponseEntity<?> searchQuyenByName(@RequestParam String name,
                                               @RequestParam(defaultValue = "0") int page,
                                               @RequestParam(defaultValue = "10") int size) {
        ResponseData responseData = new ResponseData();

        // Lấy danh sách quyen theo tên
        Page<QuyenResponseDTO> quyenResponseDTOPage = quyenService.searchQuyenByName(name, page, size);

        // Kiểm tra nếu danh sách quyen trống
        if (quyenResponseDTOPage.isEmpty()) {
            responseData.setStatusCode(204);
            responseData.setMessage("No quyen found.");
            responseData.setData("");
            return ResponseEntity.status(HttpStatus.NO_CONTENT).body(responseData);
        }

        // Nếu có dữ liệu
        responseData.setStatusCode(200);
        responseData.setData(quyenResponseDTOPage);
        responseData.setMessage("Successfully retrieved quyen by name.");
        return ResponseEntity.ok(responseData);
    }

}
