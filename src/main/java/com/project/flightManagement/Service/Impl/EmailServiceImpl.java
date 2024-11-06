package com.project.flightManagement.Service.Impl;

import com.project.flightManagement.Config.ThymeleafConfig;
import com.project.flightManagement.DTO.VeDTO.VeDTO;
import com.project.flightManagement.Model.Email;
import com.project.flightManagement.Model.TuyenBay;
import com.project.flightManagement.Service.EmailService;
import jakarta.mail.MessagingException;
import jakarta.mail.internet.MimeMessage;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.mail.SimpleMailMessage;
import org.springframework.mail.javamail.JavaMailSender;
import org.springframework.mail.javamail.MimeMessageHelper;
import org.springframework.scheduling.annotation.Async;
import org.springframework.stereotype.Service;
import org.thymeleaf.TemplateEngine;
import org.thymeleaf.context.Context;

import java.time.LocalDate;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;

@Service
public class EmailServiceImpl implements EmailService {

    @Value("${spring.mail.username}")
    private String email_host;
    @Autowired
    private JavaMailSender javaMailSender;
    @Autowired
    private TemplateEngine templateEngine;
    @Override
    @Async
    public void sendTextEmail(Email email) {
        SimpleMailMessage simpleMailMessage = new SimpleMailMessage();

        simpleMailMessage.setTo(email.getToEmail());
        simpleMailMessage.setSubject(email.getSubject());
        simpleMailMessage.setText(email.getMessageBody());
        simpleMailMessage.setFrom(email_host);
        try {
            javaMailSender.send(simpleMailMessage);
        }catch (Exception e) {
            System.out.println("Sent email fail");
            throw new RuntimeException(e);
        }
    }

    @Override
    @Async
    public void sendHtmlEMail(Email email, String resetLink, String userName) {
        try { // ham nay la gui html cho chuc nang quen mat khau
            // Create Thymeleaf context and add variables
            Context context = new Context();
            context.setVariable("name", userName);
            context.setVariable("resetLink", resetLink);

            // Process HTML template with Thymeleaf
            String emailContent = templateEngine.process("email/quenMatKhau", context);

            MimeMessage mimeMessage = javaMailSender.createMimeMessage();
            MimeMessageHelper mimeMessageHelper = new MimeMessageHelper(mimeMessage, true);
            mimeMessageHelper.setTo(email.getToEmail());
            mimeMessageHelper.setSubject(email.getSubject());

            // Set the processed HTML content
            mimeMessageHelper.setText(emailContent, true);
            mimeMessageHelper.setFrom(email_host);

            javaMailSender.send(mimeMessage);
        } catch (Exception e) {
            System.out.println("Failed to send email with Thymeleaf HTML");
            throw new RuntimeException(e);
        }
    }

    @Override
    @Async
    public void sendEmailWithAttachment(Email email) { // co the khong can ham nay -> du
        try {
            MimeMessage mimeMessage = javaMailSender.createMimeMessage();
            MimeMessageHelper mimeMessageHelper = new MimeMessageHelper(mimeMessage, true);

            mimeMessageHelper.setTo(email.getToEmail());
            mimeMessageHelper.setSubject(email.getSubject());
            mimeMessageHelper.setText(email.getMessageBody(), true);
            mimeMessageHelper.setFrom(email_host);

            // Adding attachment
            mimeMessageHelper.addAttachment("Attachment", email.getAttachment());

            javaMailSender.send(mimeMessage);
        } catch (MessagingException e) {
            System.out.println("Failed to send email with attachment");
            throw new RuntimeException(e);
        }
    }

