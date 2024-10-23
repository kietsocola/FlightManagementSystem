package com.project.flightManagement.Controller;

import com.project.flightManagement.DTO.VeDTO.VeDTO;
import com.project.flightManagement.Model.Email;
import com.project.flightManagement.Payload.ResponseData;
import com.project.flightManagement.Service.EmailService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;

@Controller
@RequestMapping("/email")
public class EmailController {
    @Autowired
    private EmailService emailService;

    @PostMapping("/text")
    public ResponseEntity<?> sendTextEmail(@RequestBody Email email) {
        ResponseData responseData = new ResponseData();
        try {
            emailService.sendTextEmail(email);
            responseData.setStatusCode(200);
            responseData.setMessage("gui email thanh cong");
            responseData.setData("");
            return new ResponseEntity<>(responseData, HttpStatus.OK);
        } catch (Exception e) {
            responseData.setStatusCode(500);
            responseData.setData("");
            responseData.setMessage("gui email thanh cong");
            System.out.println("gui email that bai: " + e);
            return new ResponseEntity<>(responseData, HttpStatus.INTERNAL_SERVER_ERROR);
        }
    }

    @PostMapping("/html")
    public ResponseEntity<?> sendHtmlEmail(@RequestBody Email email) {

        ResponseData responseData = new ResponseData();
        try {
            emailService.sendHtmlEMail(email);
            responseData.setStatusCode(200);
            responseData.setMessage("gui email thanh cong");
            responseData.setData("");
            return new ResponseEntity<>(responseData, HttpStatus.OK);
        } catch (Exception e) {
            responseData.setStatusCode(500);
            responseData.setData("");
            responseData.setMessage("gui email thanh cong");
            System.out.println("gui email that bai: " + e);
            return new ResponseEntity<>(responseData, HttpStatus.INTERNAL_SERVER_ERROR);
        }
    }

}
