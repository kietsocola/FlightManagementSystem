package com.project.flightmanagement.models;

import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Entity
@Table(name = "HangHoa")
@Data
@NoArgsConstructor
@AllArgsConstructor
public class HangHoa {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private int id;

    // Mapping to LoaiHangHoa entity (foreign key)
    @ManyToOne
    @JoinColumn(name = "idLoaiHangHoa", nullable = false)
    private LoaiHangHoa loaiHangHoa; // Thay vì sử dụng String, giờ đây là đối tượng LoaiHangHoa

    @Column(name = "ten", nullable = false)
    private String ten;

    @Column(name = "taiTrong")
    private float taiTrong;

    @Column(name = "giaPhatSinh")
    private float giaPhatSinh;

}
