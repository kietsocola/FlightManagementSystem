package com.project.flightmanagement.models;

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
    @Column(name = "idThanhPho")
    private int idThanhPho;

    @Column(name = "tenThanhPho", nullable = false)
    private String tenThanhPho;

    // Mapping to QuocGia entity
    @ManyToOne
    @JoinColumn(name = "idQuocGia", nullable = false)
    private QuocGia quocGia;

    @Enumerated(EnumType.STRING)
    @Column(name = "status", nullable = false)
    private Status status;

    // Enum to represent the status field
    public enum Status {
        ACTIVE,
        INACTIVE
    }
}

