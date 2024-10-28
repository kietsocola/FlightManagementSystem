package com.project.flightManagement.Mapper;

import com.project.flightManagement.DTO.MayBayDTO.MayBayDTO;
import com.project.flightManagement.Model.MayBay;

public class MayBayMapper {
    public static MayBayDTO toDTO(MayBay mb){
        MayBayDTO mbDTO = new MayBayDTO();
        mbDTO.setIdMayBay(mb.getIdMayBay());
        mbDTO.setTenMayBay(mb.getTenMayBay());
        mbDTO.setHangBay(mb.getHangBay());
        mbDTO.setIcaoMayBay(mb.getIcaoMayBay());
        mbDTO.setSoHieu(mb.getSoHieu());
        mbDTO.setSoLuongGhe(mb.getSoLuongGhe());
        mbDTO.setNamSanXuat(mb.getNamSanXuat());
        mbDTO.setTrangThaiActive(mb.getTrangThaiActive());
        mbDTO.setSanBay(mb.getSanBay());
        mbDTO.setChuyenBay(mb.getChuyenBay());
        return mbDTO;
    }
    public static MayBay toEntity(MayBayDTO mbDTO){
        MayBay mb = new MayBay();
        mb.setIdMayBay(mbDTO.getIdMayBay());
        mb.setTenMayBay(mbDTO.getTenMayBay());
        mb.setHangBay(mbDTO.getHangBay());
        mb.setIcaoMayBay(mbDTO.getIcaoMayBay());
        mb.setSoHieu(mbDTO.getSoHieu());
        mb.setSoLuongGhe(mbDTO.getSoLuongGhe());
        mb.setNamSanXuat(mbDTO.getNamSanXuat());
        mb.setTrangThaiActive(mbDTO.getTrangThaiActive());
        mb.setSanBay(mbDTO.getSanBay());
        mb.setChuyenBay(mbDTO.getChuyenBay());
        return mb;
    }
}
