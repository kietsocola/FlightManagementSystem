package com.project.flightManagement.Service.Impl;

import com.project.flightManagement.DTO.ChoNgoiDTO.ChoNgoiDTO;
import com.project.flightManagement.DTO.ChuyenBayDTO.ChuyenBayDTO;
import com.project.flightManagement.DTO.HanhKhachDTO.HanhKhachCreateDTO;
import com.project.flightManagement.DTO.HanhKhachDTO.HanhKhachUpdateDTO;
import com.project.flightManagement.DTO.MayBayDTO.MayBayDTO;
import com.project.flightManagement.DTO.VeDTO.*;
import com.project.flightManagement.Enum.VeEnum;
import com.project.flightManagement.Exception.IdMismatchException;
import com.project.flightManagement.Exception.NoUpdateRequiredException;
import com.project.flightManagement.Exception.ResourceNotFoundException;
import com.project.flightManagement.Mapper.*;
import com.project.flightManagement.Model.*;
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
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collections;
import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;
import java.util.stream.StreamSupport;

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
    private ChuyenBayService chuyenBayService;

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

    // @Override
    // public boolean updateVe(int idVe, VeUpdateDTO veUpdateDTO) {
    // try {
    // if (idVe != veUpdateDTO.getIdVe()) {
    // throw new IllegalArgumentException("ID in the path and DTO do not match.");
    // }
    //
    // // Lấy đối tượng Ve hiện tại
    // Ve existingVe = veRepository.findById(idVe)
    // .orElseThrow(() -> new RuntimeException("Ve not found with id: " + idVe));
    //
    // // Cập nhật các thuộc tính của Ve từ DTO
    // existingVe.setGiaVe(veUpdateDTO.getGiaVe());
    // existingVe.setTrangThaiActive(veUpdateDTO.getTrangThaiActive());
    // existingVe.setTrangThai(veUpdateDTO.getTrangThai());
    //
    // HanhKhach hanhKhach = existingVe.getHanhKhach();
    // if (hanhKhach != null) {
    // System.out.println("Tên hành khách: " + hanhKhach.getHoTen());
    // if (veRepository.existsByHanhKhach_IdHanhKhach(hanhKhach.getIdHanhKhach()) //
    // IdHangVe = 1 => Hang pho thong
    // && !hanhKhach.getHoTen().equals(veUpdateDTO.getTenHanhKhach()) &&
    // existingVe.getHangVe().getIdHangVe() != 1 ) {
    //
    // System.out.println("Se doi ten hanh khach thanh: " +
    // veUpdateDTO.getTenHanhKhach());
    // HanhKhach hanhKhachMoi = taoMoiHanhKhachVaDoiTen(hanhKhach,
    // veUpdateDTO.getTenHanhKhach());
    // existingVe.setHanhKhach(hanhKhachMoi);
    // } else {
    // System.out.println("Khong can doi ten");
    // }
    // } else {
    // System.out.println("Không lấy được hành khách");
    // }
    //
    // // Lưu lại Ve sau khi cập nhật hoàn tất
    // veRepository.save(existingVe);
    // return true;
    // } catch (Exception e) {
    // System.out.println("Error during updateVe: " + e);
    // return false;
    // }
    // }


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
        if (hanhKhach != null
                && veRepository.existsByHanhKhach_IdHanhKhach(hanhKhach.getIdHanhKhach())
                && !hanhKhach.getHoTen().equals(veUpdateDTO.getTenHanhKhach())) {

            // Tạo đối tượng hành khách mới và cập nhật tên
            HanhKhach hanhKhachMoi = taoMoiHanhKhachVaDoiTen(hanhKhach, veUpdateDTO.getTenHanhKhach());
            existingVe.setHanhKhach(hanhKhachMoi);
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
    public void createAutoVeByIdChuyenBay(int idChuyenBay, double giaVeHangPhoThong, double giaVeHangThuongGia) {
        System.out.println("hello createAutoVeByIdMayBay");
        int idMayBay = chuyenBayService.getChuyenBayEntityById(idChuyenBay).getMayBay().getIdMayBay();
        System.out.println("id May bay la: " + idMayBay);
        Iterable<ChoNgoiDTO> listCN = choNgoiService.getChoNgoiByMayBay(mayBayService.getMayBayById(idMayBay).get());
        for (ChoNgoiDTO c : listCN) {
            VeCreateDTO veCreateDTO = new VeCreateDTO();
            veCreateDTO.setIdChuyenBay(idChuyenBay);
            veCreateDTO.setIdChoNgoi(c.getIdChoNgoi());
            System.out.println("id Cho ngoi: " + c.getIdChoNgoi());
            veCreateDTO.setIdHangVe(c.getHangVe().getIdHangVe());
            veCreateDTO.setIdLoaiVe(1);
            if(c.getHangVe().getIdHangVe() == 1) { // hang pho thong
                System.out.println("idHang ve th: " + c.getHangVe().getIdHangVe());
                veCreateDTO.setGiaVe(giaVeHangPhoThong);
            } else if (c.getHangVe().getIdHangVe() == 2) { // hang thuong gia
                System.out.println("idHang ve tg: " + c.getHangVe().getIdHangVe());
                veCreateDTO.setGiaVe(giaVeHangThuongGia);
            }
            veService.createVe(veCreateDTO);
        }
    }

    @Override
    public void updateAutoGiaVeByIdChuyenBay(int idChuyenBay, double giaVeHangPhoThong, double giaVeHangThuongGia) {
        System.out.println("hello updateAutoVeByIdMayBay");
        Page<VeDTO> veDTOPage = veService.getAllVeByIdChuyenBay(idChuyenBay, 0, 500);
        for (VeDTO veDTO : veDTOPage) {
            Optional<Ve> veOptional = veRepository.findById(veDTO.getIdVe());
            Ve ve = veOptional.get();
            if (veDTO.getHangVe().getIdHangVe() == 1) {
                ve.setGiaVe(giaVeHangPhoThong);
            } else {
                ve.setGiaVe(giaVeHangThuongGia);
            }
            veRepository.save(ve);
        }
    }

    @Override
    public Page<VeDTO> searchVeMaVaAndDateBay(String maVe, LocalDate startDate, LocalDate endDate, int page, int size) {
        Page<Ve> vePage = veRepository.findByMaVeContainingIgnoreCaseAndNgayBayBetween(maVe, startDate, endDate,
                PageRequest.of(page, size));
        return vePage.map(veMapper::toDto);
    }

    @Override
    public Page<VeDTO> searchVeMaVa(String maVe, int page, int size) {
        Page<Ve> vePage = veRepository.findByMaVeContainingIgnoreCase(maVe, PageRequest.of(page, size));
        return vePage.map(veMapper::toDto);
    }

    public List<VeEnum> getAllVeStatuses() {
        return Arrays.asList(VeEnum.values());
    }

    @Override
    public List<VeDTO> getAllVeByIdChuyenBayNotPaging(int idChuyenBay) {
        // Lấy danh sách vé từ repository
        List<Ve> veList = veRepository.findByChuyenBay_IdChuyenBay(idChuyenBay);

        // Chuyển veList thành stream và sử dụng map để chuyển đổi thành VeDTO
        return veList.stream()
                .map(veMapper::toDto)
                .collect(Collectors.toList());
    }

    @Override
    public GiaVeDTO getAllGiaVe(int idChuyenBay) {
        GiaVeDTO giaVeDTO = new GiaVeDTO();
        List<Ve> veList = veRepository.findByChuyenBay_IdChuyenBay(idChuyenBay);

        for (Ve ve : veList) {
            // Kiểm tra hạng vé thường
            if (ve.getHangVe().getIdHangVe() == 1) { // Hạng thường
                giaVeDTO.setGiaVeThuong(ve.getGiaVe());
            } else { // Hạng thương gia
                giaVeDTO.setGiaVeThuongGia(ve.getGiaVe());
            }

            // Kiểm tra nếu cả 2 giá vé đều có giá trị (không phải null)
            if (giaVeDTO.getGiaVeThuong() != null && giaVeDTO.getGiaVeThuongGia() != null) {
                break;
            }
        }

        return giaVeDTO;
    }

    @Override
    public Iterable<VeDTO> getAllByIdChuyenBayByLam(int idChuyenBay) {
        try{
            List<Ve> listve =  veRepository.findByChuyenBay_IdChuyenBay(idChuyenBay);
            Iterable<VeDTO> listcbDTO = StreamSupport.stream(listve.spliterator(),false)
                    .map(veMapper::toDto)
                    .toList();
            return listcbDTO;
        }catch (Exception e){
            System.err.println("error occurred while fetching customers :" + e.getMessage());
            return Collections.emptyList();
        }
    }

    @Override
    public List<VeDTO> getAllVe(){
        try {
            Iterable<Ve> listVe = veRepository.findAll();
            return StreamSupport.stream(listVe.spliterator(), false)
                    .map(veMapper::toDto)
                    .collect(Collectors.toList());
        } catch (Exception e) {
            System.err.println("Error occurred while fetching tickets: " + e);
            return Collections.emptyList();
        }
    }
}
