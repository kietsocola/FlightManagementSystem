package com.project.flightmanagement.models;

import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Entity
@Table(name = "hanhkhach")
@Data
@NoArgsConstructor
@AllArgsConstructor
public class HanhKhach {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "idHanhKhach")
    private int idHanhKhach;

    @Column(name = "hoTen", nullable = false)
    private String hoTen;

    @Column(name = "ngaySinh", nullable = false)
    private String ngaySinh;

    @Column(name = "gioiTinh", nullable = false)
    @Enumerated(EnumType.STRING)
    private GioiTinh gioiTinh;

    @Column(name = "soDienThoai", nullable = false)
    private String soDienThoai;

    @Column(name = "email", nullable = false)
    private String email;

    public enum GioiTinh {
        NAM, NU, KHAC
    }
}

