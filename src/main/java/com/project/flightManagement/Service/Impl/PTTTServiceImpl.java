package com.project.flightManagement.Service.Impl;

import com.project.flightManagement.DTO.PTTTDTO.PTTTDTO;
import com.project.flightManagement.Mapper.PTTTMapper;
import com.project.flightManagement.Model.PhuongThucThanhToan;
import com.project.flightManagement.Repository.PTTTRepository;
import com.project.flightManagement.Service.PTTTService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Sort;
import org.springframework.stereotype.Service;

import java.util.Collections;
import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;
import java.util.stream.StreamSupport;

@Service
public class PTTTServiceImpl implements PTTTService {

    @Autowired
    private PTTTRepository ptttRepo;

    @Override
    public Iterable<PTTTDTO> getAllPTTT() {
        try {
            Iterable<PhuongThucThanhToan> listPTTT = ptttRepo.findAll();
            Iterable<PTTTDTO> listPTTTDTO = StreamSupport.stream(listPTTT.spliterator(), false)
                    .map(PTTTMapper::toDTO)
                    .toList();
            return listPTTTDTO;
        } catch (Exception e) {
            System.err.println("Error occurred while fetching Payment Methods: " + e.getMessage());
            return Collections.emptyList();
        }
    }

    @Override
    public Optional<PTTTDTO> getPTTTByID(int idPTTT) {
        try {
            Optional<PhuongThucThanhToan> pttt = ptttRepo.findById(idPTTT);
            Optional<PTTTDTO> ptttDTO = pttt.map(PTTTMapper::toDTO);
            return ptttDTO;
        } catch (Exception e) {
            System.err.println("Error occurred while get Payment Method: " + e);
            return null;
        }
    }

    @Override
    public Optional<PTTTDTO> addPTTT(PTTTDTO ptttDTO) {
        try {
            PhuongThucThanhToan pttt = PTTTMapper.toEntity(ptttDTO);
            PhuongThucThanhToan savedPTTT = ptttRepo.save(pttt);
            return Optional.of(PTTTMapper.toDTO(savedPTTT));
        } catch (Exception e) {
            System.err.println("Error occurred while save Payment method: " + e);
            return null;
        }
    }

    @Override
    public Optional<PTTTDTO> updatePTTT(PTTTDTO ptttDTO) {
        Optional<PhuongThucThanhToan> existingPTTT = ptttRepo.findById(ptttDTO.getIdPTTT());
        if (existingPTTT.isPresent()) {
            PhuongThucThanhToan PTTT = PTTTMapper.toEntity(ptttDTO);
            PhuongThucThanhToan updatedPTTT = ptttRepo.save(PTTT);
            return Optional.of(PTTTMapper.toDTO(updatedPTTT));
        }else {
            System.err.println("Payment method does not existing!!!");
            return Optional.empty();
        }
    }

    @Override
    public List<PTTTDTO> findPhuongThucThanhToanByKeyWord(String keyword) {
        List<PhuongThucThanhToan> phuongThucThanhToanList = ptttRepo.getPhuongThucThanhToanByKeyWord(keyword);
        if (phuongThucThanhToanList.isEmpty()) {
            System.out.println("Không tìm thấy phương thức thanh toán với từ khóa " + keyword);
        } else {
            PhuongThucThanhToan pttt = phuongThucThanhToanList.get(0);
            System.out.println("Found: " + pttt.getTenPhuongThucTT());
        }
        return phuongThucThanhToanList.stream().map(PTTTMapper::toDTO).collect(Collectors.toList());
    }

    @Override
    public Iterable<PTTTDTO> getAllPTTTSorted(String sortBy, String direction) {
        try {
            List<PhuongThucThanhToan> phuongThucThanhToanList;
            // Lấy danh sách KhachHang từ cơ sở dữ liệu và sắp xếp theo sortBy
            if(direction.equals("asc")) {
                phuongThucThanhToanList = ptttRepo.findAll(Sort.by(Sort.Direction.ASC, sortBy));
            } else {
                phuongThucThanhToanList = ptttRepo.findAll(Sort.by(Sort.Direction.DESC, sortBy));
            }

            // Chuyển đổi từ KhachHang sang KhachHangDTO
            List<PTTTDTO> phuongThucThanhToanDTOList = phuongThucThanhToanList.stream()
                    .map(PTTTMapper::toDTO)
                    .collect(Collectors.toList());

            return phuongThucThanhToanDTOList;

        } catch (IllegalArgumentException e) {
            // Xử lý lỗi nếu tham số sortBy không hợp lệ hoặc có lỗi khác liên quan đến tham số
            System.err.println("Invalid sorting field: " + sortBy);
            return Collections.emptyList(); // Trả về danh sách rỗng

        } catch (Exception e) {
            // Xử lý các lỗi không lường trước khác
            System.err.println("An error occurred while fetching sorted payment methods: " + e.getMessage());
            return Collections.emptyList(); // Trả về danh sách rỗng nếu có lỗi
        }
    }

    @Override
    public Optional<PTTTDTO> getPTTTByTen(String tenPTTT) {
        try {
            Optional<PhuongThucThanhToan> pttt = ptttRepo.getPhuongThucThanhToanByTen(tenPTTT);
            if (pttt.isPresent()) {
                System.out.println("Tìm thấy phương thức thanh toán với tên: " + tenPTTT);
                return pttt.map(PTTTMapper::toDTO);
            } else {
                System.out.println("Không tìm thấy phương thức thanh toán với tên: " + tenPTTT);
                return Optional.empty();
            }
        } catch (Exception e) {
            System.err.println("Lỗi: " + e);
            return Optional.empty();
        }
    }
}
