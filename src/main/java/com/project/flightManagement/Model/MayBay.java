package com.project.flightManagement.Model;

import com.project.flightManagement.Enum.ActiveEnum; // Giả sử bạn có enum StatusEnum
import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.List;

@Entity
@Table(name = "maybay")
@Data
@NoArgsConstructor
@AllArgsConstructor
public class MayBay {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "id_may_bay")
    private int idMayBay;

    @Column(name = "ten_may_bay", nullable = false)
    private String tenMayBay;

    @ManyToOne
    @JoinColumn(name = "id_hang_bay", referencedColumnName = "id_hang_bay") // Khóa ngoại đến bảng HangBay
    private HangBay hangBay;

    @Column(name = "icao_may_bay", length = 4)
    private String icaoMayBay;  // Mã ICAO có độ dài 4 ký tự

    @OneToMany(mappedBy = "mayBay")
    private List<ChuyenBay> chuyenBayList;

    @Column(name = "so_luong_ghe")
    private int soLuongGhe;

    @Column(name = "so_hieu")
    private String soHieu;

    @Column(name = "nam_san_xuat")
    private int namSanXuat;
    @OneToMany(mappedBy = "mayBay")
    private List<ChoNgoi> choNgoiList;

    @Column(name = "active_status")
    @Enumerated(EnumType.STRING)
    private ActiveEnum trangThaiActive;
}