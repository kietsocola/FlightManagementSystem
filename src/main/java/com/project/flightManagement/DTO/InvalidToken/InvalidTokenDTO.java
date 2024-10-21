package com.project.flightManagement.DTO.InvalidToken;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import java.util.Date;
@Data
@NoArgsConstructor
@AllArgsConstructor
public class InvalidTokenDTO {
    private String idToken;
    private Date expiryDate;
}
