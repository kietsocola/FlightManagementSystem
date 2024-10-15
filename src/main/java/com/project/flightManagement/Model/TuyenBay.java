package com.project.flightManagement.Model;

import com.project.flightManagement.Enum.ActiveEnum;
import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

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
    private int thoiGianChuyenBay;

    @Column(name = "khoang_cach")
    private int khoangCach;

    @Column(name = "active_status")
    @Enumerated(EnumType.STRING)
    private ActiveEnum status;

    @OneToMany(mappedBy = "tuyenBay")
    private List<ChuyenBay> chuyenBayList;

    // Getter cho mã sân bay bắt đầu
    public int getIdSanBayBatDau() {
        return sanBayBatDau.getIdSanBay();
    }

    // Getter cho mã sân bay kết thúc
    public int getIdSanBayKetThuc() {
        return sanBayKetThuc.getIdSanBay();
    }

}
