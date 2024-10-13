package com.project.flightManagement.Model;

import com.fasterxml.jackson.annotation.JsonIgnore;
import com.project.flightManagement.Enum.ActiveEnum;
import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Entity
@Table(name = "thanhpho")
@Data
@NoArgsConstructor
@AllArgsConstructor
public class ThanhPho {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "id_thanh_pho")
    private int idThanhPho;

    @Column(name = "ten_thanh_pho", nullable = false)
    private String tenThanhPho;

    // Mapping to QuocGia entity
    @ManyToOne
    @JsonIgnore
    @JoinColumn(name = "id_quoc_gia")
    private QuocGia quocGia;

    @Column(name = "active_status")
    @Enumerated(EnumType.STRING)
    private ActiveEnum trangThaiActive;
}

