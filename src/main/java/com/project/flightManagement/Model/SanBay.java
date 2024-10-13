package com.project.flightManagement.Model;

import com.project.flightManagement.Enum.ActiveEnum;
import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Entity
@Table(name = "sanbay")
@Data
@NoArgsConstructor
@AllArgsConstructor
public class SanBay {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "id_san_bay")
    private int idSanBay;

    @Column(name = "ten_san_bay", nullable = false)
    private String tenSanBay;

    @Column(name = "iata_san_bay", length = 3)
    private String iataSanBay;

    @Column(name = "icao_san_bay", length = 4)
    private String icaoSanBay;

    @Column(name = "diaChi")
    private String diaChi;

    // Mapping to ThanhPho entity
    @ManyToOne
    @JoinColumn(name = "id_thanh_pho", referencedColumnName = "id_thanh_pho")
    private ThanhPho thanhPho;
    @Column(name = "active_status")
    @Enumerated(EnumType.STRING)
    private ActiveEnum trangThaiActive;
}

