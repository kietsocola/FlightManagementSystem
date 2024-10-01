package com.project.flightManagement.Model;

import com.project.flightManagement.Enum.ActiveEnum;
import jakarta.persistence.*;
import lombok.*;

@Entity
@Table(name = "cong")
@Data
@NoArgsConstructor
@AllArgsConstructor
public class Cong {

    @Id
    @Column(name = "id_cong")
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private int idCong;

    @ManyToOne
    @JoinColumn(name = "id_san_bay", nullable = false)
    private SanBay sanBay;

    @Column(name = "ten_cong", nullable = false)
    private String tenCong;

    @Column(name = "active_status")
    @Enumerated(EnumType.STRING)
    private ActiveEnum trangThaiActive;
}
