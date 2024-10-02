package com.project.flightManagement.Service.Impl;

import com.project.flightManagement.DTO.KhachHangDTO.KhachHangDTO;
import com.project.flightManagement.Mapper.KhachHangMapper;
import com.project.flightManagement.Model.KhachHang;
import com.project.flightManagement.Repository.KhachHangRepository;
import com.project.flightManagement.Service.KhachHangService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Sort;
import org.springframework.stereotype.Service;

import java.util.Collections;
import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;
import java.util.stream.StreamSupport;

@Service
public class KhachHangServiceImpl implements KhachHangService {
    @Autowired
    private KhachHangRepository khRepo;
    @Override
    public Iterable<KhachHangDTO> getAllKhachHang() {
        try {
            Iterable<KhachHang> listKH = khRepo.findAll();
            Iterable<KhachHangDTO> listKhDTO = StreamSupport.stream(listKH.spliterator(), false)
                    .map(KhachHangMapper::toDTO)
                    .toList();
            return listKhDTO;
        } catch (Exception e){
            System.err.println("Error occurred while fetching customers: " + e.getMessage());
            return Collections.emptyList();
        }
    }

    @Override
    public Optional<KhachHangDTO> getKhachHangByIdKhachHang(int idKH) {
        try {
            Optional<KhachHang> kh = khRepo.findById(idKH);
            Optional<KhachHangDTO> khDTO = kh.map(KhachHangMapper::toDTO);
            return khDTO;
        }catch (Exception e){
            System.err.println("Error occurred while get customer: " + e.getMessage());
            return null;
        }
    }

    @Override
    public Optional<KhachHangDTO> addNewKhachHang(KhachHangDTO khDTO) {
        try {
            KhachHang kh = KhachHangMapper.toEntity(khDTO);
            KhachHang savedKH = khRepo.save(kh);
            return Optional.of(KhachHangMapper.toDTO(savedKH));
        }catch (Exception e){
            System.err.println("Error occurred while save customer: " + e.getMessage());
            return null;
        }
    }

    @Override
    public Optional<KhachHangDTO> updateKhachHang(KhachHangDTO khDTO) {
        Optional<KhachHang> existingKH = khRepo.findById((khDTO.getIdKhachHang()));
        if(existingKH.isPresent()){
            KhachHang khachHang = KhachHangMapper.toEntity(khDTO);
            KhachHang updatedKhachHang = khRepo.save(khachHang);
            return Optional.of(KhachHangMapper.toDTO(updatedKhachHang));
        }else {
            System.err.println("Customer does not existing!!!");
            return Optional.empty();
        }
    }

    @Override
    public Optional<KhachHangDTO> getKhachHangByEmail(String email) {
        try {
            KhachHang khachHang = khRepo.findByEmail(email); // Tìm khách hàng theo email
            return Optional.ofNullable(KhachHangMapper.toDTO(khachHang)); // Trả về DTO hoặc null
        } catch (Exception e) {
            // Xử lý ngoại lệ (nếu có), có thể log hoặc ném ra lỗi tùy ý
            return Optional.empty(); // Trả về Optional.empty() nếu có lỗi
        }
    }

    @Override
    public Optional<KhachHangDTO> getKhachHangBySDT(String sodienthoai) {
        try {
            KhachHang khachHang = khRepo.findBySoDienThoai(sodienthoai); // Tìm khách hàng theo số điện thoại
            return Optional.ofNullable(KhachHangMapper.toDTO(khachHang)); // Trả về DTO hoặc null
        } catch (Exception e) {
            // Xử lý ngoại lệ (nếu có)
            return Optional.empty(); // Trả về Optional.empty() nếu có lỗi
        }
    }

    @Override
    public Optional<KhachHangDTO> getKhachHangByCccd(String cccd) {
        try {
            KhachHang khachHang = khRepo.findByCccd(cccd); // Tìm khách hàng theo CCCD
            return Optional.ofNullable(KhachHangMapper.toDTO(khachHang)); // Trả về DTO hoặc null
        } catch (Exception e) {
            // Xử lý ngoại lệ (nếu có)
            return Optional.empty(); // Trả về Optional.empty() nếu có lỗi
        }
    }

    @Override
    public Iterable<KhachHangDTO> getAllKhachHangSorted(String sortBy) {
        try {
            // Lấy danh sách KhachHang từ cơ sở dữ liệu và sắp xếp theo sortBy
            List<KhachHang> khachHangList = khRepo.findAll(Sort.by(Sort.Direction.DESC, sortBy));

            // Chuyển đổi từ KhachHang sang KhachHangDTO
            List<KhachHangDTO> khachHangDTOList = khachHangList.stream()
                    .map(KhachHangMapper::toDTO)
                    .collect(Collectors.toList());

            return khachHangDTOList;

        } catch (IllegalArgumentException e) {
            // Xử lý lỗi nếu tham số sortBy không hợp lệ hoặc có lỗi khác liên quan đến tham số
            System.err.println("Invalid sorting field: " + sortBy);
            return Collections.emptyList(); // Trả về danh sách rỗng

        } catch (Exception e) {
            // Xử lý các lỗi không lường trước khác
            System.err.println("An error occurred while fetching sorted customers: " + e.getMessage());
            return Collections.emptyList(); // Trả về danh sách rỗng nếu có lỗi
        }
    }

    @Override
    public List<KhachHangDTO> findByHoTen(String keyword) {
        List<KhachHang> khachHangList = khRepo.findByHoTen(keyword);

        return khachHangList.stream()
                .map(KhachHangMapper::toDTO)
                .collect(Collectors.toList());
    }


}