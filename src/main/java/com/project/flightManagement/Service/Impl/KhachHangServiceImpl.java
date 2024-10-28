package com.project.flightManagement.Service.Impl;

import com.project.flightManagement.DTO.KhachHangDTO.KhachHangDTO;
import com.project.flightManagement.DTO.KhachHangDTO.KhachHangBasicDTO;
import com.project.flightManagement.DTO.KhachHangDTO.KhachHangCreateDTO;
import com.project.flightManagement.Enum.ActiveEnum;
import com.project.flightManagement.Mapper.KhachHangMapper;
import com.project.flightManagement.Model.KhachHang;
import com.project.flightManagement.Repository.KhachHangRepository;
import com.project.flightManagement.Service.KhachHangService;
import jakarta.persistence.EntityNotFoundException;
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
    public Iterable<KhachHangDTO> getAllKhachHangSorted(String sortBy, String direction) {
        try {
            List<KhachHang> khachHangList;
            // Lấy danh sách KhachHang từ cơ sở dữ liệu và sắp xếp theo sortBy
            if(direction.equals("asc")) {
                khachHangList = khRepo.findAll(Sort.by(Sort.Direction.ASC, sortBy));
            } else {
                khachHangList = khRepo.findAll(Sort.by(Sort.Direction.DESC, sortBy));
            }

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
        List<KhachHang> khachHangList = khRepo.findByKeywordContainingIgnoreCase(keyword);
        if (khachHangList.isEmpty()) {
            System.out.println("No customer found with the keyword: " + keyword);
        } else {
            KhachHang kh = khachHangList.get(0); // Dùng get(0) thay vì getFirst()
            System.out.println("Id kh found: " + kh.getHoTen());
        }
        return khachHangList.stream()
                .map(KhachHangMapper::toDTO)
                .collect(Collectors.toList());
    }

    @Override
    public KhachHang createKhachHang(KhachHangCreateDTO khachHangCreateDTO) {
        try {
            KhachHang khachHang = KhachHangMapper.toKhachHang(khachHangCreateDTO);
            khachHang.setTrangThaiActive(ActiveEnum.ACTIVE);
            return khRepo.save(khachHang);
        } catch (Exception e) {
            e.printStackTrace();
            return null;
        }
    }

    @Override
    public boolean existsKhachHangByEmail(String email) {
        if(khRepo.existsKhachHangByEmail(email)) {
            return true;
        }
        return false;
    }
    @Override
    public boolean existsKhachHangByCccd(String cccd) {
        if(khRepo.existsKhachHangByCccd(cccd)) {
            return true;
        }
        return false;
    }

    @Override
    public KhachHangBasicDTO getKhachHangByIdKhachHang_BASIC(int idKhachHang) {
        Optional<KhachHang> khachHangOptional = khRepo.findById(idKhachHang);
        if (khachHangOptional.isPresent()) {
            KhachHang khachHang = khachHangOptional.get();
            KhachHangBasicDTO khachHangBasicDTO = KhachHangMapper.toKhachHangBasicDTO(khachHang);
            return khachHangBasicDTO;
        }
        throw new EntityNotFoundException("Khách hàng với ID " + idKhachHang + " không tồn tại.");
    }

    @Override
    public boolean updatePoint(int idKH, int point, boolean isUse) {
        Optional<KhachHang> khachHangOptional = khRepo.findById(idKH);
        if (khachHangOptional.isPresent()) {
            KhachHang kh = khachHangOptional.get();
            if(isUse){
                if(point>kh.getPoint()) return false;
                kh.setPoint(kh.getPoint()-point);
            } else kh.setPoint(kh.getPoint()+point);
            khRepo.save(kh);
            return true;
        }
        throw new EntityNotFoundException("Khách hàng với ID " + idKH + " không tồn tại.");
    }

}
