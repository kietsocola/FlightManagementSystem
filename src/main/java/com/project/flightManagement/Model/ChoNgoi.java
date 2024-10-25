package com.project.flightManagement.Model;

import com.fasterxml.jackson.annotation.JsonIgnore;
import com.project.flightManagement.Enum.ActiveEnum;
import jakarta.persistence.*;
import jakarta.validation.constraints.Pattern;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Entity
@Table(name = "chongoi")
@Data
@NoArgsConstructor
@AllArgsConstructor
public class ChoNgoi {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "id_cho_ngoi")
    private int idChoNgoi;

    @ManyToOne
    @JoinColumn(name = "id_hang_ve",referencedColumnName = "id_hang_ve")
    private HangVe hangVe;

//    @OneToOne(mappedBy = "choNgoi")
//    @JsonIgnore
//    private Ve ve;

    // Mapping to MayBay entity
    @ManyToOne
    @JoinColumn(name = "id_may_bay", referencedColumnName = "id_may_bay")
    private MayBay mayBay;

    @Column(name = "row_index", nullable = false)
    private char rowIndex;

    @Column(name = "column_index", nullable = false)
    @Pattern(regexp = "[A-Z]{1}$", message = "Kí tự đầu tiên của chỗ ngồi phải là chữ hoa")
    private int columnIndex;

    @Column(name = "active_status")
    @Enumerated(EnumType.STRING)
    private ActiveEnum trangThaiActive;
}
