package com.project.flightManagement.Model;

import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.Date;

@Entity
@Table(name = "invalidtoken")
@Data
@NoArgsConstructor
@AllArgsConstructor
public class InvalidToken {
    @Id
    @Column(name = "id_token")
    private String idToken;

    @Column(name = "expiry_date", nullable = false)
    private Date expiryDate; // Thời gian hết hạn của token
}
