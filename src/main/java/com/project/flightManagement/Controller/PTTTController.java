package com.project.flightManagement.Controller;

import com.project.flightManagement.DTO.PTTTDTO.PTTTDTO;
import com.project.flightManagement.Payload.ResponseData;
import com.project.flightManagement.Service.PTTTService;

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
@RequestMapping("admin/PTTT")
public class PTTTController {

    @Autowired
    private PTTTService ptttService;
    private ResponseData response = new ResponseData();

    @GetMapping("/getAllPTTT")
    public ResponseEntity<ResponseData> getAllPTTT() {
        Iterable<PTTTDTO> listPTTTDTO = ptttService.getAllPTTT();

        if (listPTTTDTO.iterator().hasNext()) {
            response.setMessage("get list Payment Method success!");
            response.setData(listPTTTDTO);
            response.setStatusCode(200);
            return new ResponseEntity<>(response, HttpStatus.OK);
        } else {
            response.setMessage("get list Payment Method unsuccess!");
            response.setData(null);
            response.setStatusCode(200);
            return new ResponseEntity<>(response, HttpStatus.NO_CONTENT);
        }
    }

    @GetMapping("/getPTTTById/{idPTTT}")
    public ResponseEntity<ResponseData> getPTTTById(@PathVariable int idPTTT) {
        Optional<PTTTDTO> ptttDTO = ptttService.getPTTTByID(idPTTT);
        if (ptttDTO.isPresent()) {
            response.setMessage("Get Payment Method by ID success!");
            response.setData(ptttDTO);
            response.setStatusCode(200);
            return new ResponseEntity<>(response, HttpStatus.OK);
        } else {
            response.setMessage("Get Payment Method by ID unsuccess!");
            response.setData(null);
            response.setStatusCode(200);
            return new ResponseEntity<>(response, HttpStatus.NO_CONTENT);
        }
    }

    @PostMapping("/addPTTT")
    public ResponseEntity<ResponseData> addPTTT(@Valid @RequestBody PTTTDTO ptttDTO, BindingResult bindingResult) {
        if (bindingResult.hasErrors()) {
            Map<String, String> fieldErrors =  new HashMap<>();
            bindingResult.getFieldErrors().forEach(error -> fieldErrors.put(error.getField(), error.getDefaultMessage()));
            response.setStatusCode(400);
            response.setData(fieldErrors);
            response.setMessage("There are some fields invalid!");
            return new ResponseEntity<>(response, HttpStatus.BAD_REQUEST);
        }

        if (ptttDTO != null && (ptttDTO.getTenPTTT() != null)) {
            Optional<PTTTDTO> savedPTTT = ptttService.addPTTT(ptttDTO);
            if (savedPTTT.isPresent()) {
                response.setMessage("Save payment method successfully!");
                response.setData(savedPTTT.get());
                response.setStatusCode(200);
                return new ResponseEntity<>(response, HttpStatus.CREATED);
            } else {
                response.setMessage("Save payment method unsuccessfully!");
                response.setData(null);
                response.setStatusCode(500);
                return new ResponseEntity<>(response, HttpStatus.INTERNAL_SERVER_ERROR);
            }
        } else {
            response.setMessage("Invalid payment method data!");
            response.setData(null);
            response.setStatusCode(400);
            return new ResponseEntity<>(response, HttpStatus.BAD_REQUEST);
        }
    }

    @PutMapping("/updatePTTT/{idPTTT}")
    public ResponseEntity<ResponseData> updatePTTT(@PathVariable("idPTTT") Integer idPTTT, @Valid @RequestBody PTTTDTO ptttDTO, BindingResult bindingResult) {
        ResponseData responseData = new ResponseData();

        if (bindingResult.hasErrors()) {
            Map<String, String> fieldErrors = new HashMap<>();
            bindingResult.getFieldErrors().forEach(error -> fieldErrors.put(error.getField(), error.getDefaultMessage()));
            responseData.setStatusCode(400);
            responseData.setData(fieldErrors);
            responseData.setMessage("There are some fields invalid!");
            return new ResponseEntity<>(responseData, HttpStatus.BAD_REQUEST);
        }

        Optional<PTTTDTO> existingPTTT = ptttService.getPTTTByID(idPTTT);
        if (!existingPTTT.isPresent()) {
            responseData.setMessage("Payment method not found");
            responseData.setData(null);
            responseData.setStatusCode(400);
            return new ResponseEntity<>(responseData, HttpStatus.NOT_FOUND);
        }

        ptttDTO.setIdPTTT(idPTTT);

        Optional<PTTTDTO> updatePTTT = ptttService.updatePTTT(ptttDTO);
        if (updatePTTT.isPresent()) {
            responseData.setMessage("Update Payment Method succesfully!");
            responseData.setData(updatePTTT.get());
            responseData.setStatusCode(200);
            return new ResponseEntity<>(responseData, HttpStatus.OK);
        } else {
            responseData.setMessage("Update Payment Method Unsuccessfully!");
            responseData.setData(null);
            responseData.setStatusCode(500);
            return new ResponseEntity<>(responseData, HttpStatus.INTERNAL_SERVER_ERROR);
        }
    }

}
