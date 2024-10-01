package com.project.flightManagement.Model;

import com.project.flightManagement.Enum.ActiveEnum;
import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.List;

@Entity
@Table(name = "quacanh")
@Data
@NoArgsConstructor
@AllArgsConstructor
public class QuaCanh {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "id_qua_canh")
    private int idQuaCanh;

    @OneToOne
    @JoinColumn(name = "flight_id", nullable = false)
    private TuyenBay tuyenBay;

    @OneToMany(mappedBy = "quaCanh")
    private List<ChiTietQuaCanh> chiTietQuaCanhList;

    @ManyToOne
    @JoinColumn(name = "san_bay_bat_dau", referencedColumnName = "id_san_bay") // Foreign key đến bảng SanBay
    private SanBay sanBayBatDau;
    @Column(name = "active_status")
    @Enumerated(EnumType.STRING)
    private ActiveEnum trangThaiActive;
}
