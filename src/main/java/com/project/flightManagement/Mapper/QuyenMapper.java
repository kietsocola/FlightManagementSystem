package com.project.flightManagement.Mapper;

import com.project.flightManagement.DTO.ChiTietQuyenDTO.ChiTietQuyenDTO;
import com.project.flightManagement.DTO.QuyenDTO.QuyenBasicDTO;
import com.project.flightManagement.DTO.QuyenDTO.QuyenCreateDTO;
import com.project.flightManagement.DTO.QuyenDTO.QuyenResponseDTO;
import com.project.flightManagement.Model.ChiTietQuyen;
import com.project.flightManagement.Model.Quyen;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;
import org.springframework.stereotype.Component;

import java.util.ArrayList;
import java.util.List;

@Component
public class QuyenMapper {
    public QuyenBasicDTO toQuyenBasicDTO(Quyen quyen) {
        if ( quyen == null ) {
            return null;
        }

        QuyenBasicDTO quyenBasicDTO = new QuyenBasicDTO();

        quyenBasicDTO.setIdQuyen( quyen.getIdQuyen() );
        quyenBasicDTO.setTenQuyen( quyen.getTenQuyen() );

        return quyenBasicDTO;
    }

    public Quyen toQuyen(QuyenBasicDTO quyenBasicDTO) {
        if ( quyenBasicDTO == null ) {
            return null;
        }

        Quyen quyen = new Quyen();

        quyen.setIdQuyen( quyenBasicDTO.getIdQuyen() );
        quyen.setTenQuyen( quyenBasicDTO.getTenQuyen() );

        return quyen;
    }

    public Quyen toQuyenFromQuyenCreateDTO(QuyenCreateDTO quyenCreateDTO) {
        if ( quyenCreateDTO == null ) {
            return null;
        }

        Quyen quyen = new Quyen();

        quyen.setTenQuyen( quyenCreateDTO.getTenQuyen() );

        return quyen;
    }

    public QuyenResponseDTO toQuyenResponseDTO(Quyen quyen) {
        if ( quyen == null ) {
            return null;
        }

        QuyenResponseDTO quyenResponseDTO = new QuyenResponseDTO();

        quyenResponseDTO.setChiTietQuyenDTOList( toChiTietQuyenDTOList(quyen.getChiTietQuyenList() ) );
        quyenResponseDTO.setIdQuyen( quyen.getIdQuyen() );
        quyenResponseDTO.setTenQuyen( quyen.getTenQuyen() );

        return quyenResponseDTO;
    }

    public ChiTietQuyenDTO toChiTietQuyenDTO(ChiTietQuyen chiTietQuyen) {
        if ( chiTietQuyen == null ) {
            return null;
        }

        ChiTietQuyenDTO chiTietQuyenDTO = new ChiTietQuyenDTO();
        chiTietQuyenDTO.setIdChucNang(chiTietQuyen.getChucNang().getIdChucNang());
        chiTietQuyenDTO.setTenChucNang(chiTietQuyen.getChucNang().getTenChucNang());
        chiTietQuyenDTO.setHanhDong( chiTietQuyen.getHanhDong() );

        return chiTietQuyenDTO;
    }

    public List<ChiTietQuyenDTO> toChiTietQuyenDTOList(List<ChiTietQuyen> chiTietQuyenList) {
        if ( chiTietQuyenList == null ) {
            return null;
        }

        List<ChiTietQuyenDTO> list = new ArrayList<ChiTietQuyenDTO>( chiTietQuyenList.size() );
        for ( ChiTietQuyen chiTietQuyen : chiTietQuyenList ) {
            list.add( toChiTietQuyenDTO( chiTietQuyen ) );
        }

        return list;
    }
}
