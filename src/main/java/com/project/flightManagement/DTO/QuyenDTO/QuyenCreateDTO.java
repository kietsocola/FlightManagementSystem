package com.project.flightManagement.DTO.QuyenDTO;

import com.project.flightManagement.DTO.ChiTietQuyenDTO.ChiTietQuyenDTO;
import com.project.flightManagement.Enum.ActiveEnum;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Size;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.List;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class QuyenCreateDTO {

    @NotNull(message = "Tên quyền không được để trống")
    @Size(min = 1, message = "Tên quyền không được để trống") // Có thể sử dụng Size nếu cần quy định độ dài tối thiểu
    private String tenQuyen;

    @NotNull(message = "Chi tiết quyền không được để trống")
    private List<ChiTietQuyenDTO> chiTietQuyenDTO;

    private ActiveEnum activeEnum;
}