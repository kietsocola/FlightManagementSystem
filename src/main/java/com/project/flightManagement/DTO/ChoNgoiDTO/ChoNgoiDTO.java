package com.project.flightManagement.DTO.ChoNgoiDTO;

import com.project.flightManagement.Enum.ActiveEnum;
import com.project.flightManagement.Model.HangVe;
import com.project.flightManagement.Model.MayBay;
import com.project.flightManagement.Model.Ve;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class ChoNgoiDTO {
    private int idChoNgoi;
    private HangVe hangVe;
    private MayBay mayBay;
    private int rowIndex;
    private char columnIndex;
    private ActiveEnum trangThaiActive;

    public ChoNgoiDTO(HangVe hv, MayBay mb, int rowIndex, char columnIndex, ActiveEnum trangThaiActive) {
        this.hangVe = hv;
        this.mayBay = mb;
        this.rowIndex = rowIndex;
        this.columnIndex = columnIndex;
        this.trangThaiActive = trangThaiActive;
    }
}
