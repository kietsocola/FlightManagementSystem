package com.project.flightManagement.Mapper;

import com.project.flightManagement.DTO.KhachHangDTO.KhachHangBasicDTO;
import com.project.flightManagement.DTO.NhanVienDTO.NhanVienDTO;
import com.project.flightManagement.DTO.QuyenDTO.QuyenBasicDTO;
import com.project.flightManagement.DTO.TaiKhoanDTO.TaiKhoanDTO;
import com.project.flightManagement.Model.KhachHang;
import com.project.flightManagement.Model.NhanVien;
import com.project.flightManagement.Model.Quyen;
import com.project.flightManagement.Model.TaiKhoan;
import com.project.flightManagement.Service.KhachHangService;
import com.project.flightManagement.Service.QuyenService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import java.util.Optional;

@Component
public class TaiKhoanMapper {
    @Autowired
    private KhachHangService khachHangService;
    @Autowired
    private QuyenService quyenService;
    public TaiKhoan toTaiKhoan(TaiKhoanDTO taiKhoanDTO) {
        TaiKhoan taiKhoan = new TaiKhoan();
        taiKhoan.setIdTaiKhoan(taiKhoanDTO.getIdTaiKhoan());

        if(taiKhoanDTO.getKhachHang() != null) {

        }
        KhachHang khachHang = new KhachHang();
        khachHang.setIdKhachHang(taiKhoanDTO.getKhachHang().getIdKhachHang());
        taiKhoan.setKhachHang(khachHang); // chua tra ve nguyen doi tuong khach hang

        taiKhoan.setMatKhau(taiKhoanDTO.getMatKhau());
        taiKhoan.setTenDangNhap(taiKhoanDTO.getTenDangNhap());
        taiKhoan.setThoiGianTao(taiKhoanDTO.getThoiGianTao());
        taiKhoan.setTrangThaiActive(taiKhoanDTO.getTrangThaiActive());

        if (taiKhoanDTO.getNhanVien() != null) {
            NhanVien nhanVien = new NhanVien();
            nhanVien.setIdNhanVien(taiKhoanDTO.getNhanVien().getIdNhanVien());
            taiKhoan.setNhanVien(nhanVien);
        }


        Quyen quyen = new Quyen();
        quyen.setIdQuyen(taiKhoanDTO.getQuyen().getIdQuyen());
        taiKhoan.setQuyen(quyen);

        return taiKhoan;
    }

    public TaiKhoanDTO toTaiKhoanDTO(TaiKhoan taiKhoan) {
        TaiKhoanDTO taiKhoanDTO = new TaiKhoanDTO();
        taiKhoanDTO.setIdTaiKhoan(taiKhoan.getIdTaiKhoan());

        // Ánh xạ KhachHang
        KhachHang khachHang = taiKhoan.getKhachHang();
        if (khachHang != null) {
            KhachHangBasicDTO khachHangBasicDTO = khachHangService.getKhachHangByIdKhachHang(taiKhoan.getKhachHang().getIdKhachHang());
            taiKhoanDTO.setKhachHang(khachHangBasicDTO);
        }

        taiKhoanDTO.setMatKhau(taiKhoan.getMatKhau());
        taiKhoanDTO.setTenDangNhap(taiKhoan.getTenDangNhap());
        taiKhoanDTO.setThoiGianTao(taiKhoan.getThoiGianTao());
        taiKhoanDTO.setTrangThaiActive(taiKhoan.getTrangThaiActive());

        // Ánh xạ NhanVien
        NhanVien nhanVien = taiKhoan.getNhanVien();
        if (nhanVien != null) {
            NhanVienDTO nhanVienDTO = new NhanVienDTO();
            nhanVienDTO.setIdNhanVien(taiKhoan.getNhanVien().getIdNhanVien());
            taiKhoan.setNhanVien(nhanVien);
        }

        // Ánh xạ Quyen
        Quyen quyen = taiKhoan.getQuyen();
        if (quyen != null) {
            Optional<QuyenBasicDTO> optionalQuyenBasicDTO = quyenService.getQuyenByIdQuyen(quyen.getIdQuyen());
            if(optionalQuyenBasicDTO.isPresent()) {
                taiKhoanDTO.setQuyen(optionalQuyenBasicDTO.get());
            }
        }
        return taiKhoanDTO;
    }


}
