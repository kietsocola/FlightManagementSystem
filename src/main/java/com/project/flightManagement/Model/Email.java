package com.project.flightManagement.Model;

import jakarta.activation.DataSource;
import jakarta.persistence.Entity;
import jakarta.persistence.Table;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class Email {
    private String toEmail; // gui cho email nao
    private String subject; // tieu de la gi
    private String messageBody; // noi dung
    private DataSource attachment; // neu co file gui kem
}
