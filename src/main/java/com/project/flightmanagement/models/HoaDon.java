package com.project.flightmanagement.models;

import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.Date;

@Entity
@Table(name = "hoadon")
@Data
@NoArgsConstructor
@AllArgsConstructor
public class HoaDon {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "idHoaDon")
    private int idHoaDon; // Mã định danh duy nhất cho hóa đơn

    @ManyToOne
    @JoinColumn(name = "idNguoiDung", nullable = false)
    private KhachHang khachHang; // Khóa ngoại bây giờ là kiểu đối tượng

    @ManyToOne
    @JoinColumn(name = "idNhanVien", nullable = false)
    private NhanVien nhanVien; // Khóa ngoại bây giờ là kiểu đối tượng

    @Column(name = "tongTien", nullable = false)
    private double tongTien; // Tổng tiền của hóa đơn

    @ManyToOne
    @JoinColumn(name = "idLoaiHoaDon", nullable = false)
    private LoaiHoaDon loaiHoaDon; // Khóa ngoại bây giờ là kiểu đối tượng

    @ManyToOne
    @JoinColumn(name = "idThanhToan", nullable = false)
    private PhuongThucThanhToan phuongThucThanhToan; // Khóa ngoại bây giờ là kiểu đối tượng

    @Temporal(TemporalType.TIMESTAMP)
    @Column(name = "thoiGianLap", nullable = false)
    private Date thoiGianLap; // Thời gian lập hóa đơn

    @Enumerated(EnumType.STRING)
    @Column(name = "status", nullable = false)
    private Status status; // Trạng thái của hóa đơn

    public enum Status {
        ACTIVE, UNACTIVE
    }
}
