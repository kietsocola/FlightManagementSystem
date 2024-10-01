package com.project.flightManagement.Model;

import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Entity
@Table(name = "chitietquacanh")
@Data
@NoArgsConstructor
@AllArgsConstructor
public class ChiTietQuaCanh {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "id_chi_tiet_qua_canh")
    private int idChiTietQuaCanh;
    @ManyToOne
    @JoinColumn(name = "id_qua_canh", nullable = false)
    private QuaCanh quaCanh;
    @Column(name = "stt_chuyen")
    private int sttChuyen;
    @OneToOne
    private ChuyenBay chuyenBay;
}
