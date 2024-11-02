package com.project.flightManagement.Mapper;

import com.project.flightManagement.DTO.VeDTO.VeCreateDTO;
import com.project.flightManagement.DTO.VeDTO.VeDTO;
import com.project.flightManagement.DTO.VeDTO.VeUpdateHanhKhachDTO;
import com.project.flightManagement.Enum.ActiveEnum;
import com.project.flightManagement.Enum.VeEnum;
import com.project.flightManagement.Model.ChoNgoi;
import com.project.flightManagement.Model.HanhKhach;
import com.project.flightManagement.Model.LoaiVe;
import com.project.flightManagement.Model.Ve;
import com.project.flightManagement.Repository.HangVeRepository;
import com.project.flightManagement.Service.*;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import java.util.Optional;

@Component
public class VeMapper {
    @Autowired
    private LoaiVeMapper loaiVeMapper;
    @Autowired
    private ChuyenBayMapper chuyenBayMapper;
    @Autowired
    private ChuyenBayService chuyenBayService;
    @Autowired
    private ChoNgoiService choNgoiService;
    @Autowired
    private LoaiVeService loaiVeService;
    @Autowired
    private HanhKhachService hanhKhachService;
    @Autowired
    private HangVeService hangVeService;
    @Autowired
    private HangVeRepository hangVeRepository;

    public VeDTO toDto(Ve ve) {
        if (ve == null) {
            return null;
        }
        VeDTO veDTO = new VeDTO();
        veDTO.setIdVe(ve.getIdVe());
        veDTO.setMaVe(ve.getMaVe());
        veDTO.setChuyenBay(chuyenBayMapper.toChuyenBay_VeDTO(ve.getChuyenBay()));
        veDTO.setHangVe(HangVeMapper.toDTO(ve.getHangVe()));
        veDTO.setGiaVe(ve.getGiaVe());
        veDTO.setIdChoNgoi(ve.getChoNgoi().getIdChoNgoi());
        if(ve.getHanhKhach() != null) {
            veDTO.setHanhKhach(HanhKhachMapper.toHanhKhach_VeDTO(ve.getHanhKhach()));
        }
        veDTO.setLoaiVe(loaiVeMapper.toDto(ve.getLoaiVe()));
        veDTO.setTrangThaiActive(ve.getTrangThaiActive());
        veDTO.setTrangThai(ve.getTrangThai());

        return veDTO;
    }
    // Chuyển đổi từ VeDTO sang Ve entity
    public Ve toEntity(VeDTO veDTO) {
        if (veDTO == null) {
            return null;
        }

        Ve ve = new Ve();
        ve.setIdVe(veDTO.getIdVe());
        ve.setMaVe(veDTO.getMaVe());
//        ve.setChuyenBay(veDTO.getChuyenBay());
        ve.setGiaVe(veDTO.getGiaVe());
//        ve.setChoNgoi(veDTO.getChoNgoi());
//        ve.setHanhKhach(veDTO.getHanhKhach());
        ve.setLoaiVe(loaiVeMapper.toEntity(veDTO.getLoaiVe()));
        ve.setTrangThaiActive(veDTO.getTrangThaiActive());
        ve.setTrangThai(veDTO.getTrangThai());

        return ve;
    }

    public Ve toEntity(VeCreateDTO veCreateDTO) {

        if (veCreateDTO == null) {
            return null;
        }

        Ve ve = new Ve();
        ve.setChuyenBay(chuyenBayService.getChuyenBayEntityById(veCreateDTO.getIdChuyenBay()));
        ve.setGiaVe(veCreateDTO.getGiaVe());
        Optional<ChoNgoi> choNgoiOptional = choNgoiService.getChoNgoiEntityById(veCreateDTO.getIdChoNgoi());
        if (choNgoiOptional.isEmpty()) {
            return null;
        }
        ve.setChoNgoi(choNgoiOptional.get());
        Optional<LoaiVe> loaiVeOptional = loaiVeService.getLoaiVeEntityById(veCreateDTO.getIdLoaiVe());
        if (loaiVeOptional.isEmpty()) {
            return null;
        }
        ve.setLoaiVe(loaiVeOptional.get());
        ve.setHangVe(hangVeRepository.findById(veCreateDTO.getIdHangVe()).get());
        ve.setTrangThaiActive(ActiveEnum.ACTIVE);
        ve.setTrangThai(VeEnum.EMPTY);
        return ve;
    }

}
