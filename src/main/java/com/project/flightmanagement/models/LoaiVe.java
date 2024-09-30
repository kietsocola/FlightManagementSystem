package com.project.flightmanagement.models;

import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Entity
@Table(name = "loaive")
@Data
@NoArgsConstructor
@AllArgsConstructor
public class LoaiVe {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "idLoaiVe")
    private Integer idLoaiVe;

    @Column(name = "tenLoaiVe", nullable = false)
    private String tenLoaiVe;

    @Column(name = "moTa")
    private String moTa;

    @Enumerated(EnumType.STRING)
    @Column(name = "trangThai", nullable = false)
    private TrangThai trangThai;

    public enum TrangThai {
        ACTION, INACTION
    }
}

