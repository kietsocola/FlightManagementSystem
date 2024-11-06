package com.project.flightManagement.DTO.ChoNgoiDTO;

import com.project.flightManagement.Enum.ActiveEnum;
import com.project.flightManagement.Enum.VeEnum;
import com.project.flightManagement.Model.HangVe;
import com.project.flightManagement.Model.MayBay;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
@Data
@AllArgsConstructor
@NoArgsConstructor
public class ChoNgoi_VeReturnDTO {
        private int idChoNgoi;
        private int rowIndex;
        private char columnIndex;
        private ActiveEnum trangThaiActive;
}
