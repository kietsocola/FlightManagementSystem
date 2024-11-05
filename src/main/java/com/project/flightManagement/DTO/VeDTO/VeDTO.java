package com.project.flightManagement.DTO.VeDTO;

import com.project.flightManagement.DTO.ChoNgoiDTO.ChoNgoi_VeReturnDTO;
import com.project.flightManagement.DTO.ChuyenBayDTO.ChuyenBay_VeDTO;
import com.project.flightManagement.DTO.HangVeDTO.HangVeDTO;
import com.project.flightManagement.DTO.HanhKhachDTO.HanhKhach_VeDTO;
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
    private ChuyenBay_VeDTO chuyenBay;
    private double giaVe;
    private HangVeDTO hangVe;
    private ChoNgoi_VeReturnDTO choNgoi;
    private HanhKhach_VeDTO hanhKhach;
    private LoaiVeDTO loaiVe;
    private ActiveEnum trangThaiActive;
    private VeEnum trangThai;
}
