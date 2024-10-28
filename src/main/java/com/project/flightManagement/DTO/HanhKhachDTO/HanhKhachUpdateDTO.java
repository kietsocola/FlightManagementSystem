package com.project.flightManagement.DTO.HanhKhachDTO;

import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.Size;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class HanhKhachUpdateDTO {
    @NotBlank(message = "Họ tên không được để trống")
    @Size(min = 2, message = "Họ tên phải có ít nhất 2 ký tự")
    private String hoTen;
}
