package com.project.flightManagement.Util;

import jakarta.mail.MessagingException;
import jakarta.mail.internet.MimeMessage;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.mail.SimpleMailMessage;
import org.springframework.mail.javamail.JavaMailSender;
import org.springframework.mail.javamail.MimeMessageHelper;
import org.springframework.stereotype.Component;

@Component
public class EmailSenderUtil {
    @Value("${spring.mail.username}")
    private String email_host;
    @Autowired
    private JavaMailSender javaMailSender;

    public void sendTextEmail(String to, String subject, String content) {
        SimpleMailMessage simpleMailMessage = new SimpleMailMessage();

        simpleMailMessage.setTo(to);
        simpleMailMessage.setSubject(subject);
        simpleMailMessage.setText(content);
        simpleMailMessage.setFrom(email_host);
        System.out.println("email_host: " + email_host);

        try {
            javaMailSender.send(simpleMailMessage);
            System.out.println("Sent email success");
        }catch (Exception e) {
            System.out.println("Sent email fail");
            throw new RuntimeException(e);
        }
    }
    public void sendHtmlEmail(String to, String subject, String content) {

        try {
            MimeMessage mimeMessage = javaMailSender.createMimeMessage();
            MimeMessageHelper mimeMessageHelper = new MimeMessageHelper(mimeMessage, true);
            mimeMessageHelper.setTo(to);
            mimeMessageHelper.setSubject(subject);
            mimeMessageHelper.setText(content, true);

            mimeMessageHelper.setFrom(email_host);

            javaMailSender.send(mimeMessage);
            System.out.println("email_host: " + email_host);
        } catch (MessagingException e) {
            System.out.println("Sent email fail");
            throw new RuntimeException(e);
        }
    }


}
