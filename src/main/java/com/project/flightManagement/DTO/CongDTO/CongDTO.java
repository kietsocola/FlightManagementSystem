package com.project.flightManagement.DTO.CongDTO;

import com.project.flightManagement.Enum.ActiveEnum;
import jakarta.validation.constraints.NotBlank;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class CongDTO {

    private int idCong;

    @NotBlank(message = "Tên cổng không được để trống")  // Ràng buộc không được trống
    private String tenCong;

    private ActiveEnum trangThaiActive;

    private int sanBayId; // ID của SanBay
}

