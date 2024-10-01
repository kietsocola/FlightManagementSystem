package com.project.flightManagement.Model;

import com.project.flightManagement.Enum.ActiveEnum;
import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.time.LocalDateTime;
import java.util.Date;

@Entity
@Table(name = "danhgia")
@Data
@NoArgsConstructor
@AllArgsConstructor
public class DanhGia {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "id_danh_gia")
    private int idDanhGia;

    @ManyToOne
    @JoinColumn(name = "id_chuyen_bay", nullable = false)
    private ChuyenBay chuyenBay; // Sử dụng entity ChuyenBay thay vì int

    @ManyToOne
    @JoinColumn(name = "id_hanh_khach", nullable = false)
    private HanhKhach khachHang;

    @Column(name = "noi_dung", nullable = false)
    private String noiDung;

    @Column(name = "thoi_gian_tao", nullable = false)
    private LocalDateTime thoiGianTao;

    @Column(name = "active_status")
    @Enumerated(EnumType.STRING)
    private ActiveEnum trangThaiActive;
}
