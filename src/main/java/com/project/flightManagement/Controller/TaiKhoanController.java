package com.project.flightManagement.Controller;

import com.project.flightManagement.DTO.TaiKhoanDTO.TaiKhoanDTO;
import com.project.flightManagement.Payload.ResponseData;
import com.project.flightManagement.Service.TaiKhoanService;
import jakarta.validation.Valid;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.*;

import java.util.HashMap;
import java.util.Map;
import java.util.Objects;
import java.util.Optional;

@RestController
@CrossOrigin(origins = "http://localhost:5173")
public class TaiKhoanController {
    @Autowired
    private TaiKhoanService tkService;
    private ResponseData response = new ResponseData();

    @GetMapping("/getAllTaiKhoan")
    public ResponseEntity<ResponseData> getAllTaiKhoan() {
        Iterable<TaiKhoanDTO> listTkDTO = tkService.getAllTaiKhoan();

        if (listTkDTO.iterator().hasNext()) {
            response.setMessage("get list Tai Khoan success!!");
            response.setData(listTkDTO);
            response.setStatusCode(200);
            return new ResponseEntity<>(response, HttpStatus.OK);
        } else {
            response.setMessage("get list Tai Khoan unsuccess!!");
            response.setData(null);
            response.setStatusCode(204);
            return new ResponseEntity<>(response, HttpStatus.NO_CONTENT);
        }
    }

    @GetMapping("/getTaiKhoanByID/{idTK}")
    public ResponseEntity<ResponseData> getTaiKhoanById(@PathVariable("idTK") Integer idTK) {
        Optional<TaiKhoanDTO> tkDTO = tkService.getTaiKhoanByID(idTK);
        if (tkDTO.isPresent()) {
            response.setMessage("Get account by ID success!!");
            response.setData(tkDTO);
            response.setStatusCode(200);
            return new ResponseEntity<>(response, HttpStatus.OK);
        } else {
            response.setMessage("Account not found!!");
            response.setData(null);
            response.setStatusCode(404);
            return new ResponseEntity<>(response, HttpStatus.NOT_FOUND);
        }
    }

    @GetMapping("/getTaiKhoanByTenDN/{tenDN}")
    public ResponseEntity<ResponseData> getTaiKhoanByTenDN(@PathVariable String tenDN) {
        Optional<TaiKhoanDTO> tkDTO = tkService.getTaiKhoanByTenDN(tenDN);
        if (tkDTO.isPresent()) {
            response.setMessage("Get Account by Username success!!");
            response.setData(tkDTO);
            response.setStatusCode(200);
            return new ResponseEntity<>(response, HttpStatus.OK);
        } else {
            response.setMessage("Account not found!!");
            response.setData(null);
            response.setStatusCode(404);
            return new ResponseEntity<>(response, HttpStatus.NOT_FOUND);
        }
    }

