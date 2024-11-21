package com.project.flightManagement.Mapper;

import com.project.flightManagement.DTO.KhachHangDTO.KhachHangBasicDTO;
import com.project.flightManagement.DTO.KhachHangDTO.KhachHangDTO;
import com.project.flightManagement.DTO.NhanVienDTO.NhanVienDTO;
import com.project.flightManagement.DTO.QuyenDTO.QuyenBasicDTO;
import com.project.flightManagement.DTO.QuyenDTO.QuyenResponseDTO;
import com.project.flightManagement.DTO.TaiKhoanDTO.TaiKhoanDTO;
import com.project.flightManagement.DTO.TaiKhoanDTO.TaiKhoanResponseDTO;
import com.project.flightManagement.Model.KhachHang;
import com.project.flightManagement.Model.NhanVien;
import com.project.flightManagement.Model.Quyen;
import com.project.flightManagement.Model.TaiKhoan;
import com.project.flightManagement.Service.KhachHangService;
import com.project.flightManagement.Service.NhanVienService;
import com.project.flightManagement.Service.QuyenService;
import com.project.flightManagement.Service.TaiKhoanService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import java.util.Optional;
@Component
public class TaiKhoanMapper {
    @Autowired
    private KhachHangService khachHangService;
    @Autowired
    private QuyenService quyenService;
    @Autowired
    private NhanVienService nhanVienService;
    @Autowired
    private QuyenMapper quyenMapper;
    @Autowired
    private KhachHangMapper khachHangMapper;


