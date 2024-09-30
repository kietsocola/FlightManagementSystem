package com.project.flightmanagement.models;

import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Entity
@Table(name = "chitiethoadon")
@Data
@NoArgsConstructor
@AllArgsConstructor
public class ChiTietHoaDon {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "idChiTietHoaDon")
    private int idChiTietHoaDon; // Mã định danh duy nhất cho chi tiết hóa đơn

    // Mapping to HoaDon entity
    @ManyToOne
    @JoinColumn(name = "idHoaDon", nullable = false)
    private HoaDon hoaDon; // Khóa ngoại, mã của hóa đơn (entity HoaDon)

    // Mapping to HangHoa entity
    @ManyToOne
    @JoinColumn(name = "idHangHoa", nullable = false)
    private HangHoa hangHoa; // Khóa ngoại, mã của hàng hóa (entity HangHoa)

    // Mapping to Ve entity
    @ManyToOne
    @JoinColumn(name = "idVe", nullable = false)
    private Ve ve; // Khóa ngoại, mã của vé (entity Ve)

    @Column(name = "soTien", nullable = false)
    private double soTien; // Số tiền của chi tiết hóa đơn

    @Column(name = "soLuongVe", nullable = false)
    private int soLuongVe; // Số lượng vé trong chi tiết hóa đơn
}
