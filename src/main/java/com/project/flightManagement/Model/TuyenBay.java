package com.project.flightManagement.Model;

import com.fasterxml.jackson.annotation.JsonIgnore;
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
    @JsonIgnore
    private List<ChuyenBay> chuyenBayList;

    public String convertMinutesToHours(int minutes) {
        int hours = minutes / 60;  // Tính số giờ
        int remainingMinutes = minutes % 60;  // Tính số phút còn lại

        if (remainingMinutes == 0) {
            return hours + "h";  // Chỉ hiển thị giờ nếu không có phút
        } else {
            return hours + "h" + remainingMinutes;  // Hiển thị cả giờ và phút
        }
    }



}
