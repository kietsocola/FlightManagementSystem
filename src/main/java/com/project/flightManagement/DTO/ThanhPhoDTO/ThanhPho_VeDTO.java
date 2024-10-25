package com.project.flightManagement.DTO.ThanhPhoDTO;

import com.project.flightManagement.DTO.QuocGiaDTO.QuocGiaDTO;
import com.project.flightManagement.Enum.ActiveEnum;
import com.project.flightManagement.Model.QuocGia;
import jakarta.validation.Valid;
import jakarta.validation.constraints.NotBlank;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class ThanhPho_VeDTO {
    private int idThanhPho;
    @NotBlank(message = "Không được bỏ trống tên thành ")
    private String tenThanhPho;
    @Valid
    private QuocGiaDTO quocGia;
    private ActiveEnum trangThaiActive;
}