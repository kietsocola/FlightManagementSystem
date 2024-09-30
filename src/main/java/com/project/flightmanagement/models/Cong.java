package com.project.flightmanagement.models;

import jakarta.persistence.*;
import lombok.*;

enum TrangThai { Active, Inactive }

@Entity
@Table(name = "cong")
@Data
@NoArgsConstructor
@AllArgsConstructor
public class Cong {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private int id;

    // Mapping to SanBay entity (foreign key)
    @ManyToOne
    @JoinColumn(name = "idSanBay", nullable = false)
    private SanBay sanBay; // Thay vì sử dụng int, giờ đây là đối tượng SanBay

    @Column(name = "tenCong", nullable = false)
    private String tenCong;

    @Enumerated(EnumType.STRING)
    @Column(name = "status", nullable = false)
    private TrangThai status;
}
