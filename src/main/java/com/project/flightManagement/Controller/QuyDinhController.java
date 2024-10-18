package com.project.flightManagement.Controller;



import com.project.flightManagement.DTO.QuyDinhDTO.QuyDinhDTO;
import com.project.flightManagement.DTO.QuyDinhDTO.QuyDinhDTO;
import com.project.flightManagement.Model.QuyDinh;
import com.project.flightManagement.Payload.ResponseData;
import com.project.flightManagement.Service.QuyDinhService;
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
@RequestMapping("/admin/quydinh")
@CrossOrigin(origins = "http://localhost:5173")

public class QuyDinhController {

    @Autowired
    private QuyDinhService qdservice;
    private ResponseData response =  new ResponseData();

    @GetMapping("/getallquydinh")
    public ResponseEntity<ResponseData> getAllQuyDinh(){
        Iterable<QuyDinhDTO> listqd = qdservice.getAllQuyDinh();
        if(listqd.iterator().hasNext()){
            response.setMessage("Đã lấy được danh sách quy định");
            response.setData(listqd);
            response.setStatusCode(200);
            return new ResponseEntity<>(response, HttpStatus.OK);
        }else{
            response.setMessage("không lấy ược danh sách quy định");
            response.setData(null);
            response.setStatusCode(204);
            return new ResponseEntity<>(HttpStatus.NO_CONTENT);
        }
    }

    @PostMapping("/addquydinh")
    public ResponseEntity<ResponseData> addQuyDinh(@Valid @RequestBody QuyDinhDTO qdDTO, BindingResult bindingResult) {

        if (bindingResult.hasErrors()) {
            Map<String, String> fieldErrors = new HashMap<>();
            bindingResult.getFieldErrors().forEach(error ->
                    fieldErrors.put(error.getField(), error.getDefaultMessage()));
            response.setStatusCode(400);
            response.setData(fieldErrors);
            response.setMessage("There are some fields invalid");
            return new ResponseEntity<>(response, HttpStatus.BAD_REQUEST);
        }

        Optional<QuyDinhDTO> saveqd = qdservice.addQuyDinh(qdDTO);
        if (saveqd.isPresent()) {
            response.setMessage("Luu quy dinh thanh cong");
            response.setData(saveqd.get()); // Trả về dữ liệu của khách hàng đã lưu
            response.setStatusCode(201); // Created
            return new ResponseEntity<>(response, HttpStatus.CREATED);
        } else {
            // Xử lý lỗi khi lưu không thành công
            response.setMessage("Luu quy dinh that bai");
            response.setData(null);
            response.setStatusCode(500); // Internal Server Error
            return new ResponseEntity<>(response, HttpStatus.INTERNAL_SERVER_ERROR);
        }
    }

    @GetMapping("/getquydinhbyid/{idQuyDinh}")
    public ResponseEntity<ResponseData> getquydinhbyid(@PathVariable int idQuyDinh){
        Optional<QuyDinhDTO> cbDTO = qdservice.getQuyDinhById(idQuyDinh);
        if (cbDTO.isPresent()) {
            response.setMessage("Lay Quy Dinh Thanh Cong");
            response.setData(cbDTO.get()); // Trả về dữ liệu của khách hàng đã lưu
            response.setStatusCode(201); // Created
            return new ResponseEntity<>(response, HttpStatus.CREATED);
        } else {
            // Xử lý lỗi khi lưu không thành công
            response.setMessage("Lay Quy Dinh That Bai");
            response.setData(null);
            response.setStatusCode(500); // Internal Server Error
            return new ResponseEntity<>(response, HttpStatus.INTERNAL_SERVER_ERROR);
        }
    }

    @PutMapping("/updatequydinh/{idQuyDinh}")
    public ResponseEntity<ResponseData> updateQuyDinh(@PathVariable("idQuyDinh") Integer idQuyDinh , @Valid @RequestBody QuyDinhDTO qdDTO, BindingResult bindingResult){
        if(bindingResult.hasErrors()){
            Map<String, String> fieldErrors = new HashMap<>();
            bindingResult.getFieldErrors().forEach(error ->
                    fieldErrors.put(error.getField(), error.getDefaultMessage()));
            response.setStatusCode(400);
            response.setData(fieldErrors);
            response.setMessage("There are some fields invalid");
            return new ResponseEntity<>(response, HttpStatus.BAD_REQUEST);
        }

        Optional<QuyDinhDTO> saveqd = qdservice.addQuyDinh(qdDTO);
        if (saveqd.isPresent()) {
            response.setMessage("Luu quy dinh thanh cong");
            response.setData(saveqd.get()); // Trả về dữ liệu của khách hàng đã lưu
            response.setStatusCode(201); // Created
            return new ResponseEntity<>(response, HttpStatus.CREATED);
        } else {
            // Xử lý lỗi khi lưu không thành công
            response.setMessage("Luu quy dinh that bai");
            response.setData(null);
            response.setStatusCode(500); // Internal Server Error
            return new ResponseEntity<>(response, HttpStatus.INTERNAL_SERVER_ERROR);
        }
    }
}
