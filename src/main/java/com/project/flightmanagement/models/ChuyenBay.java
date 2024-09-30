package com.project.flightmanagement.models;

import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import java.util.Date;
import java.util.List;

@Entity
@Table(name = "chuyenbay")
@Data
@NoArgsConstructor
@AllArgsConstructor
public class ChuyenBay {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private int idChuyenBay;

    // 1 chuyến bay => nhiều tuyến bay
    @OneToMany(mappedBy = "chuyenBay")
    private List<TuyenBay> tuyenBay;

    // Nhiều chuyến bay => 1 máy bay
    @ManyToOne
    @JoinColumn(name = "idMayBay", nullable = false)
    private MayBay mayBay;

    // 1 chuyến bay => 1 cổng
    @OneToOne
    @JoinColumn(name = "idCong", nullable = false)
    private Cong cong;

    @Column(name = "thoiGianBatDauDuTinh", nullable = false)
    @Temporal(TemporalType.TIMESTAMP)
    private Date thoiGianBatDauDuTinh;

    @Column(name = "iata", length = 3, nullable = false)
    private String iata;

    @Column(name = "icao", length = 4, nullable = false)
    private String icao;

    @Column(name = "thoiGianKetThucDuTinh", nullable = false)
    @Temporal(TemporalType.TIMESTAMP)
    private Date thoiGianKetThucDuTinh;

    @Column(name = "thoiGianBatDauThucTe")
    @Temporal(TemporalType.TIMESTAMP)
    private Date thoiGianBatDauThucTe;

    @Column(name = "thoiGianKetThucThucTe")
    @Temporal(TemporalType.TIMESTAMP)
    private Date thoiGianKetThucThucTe;

    @Column(name = "delay")
    private int delay;

    @Column(name = "ngayBay", nullable = false)
    @Temporal(TemporalType.DATE)
    private Date ngayBay;

    @Column(name = "soGhe", nullable = false)
    private int soGhe;

    @Enumerated(EnumType.STRING)
    @Column(name = "status", nullable = false)
    private Status status;

    // Enum for status field
    public enum Status {
        SCHEDULED,
        DELAYED,
        CANCELED,
        COMPLETED
    }
}
