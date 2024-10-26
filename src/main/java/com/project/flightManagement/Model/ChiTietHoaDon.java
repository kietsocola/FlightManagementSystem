package com.project.flightManagement.Model;

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
    @Column(name = "id_chi_tiet_hoa_don")
    private int idChiTietHoaDon;

    @ManyToOne
    @JoinColumn(name = "id_hoa_don", nullable = false)
    private HoaDon hoaDon;

    @ManyToOne
    @JoinColumn(name = "id_hang_hoa", nullable = false)
    private HangHoa hangHoa;

    @ManyToOne
    @JoinColumn(name = "id_ve", nullable = false)
    private Ve ve;

    @Column(name = "so_tien", nullable = false)
    private double soTien;


}
