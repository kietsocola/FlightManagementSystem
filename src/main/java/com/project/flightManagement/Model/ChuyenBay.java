package com.project.flightManagement.Model;

import com.fasterxml.jackson.annotation.JsonIgnore;
import com.project.flightManagement.Enum.ActiveEnum;
import com.project.flightManagement.Enum.ChuyenBayEnum;
import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.sql.Timestamp;
import java.time.LocalDateTime;
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
    @Column(name = "id_chuyen_bay")
    private int idChuyenBay;

    @ManyToOne
    @JsonIgnore
    @JoinColumn(name = "id_tuyen_bay", nullable = false)
    private TuyenBay tuyenBay;

//    @OneToMany(mappedBy = "chuyenBay")
//    private List<DanhGia> danhGiaList;

    @ManyToOne
    @JsonIgnore
    @JoinColumn(name = "id_may_bay", nullable = false)
    private MayBay mayBay;
    
    @ManyToOne
    @JsonIgnore
    @JoinColumn(name = "id_cong", nullable = false)
    private Cong cong;

    @Column(name = "thoi_gian_bat_dau_du_tinh", nullable = false)
    private LocalDateTime thoiGianBatDauDuTinh;
    @Column(name = "thoi_gian_ket_thuc_du_tinh", nullable = false)
    private LocalDateTime thoiGianKetThucDuTinh;

    @Column(name = "iata_chuyen_bay", length = 3, nullable = false)
    private String iataChuyenBay;

    @Column(name = "icao_chuyen_bay", length = 4, nullable = false)
    private String icaoChuyenBay;

    @Column(name = "thoi_gian_bat_dau_thuc_te")
    private LocalDateTime thoiGianBatDauThucTe;

    @Column(name = "thoi_gian_ket_thuc_thuc_te")
    private LocalDateTime thoiGianKetThucThucTe;

    @Column(name = "delay")
    private int delay;

    @Column(name = "ngay_bay", nullable = false)
    private Date ngayBay;

    @Column(name = "so_ghe", nullable = false)
    private int soGhe;

    @Column(name = "status")
    @Enumerated(EnumType.STRING)
    private ChuyenBayEnum trangThai;


    @Column(name = "active_status")
    @Enumerated(EnumType.STRING)
    private ActiveEnum trangThaiActive;


}
