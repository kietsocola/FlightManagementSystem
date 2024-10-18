package com.project.flightManagement.Controller;

import com.project.flightManagement.DTO.LoaiQuyDinhDTO.LoaiQuyDinhDTO;
import com.project.flightManagement.DTO.QuyDinhDTO.QuyDinhDTO;
import com.project.flightManagement.Payload.ResponseData;
import com.project.flightManagement.Service.LoaiQuyDinhService;
import com.project.flightManagement.Service.QuyDinhService;
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
@RequestMapping("/admin/loaiquydinh")
@CrossOrigin(origins = "http://localhost:5173")

public class LoaiQuyDinhController {

    @Autowired
    private LoaiQuyDinhService lqdservice;
    private ResponseData response =  new ResponseData();

    @GetMapping("/getallloaiquydinh")
    public ResponseEntity<ResponseData> getAllQuyDinh(){
        Iterable<LoaiQuyDinhDTO> listqd = lqdservice.getall();
        if(listqd.iterator().hasNext()){
            response.setMessage("Đã lấy được danh sách loại quy định");
            response.setData(listqd);
            response.setStatusCode(200);
            return new ResponseEntity<>(response, HttpStatus.OK);
        }else{
            response.setMessage("không lấy được danh sách loại quy định");
            response.setData(null);
            response.setStatusCode(204);
            return new ResponseEntity<>(HttpStatus.NO_CONTENT);
        }
    }

    @PostMapping("/addloaiquydinh")
    public ResponseEntity<ResponseData> addQuyDinh(@Valid @RequestBody LoaiQuyDinhDTO lqdDTO, BindingResult bindingResult) {

        if (bindingResult.hasErrors()) {
            Map<String, String> fieldErrors = new HashMap<>();
            bindingResult.getFieldErrors().forEach(error ->
                    fieldErrors.put(error.getField(), error.getDefaultMessage()));
            response.setStatusCode(400);
            response.setData(fieldErrors);
            response.setMessage("There are some fields invalid");
            return new ResponseEntity<>(response, HttpStatus.BAD_REQUEST);
        }

        Optional<LoaiQuyDinhDTO> savelqd = lqdservice.add(lqdDTO);
        if (savelqd.isPresent()) {
            response.setMessage("Luu loại quy dinh thanh cong");
            response.setData(savelqd.get()); // Trả về dữ liệu của khách hàng đã lưu
            response.setStatusCode(201); // Created
            return new ResponseEntity<>(response, HttpStatus.CREATED);
        } else {
            // Xử lý lỗi khi lưu không thành công
            response.setMessage("Luu loại quy dinh that bai");
            response.setData(null);
            response.setStatusCode(500); // Internal Server Error
            return new ResponseEntity<>(response, HttpStatus.INTERNAL_SERVER_ERROR);
        }
    }

    @GetMapping("/getloaiquydinhbyid/{idLoaiQuyDinh}")
    public ResponseEntity<ResponseData> getquydinhbyid(@PathVariable int idLoaiQuyDinh){
        Optional<LoaiQuyDinhDTO> cbDTO = lqdservice.getById(idLoaiQuyDinh);
        if (cbDTO.isPresent()) {
            response.setMessage("Lay loại Quy Dinh Thanh Cong");
            response.setData(cbDTO.get()); // Trả về dữ liệu của khách hàng đã lưu
            response.setStatusCode(201); // Created
            return new ResponseEntity<>(response, HttpStatus.CREATED);
        } else {
            // Xử lý lỗi khi lưu không thành công
            response.setMessage("Lay loại Quy Dinh That Bai");
            response.setData(null);
            response.setStatusCode(500); // Internal Server Error
            return new ResponseEntity<>(response, HttpStatus.INTERNAL_SERVER_ERROR);
        }
    }

    @PutMapping("/updateloaiquydinh/{idLoaiQuyDinh}")
    public ResponseEntity<ResponseData> updateQuyDinh(@PathVariable("idLoaiQuyDinh") Integer idLoaiQuyDinh , @Valid @RequestBody LoaiQuyDinhDTO lqdDTO, BindingResult bindingResult){
        if(bindingResult.hasErrors()){
            Map<String, String> fieldErrors = new HashMap<>();
            bindingResult.getFieldErrors().forEach(error ->
                    fieldErrors.put(error.getField(), error.getDefaultMessage()));
            response.setStatusCode(400);
            response.setData(fieldErrors);
            response.setMessage("There are some fields invalid");
            return new ResponseEntity<>(response, HttpStatus.BAD_REQUEST);
        }

        Optional<LoaiQuyDinhDTO> savelqd = lqdservice.add(lqdDTO);
        if (savelqd.isPresent()) {
            response.setMessage("Luu loại quy dinh thanh cong");
            response.setData(savelqd.get()); // Trả về dữ liệu của khách hàng đã lưu
            response.setStatusCode(201); // Created
            return new ResponseEntity<>(response, HttpStatus.CREATED);
        } else {
            // Xử lý lỗi khi lưu không thành công
            response.setMessage("Luu loại quy dinh that bai");
            response.setData(null);
            response.setStatusCode(500); // Internal Server Error
            return new ResponseEntity<>(response, HttpStatus.INTERNAL_SERVER_ERROR);
        }
    }
}
