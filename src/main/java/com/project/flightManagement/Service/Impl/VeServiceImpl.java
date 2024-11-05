package com.project.flightManagement.Service.Impl;

import com.project.flightManagement.DTO.ChoNgoiDTO.ChoNgoiDTO;
import com.project.flightManagement.DTO.HanhKhachDTO.HanhKhachCreateDTO;
import com.project.flightManagement.DTO.HanhKhachDTO.HanhKhachUpdateDTO;
import com.project.flightManagement.DTO.MayBayDTO.MayBayDTO;
import com.project.flightManagement.DTO.VeDTO.VeCreateDTO;
import com.project.flightManagement.DTO.VeDTO.VeDTO;
import com.project.flightManagement.DTO.VeDTO.VeUpdateDTO;
import com.project.flightManagement.DTO.VeDTO.VeUpdateHanhKhachDTO;
import com.project.flightManagement.Exception.IdMismatchException;
import com.project.flightManagement.Exception.NoUpdateRequiredException;
import com.project.flightManagement.Exception.ResourceNotFoundException;
import com.project.flightManagement.Mapper.HanhKhachMapper;
import com.project.flightManagement.Mapper.KhachHangMapper;
import com.project.flightManagement.Mapper.LoaiVeMapper;
import com.project.flightManagement.Mapper.VeMapper;
import com.project.flightManagement.Model.HanhKhach;
import com.project.flightManagement.Model.KhachHang;
import com.project.flightManagement.Model.LoaiVe;
import com.project.flightManagement.Model.Ve;
import com.project.flightManagement.Repository.HanhKhachRepository;
import com.project.flightManagement.Repository.KhachHangRepository;
import com.project.flightManagement.Repository.VeRepository;
import com.project.flightManagement.Service.*;
import jakarta.transaction.Transactional;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Lazy;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;

import java.time.LocalDate;
import java.util.Optional;

@Service
public class VeServiceImpl implements VeService {
    @Autowired
    private VeRepository veRepository;
    @Autowired
    @Lazy
    private VeMapper veMapper;
    @Autowired
    private KhachHangRepository khachHangRepository;
    @Autowired
    @Lazy
    private HanhKhachService hanhKhachService;
    @Autowired
    private HanhKhachMapper hanhKhachMapper;
    @Autowired
    private MayBayService mayBayService;
    @Autowired
    private ChoNgoiService choNgoiService;

    @Autowired
    @Lazy
    private VeService veService;


    @Override
    public Page<VeDTO> getAllVe(int page, int size) {
        Pageable pageable = PageRequest.of(page, size);
        Page<Ve> vePage = veRepository.findAll(pageable);
        return vePage.map(veMapper::toDto);
    }

    @Override
    public Page<VeDTO> getAllVeByIdChuyenBay(int idChuyenBay, int page, int size) {
        Pageable pageable = PageRequest.of(page, size);
        Page<Ve> vePage = veRepository.findByChuyenBay_IdChuyenBay(idChuyenBay, pageable);
        return vePage.map(veMapper::toDto);
    }

//    @Override
//    public boolean updateVe(int idVe, VeUpdateDTO veUpdateDTO) {
//        try {
//            if (idVe != veUpdateDTO.getIdVe()) {
//                throw new IllegalArgumentException("ID in the path and DTO do not match.");
//            }
//
//            // Lấy đối tượng Ve hiện tại
//            Ve existingVe = veRepository.findById(idVe)
//                    .orElseThrow(() -> new RuntimeException("Ve not found with id: " + idVe));
//
//            // Cập nhật các thuộc tính của Ve từ DTO
//            existingVe.setGiaVe(veUpdateDTO.getGiaVe());
//            existingVe.setTrangThaiActive(veUpdateDTO.getTrangThaiActive());
//            existingVe.setTrangThai(veUpdateDTO.getTrangThai());
//
//            HanhKhach hanhKhach = existingVe.getHanhKhach();
//            if (hanhKhach != null) {
//                System.out.println("Tên hành khách: " + hanhKhach.getHoTen());
//                if (veRepository.existsByHanhKhach_IdHanhKhach(hanhKhach.getIdHanhKhach()) // IdHangVe = 1 => Hang pho thong
//                        && !hanhKhach.getHoTen().equals(veUpdateDTO.getTenHanhKhach()) && existingVe.getHangVe().getIdHangVe() != 1 ) {
//
//                    System.out.println("Se doi ten hanh khach thanh: " + veUpdateDTO.getTenHanhKhach());
//                    HanhKhach hanhKhachMoi =  taoMoiHanhKhachVaDoiTen(hanhKhach, veUpdateDTO.getTenHanhKhach());
//                    existingVe.setHanhKhach(hanhKhachMoi);
//                } else {
//                    System.out.println("Khong can doi ten");
//                }
//            } else {
//                System.out.println("Không lấy được hành khách");
//            }
//
//            // Lưu lại Ve sau khi cập nhật hoàn tất
//            veRepository.save(existingVe);
//            return true;
//        } catch (Exception e) {
//            System.out.println("Error during updateVe: " + e);
//            return false;
//        }
//    }


