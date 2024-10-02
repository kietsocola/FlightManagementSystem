package com.project.flightManagement.Model;

import com.project.flightManagement.Enum.ActiveEnum;
import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.time.LocalDateTime;
import java.util.Date;

@Entity
@Table(name = "taikhoan")
@Data
@NoArgsConstructor
@AllArgsConstructor
public class TaiKhoan {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "id_tai_khoan")
    private int idTaiKhoan;

    @Column(name = "ten_dang_nhap", nullable = false)
    private String tenDangNhap;

    @ManyToOne
    @JoinColumn(name = "id_quyen", nullable = false)
    private Quyen quyen;

    @Column(name = "mat_khau", nullable = false)
    private String matKhau;

    @OneToOne
    @JoinColumn(name = "id_khach_hang", nullable = true)
    private KhachHang khachHang;
    @OneToOne
    @JoinColumn(name = "id_nhan_vien", nullable = true)
    private NhanVien nhanVien;
    @Column(name = "thoi_gian_tao", nullable = false)
    private LocalDateTime thoiGianTao;

    @Column(name = "active_status")
    @Enumerated(EnumType.STRING)
    private ActiveEnum trangThaiActive;
}
