package com.project.flightManagement.Controller;

import com.project.flightManagement.DTO.ChoNgoiDTO.ChoNgoiDTO;
import com.project.flightManagement.DTO.MayBayDTO.MayBayDTO;
import com.project.flightManagement.Enum.ActiveEnum;
import com.project.flightManagement.Mapper.MayBayMapper;
import com.project.flightManagement.Model.ChoNgoi;
import com.project.flightManagement.Model.MayBay;
import com.project.flightManagement.Payload.ResponseData;
import com.project.flightManagement.Service.ChoNgoiService;
import com.project.flightManagement.Service.MayBayService;
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
//@RequestMapping("/admin/chongoi")
@CrossOrigin(origins = "http://localhost:5173")
public class ChoNgoiController {
    @Autowired
    private ChoNgoiService choNgoiService;
    @Autowired
    private MayBayService mayBayService;
    private ResponseData response = new ResponseData();


    @GetMapping("/getallchongoi")
    public ResponseEntity<ResponseData> getallchongoi(){
        System.out.println("getallchongoi");
        Iterable<ChoNgoiDTO> choNgoiDTOs = choNgoiService.getAllChoNgoi();
        if(choNgoiDTOs.iterator().hasNext()) {
            response.setStatusCode(200);
            response.setMessage("Get all seat success!!");
            response.setData(choNgoiDTOs);
            return new ResponseEntity<>(response, HttpStatus.OK);
        } else {
            response.setStatusCode(404);
            response.setMessage("Get all seat failed!!");
            response.setData(null);
            return new ResponseEntity<>(response, HttpStatus.NOT_FOUND);
        }
    }


