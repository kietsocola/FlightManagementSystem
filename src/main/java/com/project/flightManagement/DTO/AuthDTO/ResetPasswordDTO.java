package com.project.flightManagement.DTO.AuthDTO;

import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.Size;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class ResetPasswordDTO {
    @NotBlank(message = "Token không được để trống")
    private String refreshPasswordToken;

    @NotBlank(message = "Mật khẩu mới không được để trống")
    @Size(min = 6, message = "Mật khẩu mới phải có ít nhất 6 ký tự")
    private String newPassword;

    @NotBlank(message = "Mật khẩu nhập lại không được để trống")
    @Size(min = 6, message = "Mật khẩu nhập lại phải có ít nhất 6 ký tự")
    private String reNewPassword;
}
