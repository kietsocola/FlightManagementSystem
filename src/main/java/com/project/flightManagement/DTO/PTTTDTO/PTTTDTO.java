package com.project.flightManagement.DTO.PTTTDTO;

import com.project.flightManagement.Enum.ActiveEnum;
import jakarta.validation.constraints.NotBlank;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class PTTTDTO {
    private int idPTTT;

    @NotBlank(message = "Tên phương thức thanh toán không được để trống!")
    private String tenPTTT;

    private String moTa;

    private ActiveEnum status;
}
