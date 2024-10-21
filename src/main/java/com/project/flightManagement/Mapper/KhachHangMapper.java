package com.project.flightManagement.Mapper;

import com.project.flightManagement.DTO.AuthDTO.SignupDTO;
import com.project.flightManagement.DTO.KhachHangDTO.KhachHangBasicDTO;
import com.project.flightManagement.DTO.KhachHangDTO.KhachHangCreateDTO;
import com.project.flightManagement.DTO.KhachHangDTO.KhachHangDTO;
import com.project.flightManagement.Model.KhachHang;

public class KhachHangMapper {
    public static KhachHangDTO toDTO(KhachHang kh){
        KhachHangDTO khDTO = new KhachHangDTO();
        khDTO.setIdKhachHang(kh.getIdKhachHang());
        khDTO.setHoTen(kh.getHoTen());
        khDTO.setNgaySinh(kh.getNgaySinh());
        khDTO.setSoDienThoai(kh.getSoDienThoai());
        khDTO.setCccd(kh.getCccd());
        khDTO.setGioiTinhEnum(kh.getGioiTinhEnum());
        khDTO.setTrangThaiActive(kh.getTrangThaiActive());
        khDTO.setEmail(kh.getEmail());
        return khDTO;
    }
    public static KhachHang toEntity(KhachHangDTO khDTO) {
        KhachHang kh = new KhachHang();
        kh.setIdKhachHang(khDTO.getIdKhachHang());
        kh.setHoTen(khDTO.getHoTen());
        kh.setNgaySinh(khDTO.getNgaySinh());
        kh.setSoDienThoai(khDTO.getSoDienThoai());
        kh.setCccd(khDTO.getCccd());
        kh.setGioiTinhEnum(khDTO.getGioiTinhEnum());
        kh.setTrangThaiActive(khDTO.getTrangThaiActive());
        kh.setEmail(khDTO.getEmail());
        return kh;
    }


    public KhachHang toKhachHang(KhachHangCreateDTO khachHangCreateDTO) {
        if ( khachHangCreateDTO == null ) {
            return null;
        }

        KhachHang khachHang = new KhachHang();

        khachHang.setGioiTinhEnum( khachHangCreateDTO.getGioiTinh() );
        khachHang.setHoTen( khachHangCreateDTO.getHoTen() );
        khachHang.setEmail( khachHangCreateDTO.getEmail() );
        khachHang.setSoDienThoai( khachHangCreateDTO.getSoDienThoai() );
        khachHang.setCccd( khachHangCreateDTO.getCccd() );

        return khachHang;
    }

    public KhachHangCreateDTO toKhachHangCreateDTO(KhachHang khachHang) {
        if ( khachHang == null ) {
            return null;
        }

        KhachHangCreateDTO khachHangCreateDTO = new KhachHangCreateDTO();

        khachHangCreateDTO.setCccd( khachHang.getCccd() );
        khachHangCreateDTO.setEmail( khachHang.getEmail() );
        khachHangCreateDTO.setHoTen( khachHang.getHoTen() );
        khachHangCreateDTO.setNgaySinh( khachHang.getNgaySinh() );
        khachHangCreateDTO.setSoDienThoai( khachHang.getSoDienThoai() );

        return khachHangCreateDTO;
    }

    public KhachHangCreateDTO toKhachHangCreateDTO(SignupDTO signupDTO) {
        if ( signupDTO == null ) {
            return null;
        }

        KhachHangCreateDTO khachHangCreateDTO = new KhachHangCreateDTO();

        khachHangCreateDTO.setCccd( signupDTO.getCccd() );
        khachHangCreateDTO.setEmail( signupDTO.getEmail() );
        khachHangCreateDTO.setGioiTinh( signupDTO.getGioiTinh() );
        khachHangCreateDTO.setHoTen( signupDTO.getHoTen() );
        khachHangCreateDTO.setNgaySinh( signupDTO.getNgaySinh() );
        khachHangCreateDTO.setSoDienThoai( signupDTO.getSoDienThoai() );

        return khachHangCreateDTO;
    }

    public KhachHangBasicDTO toKhachHangBasicDTO(KhachHang khachHang) {
        if ( khachHang == null ) {
            return null;
        }

        KhachHangBasicDTO khachHangBasicDTO = new KhachHangBasicDTO();

        khachHangBasicDTO.setTrangThai( khachHang.getTrangThaiActive() );
        khachHangBasicDTO.setIdKhachHang( khachHang.getIdKhachHang() );
        khachHangBasicDTO.setCccd( khachHang.getCccd() );
        khachHangBasicDTO.setEmail( khachHang.getEmail() );
        khachHangBasicDTO.setHoTen( khachHang.getHoTen() );
        khachHangBasicDTO.setNgaySinh( khachHang.getNgaySinh() );
        khachHangBasicDTO.setSoDienThoai( khachHang.getSoDienThoai() );

        return khachHangBasicDTO;
    }
}
