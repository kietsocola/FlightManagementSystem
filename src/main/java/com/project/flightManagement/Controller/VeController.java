package com.project.flightManagement.Controller;

import com.project.flightManagement.DTO.LoaiVeDTO.LoaiVeDTO;
import com.project.flightManagement.DTO.VeDTO.VeCreateDTO;
import com.project.flightManagement.DTO.VeDTO.VeDTO;
import com.project.flightManagement.DTO.VeDTO.VeUpdateDTO;
import com.project.flightManagement.DTO.VeDTO.VeUpdateHanhKhachDTO;
import com.project.flightManagement.Exception.IdMismatchException;
import com.project.flightManagement.Exception.NoUpdateRequiredException;
import com.project.flightManagement.Exception.ResourceNotFoundException;
import com.project.flightManagement.Model.Email;
import com.project.flightManagement.Payload.ResponseData;
import com.project.flightManagement.Service.EmailService;
import com.project.flightManagement.Service.VeService;
import jakarta.validation.Valid;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Lazy;
import org.springframework.data.domain.Page;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.*;

@Controller
@RequestMapping("/ve")
public class VeController {
    @Autowired
    @Lazy
    private VeService veService;
    @Autowired
    private EmailService emailService;
    @GetMapping
    public ResponseEntity<?> getAllVe(@RequestParam(defaultValue = "0") int page,
                                          @RequestParam(defaultValue = "10") int size) {
        ResponseData responseData = new ResponseData();

        // Lấy danh sách đánh giá
        Page<VeDTO> veDTOPage = veService.getAllVe(page, size);

        // Kiểm tra nếu danh sách đánh giá trống
        if (veDTOPage.isEmpty()) {
            responseData.setStatusCode(204);
            responseData.setMessage("No ve found.");
            return ResponseEntity.status(HttpStatus.NO_CONTENT).body(responseData);
        }
        responseData.setStatusCode(200);
        responseData.setData(veDTOPage);
        responseData.setMessage("Successfully retrieved all ve.");
        return ResponseEntity.ok(responseData);
    }

    @GetMapping("/chuyenbay/{idChuyenBay}")
    public ResponseEntity<?> getAllVeByIdChuyenBay(@PathVariable int idChuyenBay,
                                                   @RequestParam(defaultValue = "0") int page,
                                                   @RequestParam(defaultValue = "10") int size) {
        ResponseData responseData = new ResponseData();

        // Lấy danh sách vé theo chuyến bay
        Page<VeDTO> veDTOPage = veService.getAllVeByIdChuyenBay(idChuyenBay, page, size);

        // Kiểm tra nếu danh sách vé trống
        if (veDTOPage.isEmpty()) {
            responseData.setStatusCode(204);
            responseData.setMessage("No ve found for the given chuyen bay.");
            return ResponseEntity.status(HttpStatus.NO_CONTENT).body(responseData);
        }
        responseData.setStatusCode(200);
        responseData.setData(veDTOPage);
        responseData.setMessage("Successfully retrieved all ve for the given chuyen bay.");
        return ResponseEntity.ok(responseData);
    }

    @PutMapping("/{idVe}")
    public ResponseEntity<ResponseData> updateVe(@PathVariable int idVe,@Valid @RequestBody VeUpdateDTO veUpdateDTO) {

        ResponseData responseData = new ResponseData();

        try {
            veService.updateVe(idVe, veUpdateDTO);
            responseData.setStatusCode(200);
            responseData.setMessage("Ve updated successfully.");
            return ResponseEntity.ok(responseData);
        } catch (IdMismatchException e) {
            responseData.setStatusCode(400);
            responseData.setMessage(e.getMessage());
            return ResponseEntity.badRequest().body(responseData);
        } catch (ResourceNotFoundException e) {
            responseData.setStatusCode(404);
            responseData.setMessage(e.getMessage());
            return ResponseEntity.status(HttpStatus.NOT_FOUND).body(responseData);
        } catch (NoUpdateRequiredException e) {
            responseData.setStatusCode(200); // Still a successful request
            responseData.setMessage(e.getMessage());
            return ResponseEntity.ok(responseData);
        } catch (Exception e) {
            responseData.setStatusCode(500);
            responseData.setMessage("Failed to update Ve due to an unexpected error.");
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body(responseData);
        }
    }
    @PostMapping("/email")
    public ResponseEntity<ResponseData> sendHtmlVeOnlineEmail(@RequestBody Email email) {
        ResponseData responseData = new ResponseData();
        try {
            emailService.sendHtmlVeOnlineEmail(email);
            responseData.setMessage("Email sent successfully.");
            responseData.setStatusCode(200);
            return ResponseEntity.ok(responseData);
        } catch (Exception e) {
            responseData.setMessage("Failed to send email: " + e.getMessage());
            responseData.setStatusCode(500);
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body(responseData);
        }
    }

    @GetMapping("/{idVe}")
    public ResponseEntity<?> getVeByIdVe(@PathVariable int idVe) {
        ResponseData responseData = new ResponseData();

        // Lấy danh sách vé theo chuyến bay
        VeDTO veDTO = veService.getVeById(idVe);

        // Kiểm tra nếu danh sách vé trống
        if (veDTO == null) {
            responseData.setStatusCode(204);
            responseData.setMessage("No ve found for id Ve = " + idVe );
            responseData.setData("");
            return new ResponseEntity<>(responseData, HttpStatus.NO_CONTENT);
        }
        responseData.setStatusCode(200);
        responseData.setData(veDTO);
        responseData.setMessage("Successfully retrieved ve for idVe.");
        return ResponseEntity.ok(responseData);
    }

    @PostMapping
    public ResponseEntity<?> createVe(@Valid @RequestBody VeCreateDTO veCreateDTO) {
        ResponseData responseData = new ResponseData();
        try {
            boolean isSuccess = veService.createVe(veCreateDTO);

            if (isSuccess) {
                responseData.setStatusCode(200);  // 201 Created
                responseData.setData("");
                responseData.setMessage("Successfully created ve");
                return new ResponseEntity<>(responseData, HttpStatus.OK);
            } else {
                responseData.setStatusCode(400);
                responseData.setMessage("Failed to create ve.");
                return new ResponseEntity<>(responseData, HttpStatus.BAD_REQUEST);
            }
        } catch (Exception e) {
            // Handle exceptions and set response message
            responseData.setStatusCode(500);
            responseData.setMessage("An error occurred while creating ve: " + e.getMessage());
            return new ResponseEntity<>(responseData, HttpStatus.INTERNAL_SERVER_ERROR);
        }
    }

    @PutMapping("/update_hanhkhach")
    public ResponseEntity<?> updateHanhKhachVe(@Valid @RequestBody VeUpdateHanhKhachDTO veUpdateHanhKhachDTO) {
        ResponseData responseData = new ResponseData();
        try {
            boolean isSuccess = veService.updateHanhKhachVe(veUpdateHanhKhachDTO);

            if (isSuccess) {
                responseData.setStatusCode(200);
                responseData.setData("");
                responseData.setMessage("updated ve");
                return new ResponseEntity<>(responseData, HttpStatus.OK);
            } else {
                responseData.setStatusCode(400);
                responseData.setMessage("Failed to update ve.");
                return new ResponseEntity<>(responseData, HttpStatus.BAD_REQUEST);
            }
        } catch (Exception e) {
            // Handle exceptions and set response message
            responseData.setStatusCode(500);
            responseData.setMessage("An error occurred while creating ve: " + e.getMessage());
            return new ResponseEntity<>(responseData, HttpStatus.INTERNAL_SERVER_ERROR);
        }
    }

}
