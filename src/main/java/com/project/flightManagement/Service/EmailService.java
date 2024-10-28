package com.project.flightManagement.Service;

import com.project.flightManagement.Model.Email;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.mail.javamail.JavaMailSender;

public interface EmailService {

    String sendTextEmail(Email email);
    String sendHtmlEMail(Email email, String resetLink, String userName);
    String sendEmailWithAttachment(Email email);

    String sendHtmlVeOnlineEmail(Email email);
}
