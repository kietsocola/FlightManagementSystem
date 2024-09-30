package com.project.flightmanagement.models;

import jakarta.persistence.*;

@Entity
@Table(name = "QuocGia")


public class QuocGia {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private int id;

    @Column(name = "ten" , nullable = false)
    private String ten;

    @Enumerated(EnumType.STRING)
    @Column(name = "status" , nullable = false)
    private TrangThai status;

}