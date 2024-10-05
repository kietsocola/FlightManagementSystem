package com.project.flightManagement.DTO.TuyenBayDTO;

import com.project.flightManagement.Enum.ActiveEnum;
import com.project.flightManagement.Model.SanBay;
import jakarta.validation.constraints.NotNull;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.time.LocalTime;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class TuyenBayDTO {
	
    private int idTuyenBay;

    @NotNull(message = "Không để trống mã của sân bay khởi hành")
    private SanBay idSanBayBatDau;

    @NotNull(message = "Không để trống mã của sân bay hạ cánh")
    private SanBay idSanBayKetThuc;

    @NotNull(message = "Không để trống thời gian của chuyến bay")
    private LocalTime thoiGianChuyenBay;

    @NotNull(message = "Không để trống khoảng cách của chuyến bay")
    private int khoangCach;

    @NotNull(message = "Không để trống trạng thái chuyến bay")
    private ActiveEnum status;

	public int getIdTuyenBay() {
		return idTuyenBay;
	}

	public void setIdTuyenBay(int idTuyenBay) {
		this.idTuyenBay = idTuyenBay;
	}

	
}
