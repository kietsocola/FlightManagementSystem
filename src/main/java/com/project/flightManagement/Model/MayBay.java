package com.project.flightManagement.Model;

import com.fasterxml.jackson.annotation.JsonIgnore;
import com.project.flightManagement.Enum.ActiveEnum; // Giả sử bạn có enum StatusEnum
import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.List;
import java.util.Optional;

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
    @JsonIgnore
    @JoinColumn(name = "id_hang_bay", referencedColumnName = "id_hang_bay") // Khóa ngoại đến bảng HangBay
    private HangBay hangBay;

    @Column(name = "icao_may_bay", length = 6)
    private String icaoMayBay;  // Mã ICAO có độ dài 4 ký tự

    @OneToMany(mappedBy = "mayBay")
    @JsonIgnore
    private List<ChuyenBay> chuyenBayList;

    @Column(name = "so_hang_ghe_thuong", nullable = false)
    private String soHangGheThuong;
    @Column(name = "so_hang_ghe_vip", nullable = false)
    private String soHangGheVip;

    @Column(name = "so_cot_ghe_thuong", nullable = false)
    private int soCotGheThuong;
    @Column(name = "so_cot_ghe_vip", nullable = false)
    private int soCotGheVip;

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