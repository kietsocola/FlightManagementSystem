package com.project.flightManagement.Service.Impl;

import com.project.flightManagement.DTO.NhanVienDTO.NhanVienDTO;
import com.project.flightManagement.Mapper.NhanVienMapper;
import com.project.flightManagement.Model.NhanVien;
import com.project.flightManagement.Repository.NhanVienRepository;
import com.project.flightManagement.Service.NhanVienService;
import org.hibernate.Internal;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Sort;
import org.springframework.stereotype.Service;

import java.util.Collection;
import java.util.Collections;
import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;
import java.util.stream.StreamSupport;

@Service

public class NhanVienImpl implements NhanVienService {

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
    public List<NhanVienDTO> getAllNhanVienSorted(String sort) {
        try{
            List<NhanVien> listnv = repo.findAll(Sort.by(Sort.Direction.ASC , sort));
            List<NhanVienDTO> listnvDTO = listnv.stream().map(NhanVienMapper::toDTO).collect(Collectors.toList());
            return listnvDTO;
        }catch (IllegalArgumentException e){
            // Xử lý lỗi nếu tham số sortBy không hợp lệ hoặc có lỗi khác liên quan đến tham số
            System.err.println("Invalid sorting field: " + sort);
            return Collections.emptyList(); // Trả về danh sách rỗng
        }catch (Exception e) {
            // Xử lý các lỗi không lường trước khác
            System.err.println("An error occurred while fetching sorted customers: " + e.getMessage());
            return Collections.emptyList(); // Trả về danh sách rỗng nếu có lỗi
        }
    }

    @Override
    public Optional<NhanVienDTO> getNhanVienByIdNhanVien(int idNhanVien) {

        try{
            Optional<NhanVien> nv = repo.findById(idNhanVien);
            Optional<NhanVienDTO> nvDTO = nv.map(NhanVienMapper::toDTO);
            return nvDTO;
        }catch (Exception e){
            System.err.println("Error occurred while get customer: " + e.getMessage());
            return Optional.empty();
        }

    }

    @Override
    public List<NhanVienDTO> getNhanVienByhoTen(String hoTen) {
            List<NhanVien> nv = repo.findByHoTen(hoTen);
            return nv.stream().map(NhanVienMapper::toDTO).collect(Collectors.toList());
    }

    @Override
    public Optional<NhanVienDTO> getNhanVienByEmail(String email) {
        try {
            NhanVien nv = repo.findByEmail(email);
            return Optional.ofNullable(NhanVienMapper.toDTO(nv));// Trả về DTO hoặc null
        } catch (Exception e) {
            // Xử lý ngoại lệ (nếu có), có thể log hoặc ném ra lỗi tùy ý
            return Optional.empty(); // Trả về Optional.empty() nếu có lỗi
        }
    }

    @Override
    public Optional<NhanVienDTO> getNhanVienBySDT(String SDT) {
        try {
            NhanVien nv = repo.findBySoDienThoai(SDT);
            return Optional.ofNullable(NhanVienMapper.toDTO(nv));// Trả về DTO hoặc null
        } catch (Exception e) {
            // Xử lý ngoại lệ (nếu có), có thể log hoặc ném ra lỗi tùy ý
            return Optional.empty(); // Trả về Optional.empty() nếu có lỗi
        }
    }

    @Override
    public Optional<NhanVienDTO> getNhanVienByCCCD(String cccd) {
        try {
            NhanVien nv = repo.findByCccd(cccd);
            return Optional.ofNullable(NhanVienMapper.toDTO(nv));// Trả về DTO hoặc null
        } catch (Exception e) {
            // Xử lý ngoại lệ (nếu có), có thể log hoặc ném ra lỗi tùy ý
            return Optional.empty(); // Trả về Optional.empty() nếu có lỗi
        }
    }
}
