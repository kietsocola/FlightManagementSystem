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
    private String toEmail;
    private String subject;
    private String messageBody;
    private DataSource attachment;
}
