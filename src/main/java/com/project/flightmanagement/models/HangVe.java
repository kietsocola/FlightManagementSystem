package com.project.flightmanagement.models;

import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Entity
@Table(name = "hangve")
@Data
@NoArgsConstructor
@AllArgsConstructor
public class HangVe {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "idHangVe")
    private Integer idHangVe;

    @Column(name = "tenHangVe", nullable = false)
    private String tenHangVe;

    @Column(name = "moTa")
    private String moTa;
}

