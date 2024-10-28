package com.project.flightManagement.Controller;

import com.project.flightManagement.DTO.ChucVuDTO.ChucVuDTO;
import com.project.flightManagement.Payload.ResponseData;
import com.project.flightManagement.Service.ChucVuService;
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
@RequestMapping("/admin/chucvu")
@CrossOrigin(origins = "http://localhost:5173")

public class ChucVuController {
    @Autowired
    private ChucVuService cvservice ;
    private ResponseData response = new ResponseData();

    @GetMapping("/getallchucvu")
    public ResponseEntity<ResponseData> getAllNhanVien(){
        Iterable<ChucVuDTO> listNvDTO = cvservice.getAllChucVu();
        if(listNvDTO.iterator().hasNext()){
            response.setMessage("get list Chuc vu success!!");
            response.setData(listNvDTO);
            response.setStatusCode(200);
            return new ResponseEntity<>(response, HttpStatus.OK);
        } else {
            response.setMessage("get list chuc vu unsuccess!!");
            response.setData(null);
            response.setStatusCode(204);
            return new ResponseEntity<>(HttpStatus.NO_CONTENT);
        }
    }


    @GetMapping("/getchucvubyten")
    public ResponseEntity<ResponseData> getNhanVienByTen(@RequestParam String ten){
        Optional<ChucVuDTO> listcvDTO = cvservice.getChucVuByTen(ten);
        if(listcvDTO.isPresent()){
            response.setMessage("get Chuc Vu success!!");
            response.setData(listcvDTO);
            response.setStatusCode(200);
            return new ResponseEntity<>(response, HttpStatus.OK);
        } else {
            response.setMessage("get Chuc Vu unsuccess!!");
            response.setData(null);
            response.setStatusCode(404);
            return new ResponseEntity<>(response, HttpStatus.NOT_FOUND);
        }
    }

    @GetMapping("/getchucvubyid")
    public ResponseEntity<ResponseData> getNhanVienById(@RequestParam int id){
        Optional<ChucVuDTO> cvDTO = cvservice.getChucVuById(id);
        if(cvDTO.isPresent()){
            response.setMessage("get Chuc Vu success!!");
            response.setData(cvDTO);
            response.setStatusCode(200);
            return new ResponseEntity<>(response, HttpStatus.OK);
        } else {
            response.setMessage("get Chuc Vu unsuccess!!");
            response.setData(null);
            response.setStatusCode(404);
            return new ResponseEntity<>(response, HttpStatus.NOT_FOUND);
        }
    }


    @PostMapping("/addchucvu")
    public ResponseEntity<ResponseData> addChucVu(@Valid @RequestBody ChucVuDTO cvDTO, BindingResult bindingResult) {

        if (bindingResult.hasErrors()) {
            Map<String, String> fieldErrors = new HashMap<>();
            bindingResult.getFieldErrors().forEach(error ->
                    fieldErrors.put(error.getField(), error.getDefaultMessage()));
            response.setStatusCode(400);
            response.setData(fieldErrors);
            response.setMessage("There are some fields invalid");
            return new ResponseEntity<>(response, HttpStatus.BAD_REQUEST);
        }

        Optional<ChucVuDTO> existTenChucVu =  cvservice.getChucVuByTen(cvDTO.getTen().trim());
        if (existTenChucVu.isPresent()){
            response.setMessage("chuc vu nay da ton tai!!");
            response.setData(null);
            response.setStatusCode(409);
            return new ResponseEntity<>(response, HttpStatus.CONFLICT);
        }

        Optional<ChucVuDTO> saveCv = cvservice.addChucVu(cvDTO);
        if (saveCv.isPresent()) {
            response.setMessage("Save Chuc Vu successfully!!");
            response.setData(saveCv.get()); // Trả về dữ liệu của khách hàng đã lưu
            response.setStatusCode(201); // Created
            return new ResponseEntity<>(response, HttpStatus.CREATED);
        } else {
            // Xử lý lỗi khi lưu không thành công
            response.setMessage("Save chuc vu unsuccessfully!!");
            response.setData(null);
            response.setStatusCode(500); // Internal Server Error
            return new ResponseEntity<>(response, HttpStatus.INTERNAL_SERVER_ERROR);
        }
    }
}
