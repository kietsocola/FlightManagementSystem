package com.project.flightManagement.Model;

import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.List;

@Entity
@Table(name = "chucnang")
@Data
@NoArgsConstructor
@AllArgsConstructor
public class ChucNang {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "id_chuc_nang")
    private int idChucNang;

    @Column(name="ten_chuc_nang")
    private String tenChucNang;
    @OneToMany(mappedBy = "chucNang")
    private List<ChiTietQuyen> chiTietQuyenList;
}
