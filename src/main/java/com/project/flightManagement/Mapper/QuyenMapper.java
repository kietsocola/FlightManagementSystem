package com.project.flightManagement.Mapper;

import com.project.flightManagement.DTO.QuyenDTO.QuyenBasicDTO;
import com.project.flightManagement.DTO.QuyenDTO.QuyenCreateDTO;
import com.project.flightManagement.Model.Quyen;
import org.mapstruct.Mapper;
import org.springframework.stereotype.Component;
@Mapper(componentModel = "spring")
public interface QuyenMapper {
    QuyenBasicDTO toQuyenBasicDTO(Quyen quyen);
    Quyen toQuyen(QuyenBasicDTO quyenBasicDTO);
    Quyen toQuyenFromQuyenCreateDTO(QuyenCreateDTO quyenCreateDTO);
}
