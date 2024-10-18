package com.project.flightManagement.Controller;

import com.project.flightManagement.DTO.ChuyenBayDTO.ChuyenBayDTO;
import com.project.flightManagement.Payload.ResponseData;
import com.project.flightManagement.Service.ChuyenBayService;
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
@RequestMapping("/admin/chuyenbay")
@CrossOrigin(origins = "http://localhost:5173")

public class ChuyenBayController {

    @Autowired
    private ChuyenBayService cbservice;
    private ResponseData response =  new ResponseData();

    @GetMapping("/getallchuyenbay")
    public ResponseEntity<ResponseData> getAllChuyenBay(){
        Iterable<ChuyenBayDTO> listcb = cbservice.getAllChuyenBay();
        if(listcb.iterator().hasNext()){
            response.setMessage("Tim thay chuyen bay");
            response.setData(listcb);
            response.setStatusCode(200);
            return new ResponseEntity<>(response, HttpStatus.OK);
        }else{
            response.setMessage("get list Chuyen Bay unsuccess!!");
            response.setData(null);
            response.setStatusCode(204);
            return new ResponseEntity<>(HttpStatus.NO_CONTENT);
        }
    }

    @GetMapping("/getallchuyenbaysorted")
    public ResponseEntity<ResponseData> getAllNhanVien(@RequestParam(defaultValue = "idChuyenBay") String sortField ,@RequestParam(defaultValue = "asc") String sortOrder){
        Iterable<ChuyenBayDTO> listncbDTO = cbservice.getAllChuyenBaySorted(sortField,sortOrder);
        if(listncbDTO.iterator().hasNext()){
            response.setMessage("get list Chuyen Bay success!!");
            response.setData(listncbDTO);
            response.setStatusCode(200);
            return new ResponseEntity<>(response, HttpStatus.OK);
        } else {
            response.setMessage("get list Chuyen Bay unsuccess!!");
            response.setData(null);
            response.setStatusCode(204);
            return new ResponseEntity<>(HttpStatus.NO_CONTENT);
        }
    }

    @PostMapping("/addchuyenbay")
    public ResponseEntity<ResponseData> addChuyenBay(@Valid @RequestBody ChuyenBayDTO cbDTO, BindingResult bindingResult) {

        if (bindingResult.hasErrors()) {
            Map<String, String> fieldErrors = new HashMap<>();
            bindingResult.getFieldErrors().forEach(error ->
                    fieldErrors.put(error.getField(), error.getDefaultMessage()));
            response.setStatusCode(400);
            response.setData(fieldErrors);
            response.setMessage("There are some fields invalid");
            return new ResponseEntity<>(response, HttpStatus.BAD_REQUEST);
        }

        Optional<ChuyenBayDTO> saveCB = cbservice.addChuyenBay(cbDTO);
        if (saveCB.isPresent()) {
            response.setMessage("Save Nhan vien successfully!!");
            response.setData(saveCB.get()); // Trả về dữ liệu của khách hàng đã lưu
            response.setStatusCode(201); // Created
            return new ResponseEntity<>(response, HttpStatus.CREATED);
        } else {
            // Xử lý lỗi khi lưu không thành công
            response.setMessage("Save nhan vien unsuccessfully!!");
            response.setData(null);
            response.setStatusCode(500); // Internal Server Error
            return new ResponseEntity<>(response, HttpStatus.INTERNAL_SERVER_ERROR);
        }
    }

    @GetMapping("/getchuyenbaybyid/{idChuyenBay}")
    public ResponseEntity<ResponseData> getchuyenbaybyid(@PathVariable int idChuyenBay){
        Optional<ChuyenBayDTO> cbDTO = cbservice.getChuyenBayById(idChuyenBay);
        if (cbDTO.isPresent()) {
            response.setMessage("Save Nhan vien successfully!!");
            response.setData(cbDTO.get()); // Trả về dữ liệu của khách hàng đã lưu
            response.setStatusCode(201); // Created
            return new ResponseEntity<>(response, HttpStatus.CREATED);
        } else {
            // Xử lý lỗi khi lưu không thành công
            response.setMessage("Save nhan vien unsuccessfully!!");
            response.setData(null);
            response.setStatusCode(500); // Internal Server Error
            return new ResponseEntity<>(response, HttpStatus.INTERNAL_SERVER_ERROR);
        }
    }

    @PutMapping("/updatechuyenbay/{idChuyenBay}")
    public ResponseEntity<ResponseData> updatechuyenbay(@PathVariable("idChuyenBay") Integer idChuyenBay , @Valid @RequestBody ChuyenBayDTO cbDTO, BindingResult bindingResult){
        if(bindingResult.hasErrors()){
            Map<String, String> fieldErrors = new HashMap<>();
            bindingResult.getFieldErrors().forEach(error ->
                    fieldErrors.put(error.getField(), error.getDefaultMessage()));
            response.setStatusCode(400);
            response.setData(fieldErrors);
            response.setMessage("There are some fields invalid");
            return new ResponseEntity<>(response, HttpStatus.BAD_REQUEST);
        }

        Optional<ChuyenBayDTO> saveCb = cbservice.updateChuyenBay(cbDTO);
        if (saveCb.isPresent()) {
            response.setMessage("Save Chuyen Bay successfully!!");
            response.setData(saveCb.get()); // Trả về dữ liệu của khách hàng đã lưu
            response.setStatusCode(201); // Created
            return new ResponseEntity<>(response, HttpStatus.CREATED);
        } else {
            // Xử lý lỗi khi lưu không thành công
            response.setMessage("Save Chuyen Bay unsuccessfully!!");
            response.setData(null);
            response.setStatusCode(500); // Internal Server Error
            return new ResponseEntity<>(response, HttpStatus.INTERNAL_SERVER_ERROR);
        }
    }
}
