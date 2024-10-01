package com.project.flightManagement.Model;

import com.project.flightManagement.Enum.ActiveEnum;
import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.List;

@Entity
@Table(name = "quocgia")
@Data
@NoArgsConstructor
@AllArgsConstructor
public class QuocGia {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "id_quoc_gia")
    private int idQuocGia;

    @Column(name = "ten_quoc_gia" , nullable = false)
    private String tenQuocGia;

    @OneToMany(mappedBy = "quocGia")
    private List<ThanhPho> thanhPhoList;

    @Column(name = "active_status")
    @Enumerated(EnumType.STRING)
    private ActiveEnum trangThaiActive;


}