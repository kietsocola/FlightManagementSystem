package com.project.flightManagement.Service;

import com.project.flightManagement.Model.Email;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.mail.javamail.JavaMailSender;

public interface EmailService {

    void sendTextEmail(Email email);
    void sendHtmlEMail(Email email, String resetLink, String userName);
    void sendEmailWithAttachment(Email email);

    void sendHtmlVeOnlineEmail(Email email);
}
