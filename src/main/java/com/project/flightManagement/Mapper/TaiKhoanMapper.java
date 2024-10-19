package com.project.flightManagement.Mapper;

import com.project.flightManagement.DTO.TaiKhoanDTO.TaiKhoanDTO;
import com.project.flightManagement.Model.TaiKhoan;

public class TaiKhoanMapper {
    public static TaiKhoanDTO toDTO(TaiKhoan tk) {
        TaiKhoanDTO tkDTO = new TaiKhoanDTO();
        tkDTO.setIdTaiKhoan(tk.getIdTaiKhoan());
        tkDTO.setTenDangNhap(tk.getTenDangNhap());
        tkDTO.setMatKhau(tk.getMatKhau());
        tkDTO.setIdKH(tk.getKhachHang());
        tkDTO.setIdNV(tk.getNhanVien());
        tkDTO.setIdQuyen(tk.getQuyen());
        tkDTO.setThoiGianTao(tk.getThoiGianTao());
        tkDTO.setStatus(tk.getTrangThaiActive());

        return tkDTO;
    }

    public static TaiKhoan toEntity(TaiKhoanDTO tkDTO) {
        TaiKhoan tk = new TaiKhoan();
        tk.setIdTaiKhoan(tkDTO.getIdTaiKhoan());
        tk.setTenDangNhap(tkDTO.getTenDangNhap());
        tk.setQuyen(tkDTO.getIdQuyen());
        tk.setMatKhau(tkDTO.getMatKhau());
        tk.setKhachHang(tkDTO.getIdKH());
        tk.setNhanVien(tkDTO.getIdNV());
        tk.setThoiGianTao(tkDTO.getThoiGianTao());
        tk.setTrangThaiActive(tkDTO.getStatus());

        return tk;
    }

}