    @Override
    public boolean updateVe(int idVe, VeUpdateDTO veUpdateDTO) {
        if (idVe != veUpdateDTO.getIdVe()) {
            throw new IdMismatchException("ID in the path and DTO do not match.");
        }

        // Lấy đối tượng Ve hiện tại
        Ve existingVe = veRepository.findById(idVe)
                .orElseThrow(() -> new ResourceNotFoundException("Ve", idVe));

        // Cập nhật các thuộc tính của Ve từ DTO
        existingVe.setGiaVe(veUpdateDTO.getGiaVe());
        existingVe.setTrangThaiActive(veUpdateDTO.getTrangThaiActive());
        existingVe.setTrangThai(veUpdateDTO.getTrangThai());

        HanhKhach hanhKhach = existingVe.getHanhKhach();
        if (hanhKhach != null) {
            if (veRepository.existsByHanhKhach_IdHanhKhach(hanhKhach.getIdHanhKhach())
                    && !hanhKhach.getHoTen().equals(veUpdateDTO.getTenHanhKhach())
                    && existingVe.getHangVe().getIdHangVe() != 1) {

                // Tạo đối tượng hành khách mới và cập nhật
                HanhKhach hanhKhachMoi = taoMoiHanhKhachVaDoiTen(hanhKhach, veUpdateDTO.getTenHanhKhach());
                existingVe.setHanhKhach(hanhKhachMoi);
                // sau nay muon toi uu thi phai thong qua createDTO vi no khong chua id
                // can so sanh 2 object co bang nhau khong
            } else {
                throw new NoUpdateRequiredException("No update required for the customer's name.");
            }
        }
        // Lưu lại Ve sau khi cập nhật hoàn tất
        veRepository.save(existingVe);
        return true;
    }

    private HanhKhach taoMoiHanhKhachVaDoiTen(HanhKhach hanhKhach, String tenMoi) {
        HanhKhachCreateDTO hanhKhachCreateDTO = hanhKhachMapper.toHanhKhachCreateDTO(hanhKhach);
        hanhKhachCreateDTO.setHoTen(tenMoi);
        return hanhKhachService.createHanhKhach(hanhKhachCreateDTO);
    }

    @Override
    public VeDTO getVeById(int idVe) {
        Optional<Ve> ve = veRepository.findById(idVe);
        if (ve.isEmpty()) {
            return null;
        }
        return veMapper.toDto(ve.get());
    }

    @Override
    public boolean createVe(VeCreateDTO veCreateDTO) {
        Ve ve = veMapper.toEntity(veCreateDTO);
        veRepository.save(ve);
        return true;
    }

    @Override
    public boolean updateHanhKhachVe(VeUpdateHanhKhachDTO veUpdateHanhKhachDTO) {
        try {
            Optional<Ve> veOptional = veRepository.findById(veUpdateHanhKhachDTO.getIdVe());
            Optional<HanhKhach> hanhKhach = hanhKhachService.getHanhKhachById(veUpdateHanhKhachDTO.getIdHanhKhach());
            Ve ve = veOptional.get();
            ve.setHanhKhach(hanhKhach.get());
            ve.setTrangThai(veUpdateHanhKhachDTO.getTrangThai());
            veRepository.save(ve);
            return true;
        } catch (Exception e) {
            System.out.println("Ham updateHanhKhachVe: " + e);
            return false;
        }
    }

    @Override
    @Transactional
    public void createAutoVeByIdMayBay(int idChuyenBay, int idMayBay, double giaVeHangPhoThong, double giaVeHangThuongGia, double giaVeHangNhat) {
        System.out.println("hello createAutoVeByIdMayBay");
        Iterable<ChoNgoiDTO> listCN = choNgoiService.getChoNgoiByMayBay(mayBayService.getMayBayById(idMayBay).get());
        for (ChoNgoiDTO c : listCN) {
            VeCreateDTO veCreateDTO = new VeCreateDTO();
            veCreateDTO.setIdChuyenBay(idChuyenBay);
            veCreateDTO.setIdChoNgoi(c.getIdChoNgoi());
            veCreateDTO.setIdHangVe(c.getHangVe().getIdHangVe());
            veCreateDTO.setIdLoaiVe(1);
            if(c.getHangVe().getIdHangVe() == 1) { // hang pho thong
                veCreateDTO.setGiaVe(giaVeHangPhoThong);
            } else if (c.getHangVe().getIdHangVe() == 2) { // hang thuong gia
                veCreateDTO.setGiaVe(giaVeHangThuongGia);
            } else { // hang nhat
                veCreateDTO.setGiaVe(giaVeHangNhat);
            }
            veService.createVe(veCreateDTO);
        }
    }

    @Override
    public Page<VeDTO> searchVeMaVaAndDateBay(String maVe, LocalDate startDate, LocalDate endDate, int page, int size) {
        Page<Ve> vePage = veRepository.findByMaVeContainingIgnoreCaseAndNgayBayBetween(maVe, startDate, endDate ,PageRequest.of(page, size));
        return vePage.map(veMapper::toDto);
    }

    @Override
    public Page<VeDTO> searchVeMaVa(String maVe, int page, int size) {
        Page<Ve> vePage = veRepository.findByMaVeContainingIgnoreCase(maVe,PageRequest.of(page, size));
        return vePage.map(veMapper::toDto);
    }
}
