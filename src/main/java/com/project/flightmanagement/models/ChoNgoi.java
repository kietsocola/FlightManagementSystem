package com.project.flightmanagement.models;

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
    @Column(name = "idChoNgoi")
    private int idChoNgoi;

    // Mapping to HangVe entity
    @ManyToOne
    @JoinColumn(name = "idHangVe", referencedColumnName = "idHangVe", nullable = false)
    private HangVe hangVe;

    // Mapping to MayBay entity
    @ManyToOne
    @JoinColumn(name = "idMayBay", referencedColumnName = "idMayBay", nullable = false)
    private MayBay mayBay;

    @Column(name = "viTri", nullable = false)
    private String viTri;

    @Enumerated(EnumType.STRING)
    @Column(name = "status", nullable = false)
    private Status status;

    // Enum for status field
    public enum Status {
        ACTIVE,
        INACTIVE
    }
}
