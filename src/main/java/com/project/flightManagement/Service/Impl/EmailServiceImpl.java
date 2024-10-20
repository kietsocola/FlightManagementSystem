package com.project.flightManagement.Service.Impl;

import com.project.flightManagement.Model.Email;
import com.project.flightManagement.Service.EmailService;
import jakarta.mail.MessagingException;
import jakarta.mail.internet.MimeMessage;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.mail.SimpleMailMessage;
import org.springframework.mail.javamail.JavaMailSender;
import org.springframework.mail.javamail.MimeMessageHelper;
import org.springframework.stereotype.Service;

@Service
public class EmailServiceImpl implements EmailService {

    @Value("${spring.mail.username}")
    private String email_host;
    @Autowired
    private JavaMailSender javaMailSender;
    @Override
    public String sendTextEmail(Email email) {
        SimpleMailMessage simpleMailMessage = new SimpleMailMessage();

        simpleMailMessage.setTo(email.getToEmail());
        simpleMailMessage.setSubject(email.getSubject());
        simpleMailMessage.setText(email.getMessageBody());
        simpleMailMessage.setFrom(email_host);
        try {
            javaMailSender.send(simpleMailMessage);
            return "Email sent text successfuly";
        }catch (Exception e) {
            System.out.println("Sent email fail");
            throw new RuntimeException(e);
        }
    }

    @Override
    public String sendHtmlEMail(Email email) {
        try {
            MimeMessage mimeMessage = javaMailSender.createMimeMessage();
            MimeMessageHelper mimeMessageHelper = new MimeMessageHelper(mimeMessage, true);
            mimeMessageHelper.setTo(email.getToEmail());
            mimeMessageHelper.setSubject(email.getSubject());
            mimeMessageHelper.setText(email.getMessageBody(), true);

            mimeMessageHelper.setFrom(email_host);

            javaMailSender.send(mimeMessage);
            return "Email sent htmls successfuly";
        } catch (MessagingException e) {
            System.out.println("Sent email fail");
            throw new RuntimeException(e);
        }
    }

    @Override
    public String sendEmailAttachmentEmail(Email email) {
        return null;
    }
}
