package com.project.flightmanagement.models;

import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import java.util.Date;

@Entity
@Table(name = "danhgia")
@Data
@NoArgsConstructor
@AllArgsConstructor
public class DanhGia {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "idDanhGia")
    private int idDanhGia;

    // Mapping to ChuyenBay entity (foreign key)
    @ManyToOne
    @JoinColumn(name = "idChuyenBay", nullable = false)
    private ChuyenBay chuyenBay; // Sử dụng entity ChuyenBay thay vì int

    // Mapping to KhachHang entity (foreign key)
    @ManyToOne
    @JoinColumn(name = "idNguoiDung", nullable = false)
    private KhachHang khachHang; // Sử dụng entity KhachHang thay vì int

    @Column(name = "noiDung", nullable = false)
    private String noiDung;

    @Temporal(TemporalType.TIMESTAMP)
    @Column(name = "thoiGianTao", nullable = false)
    private Date thoiGianTao;

    @Enumerated(EnumType.STRING)
    @Column(name = "status", nullable = false)
    private Status status;

    public enum Status {
        ACTIVE, UNACTIVE
    }
}
