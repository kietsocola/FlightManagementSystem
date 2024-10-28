package com.project.flightManagement.Model;

import com.project.flightManagement.Enum.ActiveEnum;
import com.project.flightManagement.Enum.ChuyenBayEnum;
import com.project.flightManagement.Enum.VeEnum;
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
    @Column(name = "id_ve")
    private int idVe;

    @Column(name = "ma_ve", nullable = false)
    private String maVe;

    @ManyToOne
    @JoinColumn(name = "id_chuyen_bay", nullable = false)
    private ChuyenBay chuyenBay;

    @Column(name = "gia_ve", nullable = false)
    private double giaVe;

    @OneToOne(optional = false)
    @JoinColumn(name = "id_cho_ngoi", nullable = false)
    private ChoNgoi choNgoi;

    @ManyToOne
    @JoinColumn(name = "id_hanh_khach", nullable = true)
    private HanhKhach hanhKhach;

    @ManyToOne
    @JoinColumn(name = "idLoaiVe", referencedColumnName = "id_loai_ve", nullable = false)
    private LoaiVe loaiVe;

    @ManyToOne
    @JoinColumn(name = "id_hang_ve")
    private HangVe hangVe;

    @Column(name = "active_status")
    @Enumerated(EnumType.STRING)
    private ActiveEnum trangThaiActive;

    @Column(name = "status")
    @Enumerated(EnumType.STRING)
    private VeEnum trangThai;
}
