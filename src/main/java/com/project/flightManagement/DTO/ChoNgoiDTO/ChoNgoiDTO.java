package com.project.flightManagement.DTO.ChoNgoiDTO;

import com.project.flightManagement.Enum.ActiveEnum;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class ChoNgoiDTO {

    private int idChoNgoi;
    private int idHangVe; // Thay vì đối tượng HangVe, chỉ cần ID
    private int idMayBay; // Thay vì đối tượng MayBay, chỉ cần ID
    private String viTri;
    private ActiveEnum trangThaiActive;
}
