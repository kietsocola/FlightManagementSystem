package com.project.flightManagement.DTO.CongDTO;

import com.project.flightManagement.Enum.ActiveEnum;
import jakarta.validation.constraints.NotBlank;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class Cong_VeDTO {
    private int idCong;
    private String tenCong;
    private ActiveEnum trangThaiActive;
}
