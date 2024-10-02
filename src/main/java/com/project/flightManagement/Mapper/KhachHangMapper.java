package com.project.flightManagement.Mapper;

import com.project.flightManagement.DTO.AuthDTO.SignupDTO;
import com.project.flightManagement.DTO.KhachHangDTO.KhachHangCreateDTO;
import com.project.flightManagement.Model.KhachHang;
import org.mapstruct.Mapper;

@Mapper(componentModel = "spring")
public interface KhachHangMapper {
    KhachHang toKhachHang(KhachHangCreateDTO khachHangCreateDTO);
    KhachHangCreateDTO toKhachHangCreateDTO(KhachHang khachHang);
    KhachHangCreateDTO toKhachHangCreateDTO(SignupDTO signupDTO);
}
