package com.project.flightManagement.Service.Impl;

import com.project.flightManagement.DTO.NhanVienDTO.NhanVienDTO;
import com.project.flightManagement.Mapper.ChuyenBayMapper;
import com.project.flightManagement.Mapper.NhanVienMapper;
import com.project.flightManagement.Model.ChucVu;
import com.project.flightManagement.Model.NhanVien;
import com.project.flightManagement.Repository.NhanVienRepository;
import com.project.flightManagement.Service.NhanVienService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Sort;
import org.springframework.stereotype.Service;

import java.util.Collections;
import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;
import java.util.stream.StreamSupport;

@Service

public class NhanVienServiceImpl implements NhanVienService {

    @Autowired
    private NhanVienRepository repo ;

    @Override
    public Optional<NhanVienDTO> addNhanVien(NhanVienDTO nvDTO) {
        NhanVien nv = NhanVienMapper.toEntity(nvDTO);
        NhanVien saveNv = repo.save(nv);
        return Optional.of(NhanVienMapper. toDTO(saveNv));
    }

    @Override
    public Optional<NhanVienDTO> updateNhanVien(NhanVienDTO nvDTO) {

        NhanVien nv = NhanVienMapper.toEntity(nvDTO);
        NhanVien updateNv = repo.save(nv);
        return Optional.of(NhanVienMapper.toDTO(updateNv));
    }

    @Override
    public Iterable<NhanVienDTO> getAllNhanVien() {

        try{
            Iterable<NhanVien> listnv =  repo.findAll();
            Iterable<NhanVienDTO> listnvDTO = StreamSupport.stream(listnv.spliterator(),false)
                    .map(NhanVienMapper::toDTO)
                    .toList();
            return listnvDTO;
        }catch (Exception e){
            System.err.println("error occurred while fetching customers :" + e.getMessage());
            return Collections.emptyList();
        }
    }

    @Override
    public List<NhanVienDTO> getAllNhanVienSorted(String sortField ,String sortOrder) {
        try{
            // Kiểm tra hướng sắp xếp
            Sort.Direction direction = Sort.Direction.ASC; // Mặc định là ASC
            if ("desc".equalsIgnoreCase(sortOrder)) {
                direction = Sort.Direction.DESC; // Thay đổi thành DESC nếu yêu cầu
            }
            List<NhanVien> listnv = repo.findAll(Sort.by( direction , sortField));
            List<NhanVienDTO> listnvDTO = listnv.stream().map(NhanVienMapper::toDTO).collect(Collectors.toList());
            return listnvDTO;
        }catch (IllegalArgumentException e){

            System.err.println("Invalid sorting field: " + sortField);
            return Collections.emptyList();
        }catch (Exception e) {
            System.err.println("An error occurred while fetching sorted customers: " + e.getMessage());
            return Collections.emptyList();
        }
    }

    @Override
    public Optional<NhanVienDTO> getNhanVienByIdNhanVien(int idNhanVien) {

        try{
            Optional<NhanVien> nv = repo.findById(idNhanVien);
            return nv.map(NhanVienMapper::toDTO);
        }catch (Exception e){
            System.err.println("Error occurred while get customer: " + e.getMessage());
            return Optional.empty();
        }

    }

    @Override
    public List<NhanVienDTO> getNhanVienByhoTen(String hoTen) {
            List<NhanVien> nv = repo.findByHoTenContainingIgnoreCase(hoTen);
            return nv.stream().map(NhanVienMapper::toDTO).collect(Collectors.toList());
    }

    @Override
    public List<NhanVienDTO> getNhanVienByEmail(String email) {
        List<NhanVien> nv = repo.findByEmailContaining(email);
        return nv.stream().map(NhanVienMapper::toDTO).collect(Collectors.toList());
    }

    @Override
    public List<NhanVienDTO> getNhanVienBySDT(String SDT) {
        List<NhanVien> nv = repo.findBySoDienThoaiContaining(SDT);
        return nv.stream().map(NhanVienMapper::toDTO).collect(Collectors.toList());
    }

    @Override
    public List<NhanVienDTO> getNhanVienByCCCD(String cccd) {
        List<NhanVien> nv = repo.findByCccdContaining(cccd);
        return nv.stream().map(NhanVienMapper::toDTO).collect(Collectors.toList());
    }

    @Override
    public List<NhanVienDTO> getNhanVienBetween(String start, String end) {
        List<NhanVien> nv = repo.findByContentBetween(start ,end);
        return nv.stream().map(NhanVienMapper::toDTO).collect(Collectors.toList());
    }

    @Override
    public List<NhanVienDTO> filterNhanVien(String hoTen, String email, String soDienThoai, String cccd, ChucVu chucVu) {
        List<NhanVien> nv =  repo.filterNhanVien(hoTen , email,soDienThoai,cccd ,chucVu);
        return nv.stream().map(NhanVienMapper::toDTO).collect(Collectors.toList());
    }


}
