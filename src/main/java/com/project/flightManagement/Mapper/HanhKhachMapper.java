package com.project.flightManagement.Mapper;

import com.project.flightManagement.DTO.HanhKhachDTO.HanhKhachCreateDTO;
import com.project.flightManagement.DTO.HanhKhachDTO.HanhKhachDTO;
import com.project.flightManagement.DTO.HanhKhachDTO.HanhKhachUpdateDTO;
import com.project.flightManagement.DTO.HanhKhachDTO.HanhKhach_VeDTO;
import com.project.flightManagement.Enum.ActiveEnum;
import com.project.flightManagement.Enum.GioiTinhEnum;
import com.project.flightManagement.Model.HanhKhach;
import com.project.flightManagement.Model.Ve;
import com.project.flightManagement.Service.VeService;
import jakarta.persistence.Column;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Lazy;
import org.springframework.stereotype.Component;

import java.util.Optional;
@Component
public class HanhKhachMapper {
    // Phương thức chuyển từ HanhKhach entity sang HanhKhachDTO
    @Autowired
    @Lazy
    private VeService veService;
    public static HanhKhachDTO toDTO(HanhKhach hanhKhach) {
        return new HanhKhachDTO(
                hanhKhach.getIdHanhKhach(),
                null,
                hanhKhach.getHoTen(),
                hanhKhach.getNgaySinh(),
                hanhKhach.getSoDienThoai(),
                hanhKhach.getEmail(),
                hanhKhach.getCccd(),
                hanhKhach.getHoChieu(),
                hanhKhach.getGioiTinhEnum(),
                hanhKhach.getTrangThaiActive()
        );
    }

    // Phương thức chuyển từ HanhKhachDTO sang HanhKhach entity
    public static HanhKhach toEntity(HanhKhachDTO dto) {
        HanhKhach hanhKhach = new HanhKhach();
        hanhKhach.setIdHanhKhach(dto.getIdHanhKhach());
        hanhKhach.setHoTen(dto.getHoTen());
        hanhKhach.setNgaySinh(dto.getNgaySinh());
        hanhKhach.setSoDienThoai(dto.getSoDienThoai());
        hanhKhach.setEmail(dto.getEmail());
        hanhKhach.setCccd(dto.getCccd());
        hanhKhach.setHoChieu(dto.getHoChieu());
        hanhKhach.setGioiTinhEnum(GioiTinhEnum.valueOf(dto.getGioiTinhEnum().toString())); // Chuyển chuỗi thành Enum nếu cần
        hanhKhach.setTrangThaiActive(ActiveEnum.valueOf(dto.getTrangThaiActive().toString())); // Chuyển chuỗi thành Enum nếu cần

        return hanhKhach;
    }
    public static HanhKhach_VeDTO toHanhKhach_VeDTO(HanhKhach hanhKhach) {
        return new HanhKhach_VeDTO(
                hanhKhach.getIdHanhKhach(),
                hanhKhach.getHoTen(),
                hanhKhach.getNgaySinh(),
                hanhKhach.getSoDienThoai(),
                hanhKhach.getEmail(),
                hanhKhach.getCccd(),
                hanhKhach.getHoChieu(),
                hanhKhach.getGioiTinhEnum(),
                hanhKhach.getTrangThaiActive()
        );
    }
    public HanhKhach toHanhKhach(HanhKhachCreateDTO dto) {
        HanhKhach hanhKhach = new HanhKhach();
        hanhKhach.setHoTen(dto.getHoTen());
        hanhKhach.setNgaySinh(dto.getNgaySinh());
        hanhKhach.setSoDienThoai(dto.getSoDienThoai());
        hanhKhach.setEmail(dto.getEmail());
        hanhKhach.setCccd(dto.getCccd());
        hanhKhach.setHoChieu(dto.getHoChieu());
        hanhKhach.setGioiTinhEnum(GioiTinhEnum.valueOf(dto.getGioiTinhEnum().toString())); // Chuyển chuỗi thành Enum nếu cần
        hanhKhach.setTrangThaiActive(ActiveEnum.valueOf(dto.getTrangThaiActive().toString())); // Chuyển chuỗi thành Enum nếu cần
        return hanhKhach;
    }

    public HanhKhachCreateDTO toHanhKhachCreateDTO(HanhKhach hanhKhach) {
        HanhKhachCreateDTO hanhKhachCreateDTO = new HanhKhachCreateDTO();
        hanhKhachCreateDTO.setHoTen(hanhKhach.getHoTen());
        hanhKhachCreateDTO.setNgaySinh(hanhKhach.getNgaySinh());
        hanhKhachCreateDTO.setSoDienThoai(hanhKhach.getSoDienThoai());
        hanhKhachCreateDTO.setEmail(hanhKhach.getEmail());
        hanhKhachCreateDTO.setCccd(hanhKhach.getCccd());
        hanhKhachCreateDTO.setHoChieu(hanhKhach.getHoChieu());
        hanhKhachCreateDTO.setGioiTinhEnum(GioiTinhEnum.valueOf(hanhKhach.getGioiTinhEnum().toString())); // Chuyển chuỗi thành Enum nếu cần
        hanhKhachCreateDTO.setTrangThaiActive(ActiveEnum.valueOf(hanhKhach.getTrangThaiActive().toString())); // Chuyển chuỗi thành Enum nếu cần
        return hanhKhachCreateDTO;
    }
    public static HanhKhach toHanhKhachFromHanhKhachUpdateDTO(HanhKhachUpdateDTO hanhKhachUpdateDTO) {
        HanhKhach hanhKhach = new HanhKhach();
        hanhKhach.setHoTen(hanhKhachUpdateDTO.getHoTen());
        return hanhKhach;
    }
}
