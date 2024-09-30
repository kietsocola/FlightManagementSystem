package com.project.flightmanagement.models;



import jakarta.persistence.*;
import jdk.jfr.DataAmount;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Entity
@Table(name = "loaiQuyDinh")
@Data
@NoArgsConstructor
@AllArgsConstructor


public class LoaiQuyDinh {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private int id ;

    @Column(name = "ten" , nullable = false)
    private String ten ;


    @Enumerated(EnumType.STRING)
    @Column(name = "status" , nullable = false)
    private TrangThai status ;


}