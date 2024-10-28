package com.project.flightManagement.Service.Impl;

import com.project.flightManagement.DTO.HanhKhachDTO.HanhKhachCreateDTO;
import com.project.flightManagement.DTO.HanhKhachDTO.HanhKhachUpdateDTO;
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
import com.project.flightManagement.Service.HanhKhachService;
import com.project.flightManagement.Service.KhachHangService;
import com.project.flightManagement.Service.VeService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Lazy;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;

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
        try {
            Ve ve = veMapper.toEntity(veCreateDTO);
            veRepository.save(ve);
            return true;
        }catch (Exception e) {
            System.out.println("loi tao ve: " + e);
            return false;
        }
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
    public void createAutoVeByIdMayBay(int idMayBay) {
        // B1: lấy tat ca cho ngoi theo may bay
        // B2: duyet qua cho ngoi (VIP, thuong,...)
        // B3: thay doi gia ve tuy vao (VIP, thuong,...) -> nhap % (van chua toi uu lam)
        // B4: insert vao db
    }
}
