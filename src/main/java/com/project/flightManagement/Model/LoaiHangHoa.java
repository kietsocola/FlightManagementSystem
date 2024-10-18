package com.project.flightManagement.Model;

import com.project.flightManagement.Enum.ActiveEnum;
import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.List;

@Entity
@Table(name = "loaihanghoa")
@Data
@NoArgsConstructor
@AllArgsConstructor
public class LoaiHangHoa {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "id_loai_hang_hoa")
    private int idLoaiHangHoa;

    @Column(name = "ten_loai_hang_hoa", nullable = false)
    private String tenLoaiHangHoa;

//    @OneToMany(mappedBy = "loaiHangHoa")
//    private List<HangHoa> hangHoaList;

    @Column(name = "gioi_han_kg", nullable = false)
    private double gioiHanKg;

    @Column(name = "gia_them_moi_kg", nullable = false)
    private double giaThemMoiKg;

    @Column(name = "active_status")
    @Enumerated(EnumType.STRING)
    private ActiveEnum trangThaiActive;

}
