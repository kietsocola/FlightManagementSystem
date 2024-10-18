package com.project.flightManagement.DTO.LoaiQuyDinhDTO;


import com.fasterxml.jackson.annotation.JsonIgnore;
import com.project.flightManagement.Enum.ActiveEnum;
import com.project.flightManagement.Model.QuyDinh;
import jakarta.persistence.*;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.List;


@Data
@NoArgsConstructor
@AllArgsConstructor
public class LoaiQuyDinhDTO {

    private int idLoaiQuyDinh ;

    @NotBlank(message = "Tên quy định không được để trông")
    private String tenLoaiQuyDinh ;

    @NotBlank(message = "Mô tả không được để trông")
    private String moTaLoaiQuyDinh ;

    @NotNull(message = "Trạng thái không  được để trống không được để trông")
    private ActiveEnum trangThaiActive;

}
