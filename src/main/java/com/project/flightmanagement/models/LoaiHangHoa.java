package com.project.flightmanagement.models;

import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Entity
@Table(name = "loaihanghoa")
@Data
@NoArgsConstructor
@AllArgsConstructor
public class LoaiHangHoa {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "idLoaiHangHoa")
    private int idLoaiHangHoa;

    @Column(name = "ten", nullable = false)
    private String ten;

    @Column(name = "gioiHanKg", nullable = false)
    private double gioiHanKg;

    @Column(name = "giaThemMoiKg", nullable = false)
    private double giaThemMoiKg;

    @Enumerated(EnumType.STRING)
    @Column(name = "status", nullable = false)
    private Status status;

    // Enum for status field
    public enum Status {
        ACTIVE,
        INACTIVE
    }
}
