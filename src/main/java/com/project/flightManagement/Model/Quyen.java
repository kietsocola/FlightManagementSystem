package com.project.flightManagement.Model;

import com.fasterxml.jackson.annotation.JsonIgnore;
import com.fasterxml.jackson.annotation.JsonManagedReference;
import com.project.flightManagement.Enum.ActiveEnum;
import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.List;

@Entity
@Table(name = "quyen")
@Data
@NoArgsConstructor
@AllArgsConstructor
public class Quyen {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "id_quyen")
    private int idQuyen;

    @Column(name = "tenQuyen", nullable = false)
    private String tenQuyen;

    @JsonIgnore
    @OneToMany(mappedBy = "quyen")
    private List<TaiKhoan> taiKhoanList;

    @JsonIgnore
    @OneToMany(mappedBy = "quyen")
    private List<ChiTietQuyen> chiTietQuyenList;

    @Column(name = "active_status")
    @Enumerated(EnumType.STRING)
    private ActiveEnum trangThaiActive;
}

