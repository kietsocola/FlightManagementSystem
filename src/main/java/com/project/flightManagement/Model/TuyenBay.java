package com.project.flightManagement.Model;

import com.project.flightManagement.Enum.ActiveEnum;
import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.sql.Timestamp;
import java.util.List;

@Entity
@Table(name = "tuyenbay")
@Data
@NoArgsConstructor
@AllArgsConstructor
public class TuyenBay {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY) // Khóa chính tự động tăng
    @Column(name = "id_tuyen_bay")
    private int idTuyenBay;

    @ManyToOne
    @JoinColumn(name = "san_bay_bat_dau", referencedColumnName = "id_san_bay") // Foreign key đến bảng SanBay
    private SanBay sanBayBatDau;

    @ManyToOne
    @JoinColumn(name = "san_bay_ket_thuc", referencedColumnName = "id_san_bay") // Foreign key đến bảng SanBay
    private SanBay sanBayKetThuc;

    @Column(name = "thoi_gian_chuyen_bay")
    private Timestamp thoiGianChuyenBay;

    @Column(name = "khoang_cach")
    private int khoangCach;

    @OneToMany(mappedBy = "tuyenBay")
    private List<ChuyenBay> chuyenBayList;

    // Mối quan hệ 1-1 với Transit là tùy chọn (có thể không có Transit)
    @OneToOne(mappedBy = "tuyenBay", cascade = CascadeType.ALL, fetch = FetchType.LAZY, optional = true)
    private QuaCanh quaCanh; // Tuyến bay có thể không có quá cảnh
    @Column(name = "active_status")
    @Enumerated(EnumType.STRING)
    private ActiveEnum trangThaiActive;
}