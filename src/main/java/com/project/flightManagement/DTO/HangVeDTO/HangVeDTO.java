package com.project.flightManagement.DTO.HangVeDTO;

import com.project.flightManagement.Enum.ActiveEnum;
import com.project.flightManagement.Model.Ve;
import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.List;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class HangVeDTO {
    private int idHangVe;
    private String tenHangVe;
    private String moTa;
    private ActiveEnum trangThaiActive;
}
