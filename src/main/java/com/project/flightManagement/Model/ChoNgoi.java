package com.project.flightManagement.Model;

import com.project.flightManagement.Enum.ActiveEnum;
import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Entity
@Table(name = "chongoi")
@Data
@NoArgsConstructor
@AllArgsConstructor
public class ChoNgoi {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "id_cho_ngoi")
    private int idChoNgoi;

    @ManyToOne
    @JoinColumn(name = "id_hang_ve",nullable = false)
    private HangVe hangVe;

//    @OneToOne(mappedBy = "choNgoi")
//    private Ve ve;

    // Mapping to MayBay entity
    @ManyToOne
    @JoinColumn(name = "id_may_bay", nullable = false)
    private MayBay mayBay;

    @Column(name = "vi_tri", nullable = false)
    private String viTri;

    @Column(name = "active_status")
    @Enumerated(EnumType.STRING)
    private ActiveEnum trangThaiActive;
}
