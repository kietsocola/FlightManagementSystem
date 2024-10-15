package com.project.flightManagement.DTO.QuocGiaDTO;

import com.project.flightManagement.Enum.ActiveEnum;
import jakarta.validation.constraints.NotBlank;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class QuocGiaDTO {
    private int idQuocGia;
    @NotBlank(message = "Không để trống tên quốc gia")
    private String tenQuocGia;
    private ActiveEnum trangThaiActive;
}