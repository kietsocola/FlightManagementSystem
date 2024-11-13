package com.project.flightManagement.Service.Impl;

import com.project.flightManagement.DTO.HangHoaDTO.HangHoaDTO;
import com.project.flightManagement.Enum.ActiveEnum;
import com.project.flightManagement.Mapper.HangHoaMapper;
import com.project.flightManagement.Model.HangHoa;
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
        if (hangHoaDTO == null) {
            System.err.println("DTO hàng hóa không thể null");
            return Optional.empty();
        }

        try {
            // Kiểm tra giá trị mã hàng hóa trước khi thiết lập
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

            // Tính giá phát sinh
            calculateGiaPhatSinh(hangHoa, loaiHangHoaOpt.get());

            // Lưu đối tượng HangHoa
            HangHoa savedHangHoa = hangHoaRepo.save(hangHoa);
            return Optional.of(HangHoaMapper.toDTO(savedHangHoa));
        } catch (Exception e) {
            System.err.println("Lỗi xảy ra trong quá trình thêm sản phẩm mới: " + e.getMessage());
            // Bạn có thể ném một Exception tùy chỉnh ở đây nếu cần
            return Optional.empty();
        }
    }


    @Override
    public Optional<HangHoaDTO> updateHangHoa(Integer idHangHoa, HangHoaDTO dto) {
        Optional<LoaiHangHoa> loaiHangHoaOpt = loaiHangHoaRepo.findById(dto.getIdLoaiHangHoa());
        if (!loaiHangHoaOpt.isPresent()) {
            throw new EntityNotFoundException("LoaiHangHoa không tìm thấy cho id: " + dto.getIdLoaiHangHoa());
        }

        // Tìm đối tượng HangHoa hiện tại từ cơ sở dữ liệu
        HangHoa existingHangHoa = hangHoaRepo.findById(idHangHoa)
                .orElseThrow(() -> new EntityNotFoundException("HangHoa không tìm thấy cho id: " + idHangHoa));

        // Chuyển đổi DTO thành Entity, nhưng giữ nguyên mã hàng hóa
        HangHoa updatedHangHoa = HangHoaMapper.toEntity(dto, existingHangHoa);

        // Tính giá phát sinh (nếu cần)
        calculateGiaPhatSinh(updatedHangHoa, loaiHangHoaOpt.get());

        // Lưu cập nhật vào cơ sở dữ liệu
        HangHoa savedHangHoa = hangHoaRepo.save(updatedHangHoa);
        return Optional.of(HangHoaMapper.toDTO(savedHangHoa));
    }

    private void calculateGiaPhatSinh(HangHoa hangHoa, LoaiHangHoa loaiHangHoa) {
        double gioiHanKg = loaiHangHoa.getGioiHanKg();
        double taiTrong = hangHoa.getTaiTrong();

        // Khởi tạo giá phát sinh mặc định là 0
        hangHoa.setGiaPhatSinh(0);

        // Nếu trọng tải lớn hơn giới hạn kg, tính giá phát sinh
        if (taiTrong > gioiHanKg) {
            double excessWeight = taiTrong - gioiHanKg;
            // Tính giá phát sinh
            hangHoa.setGiaPhatSinh(excessWeight * loaiHangHoa.getGiaThemMoiKg() );
        }
    }

    @Override
    public Optional<HangHoaDTO> blockHangHoa(int id){
        Optional<HangHoa> existingHH = hangHoaRepo.findById(id);
        if(existingHH.isPresent()){
            HangHoa hangHoa = existingHH.get();
            hangHoa.setTrangThaiActive(ActiveEnum.IN_ACTIVE);
            HangHoa updatedHangHoa = hangHoaRepo.save(hangHoa);
            return Optional.of(HangHoaMapper.toDTO(updatedHangHoa));
        } else {
            System.err.println("Merchandise does not existing!!!");
            return Optional.empty();
        }
    }

    @Override
    public Optional<HangHoaDTO> unblockHangHoa(int id){
        Optional<HangHoa> existingHH = hangHoaRepo.findById(id);
        if(existingHH.isPresent()){
            HangHoa hangHoa = existingHH.get();
            hangHoa.setTrangThaiActive(ActiveEnum.ACTIVE);
            HangHoa updatedHangHoa = hangHoaRepo.save(hangHoa);
            return Optional.of(HangHoaMapper.toDTO(updatedHangHoa));
        } else {
            System.err.println("Merchandise does not existing!!!");
            return Optional.empty();
        }
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


}
