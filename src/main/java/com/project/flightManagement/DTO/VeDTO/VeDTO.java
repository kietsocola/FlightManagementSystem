package com.project.flightManagement.DTO.VeDTO;

import com.project.flightManagement.DTO.LoaiVeDTO.LoaiVeDTO;
import com.project.flightManagement.Enum.ActiveEnum;
import com.project.flightManagement.Enum.VeEnum;
import com.project.flightManagement.Model.ChoNgoi;
import com.project.flightManagement.Model.ChuyenBay;
import com.project.flightManagement.Model.HanhKhach;
import com.project.flightManagement.Model.LoaiVe;
import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class VeDTO {
    private int idVe;
    private String maVe;
    private int idChuyenBay;
    private double giaVe;
    private int idChoNgoi;
    private int idHanhKhach;
    private LoaiVeDTO loaiVe;
    private ActiveEnum trangThaiActive;
    private VeEnum trangThai;
}
