package com.project.flightManagement.DTO.ChuyenBayDTO;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import java.time.LocalDateTime;
import java.sql.Date;
@Data
@NoArgsConstructor
@AllArgsConstructor
public class ChuyenBayDTO {
    private int idChuyenBay;
    private int idTuyenBay; // Thay vì trả cả đối tượng TuyenBay, chỉ trả ID của nó
    private int idMayBay;
    private int idCong;
    private LocalDateTime thoiGianBatDauDuTinh;
    private LocalDateTime thoiGianKetThucDuTinh;
    private String iataChuyenBay;
    private String icaoChuyenBay;
    private LocalDateTime thoiGianBatDauThucTe;
    private LocalDateTime thoiGianKetThucThucTe;
    private int delay;
    private Date ngayBay;
    private int soGhe;
    private String trangThai; // Dùng String cho Enum
    private String trangThaiActive; // Dùng String cho Enum
}
