package com.project.flightmanagement.models;

import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.Date;

@Entity
@Table(name = "nguoidung")
@Data
@NoArgsConstructor
@AllArgsConstructor
public class NguoiDung {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "idNguoiDung")
    private int idNguoiDung;

    @Column(name = "hoTen", nullable = false)
    private String hoTen;

    @Column(name = "ngaySinh", nullable = false)
    @Temporal(TemporalType.DATE)
    private Date ngaySinh;

    @Enumerated(EnumType.STRING)
    @Column(name = "gioiTinh", nullable = false)
    private GioiTinh gioiTinh;

    @Column(name = "email", nullable = false)
    private String email;

    @Column(name = "sodienthoai")
    private String soDienThoai;

    @Column(name = "cccd")
    private String cccd;

    @Enumerated(EnumType.STRING)
    @Column(name = "chucVu")
    private ChucVu chucVu;

    @Column(name = "idTaiKhoan", nullable = false)
    private int idTaiKhoan;

    public enum GioiTinh {
        MALE, FEMALE, OTHER
    }

    public enum ChucVu {
        ADMIN, USER, EMPLOYEE
    }
}

