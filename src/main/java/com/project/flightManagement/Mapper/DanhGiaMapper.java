package com.project.flightManagement.Mapper;

import com.project.flightManagement.DTO.DanhGiaDTO.DanhGiaDTO;
import com.project.flightManagement.Model.DanhGia;

public class DanhGiaMapper {

    // Chuyển từ DTO sang Entity
    public static DanhGia toEntity(DanhGiaDTO danhGiaDTO) {
        DanhGia danhGia = new DanhGia();
        danhGia.setIdDanhGia(danhGiaDTO.getIdDanhGia());
        danhGia.setKhachHang(danhGiaDTO.getKhachHang());
        danhGia.setSao(danhGiaDTO.getSao());
        danhGia.setHangBay(danhGiaDTO.getHangBay());
        danhGia.setNoiDung(danhGiaDTO.getNoiDung());
        danhGia.setThoiGianTao(danhGiaDTO.getThoiGianTao());
        danhGia.setTrangThaiActive(danhGiaDTO.getTrangThaiActive());

        // Thiết lập bình luận cha (nếu có)
        if (danhGiaDTO.getParentComment() != null) {
            DanhGia parentComment = new DanhGia();
            parentComment.setIdDanhGia(danhGiaDTO.getParentComment().getIdDanhGia());
            danhGia.setParentComment(parentComment);
        } else {
            danhGia.setParentComment(null);
        }

        return danhGia;
    }

    // Chuyển từ Entity sang DTO
    public static DanhGiaDTO toDTO(DanhGia danhGia) {
        DanhGiaDTO danhGiaDTO = new DanhGiaDTO();
        danhGiaDTO.setIdDanhGia(danhGia.getIdDanhGia());
        danhGiaDTO.setHangBay(danhGia.getHangBay());
        danhGiaDTO.setSao(danhGia.getSao());
        danhGiaDTO.setNoiDung(danhGia.getNoiDung());
        danhGiaDTO.setKhachHang(danhGia.getKhachHang());
        danhGiaDTO.setThoiGianTao(danhGia.getThoiGianTao());
        danhGiaDTO.setTrangThaiActive(danhGia.getTrangThaiActive());

        // Thiết lập parentCommentDTO nếu bình luận có bình luận cha
        if (danhGia.getParentComment() != null) {
            DanhGiaDTO parentCommentDTO = new DanhGiaDTO();
            parentCommentDTO.setIdDanhGia(danhGia.getParentComment().getIdDanhGia());
            danhGiaDTO.setParentComment(parentCommentDTO);
        } else {
            danhGiaDTO.setParentComment(null);
        }

        return danhGiaDTO;
    }
}
