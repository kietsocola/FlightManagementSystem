package com.project.flightManagement.Model;

import com.project.flightManagement.Enum.ActiveEnum;
import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.time.LocalDateTime;
import java.util.Date;
import java.util.List;

@Entity
@Table(name = "quydinh")
@Data
@NoArgsConstructor
@AllArgsConstructor
public class QuyDinh {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "id_quy_dinh")
    private int idQuyDinh;

    @ManyToOne
    @JoinColumn(name = "idLoaiQuyDinh", referencedColumnName = "idLoaiQuyDinh", nullable = false)
    private LoaiQuyDinh loaiQuyDinh;

    @Column(name = "ten_quy_dinh", nullable = false)
    private String tenQuyDinh;

    @Column(name = "noi_dung", nullable = false)
    private String noiDung;

    @Column(name = "thoi_gian_tao", nullable = false)
    private LocalDateTime thoiGianTao;

    @Column(name = "thoi_gian_cap_nhat")
    private LocalDateTime thoiGianCapNhat;

    @ManyToOne
    @JoinColumn(name = "id_nhan_vien", nullable = false)
    private NhanVien nhanVien;

    @Column(name = "active_status")
    @Enumerated(EnumType.STRING)
    private ActiveEnum trangThaiActive;
}