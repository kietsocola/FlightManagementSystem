package com.project.flightManagement.Mapper;

import com.project.flightManagement.DTO.AuthDTO.SignupDTO;
import com.project.flightManagement.DTO.KhachHangDTO.KhachHangBasicDTO;
import com.project.flightManagement.DTO.KhachHangDTO.KhachHangCreateDTO;
import com.project.flightManagement.Enum.GioiTinhEnum;
import com.project.flightManagement.Model.KhachHang;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;

@Mapper(componentModel = "spring")
public interface KhachHangMapper {
    // Map từ DTO sang entity
    @Mapping(source = "gioiTinh", target = "gioiTinhEnum")
    KhachHang toKhachHang(KhachHangCreateDTO khachHangCreateDTO);
    KhachHangCreateDTO toKhachHangCreateDTO(KhachHang khachHang);
    KhachHangCreateDTO toKhachHangCreateDTO(SignupDTO signupDTO);
    @Mapping(source = "gioiTinhEnum", target = "gioiTinh")
    @Mapping(source = "trangThaiActive", target = "trangThai")
    KhachHangBasicDTO toKhachHangBasicDTO(KhachHang khachHang);

    // Chuyển đổi enum sang chuỗi (map enum -> string)
    default String map(GioiTinhEnum gioiTinhEnum) {
        return gioiTinhEnum != null ? gioiTinhEnum.name() : null;
    }

    // Chuyển đổi chuỗi sang enum (map string -> enum)
    default GioiTinhEnum map(String gioiTinh) {
        return gioiTinh != null ? GioiTinhEnum.valueOf(gioiTinh) : null;
    }
}
