package com.project.flightManagement.DTO.TaiKhoanDTO;

import com.project.flightManagement.Enum.ActiveEnum;
import com.project.flightManagement.Model.Quyen;
import jakarta.validation.constraints.NotBlank;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.time.LocalDateTime;
import java.util.Date;

@Data
@NoArgsConstructor
@AllArgsConstructor

public class TaiKhoanDTO {
    private  int idTaiKhoan;

    @NotBlank(message = "Tên đăng nhập không được bỏ trống")
    private  String tenDangNhap;

    private Quyen idQuyen;

    @NotBlank(message = "Mật khẩu không được bỏ trống")
    private  String matKhau;

    private LocalDateTime thoiGianTao;

    private ActiveEnum status;
}
