package com.project.flightManagement.Model;

import com.project.flightManagement.Enum.ActiveEnum;
import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Entity
@Table(name = "hanghoa")
@Data
@NoArgsConstructor
@AllArgsConstructor
public class HangHoa {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "id_hang_hoa", nullable = false)
    private int idHangHoa;

    @ManyToOne
    @JoinColumn(name = "id_loai_hang_hoa", nullable = false)
    private LoaiHangHoa loaiHangHoa;

    @Column(name = "ten_hang_hoa", nullable = false)
    private String tenHangHoa;

    @Column(name = "tai_trong")
    private double taiTrong;

    @Column(name = "gia_phat_sinh")
    private double giaPhatSinh;
//    @OneToOne(mappedBy = "hangHoa")
//    private ChiTietHoaDon chiTietHoaDon;

    @Column(name = "active_status")
    @Enumerated(EnumType.STRING)
    private ActiveEnum trangThaiActive;

}