    public static TaiKhoan toTaiKhoan(TaiKhoanDTO taiKhoanDTO) {
        TaiKhoan taiKhoan = new TaiKhoan();
        taiKhoan.setIdTaiKhoan(taiKhoanDTO.getIdTaiKhoan());

        if(taiKhoanDTO.getKhachHang() != null) {
            KhachHang khachHang = new KhachHang();
            khachHang.setIdKhachHang(taiKhoanDTO.getKhachHang().getIdKhachHang());
            taiKhoan.setKhachHang(khachHang); // chua tra ve nguyen doi tuong khach hang
        } else {
            taiKhoan.setKhachHang(null);
        }


        taiKhoan.setMatKhau(taiKhoanDTO.getMatKhau());
        taiKhoan.setTenDangNhap(taiKhoanDTO.getTenDangNhap());
        taiKhoan.setThoiGianTao(taiKhoanDTO.getThoiGianTao());
        taiKhoan.setTrangThaiActive(taiKhoanDTO.getTrangThaiActive());

        if (taiKhoanDTO.getNhanVien() != null) {
            NhanVien nhanVien = new NhanVien();
            nhanVien.setIdNhanVien(taiKhoanDTO.getNhanVien().getIdNhanVien());
            taiKhoan.setNhanVien(nhanVien);
        } else {
            taiKhoan.setNhanVien(null);
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
            KhachHangBasicDTO khachHangBasicDTO = khachHangService.getKhachHangByIdKhachHang_BASIC(taiKhoan.getKhachHang().getIdKhachHang());
            taiKhoanDTO.setKhachHang(khachHangBasicDTO);
        }

        taiKhoanDTO.setMatKhau(taiKhoan.getMatKhau());
        taiKhoanDTO.setTenDangNhap(taiKhoan.getTenDangNhap());
        taiKhoanDTO.setThoiGianTao(taiKhoan.getThoiGianTao());
        taiKhoanDTO.setTrangThaiActive(taiKhoan.getTrangThaiActive());

        // Ánh xạ NhanVien
        NhanVien nhanVien = taiKhoan.getNhanVien();
        if (nhanVien != null) {
            Optional<NhanVienDTO> nhanVienDTO = nhanVienService.getNhanVienByIdNhanVien(nhanVien.getIdNhanVien());
            nhanVienDTO.ifPresent(taiKhoanDTO::setNhanVien); // Chỉ set nếu nhanVienDTO có giá trị
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
    public TaiKhoanResponseDTO toTaiKhoanResponseDTO(TaiKhoan taiKhoan) {
        TaiKhoanResponseDTO taiKhoanDTO = new TaiKhoanResponseDTO();
        taiKhoanDTO.setIdTaiKhoan(taiKhoan.getIdTaiKhoan());

        // Ánh xạ KhachHang
        KhachHang khachHang = taiKhoan.getKhachHang();
        if (khachHang != null) {
            KhachHangBasicDTO khachHangBasicDTO = khachHangService.getKhachHangByIdKhachHang_BASIC(taiKhoan.getKhachHang().getIdKhachHang());
            taiKhoanDTO.setKhachHang(khachHangBasicDTO);
        }

        taiKhoanDTO.setTenDangNhap(taiKhoan.getTenDangNhap());
        taiKhoanDTO.setThoiGianTao(taiKhoan.getThoiGianTao());
        taiKhoanDTO.setTrangThaiActive(taiKhoan.getTrangThaiActive());

        // Ánh xạ NhanVien
        NhanVien nhanVien = taiKhoan.getNhanVien();
        if (nhanVien != null) {
            NhanVienDTO nhanVienDTO = mapperNhanVienDTOFromNhanVien(nhanVien);
            taiKhoanDTO.setNhanVien(nhanVienDTO);
        }

        // Ánh xạ Quyen
        Quyen quyen = taiKhoan.getQuyen();
        if (quyen != null) {
            taiKhoanDTO.setQuyen(quyenMapper.toQuyenResponseDTO(quyen));
        }
        return taiKhoanDTO;
    }
    private NhanVienDTO mapperNhanVienDTOFromNhanVien(NhanVien nhanVien) {
        NhanVienDTO nhanVienDTO = new NhanVienDTO();
        nhanVienDTO.setIdNhanVien(nhanVien.getIdNhanVien());
        nhanVienDTO.setEmail(nhanVien.getEmail());
        nhanVienDTO.setCccd(nhanVien.getCccd());
        nhanVienDTO.setChucVu(nhanVien.getChucVu());
        nhanVienDTO.setSoDienThoai(nhanVien.getSoDienThoai());
        nhanVienDTO.setNgaySinh(nhanVien.getNgaySinh());
        nhanVienDTO.setHoTen(nhanVien.getHoTen());
        nhanVienDTO.setGioiTinhEnum(nhanVien.getGioiTinhEnum());
        nhanVienDTO.setTrangThaiActive(nhanVien.getTrangThaiActive());
        return nhanVienDTO;
    }

    public TaiKhoan toTaiKhoan(TaiKhoanResponseDTO taiKhoanResponseDTO) {
        TaiKhoan taiKhoan = new TaiKhoan();
        taiKhoan.setIdTaiKhoan(taiKhoanResponseDTO.getIdTaiKhoan());
         // Ánh xạ KhachHang
        KhachHangBasicDTO khachHang = taiKhoanResponseDTO.getKhachHang();
        if (khachHang != null) {
            Optional<KhachHangDTO> khachHangDTO = khachHangService.getKhachHangByIdKhachHang(khachHang.getIdKhachHang());
            taiKhoan.setKhachHang(KhachHangMapper.toEntity(khachHangDTO.get()));
        } else {
            taiKhoan.setKhachHang(null);
        }
        taiKhoan.setTenDangNhap(taiKhoanResponseDTO.getTenDangNhap());
        taiKhoan.setThoiGianTao(taiKhoanResponseDTO.getThoiGianTao());
        taiKhoan.setTrangThaiActive(taiKhoanResponseDTO.getTrangThaiActive());

        // Ánh xạ NhanVien
        NhanVienDTO nhanVien = taiKhoanResponseDTO.getNhanVien();
        if (nhanVien != null) {
            Optional<NhanVienDTO> nhanVienDTO = nhanVienService.getNhanVienByIdNhanVien(nhanVien.getIdNhanVien());
            if (nhanVienDTO.isPresent()) {
                taiKhoan.setNhanVien(NhanVienMapper.toEntity(nhanVienDTO.get()));
            }
        } else {
            taiKhoan.setNhanVien(null);
        }

        // Ánh xạ Quyen
        QuyenResponseDTO quyen = taiKhoanResponseDTO.getQuyen();
        if (quyen != null) {
            Optional<QuyenBasicDTO> optionalQuyenBasicDTO = quyenService.getQuyenByIdQuyen(quyen.getIdQuyen());
            if(optionalQuyenBasicDTO.isPresent()) {
                taiKhoan.setQuyen(quyenMapper.toQuyen(optionalQuyenBasicDTO.get()));
            }
        }

        return taiKhoan;
    }
}
