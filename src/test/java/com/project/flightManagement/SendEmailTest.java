package com.project.flightManagement;

import com.project.flightManagement.Util.EmailSenderUtil;
import jakarta.validation.constraints.Email;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.core.io.ClassPathResource;
import org.springframework.core.io.Resource;

import java.io.IOException;

@SpringBootTest
public class SendEmailTest {
    @Autowired
    private EmailSenderUtil emailSenderUtil;

    @Test
    void sendTextEmail() {
        String to = "lockbkbang@gmail.com";
        String subject = "test email Spring boot text";
        String content  = "12344";


        emailSenderUtil.sendTextEmail(to, subject, content);
    }

    @Test
    void sendHtmlEmail() throws IOException {
        String to = "lockbkbang@gmail.com";
        String subject = "test email Spring boot HTML";

        Resource resource = new ClassPathResource("/templates/email/quenMatKhau.html");
        String htmlContent = new String(resource.getInputStream().readAllBytes());
        emailSenderUtil.sendTextEmail(to, subject, htmlContent);
    }
}