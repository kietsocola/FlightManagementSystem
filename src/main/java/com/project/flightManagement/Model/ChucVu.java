package com.project.flightManagement.Model;

import com.fasterxml.jackson.annotation.JsonIgnore;
import com.project.flightManagement.Enum.ActiveEnum;
import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.List;

@Data
@NoArgsConstructor
@AllArgsConstructor

@Entity
@Table(name = "chucvu")

public class ChucVu {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "idChucVu")
    private int idChucVu ;

    @Column(name = "ten" , nullable = false)
    private String ten ;

    @Column(name = "mota" , nullable = false)
    private String moTa ;

    @Column(name = "active_status")
    @Enumerated(EnumType.STRING)
    private ActiveEnum trangThaiActive;

    @OneToMany(mappedBy = "chucVu")
    @JsonIgnore
    private List<NhanVien> nhanvienList;
}
