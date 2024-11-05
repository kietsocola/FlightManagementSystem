package com.project.flightManagement.Service.Impl;

import com.project.flightManagement.DTO.HangHoaDTO.HangHoaDTO;
import com.project.flightManagement.DTO.KhachHangDTO.KhachHangDTO;
import com.project.flightManagement.Mapper.HangHoaMapper;
import com.project.flightManagement.Mapper.KhachHangMapper;
import com.project.flightManagement.Model.HangHoa;
import com.project.flightManagement.Model.KhachHang;
import com.project.flightManagement.Model.LoaiHangHoa;
import com.project.flightManagement.Repository.HangHoaRepository;
import com.project.flightManagement.Repository.LoaiHangHoaRepository;
import com.project.flightManagement.Service.HangHoaService;
import jakarta.persistence.EntityNotFoundException;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Sort;
import org.springframework.stereotype.Service;

import java.util.Collections;
import java.util.List;
import java.util.Objects;
import java.util.Optional;
import java.util.stream.Collectors;
import java.util.stream.StreamSupport;
import java.util.UUID;

@Service
@Slf4j
public class HangHoaServiceImpl implements HangHoaService {

    @Autowired
    private HangHoaRepository hangHoaRepo;

    @Autowired
    private LoaiHangHoaRepository loaiHangHoaRepo;

    @Override
    public List<HangHoaDTO> getAllHangHoa() {
        try {
            Iterable<HangHoa> hangHoaList = hangHoaRepo.findAll();
            return StreamSupport.stream(hangHoaList.spliterator(), false)
                    .map(HangHoaMapper::toDTO)
                    .collect(Collectors.toList());
        } catch (Exception e) {
            System.err.println("Error occurred while fetching products: " + e.getMessage());
            return Collections.emptyList();
        }
    }



    @Override
    public Optional<HangHoaDTO> getHangHoaByIdHangHoa(int idHangHoa) {
        try {
            return hangHoaRepo.findById(idHangHoa).map(HangHoaMapper::toDTO);
        } catch (Exception e) {
            System.err.println("Error occurred while fetching product by ID: " + e.getMessage());
            return Optional.empty();
        }
    }

    private String generateUniqueCode() {
        // Sử dụng UUID để tạo mã hàng hóa duy nhất
        return UUID.randomUUID().toString();
    }

    @Override
    public Optional<HangHoaDTO> addNewHangHoa(HangHoaDTO hangHoaDTO) {
        try {
            System.out.println("Giá trị của maHangHoa trước khi thiết lập: " + hangHoaDTO.getMaHangHoa());

            // Tìm và lấy đối tượng LoaiHangHoa từ cơ sở dữ liệu
            Optional<LoaiHangHoa> loaiHangHoaOpt = loaiHangHoaRepo.findById(hangHoaDTO.getIdLoaiHangHoa());

            // Kiểm tra nếu loại hàng hóa không tồn tại
            if (loaiHangHoaOpt.isEmpty()) {
                System.err.println("Loại hàng hóa không tìm thấy: ID = " + hangHoaDTO.getIdLoaiHangHoa());
                return Optional.empty();
            }

            // Tạo mã hàng hóa duy nhất
            hangHoaDTO.setMaHangHoa(generateUniqueCode());
            System.out.println("Mã hàng hóa được thiết lập: " + hangHoaDTO.getMaHangHoa());

            // Chuyển đổi DTO sang thực thể HangHoa
            HangHoa hangHoa = HangHoaMapper.toEntity(hangHoaDTO);
            hangHoa.setLoaiHangHoa(loaiHangHoaOpt.get());

            // Lưu đối tượng HangHoa
            HangHoa savedHangHoa = hangHoaRepo.save(hangHoa);
            return Optional.of(HangHoaMapper.toDTO(savedHangHoa));
        } catch (Exception e) {
            System.err.println("Error occurred while adding new product: " + e.getMessage());
            return Optional.empty();
        }
    }

    public Optional<HangHoaDTO> updateHangHoa(Integer idHangHoa, HangHoaDTO dto) {
        Optional<LoaiHangHoa> loaiHangHoaOpt = loaiHangHoaRepo.findById(dto.getIdLoaiHangHoa());
        if (!loaiHangHoaOpt.isPresent()) {
            throw new EntityNotFoundException("LoaiHangHoa not found for id: " + dto.getIdLoaiHangHoa());
        }

        LoaiHangHoa loaiHangHoa = loaiHangHoaOpt.get();
        HangHoa hangHoa = HangHoaMapper.toEntity(dto);
        hangHoa.setIdHangHoa(idHangHoa);

        HangHoa updatedHangHoa = hangHoaRepo.save(hangHoa);
        return Optional.of(HangHoaMapper.toDTO(updatedHangHoa));
    }


    @Override
    public Iterable<HangHoaDTO> getAllHangHoaSorted(String sortBy, String direction) {
        try {
            List<HangHoa> hhList;
            if (direction.equals("asc")) {
                hhList = hangHoaRepo.findAll(Sort.by(Sort.Direction.ASC, sortBy));
            } else {
                hhList = hangHoaRepo.findAll(Sort.by(Sort.Direction.DESC, sortBy));
            }

            List<HangHoaDTO> hangHoaDTOList = hhList.stream()
                    .map(hangHoa -> {
                        try {
                            return HangHoaMapper.toDTO(hangHoa);
                        } catch (Exception e) {
                            System.err.println("Error converting HangHoa to HangHoaDTO: " + e.getMessage());
                            return null; // Hoặc xử lý cách khác
                        }
                    })
                    .filter(Objects::nonNull) // Lọc bỏ giá trị null
                    .collect(Collectors.toList());

            return hangHoaDTOList;

        } catch (IllegalArgumentException e) {
            System.err.println("Invalid sorting field: " + sortBy);
            return Collections.emptyList();
        } catch (Exception e) {
            System.err.println("An error occurred while fetching sorted merchans: " + e.getMessage());
            return Collections.emptyList();
        }
    }


    @Override
    public List<HangHoaDTO> findByTenHangHoa(String keyword) {
        List<HangHoa> hangHoaList = hangHoaRepo.findByTenHangHoaContainingIgnoreCase(keyword);
        if (hangHoaList.isEmpty()) {
            log.warn("No product found with the keyword: {}", keyword);
            throw new EntityNotFoundException("No merchandise found for the keyword: " + keyword);
        }
        log.info("Found {} products with the keyword: {}", hangHoaList.size(), keyword);
        return hangHoaList.stream()
                .map(HangHoaMapper::toDTO)
                .collect(Collectors.toList());
    }


    @Override
    public String deleteHangHoa(int idHangHoa) {
        try {
            hangHoaRepo.deleteById(idHangHoa);
            return "Product removed: ID = " + idHangHoa;
        } catch (Exception e) {
            System.err.println("Error occurred while deleting product: " + e.getMessage());
            return "Failed to remove product: ID = " + idHangHoa;
        }
    }
}
