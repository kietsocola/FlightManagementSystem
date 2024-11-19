package com.project.flightManagement.DTO.AuthDTO;

import com.project.flightManagement.Enum.GioiTinhEnum;
import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Pattern;
import jakarta.validation.constraints.Size;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.sql.Date;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class SignupDTO {

    @NotBlank(message = "Username is required")
    @Size(min = 5, max = 15, message = "Username must be between 5 and 15 characters")
    private String userName;

    @NotBlank(message = "Password is required")
    @Size(min = 6, message = "Password must be at least 6 characters long")
    private String password;

    @NotBlank(message = "Re-entering password is required")
    @Size(min = 6, message = "Re-entered password must match the password")
    private String rePassword;

    @NotBlank(message = "CCCD is required")
    @Pattern(regexp = "^[0-9]{12}$", message = "CCCD must contain exactly 12 digits and no letters")
    private String cccd;

    @NotBlank(message = "Email is required")
    @Email(message = "Email should be valid")
    private String email;

    @NotNull(message = "Gender is required")
    private GioiTinhEnum gioiTinh;

    @NotBlank(message = "Full name is required")
    @Size(min = 3, max = 50, message = "Full name must be between 3 and 50 characters")
    private String hoTen;

    @NotNull(message = "Date of birth is required")
    private Date ngaySinh;

    @NotBlank(message = "Phone number is required")
    @Pattern(regexp = "^[0-9]{10}$", message = "Phone number must be exactly 10 digits")
    private String soDienThoai;
}