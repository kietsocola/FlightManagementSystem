package com.project.flightManagement.DTO.HangHoaDTO;

import com.project.flightManagement.Enum.ActiveEnum;
import jakarta.validation.constraints.Min;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Pattern;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class HangHoaDTO {

    private int idHangHoa;

//    @NotNull(message = "Loại hàng hóa không được để trống")
    private int idLoaiHangHoa;

    private String maHangHoa;

    @NotBlank(message = "Tên hàng hóa không được để trống")
    @Pattern(regexp = "^[\\p{L}0-9\\s]+$", message = "Tên hàng hóa chỉ được chứa chữ cái (có dấu), số và khoảng trắng")
    private String tenHangHoa;


    @NotNull(message = "Tải trọng không được để trống")
    @Min(value = 0, message = "Tải trọng không thể âm")
    private double taiTrong;

    @NotNull(message = "Giá phát sinh không được để trống")
    @Min(value = 0, message = "Giá phát sinh không thể âm")
    private double giaPhatSinh;

    @NotNull(message = "Trạng thái không được để trống")
    private ActiveEnum trangThaiActive;
}
