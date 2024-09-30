package com.project.flightmanagement.models;

import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import java.util.Date;

@Entity
@Table(name = "sanbay")
@Data
@NoArgsConstructor
@AllArgsConstructor
public class SanBay {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "idSanBay")
    private int idSanBay;

    @Column(name = "tenSanBay", nullable = false)
    private String tenSanBay;

    @Column(name = "iata", length = 3)
    private String iata;

    @Column(name = "icao", length = 4)
    private String icao;

    @Column(name = "maSanBay", nullable = false)
    private String maSanBay;

    @Column(name = "diaChi")
    private String diaChi;

    // Mapping to ThanhPho entity
    @OneToOne
    @JoinColumn(name = "idThanhPho", nullable = false)
    private ThanhPho thanhPho;

    @Enumerated(EnumType.STRING)
    @Column(name = "status", nullable = false)
    private Status status;

    // Enum to represent the status field
    public enum Status {
        ACTIVE,
        INACTIVE
    }
}

