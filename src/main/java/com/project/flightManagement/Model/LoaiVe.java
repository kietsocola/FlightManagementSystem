package com.project.flightManagement.Model;

import com.fasterxml.jackson.annotation.JsonIgnore;
import com.project.flightManagement.Enum.ActiveEnum;
import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.List;

@Entity
@Table(name = "loaive")
@Data
@NoArgsConstructor
@AllArgsConstructor
public class LoaiVe {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "id_loai_ve")
    private Integer idLoaiVe;

    @Column(name = "ten_loai_ve", nullable = false)
    private String tenLoaiVe;

    @Column(name = "mo_ta")
    private String moTa;

    @JsonIgnore
    @OneToMany(mappedBy = "loaiVe")
    private List<Ve> listVe;

    @Column(name = "active_status")
    @Enumerated(EnumType.STRING)
    private ActiveEnum trangThaiActive;
}

