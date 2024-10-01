package com.project.flightManagement.Model;

import com.project.flightManagement.Enum.ActiveEnum;
import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.List;

@Entity
@Table(name = "phuongthucthanhtoan")
@Data
@NoArgsConstructor
@AllArgsConstructor
public class PhuongThucThanhToan {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "id_phuong_thuc_tt")
    private int idPhuongThucTT;

    @Column(name = "ten_phuong_thuc", nullable = false)
    private String tenPhuongThucTT;
@OneToMany(mappedBy = "phuongThucTT")
    private List<HoaDon> hoaDonList;

    @Column(name = "mo_ta")
    private String moTa;

    @Column(name = "active_status")
    @Enumerated(EnumType.STRING)
    private ActiveEnum trangThaiActive;
}