    @PostMapping("/addTaiKhoan")
    public ResponseEntity<ResponseData> addTaiKhoan(@Valid @RequestBody TaiKhoanDTO tkDTO, BindingResult bindingResult) {
        if(bindingResult.hasErrors()){
            Map<String, String> fieldErrors = new HashMap<>();
            bindingResult.getFieldErrors().forEach(error ->
                    fieldErrors.put(error.getField(), error.getDefaultMessage()));
            response.setStatusCode(400);
            response.setData(fieldErrors);
            response.setMessage("There are some fields invalid");
            return new ResponseEntity<>(response, HttpStatus.BAD_REQUEST);
        }

        // Kiểm tra xem khDTO có khác null và có ít nhất một trường thông tin cần thiết không
        if (tkDTO != null) {

            // Kiểm tra sự tồn tại theo tên đăng nhập
            if (tkDTO.getTenDangNhap() != null) {
                Optional<TaiKhoanDTO> existingTKByTenDN = tkService.getTaiKhoanByTenDN(tkDTO.getTenDangNhap());
                if (existingTKByTenDN.isPresent()) {
                    response.setMessage("Account with this username already exists!!");
                    response.setData(null);
                    response.setStatusCode(409); // Conflict
                    return new ResponseEntity<>(response, HttpStatus.CONFLICT);
                }
            }

            // Nếu không có thông tin nào tồn tại, tiến hành lưu tài khoản mới
            Optional<TaiKhoanDTO> savedTK = tkService.addTaiKhoan(tkDTO);
            if (savedTK.isPresent()) {
                response.setMessage("Save account successfully!!");
                response.setData(savedTK.get()); // Trả về dữ liệu của khách hàng đã lưu
                response.setStatusCode(201); // Created
                return new ResponseEntity<>(response, HttpStatus.CREATED);
            } else {
                // Xử lý lỗi khi lưu không thành công
                response.setMessage("Save account unsuccessfully!!");
                response.setData(null);
                response.setStatusCode(500); // Internal Server Error
                return new ResponseEntity<>(response, HttpStatus.INTERNAL_SERVER_ERROR);
            }
        } else {
            // Xử lý trường hợp dữ liệu tài khoản không hợp lệ
            response.setMessage("Invalid account data!!");
            response.setData(null);
            response.setStatusCode(400); // Bad Request
            return new ResponseEntity<>(response, HttpStatus.BAD_REQUEST);
        }
    }

    @PutMapping("/updateTaiKhoan/{idTK}")
    public ResponseEntity<ResponseData> updateTaiKhoan(@PathVariable("idTK") Integer idTK, @Valid @RequestBody TaiKhoanDTO tkDTO, BindingResult bindingResult) {
        ResponseData responseData = new ResponseData();

        // Kiểm tra lỗi từ bindingResult
        if (bindingResult.hasErrors()) {
            Map<String, String> fieldErrors = new HashMap<>();
            bindingResult.getFieldErrors().forEach(error -> fieldErrors.put(error.getField(), error.getDefaultMessage()));
            responseData.setStatusCode(400);
            responseData.setData(fieldErrors);
            responseData.setMessage("There are some fields invalid");
            return new ResponseEntity<>(responseData, HttpStatus.BAD_REQUEST);
        }

        // Kiểm tra xem tài khoản có tồn tại không
        Optional<TaiKhoanDTO> existingTK = tkService.getTaiKhoanByID(idTK);
        if (!existingTK.isPresent()) {
            responseData.setMessage("Account not found!!");
            responseData.setData(null);
            responseData.setStatusCode(404);
            return new ResponseEntity<>(responseData, HttpStatus.NOT_FOUND);
        }

        // Cập nhật ID tài khoản
        tkDTO.setIdTaiKhoan(idTK);

        // Kiểm tra tên đăng nhập đã tồn tại chưa
        if (tkDTO.getTenDangNhap() != null && !Objects.equals(existingTK.get().getTenDangNhap(), tkDTO.getTenDangNhap())) {
            Optional<TaiKhoanDTO> existingTKByTenDN = tkService.getTaiKhoanByTenDN(tkDTO.getTenDangNhap());
            if (existingTKByTenDN.isPresent()) {
                responseData.setMessage("Account with this username already exists!!");
                responseData.setData(null);
                responseData.setStatusCode(409); // Conflict
                return new ResponseEntity<>(responseData, HttpStatus.CONFLICT);
            }
        }

        // Thực hiện cập nhật
        Optional<TaiKhoanDTO> updatedTK = tkService.updateTaiKhoan(tkDTO);
        if (updatedTK.isPresent()) {
            responseData.setMessage("Update account successfully!!");
            responseData.setData(updatedTK.get());
            responseData.setStatusCode(200); // OK
            return new ResponseEntity<>(responseData, HttpStatus.OK);
        } else {
            responseData.setMessage("Update account unsuccessfully!!");
            responseData.setData(null);
            responseData.setStatusCode(500); // Internal Server Error
            return new ResponseEntity<>(responseData, HttpStatus.INTERNAL_SERVER_ERROR);
        }
    }

}

