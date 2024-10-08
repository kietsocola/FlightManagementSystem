package com.project.flightManagement.Model;

import com.project.flightManagement.Enum.ActiveEnum;
import com.project.flightManagement.Enum.ChiTietQuyenActionEnum;
import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Entity
@Table(name = "chitietquyen")
@Data
@NoArgsConstructor
@AllArgsConstructor
public class ChiTietQuyen {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "id_chi_tiet_quyen")
    private int idChiTietQuyen;

    @ManyToOne
    @JoinColumn(name = "id_quyen", nullable = false)
    private Quyen quyen;

    @ManyToOne
    @JoinColumn(name = "id_chuc_nang", nullable = false)
    private ChucNang chucNang;

    @Column(name = "hanh_dong")
    @Enumerated(EnumType.STRING)
    private ChiTietQuyenActionEnum hanhDong;

    @Column(name = "active_status")
    @Enumerated(EnumType.STRING)
    private ActiveEnum trangThaiActive;
}

