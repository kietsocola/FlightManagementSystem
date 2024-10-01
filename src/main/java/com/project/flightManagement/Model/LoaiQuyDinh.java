package com.project.flightManagement.Model;



import com.project.flightManagement.Enum.ActiveEnum;
import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.List;


@Entity
@Table(name = "loaiquydinh")
@Data
@NoArgsConstructor
@AllArgsConstructor
public class LoaiQuyDinh {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private int idLoaiQuyDinh ;

    @Column(name = "ten_loai_quy_dinh" , nullable = false)
    private String tenLoaiQuyDinh ;

    @Column(name = "mo_ta_loai_quy_dinh" , nullable = false)
    private String moTaLoaiQuyDinh ;

    @OneToMany(mappedBy = "loaiQuyDinh")
    private List<QuyDinh> quyDinhList;

    @Column(name = "active_status")
    @Enumerated(EnumType.STRING)
    private ActiveEnum trangThaiActive;


}