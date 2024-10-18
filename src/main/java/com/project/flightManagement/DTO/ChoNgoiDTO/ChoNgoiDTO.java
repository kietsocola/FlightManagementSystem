package com.project.flightManagement.DTO.ChoNgoiDTO;

import com.project.flightManagement.Enum.ActiveEnum;
import com.project.flightManagement.Enum.VeEnum;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class ChoNgoiDTO {
    private int idChoNgoi;
    private int idVe;
    private String row;
    private int column;
    private VeEnum trangThaiChoNgoi;
}
