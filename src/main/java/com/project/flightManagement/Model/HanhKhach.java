package com.project.flightManagement.Model;

import com.fasterxml.jackson.annotation.JsonIgnore;
import com.project.flightManagement.Enum.ActiveEnum;
import com.project.flightManagement.Enum.GioiTinhEnum;
import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.time.LocalDate;
import java.time.Period;
import java.time.format.DateTimeParseException;
import java.util.List;

@Entity
@Table(name = "hanhkhach")
@Data
@NoArgsConstructor
@AllArgsConstructor
public class HanhKhach {

    // tao xong bang
    // viet tai lieu api
    // luong di code
    // ten ham
    // ten commit code

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "id_hanh_khach")
    private int idHanhKhach;

    @Column(name = "ho_ten", nullable = false)
    private String hoTen;

    @Column(name = "ngay_sinh", nullable = false)
    private String ngaySinh;

    @Column(name = "so_dien_thoai", nullable = false)
    private String soDienThoai;

    @Column(name = "email", nullable = false)
    private String email;
    @Column(name = "cccd")
    private String cccd;
    @Column(name = "ho_chieu")
    private String hoChieu;

    @JsonIgnore
    @OneToMany(mappedBy = "hanhKhach")
    private List<Ve> ve;
    @Column(name = "gioi_tinh")
    @Enumerated(EnumType.STRING)
    private GioiTinhEnum gioiTinhEnum;
    @Column(name = "active_status")
    @Enumerated(EnumType.STRING)
    private ActiveEnum trangThaiActive;


}