    @PostMapping("/addSeat")
    public ResponseEntity<ResponseData> addNewChoNgoi(@Valid @RequestBody ChoNgoiDTO choNgoiDTO, BindingResult bindingResult) {
        if(bindingResult.hasErrors()){
            Map<String, String> fieldErrors = new HashMap<>();
            bindingResult.getFieldErrors().forEach(error ->
                    fieldErrors.put(error.getField(), error.getDefaultMessage()));
            response.setStatusCode(400);
            response.setData(fieldErrors);
            response.setMessage("There are some fields invalid");
            return new ResponseEntity<>(response, HttpStatus.BAD_REQUEST);
        }
        if(choNgoiDTO != null) {
            Optional<ChoNgoiDTO> savedCN = choNgoiService.addNewChoNgoi(choNgoiDTO);
            if(savedCN.isPresent()) {
                response.setStatusCode(200);
                response.setMessage("Add ChoNgoi success!");
                response.setData(savedCN.get());
                return new ResponseEntity<>(response, HttpStatus.OK);
            } else {
                response.setStatusCode(404);
                response.setMessage("Add ChoNgoi failed!!");
                response.setData(null);
                return new ResponseEntity<>(response, HttpStatus.NOT_FOUND);
            }
        } else {
            response.setStatusCode(400);
            response.setMessage("Add ChoNgoi failed!!");
            response.setData(null);
            return new ResponseEntity<>(response, HttpStatus.BAD_REQUEST);
        }
    }
    @GetMapping("/getallchongoibymaybay/{idMayBay}")
    public ResponseEntity<ResponseData> getChoNgoiByMayBay(@PathVariable int idMayBay) {
        Optional<MayBayDTO> mbDTO = mayBayService.getMayBayById(idMayBay);
        Iterable<ChoNgoiDTO> listCN = choNgoiService.getChoNgoiByMayBay(mayBayService.getMayBayById(idMayBay).get());
        if(listCN.iterator().hasNext()) {
            response.setStatusCode(200);
            response.setMessage("Get seat by plane success!!");
            response.setData(listCN);
            return new ResponseEntity<>(response, HttpStatus.OK);
        } else {
            response.setStatusCode(404);
            response.setMessage("Get seat by plane failed!!");
            response.setData(null);
            return new ResponseEntity<>(response, HttpStatus.NOT_FOUND);
        }
    }
    //    @PutMapping("/blockSeat/{idChoNgoi}")
//    public ResponseEntity<ResponseData> blockChoNgoi(@PathVariable int idChoNgoi) {
//        Optional<ChoNgoiDTO> existingCN = choNgoiService.getChoNgoiById(idChoNgoi);
//        if(existingCN.isPresent()){
//            if(existingCN.get().getTrangThaiActive() == ActiveEnum.ACTIVE){
//                Optional<ChoNgoiDTO> blockCN = choNgoiService.;
//
//                if(blockCN.isPresent()){
//                    response.setMessage("Block seat successfully!!");
//                    response.setData(blockCN.get());
//                    response.setStatusCode(200); // OK
//                    return new ResponseEntity<>(response, HttpStatus.OK);
//                } else {
//                    // Xử lý lỗi khi cập nhật không thành công
//                    response.setMessage("Block seat unsuccessfully!!");
//                    response.setData(null);
//                    response.setStatusCode(500); // Internal Server Error
//                    return new ResponseEntity<>(response, HttpStatus.INTERNAL_SERVER_ERROR);
//                }
//            } else {
//                Optional<MayBayDTO> unblockCN = mayBayService.unblockMayBay(idChoNgoi);
//
//                if(unblockCN.isPresent()){
//                    response.setMessage("Unblock seat successfully!!");
//                    response.setData(unblockCN.get());
//                    response.setStatusCode(200); // OK
//                    return new ResponseEntity<>(response, HttpStatus.OK);
//                } else {
//                    // Xử lý lỗi khi cập nhật không thành công
//                    response.setMessage("Unblock seat unsuccessfully!!");
//                    response.setData(null);
//                    response.setStatusCode(500); // Internal Server Error
//                    return new ResponseEntity<>(response, HttpStatus.INTERNAL_SERVER_ERROR);
//                }
//            }
//        } else {
//            response.setMessage("Seat not found!!");
//            response.setData(null);
//            response.setStatusCode(404); // Not Found
//            return new ResponseEntity<>(response, HttpStatus.NOT_FOUND);
//        }
//    }
    @GetMapping("/getSeat/{idChoNgoi}")
    public ResponseEntity<ResponseData> getChoNgoiById(@PathVariable int idChoNgoi) {
        try {
            Optional<ChoNgoiDTO> cnDTO = choNgoiService.getChoNgoiById(idChoNgoi);
            if (cnDTO.isPresent()) {
                response.setData(cnDTO);
                response.setMessage("Get seat by id success!");
                response.setStatusCode(200);
                return new ResponseEntity<>(response, HttpStatus.OK);
            } else {
                response.setStatusCode(404);
                response.setMessage("Get seat by id failed!!");
                response.setData(null);
                return new ResponseEntity<>(response, HttpStatus.NOT_FOUND);
            }
        } catch (Exception e) {
            response.setStatusCode(500);
            response.setMessage("Not fount seat by ID!!");
            response.setData(null);
            return new ResponseEntity<>(response, HttpStatus.INTERNAL_SERVER_ERROR);
        }
    }
    @PutMapping("/blockSeat/{idChoNgoi}")
    public ResponseEntity<ResponseData> blockChoNgoi(@PathVariable int idChoNgoi) {
        Optional<ChoNgoiDTO> existingCN = choNgoiService.getChoNgoiById(idChoNgoi);
        if(existingCN.isPresent()) {
            if (existingCN.get().getTrangThaiActive() == ActiveEnum.ACTIVE) {
                Optional<ChoNgoiDTO> blockCN = choNgoiService.blockChoNgoi(idChoNgoi);
                if(blockCN.isPresent()) {
                    response.setStatusCode(200);
                    response.setMessage("Block seat by id success!");
                    response.setData(blockCN.get());
                    return new ResponseEntity<>(response, HttpStatus.OK);
                } else {
                    response.setStatusCode(404);
                    response.setMessage("Block seat by id failed!!");
                    response.setData(null);
                    return new ResponseEntity<>(response, HttpStatus.NOT_FOUND);
                }
            } else {
                Optional<ChoNgoiDTO> blockCN = choNgoiService.unblockChoNgoi(idChoNgoi);
                if(blockCN.isPresent()) {
                    response.setStatusCode(200);
                    response.setMessage("Unblock seat by id success!");
                    response.setData(blockCN.get());
                    return new ResponseEntity<>(response, HttpStatus.OK);
                } else {
                    response.setStatusCode(404);
                    response.setMessage("Unblock seat by id failed!!");
                    response.setData(null);
                    return new ResponseEntity<>(response, HttpStatus.NOT_FOUND);
                }
            }
        } else {
            response.setStatusCode(404);
            response.setMessage("Seat is not existing!!");
            response.setData(null);
            return new ResponseEntity<>(response, HttpStatus.NOT_FOUND);
        }
    }
}
