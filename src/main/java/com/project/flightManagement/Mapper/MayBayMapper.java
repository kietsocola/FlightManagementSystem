package com.project.flightManagement.Mapper;

import com.project.flightManagement.DTO.MayBayDTO.MayBayDTO;
import com.project.flightManagement.DTO.MayBayDTO.MayBay_VeDTO;
import com.project.flightManagement.Model.MayBay;

public class MayBayMapper {
    public static MayBayDTO toDTO(MayBay mb){
        MayBayDTO mbDTO = new MayBayDTO();
        mbDTO.setIdMayBay(mb.getIdMayBay());
        mbDTO.setTenMayBay(mb.getTenMayBay());
        mbDTO.setHangBay(mb.getHangBay());
        mbDTO.setIcaoMayBay(mb.getIcaoMayBay());
        mbDTO.setSoHieu(mb.getSoHieu());
        mbDTO.setSoCotGheThuong(mb.getSoCotGheThuong());
        mbDTO.setSoCotGheVip(mb.getSoCotGheVip());
        mbDTO.setSoHangGheThuong(mb.getSoHangGheThuong());
        mbDTO.setSoHangGheVip(mb.getSoHangGheVip());
        mbDTO.setNamSanXuat(mb.getNamSanXuat());
        mbDTO.setTrangThaiActive(mb.getTrangThaiActive());
        mbDTO.setSanBay(mb.getSanBay());
        return mbDTO;
    }
    public static MayBay toEntity(MayBayDTO mbDTO) {
        MayBay mb = new MayBay();
        mb.setIdMayBay(mbDTO.getIdMayBay());
        mb.setTenMayBay(mbDTO.getTenMayBay());
        mb.setHangBay(mbDTO.getHangBay());
        mb.setIcaoMayBay(mbDTO.getIcaoMayBay());
        mb.setSoHieu(mbDTO.getSoHieu());
        mb.setSoCotGheThuong(mbDTO.getSoCotGheThuong());
        mb.setSoCotGheVip(mbDTO.getSoCotGheVip());
        mb.setSoHangGheThuong(mbDTO.getSoHangGheThuong());
        mb.setSoHangGheVip(mbDTO.getSoHangGheVip());
        mb.setNamSanXuat(mbDTO.getNamSanXuat());
        mb.setTrangThaiActive(mbDTO.getTrangThaiActive());
        mb.setSanBay(mbDTO.getSanBay());
        return mb;
    }

    public static MayBay_VeDTO toMayBay_VeDTO(MayBay mb){
        MayBay_VeDTO mbDTO = new MayBay_VeDTO();
        mbDTO.setIdMayBay(mb.getIdMayBay());
        mbDTO.setTenMayBay(mb.getTenMayBay());
        mbDTO.setHangBay(HangBayMapper.toDTO(mb.getHangBay()));
        mbDTO.setIcaoMayBay(mb.getIcaoMayBay());
        mbDTO.setSoHieu(mb.getSoHieu());
        mbDTO.setTrangThaiActive(mb.getTrangThaiActive()) ;
        return mbDTO;
    }

}
