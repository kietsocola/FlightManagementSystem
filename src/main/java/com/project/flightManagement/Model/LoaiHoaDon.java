package com.project.flightManagement.Model;

import com.project.flightManagement.Enum.ActiveEnum;
import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Entity
@Table(name = "loaihoadon")
@Data
@NoArgsConstructor
@AllArgsConstructor
public class LoaiHoaDon {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "id_loai_hoa_don")
    private int idLoaiHoaDon;

    @Column(name = "ten_loai_hoa_don", nullable = false)
    private String tenLoaiHoaDon;

    @Column(name = "mo_ta", nullable = false)
    private String moTaLoaiHoaDon;

    @Column(name = "active_status")
    @Enumerated(EnumType.STRING)
    private ActiveEnum trangThaiActive;
}
