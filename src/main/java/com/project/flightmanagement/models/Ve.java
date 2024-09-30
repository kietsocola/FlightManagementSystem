package com.project.flightmanagement.models;

import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import java.util.Date;

@Entity
@Table(name = "ve")
@Data
@NoArgsConstructor
@AllArgsConstructor
public class Ve {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "idVe")
    private int idVe;

    @Column(name = "codeVe", nullable = false)
    private String codeVe;

    // Mapping to ChuyenBay entity
    @ManyToOne
    @JoinColumn(name = "idChuyenBay", referencedColumnName = "idChuyenBay", nullable = false)
    private ChuyenBay chuyenBay;

    @Column(name = "giaVe", nullable = false)
    private double giaVe;

    // Mapping to ChoNgoi entity
    @ManyToOne
    @JoinColumn(name = "idChoNgoi", referencedColumnName = "idChoNgoi", nullable = false)
    private ChoNgoi choNgoi;

    // Mapping to LoaiVe entity
    @ManyToOne
    @JoinColumn(name = "idLoaiVe", referencedColumnName = "idLoaiVe", nullable = false)
    private LoaiVe loaiVe;

    @Column(name = "ngayDi", nullable = false)
    @Temporal(TemporalType.DATE)
    private Date ngayDi;

    @Column(name = "ngayVe")
    @Temporal(TemporalType.DATE)
    private Date ngayVe;

    // Mapping to KhachHang entity
    @ManyToOne
    @JoinColumn(name = "idKhachHang", referencedColumnName = "idKhachHang", nullable = false)
    private KhachHang khachHang;

    @Enumerated(EnumType.STRING)
    @Column(name = "status", nullable = false)
    private Status status;

    // Enum for status field
    public enum Status {
        BOOKED,
        CHANGED,
        CANCELED,
        EMPTY
    }
}
