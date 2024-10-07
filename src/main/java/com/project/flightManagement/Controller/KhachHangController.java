package com.project.flightManagement.Controller;

import com.project.flightManagement.DTO.KhachHangDTO.KhachHangDTO;
import com.project.flightManagement.Mapper.KhachHangMapper;
import com.project.flightManagement.Model.KhachHang;
import com.project.flightManagement.Payload.ResponseData;
import com.project.flightManagement.Service.KhachHangService;
import jakarta.validation.Valid;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.HttpStatusCode;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.*;

import java.util.HashMap;
import java.util.Map;
import java.util.Objects;
import java.util.Optional;

@RestController
@CrossOrigin(origins = "http://localhost:5173")
public class KhachHangController {
    @Autowired
    private KhachHangService khachHangService;

    private ResponseData response = new ResponseData();

    @GetMapping("/getAllCustomer")
    public ResponseEntity<ResponseData> getAllKhachHang(){
        Iterable<KhachHangDTO> listKhDTO = khachHangService.getAllKhachHang();
        if(listKhDTO.iterator().hasNext()){
            response.setMessage("get list customers success!!");
            response.setData(listKhDTO);
            response.setStatusCode(200);
            return new ResponseEntity<>(response, HttpStatus.OK);
        } else {
            response.setMessage("get list customers unsuccess!!");
            response.setData(null);
            response.setStatusCode(204);
            return new ResponseEntity<>(HttpStatus.NO_CONTENT);
        }
    }

    @GetMapping("/getAllCustomerSorted")
    public ResponseEntity<ResponseData> getAllKhachHang(@RequestParam(defaultValue = "idKhachHang") String sortBy,
                                                        @RequestParam(defaultValue = "asc") String order){
        Iterable<KhachHangDTO> listKhDTO = khachHangService.getAllKhachHangSorted(sortBy, order);
        if(listKhDTO.iterator().hasNext()){
            response.setMessage("get list customers success!!");
            response.setData(listKhDTO);
            response.setStatusCode(200);
            return new ResponseEntity<>(response, HttpStatus.OK);
        } else {
            response.setMessage("get list customers unsuccess!!");
            response.setData(null);
            response.setStatusCode(204);
            return new ResponseEntity<>(HttpStatus.NO_CONTENT);
        }
    }
    @GetMapping("/findKhachHang")
    public ResponseEntity<ResponseData> getKhachHangByKeyWord(@RequestParam String keyword){
        System.out.println("Searching for: " + keyword);
        Iterable<KhachHangDTO> listKhDTO = khachHangService.findByHoTen(keyword);
        if(listKhDTO.iterator().hasNext()){
            System.out.println("Searching for 1: " + keyword);
            response.setMessage("get list customers success!!");
            response.setData(listKhDTO);
            response.setStatusCode(200);
            return new ResponseEntity<>(response, HttpStatus.OK);
        } else {
            System.out.println("Searching for 2: " + keyword);
            response.setMessage("get list customers unsuccess!!");
            response.setData(listKhDTO);
            response.setStatusCode(404);
            return new ResponseEntity<>(response, HttpStatus.NOT_FOUND);
        }
    }
    @GetMapping("/getCustomer/{idKH}")
    public ResponseEntity<ResponseData> getKhachHangById(@PathVariable int idKH) {
        Optional<KhachHangDTO> khDTO = khachHangService.getKhachHangByIdKhachHang(idKH);
        if (khDTO.isPresent()) {
            response.setMessage("Get customer by ID success!!");
            response.setData(khDTO);
            response.setStatusCode(200);
            return new ResponseEntity<>(response, HttpStatus.OK);
        } else {
            response.setMessage("Customer not found!!");
            response.setData(null);
            response.setStatusCode(404);
            return new ResponseEntity<>(response, HttpStatus.NOT_FOUND);
        }
    }
    @PostMapping("/addCustomer")
    public ResponseEntity<ResponseData> addNewCustomer(@Valid @RequestBody KhachHangDTO khDTO, BindingResult bindingResult) {
        ResponseData response = new ResponseData();

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
        if (khDTO != null && (khDTO.getEmail() != null || khDTO.getCccd() != null || khDTO.getSoDienThoai() != null)) {

            ResponseEntity<ResponseData> rs = checkExistEmail(khDTO.getEmail());
            if(rs!=null){
                return rs;
            }
            rs = checkExistCCCD(khDTO.getCccd());
            if(rs!=null){
                return rs;
            }
            rs = checkExistSDT(khDTO.getSoDienThoai());
            if(rs!=null){
                return rs;
            }

            // Nếu không có thông tin nào tồn tại, tiến hành lưu khách hàng mới
            Optional<KhachHangDTO> savedKH = khachHangService.addNewKhachHang(khDTO);
            if (savedKH.isPresent()) {
                response.setMessage("Save customer successfully!!");
                response.setData(savedKH.get()); // Trả về dữ liệu của khách hàng đã lưu
                response.setStatusCode(201); // Created
                return new ResponseEntity<>(response, HttpStatus.CREATED);
            } else {
                // Xử lý lỗi khi lưu không thành công
                response.setMessage("Save customer unsuccessfully!!");
                response.setData(null);
                response.setStatusCode(500); // Internal Server Error
                return new ResponseEntity<>(response, HttpStatus.INTERNAL_SERVER_ERROR);
            }
        } else {
            // Xử lý trường hợp dữ liệu khách hàng không hợp lệ
            response.setMessage("Invalid customer data!!");
            response.setData(null);
            response.setStatusCode(400); // Bad Request
            return new ResponseEntity<>(response, HttpStatus.BAD_REQUEST);
        }
    }

