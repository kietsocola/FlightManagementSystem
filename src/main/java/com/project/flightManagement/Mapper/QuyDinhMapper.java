package com.project.flightManagement.Mapper;

import com.project.flightManagement.DTO.QuyDinhDTO.QuyDinhDTO;
import com.project.flightManagement.Model.LoaiQuyDinh;
import com.project.flightManagement.Model.QuyDinh;

public class QuyDinhMapper {

    // Chuyển từ Entity sang DTO
    public static QuyDinhDTO toDTO(QuyDinh quyDinh) {
        return new QuyDinhDTO(
                quyDinh.getIdQuyDinh(),
                quyDinh.getLoaiQuyDinh(), // Lấy ID của loại quy định
                quyDinh.getTenQuyDinh(),
                quyDinh.getNoiDung(),
                quyDinh.getThoiGianTao(),
                quyDinh.getThoiGianCapNhat(),
                quyDinh.getNhanVien(), // Trả về đối tượng NhanVien
                quyDinh.getTrangThaiActive()
        );
    }

    // Chuyển từ DTO sang Entity
    public static QuyDinh toEntity(QuyDinhDTO quyDinhDTO) {
        QuyDinh quyDinh = new QuyDinh();
        quyDinh.setIdQuyDinh(quyDinhDTO.getIdQuyDinh());
        quyDinh.setLoaiQuyDinh(quyDinhDTO.getLoaiQuyDinh()); // Gán loại quy định từ bên ngoài
        quyDinh.setTenQuyDinh(quyDinhDTO.getTenQuyDinh());
        quyDinh.setNoiDung(quyDinhDTO.getNoiDung());
        quyDinh.setThoiGianTao(quyDinhDTO.getThoiGianTao());
        quyDinh.setThoiGianCapNhat(quyDinhDTO.getThoiGianCapNhat());
        quyDinh.setNhanVien(quyDinhDTO.getNhanVien()); // Gán đối tượng NhanVien
        quyDinh.setTrangThaiActive(quyDinhDTO.getTrangThaiActive());
        return quyDinh;
    }
}

