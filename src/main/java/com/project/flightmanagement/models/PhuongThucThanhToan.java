package com.project.flightmanagement.models;

import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Entity
@Table(name = "phuongthucthanhtoan")
@Data
@NoArgsConstructor
@AllArgsConstructor
public class PhuongThucThanhToan {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "idThanhToan")
    private int idThanhToan;

    @Column(name = "tenPhuongThuc", nullable = false)
    private String tenPhuongThuc;

    @Column(name = "moTa")
    private String moTa;

    @Enumerated(EnumType.STRING)
    @Column(name = "status", nullable = false)
    private Status status;

    public enum Status {
        ACTIVE, INACTIVE
    }
}