    @Override
    @Async
    public void sendHtmlVeOnlineEmail(Email email, VeDTO veDTO) { // ham gui ve online -> thieu file pdf gui kem theo
        try {
            TuyenBay tuyenBay = new TuyenBay();
            Context context = new Context(); // dat cac bien de thay doi noi dung html o day
            context.setVariable("passengerName", veDTO.getHanhKhach().getHoTen());
            context.setVariable("bookingCode", veDTO.getChoNgoi().getIdChoNgoi());
            context.setVariable("ticketCode", veDTO.getMaVe());
            // Lấy ngày hiện tại
            LocalDate today = LocalDate.now();

            // Định dạng ngày thành "dd/MM/yyyy" (tuỳ chỉnh định dạng nếu cần)
            DateTimeFormatter formatter = DateTimeFormatter.ofPattern("dd/MM/yyyy");
            String formattedDate = today.format(formatter);
// Parse chuỗi vào LocalDateTime


            context.setVariable("issueDate", formattedDate);
            context.setVariable("departureCity", veDTO.getChuyenBay().getTuyenBay().getSanBayBatDau().getTenSanBay());
            context.setVariable("arrivalCity", veDTO.getChuyenBay().getTuyenBay().getSanBayKetThuc().getTenSanBay());
            context.setVariable("nameDepartCity", veDTO.getChuyenBay().getTuyenBay().getSanBayBatDau().getThanhPho().getTenThanhPho() + " ");
            context.setVariable("nameArriveCity", veDTO.getChuyenBay().getTuyenBay().getSanBayKetThuc().getThanhPho().getTenThanhPho() + " ");
            context.setVariable("flightNumber", veDTO.getChuyenBay().getMayBay().getSoHieu());
            String timeDepart = formatFromDateTimeToTime(String.valueOf(veDTO.getChuyenBay().getThoiGianBatDauDuTinh()));
            String timeArrive = formatFromDateTimeToTime(String.valueOf(veDTO.getChuyenBay().getThoiGianKetThucDuTinh()));
            context.setVariable("departureTime", timeDepart);
            context.setVariable("arrivalTime", timeArrive);
            context.setVariable("departureGate", veDTO.getChuyenBay().getCong().getTenCong());
            String departDate = formatFromDateTimeToDate(String.valueOf(veDTO.getChuyenBay().getThoiGianBatDauDuTinh()));
            context.setVariable("departureDate", departDate);
            String dateArrive = formatFromDateTimeToDate(String.valueOf(veDTO.getChuyenBay().getThoiGianKetThucDuTinh()));
            context.setVariable("arriveDate", dateArrive);
            context.setVariable("seatClass", veDTO.getHangVe().getTenHangVe());
            context.setVariable("carrier", "BAMBOO AIRLINE");
            context.setVariable("flightDuration", tuyenBay.convertMinutesToHours(veDTO.getChuyenBay().getTuyenBay().getThoiGianChuyenBay()));
            context.setVariable("issuingCarrier", "BAMBOO AIRLINE");
            // Process HTML template with Thymeleaf
            String emailContent = templateEngine.process("email/veOnline", context);

            MimeMessage mimeMessage = javaMailSender.createMimeMessage();
            MimeMessageHelper mimeMessageHelper = new MimeMessageHelper(mimeMessage, true);
            mimeMessageHelper.setTo(email.getToEmail());
            mimeMessageHelper.setSubject(email.getSubject());

            // Set the processed HTML content
            mimeMessageHelper.setText(emailContent, true);
            mimeMessageHelper.setFrom(email_host);

            javaMailSender.send(mimeMessage);
        } catch (Exception e) {
            System.out.println("Failed to send email with Thymeleaf HTML");
            throw new RuntimeException(e);
        }
    }
    private String formatFromDateTimeToTime(String dateTimeString) {
        LocalDateTime dateTime = LocalDateTime.parse(dateTimeString);
        // Định dạng lại để lấy giờ và phút, thêm "h" ở giữa
        DateTimeFormatter timeFormatter = DateTimeFormatter.ofPattern("HH'h'mm");
        String timeOnly = dateTime.format(timeFormatter);
        return timeOnly;
    }

    private String formatFromDateTimeToDate(String dateTimeString) {
        LocalDateTime dateTime = LocalDateTime.parse(dateTimeString);
        // Định dạng để lấy ngày và tháng
        DateTimeFormatter dayMonthFormatter = DateTimeFormatter.ofPattern("dd/MM/YYYY");
        String dayMonthOnly = dateTime.format(dayMonthFormatter);
        return dayMonthOnly;
    }
}


