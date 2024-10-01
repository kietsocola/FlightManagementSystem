package com.project.flightManagement.Model;

import com.project.flightManagement.Enum.ActiveEnum;
import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.List;

@Entity
@Table(name = "hangve")
@Data
@NoArgsConstructor
@AllArgsConstructor
public class HangVe {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "id_hang_ve")
    private int idHangVe;

    @Column(name = "ten_hang_ve", nullable = false)
    private String tenHangVe;

    @Column(name = "mo_ta")
    private String moTa;

    @OneToMany(mappedBy = "hangVe")
    private List<ChoNgoi> choNgoiList;
    @Column(name = "active_status")
    @Enumerated(EnumType.STRING)
    private ActiveEnum trangThaiActive;
}

