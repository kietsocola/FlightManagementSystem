package com.project.flightManagement.Model;

import com.fasterxml.jackson.annotation.JsonIgnore;
import com.project.flightManagement.Enum.ActiveEnum; // Giả sử bạn có enum StatusEnum
import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.List;

@Entity
@Table(name = "hangbay")
@Data
@NoArgsConstructor
@AllArgsConstructor
public class HangBay {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "id_hang_bay")
    private int idHangBay;

    @Column(name = "ten_hang_bay", nullable = false)
    private String tenHangBay;

    @Column(name = "iata_hang_bay", length = 3)
    private String iataHangBay;  // Mã IATA thường có độ dài 3 ký tự

    @Column(name = "icao_hang_bay", length = 4)
    private String icaoHangBay;  // Mã ICAO thường có độ dài 4 ký tự

    @Enumerated(EnumType.STRING)
    @Column(name = "active_status")
    private ActiveEnum trangThaiActive;

    @OneToMany(mappedBy = "hangBay")
    @JsonIgnore
    private List<MayBay> mayBayList;
}