package com.project.flightManagement.DTO.LoaiHangHoaDTO;

import com.project.flightManagement.Enum.ActiveEnum;
import jakarta.validation.constraints.Min;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Pattern;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class LoaiHangHoaDTO {
    private int idLoaiHangHoa;

    @NotBlank(message = "Tên loại hàng hóa không được để trống")
    @Pattern(regexp = "^[\\p{L}0-9\\s]+$", message = "Tên loại hàng hóa chỉ được chứa chữ cái (có dấu), số và khoảng trắng")
    private String tenLoaiHangHoa;

    @NotNull(message = "Giới hạn kg không được để trống")
    @Min(value = 0, message = "Giới hạn kg không thể âm")
    private double gioiHanKg;

    @NotNull(message = "Giá thêm mới kg không được để trống")
    @Min(value = 0, message = "Giá thêm mới kg không thể âm")
    private double giaThemMoiKg;

    @NotNull(message = "Trạng thái không được để trống")
    private ActiveEnum trangThaiActive;
}

