package com.project.flightmanagement.models;

import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import java.util.Date;
import java.util.List;

@Entity
@Table(name = "quydinh")
@Data
@NoArgsConstructor
@AllArgsConstructor
public class QuyDinh {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "idQuyDinh")
    private int idQuyDinh;

    // Mapping to LoaiQuyDinh entity
    @OneToMany(mappedBy = "quyDinh")
    private List<LoaiQuyDinh> loaiQuyDinh;

    @Column(name = "tenQuyDinh", nullable = false)
    private String tenQuyDinh;

    @Column(name = "noiDung", nullable = false)
    private String noiDung;

    @Column(name = "ngayTao", nullable = false)
    @Temporal(TemporalType.DATE)
    private Date ngayTao;

    @Column(name = "ngayUpdate")
    @Temporal(TemporalType.DATE)
    private Date ngayUpdate;

    @Enumerated(EnumType.STRING)
    @Column(name = "status", nullable = false)
    private Status status;

    // Enum to represent the status field
    public enum Status {
        ACTIVE,
        INACTIVE
    }
}

