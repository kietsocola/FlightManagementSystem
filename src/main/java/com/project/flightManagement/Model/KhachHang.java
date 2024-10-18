package com.project.flightManagement.Model;

import com.fasterxml.jackson.annotation.JsonIgnore;
import com.project.flightManagement.Enum.ActiveEnum;
import com.project.flightManagement.Enum.ChucVuEnum;
import com.project.flightManagement.Enum.GioiTinhEnum;
import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.Date;
import java.util.List;

@Entity
@Table(name = "khachhang")
@Data
@NoArgsConstructor
@AllArgsConstructor
public class KhachHang {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "id_khach_hang")
    private int idKhachHang;

    @Column(name = "ho_ten", nullable = false)
    private String hoTen;

    @Column(name = "ngay_sinh", nullable = false)
    private java.sql.Date ngaySinh;


    @Column(name = "email", nullable = false)
    private String email;

    @Column(name = "so_dien_thoai")
    private String soDienThoai;

    @Column(name = "cccd")
    private String cccd;

//     Mối quan hệ 1-1 tùy chọn với Account
    @JsonIgnore
    @OneToOne(mappedBy = "khachHang", optional = true)
    private TaiKhoan taiKhoan;

    @Column(name = "gioi_tinh")
    @Enumerated(EnumType.STRING)
    private GioiTinhEnum gioiTinhEnum;
    @Column(name = "active_status")
    @Enumerated(EnumType.STRING)
    private ActiveEnum trangThaiActive;


}

