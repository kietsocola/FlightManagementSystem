package com.project.flightmanagement.models;

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
    @Column(name = "idChiTietQuyen")
    private int idChiTietQuyen;

    @ManyToOne
    @JoinColumn(name = "idQuyen", nullable = false)
    private Quyen quyen;

    @ManyToOne
    @JoinColumn(name = "idChucNang", nullable = false)
    private ChucNang chucNang;
}

