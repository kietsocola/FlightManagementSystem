package com.project.flightmanagement.models;

import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.Date;

@Entity
@Table(name = "taikhoan")
@Data
@NoArgsConstructor
@AllArgsConstructor
public class TaiKhoan {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "idTaiKhoan")
    private int idTaiKhoan;

    @Column(name = "tenDangNhap", nullable = false)
    private String tenDangNhap;

    @ManyToOne
    @JoinColumn(name = "idQuyen", nullable = false)
    private Quyen quyen; // Assuming Quyen is the class representing the permission entity

    @Column(name = "matKhau", nullable = false)
    private String matKhau;

    @Temporal(TemporalType.TIMESTAMP)
    @Column(name = "thoiGianTao", nullable = false)
    private Date thoiGianTao;

    @Enumerated(EnumType.STRING)
    @Column(name = "status", nullable = false)
    private Status status;

    public enum Status {
        ACTIVE, INACTIVE
    }
}
