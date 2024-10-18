package com.project.flightManagement.DTO.QuyDinhDTO;

import com.project.flightManagement.Enum.ActiveEnum;
import com.project.flightManagement.Model.LoaiQuyDinh;
import com.project.flightManagement.Model.NhanVien;
import com.project.flightManagement.Model.QuyDinh;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.time.LocalDateTime;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class QuyDinhDTO {

    @NotNull(message = "ID quy định không được để trống")
    private int idQuyDinh;

    @NotNull(message = "ID loại quy định không được để trống")
    private LoaiQuyDinh LoaiQuyDinh;

    @NotBlank(message = "Tên quy định không được để trống")
    private String tenQuyDinh;

    @NotBlank(message = "Nội dung không được để trống")
    private String noiDung;

    @NotNull(message = "Thời gian tạo không được để trống")
    private LocalDateTime thoiGianTao;

    private LocalDateTime thoiGianCapNhat;

    @NotNull(message = "ID nhân viên không được để trống")
    private NhanVien nhanVien;

    @NotNull(message = "Trạng thái không được để trống")
    private ActiveEnum trangThaiActive;
}
