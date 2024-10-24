package com.project.flightManagement.DTO.HangVeDTO;

import com.project.flightManagement.Enum.ActiveEnum;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class HangVeDTO {
    private int idHangVe;
    private String tenHangVe;
    private String moTa;
    private ActiveEnum trangThaiActive;
}
