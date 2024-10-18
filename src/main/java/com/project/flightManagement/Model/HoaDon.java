package com.project.flightManagement.Model;

import com.fasterxml.jackson.annotation.JsonIgnore;
import com.project.flightManagement.Enum.ActiveEnum;
import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.time.LocalDateTime;
import java.util.Date;
import java.util.List;

@Entity
@Table(name = "hoadon")
@Data
@NoArgsConstructor
@AllArgsConstructor
public class HoaDon {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "id_hoa_don")
    private int idHoaDon; // Mã định danh duy nhất cho hóa đơn

    @ManyToOne
    @JoinColumn(name = "id_khach_hang", nullable = false)
    private KhachHang khachHang;

    @ManyToOne
    @JoinColumn(name = "id_nhan_vien", nullable = false)
    private NhanVien nhanVien;

    @Column(name = "tong_tien", nullable = false)
    private double tongTien; // Tổng tiền của hóa đơn

    @Column(name = "so_luong_ve", nullable = false)
    private int soLuongVe;

    @ManyToOne
    @JoinColumn(name = "id_loai_hoa_don", nullable = false)
    private LoaiHoaDon loaiHoaDon; // Khóa ngoại bây giờ là kiểu đối tượng

    @ManyToOne
    @JoinColumn(name = "id_phuong_thuc", nullable = false)
    private PhuongThucThanhToan phuongThucTT;

    @Column(name = "thoi_gian_lap", nullable = false)
    private LocalDateTime thoiGianLap;

    @JsonIgnore
    @OneToMany(mappedBy = "hoaDon")
    private List<ChiTietHoaDon> chiTietHoaDonList;

    @Column(name = "active_status")
    @Enumerated(EnumType.STRING)
    private ActiveEnum trangThaiActive;

}