    @PutMapping("/updateCustomer/{idKH}")
    public ResponseEntity<ResponseData> updateKhachHang(@PathVariable("idKH") Integer idKH,@Valid @RequestBody KhachHangDTO khDTO, BindingResult bindingResult) {
        ResponseData response = new ResponseData();
        if(bindingResult.hasErrors()){
            Map<String, String> fieldErrors = new HashMap<>();
            bindingResult.getFieldErrors().forEach(error ->
                    fieldErrors.put(error.getField(), error.getDefaultMessage()));
            response.setStatusCode(400);
            response.setData(fieldErrors);
            response.setMessage("There are some fields invalid");
            return new ResponseEntity<>(response, HttpStatus.BAD_REQUEST);
        }
        // Kiểm tra xem đối tượng khDTO có khác null và ID có hợp lệ không
        if (khDTO != null && idKH != null) {
            // Kiểm tra xem khách hàng có tồn tại hay không
            Optional<KhachHangDTO> existingKH = khachHangService.getKhachHangByIdKhachHang(idKH);
            if (existingKH.isPresent()) {
                // Cập nhật thông tin khách hàng
                khDTO.setIdKhachHang(idKH); // Đảm bảo rằng ID của khách hàng được thiết lập

                // Kiểm tra xem email, số điện thoại, CCCD đã tồn tại hay chưa (nếu có sự thay đổi)
                if (khDTO.getEmail() != null && !Objects.equals(existingKH.get().getEmail(), khDTO.getEmail()) ) {
                    ResponseEntity<ResponseData> rs = checkExistEmail(khDTO.getEmail());
                    if(rs!=null){
                        return rs;
                    }
                }

                if (khDTO.getSoDienThoai() != null && !Objects.equals(existingKH.get().getSoDienThoai(), khDTO.getSoDienThoai())) {
                    ResponseEntity<ResponseData> rs = checkExistSDT(khDTO.getSoDienThoai());
                    if(rs!=null){
                        return rs;
                    }
                }

                if (khDTO.getCccd() != null && !Objects.equals(existingKH.get().getCccd(), khDTO.getCccd())) {
                    ResponseEntity<ResponseData> rs = checkExistCCCD(khDTO.getCccd());
                    if(rs!=null){
                        return rs;
                    }
                }

                // Cập nhật khách hàng
                Optional<KhachHangDTO> updatedKH = khachHangService.updateKhachHang(khDTO);
                if (updatedKH.isPresent()) {
                    response.setMessage("Update customer successfully!!");
                    response.setData(updatedKH.get()); // Trả về thông tin khách hàng đã cập nhật
                    response.setStatusCode(200); // OK
                    return new ResponseEntity<>(response, HttpStatus.OK);
                } else {
                    // Xử lý lỗi khi cập nhật không thành công
                    response.setMessage("Update customer unsuccessfully!!");
                    response.setData(null);
                    response.setStatusCode(500); // Internal Server Error
                    return new ResponseEntity<>(response, HttpStatus.INTERNAL_SERVER_ERROR);
                }
            } else {
                // Khách hàng không tồn tại
                response.setMessage("Customer not found!!");
                response.setData(null);
                response.setStatusCode(404); // Not Found
                return new ResponseEntity<>(response, HttpStatus.NOT_FOUND);
            }
        } else {
            // Dữ liệu không hợp lệ
            response.setMessage("Invalid customer data!!");
            response.setData(null);
            response.setStatusCode(400); // Bad Request
            return new ResponseEntity<>(response, HttpStatus.BAD_REQUEST);
        }
    }


    public ResponseEntity<ResponseData> checkExistEmail(String email){
        Optional<KhachHangDTO> existingKHByEmail = khachHangService.getKhachHangByEmail(email);
        if (existingKHByEmail.isPresent()) {
            response.setMessage("Customer with this email already exists!!");
            Map<String, String> errorMessage = new HashMap<>();
            errorMessage.put("email", "Customer with this email already exists!!");
            response.setData(errorMessage);
            response.setStatusCode(409); // Conflict
            return new ResponseEntity<>(response, HttpStatus.CONFLICT);
        }
        return null;
    }

    public ResponseEntity<ResponseData> checkExistSDT(String sdt){
        Optional<KhachHangDTO> existingKHByPhone = khachHangService.getKhachHangBySDT(sdt);
        if (existingKHByPhone.isPresent()) {
            response.setMessage("Customer with this phone number already exists!!");
            Map<String, String> errorMessage = new HashMap<>();
            errorMessage.put("soDienThoai", "Customer with this phone number already exists!!");
            response.setData(errorMessage);
            response.setStatusCode(409); // Conflict
            return new ResponseEntity<>(response, HttpStatus.CONFLICT);
        }
        return null;
    }
    public ResponseEntity<ResponseData> checkExistCCCD(String cccd){
        Optional<KhachHangDTO> existingKHByCccd = khachHangService.getKhachHangByCccd(cccd);
        if (existingKHByCccd.isPresent()) {
            response.setMessage("Customer with this CCCD already exists!!");
            Map<String, String> errorMessage = new HashMap<>();
            errorMessage.put("cccd", "Customer with this cccd already exists!!");
            response.setData(errorMessage);
            response.setStatusCode(409); // Conflict
            return new ResponseEntity<>(response, HttpStatus.CONFLICT);
        }
        return null;
    }


}
