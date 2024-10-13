package com.project.flightManagement.Service.Impl;

import com.project.flightManagement.DTO.HangBayDTO.HangBayDTO;
import com.project.flightManagement.DTO.MayBayDTO.MayBayDTO;
import com.project.flightManagement.Enum.ActiveEnum;
import com.project.flightManagement.Mapper.MayBayMapper;
import com.project.flightManagement.Model.HangBay;
import com.project.flightManagement.Model.MayBay;
import com.project.flightManagement.Repository.MayBayRepository;
import com.project.flightManagement.Service.MayBayService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Sort;
import org.springframework.stereotype.Service;

import java.util.Collections;
import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;
import java.util.stream.StreamSupport;

@Service
public class MayBayServiceImpl implements MayBayService {
    @Autowired
    private MayBayRepository mbRepo;

    @Override
    public Optional<MayBayDTO> getMayBayById(int id) {
        try {
            Optional<MayBay> mb = mbRepo.findById(id);
            Optional<MayBayDTO> mbDTO = mb.map(MayBayMapper::toDTO);
            return mbDTO;
        } catch (Exception e) {
            System.err.println("Error occurred while get plane: " + e.getMessage());
            return null;
        }
    }

    @Override
    public Iterable<MayBayDTO> getAllMayBay() {
        try{
            Iterable<MayBay> mbList = mbRepo.findAll();
            Iterable<MayBayDTO> mbDTOList = StreamSupport.stream(mbList.spliterator(), false).map(MayBayMapper::toDTO).toList();
            return mbDTOList;
        } catch (Exception e) {
            System.err.println("Error occurred while fetching plane: " + e.getMessage());
            return Collections.emptyList();
        }
    }

    @Override
    public Optional<MayBayDTO> getMayBayBySoHieu(String soHieu){
        try {
            MayBay mb = mbRepo.findBySoHieu(soHieu);
            return Optional.ofNullable(MayBayMapper.toDTO(mb));
        } catch (Exception e) {
            return Optional.empty();
        }
    }
    @Override
    public List<MayBayDTO> findMayBayByTenMayBay(String keyword){
        List<MayBay> mayBayList = mbRepo.findByKeywordContainingIgnoreCase(keyword);
        if (mayBayList.isEmpty()) {
            System.out.println("No plane found with the keyword: " + keyword);
        } else {
            MayBay kh = mayBayList.get(0); // Dùng get(0) thay vì getFirst()
            System.out.println("Id mb found: " + kh.getTenMayBay());
        }
        return mayBayList.stream()
                .map(MayBayMapper::toDTO)
                .collect(Collectors.toList());
    }
    @Override
    public Iterable<MayBayDTO> getAllMayBaySorted(String sortBy, String order){
        try {
            List<MayBay> mayBayList;
            if(order.equals("asc")){
                mayBayList = mbRepo.findAll(Sort.by(Sort.Direction.ASC, sortBy));
            } else {
                mayBayList = mbRepo.findAll(Sort.by(Sort.Direction.DESC, sortBy));
            }
            List<MayBayDTO> mayBayDTOList = mayBayList.stream().map(MayBayMapper::toDTO).collect(Collectors.toList());
            return mayBayDTOList;
        } catch (IllegalArgumentException e) {
            // Xử lý lỗi nếu tham số sortBy không hợp lệ hoặc có lỗi khác liên quan đến tham số
            System.err.println("Invalid sorting field: " + sortBy);
            return Collections.emptyList(); // Trả về danh sách rỗng
        } catch (Exception e) {
            // Xử lý các lỗi không lường trước khác
            System.err.println("An error occurred while fetching sorted planes: " + e.getMessage());
            return Collections.emptyList(); // Trả về danh sách rỗng nếu có lỗi
        }
    }
    @Override
    public List<MayBayDTO> findMayBayByHangBay(HangBay hangBay){
        List<MayBay> listMb = mbRepo.findByHangBay(hangBay);
        return listMb.stream().map(MayBayMapper::toDTO).collect(Collectors.toList());
    }
    @Override
    public Optional<MayBayDTO> addNewMayBay(MayBayDTO mbDTO){
        try {
            MayBay mb = MayBayMapper.toEntity(mbDTO);
            MayBay mbSaved = mbRepo.save(mb);
            return Optional.of(MayBayMapper.toDTO(mbSaved));
        }catch (Exception e){
            System.err.println("Error occurred while save plane: " + e.getMessage());
            return null;
        }
    }
    @Override
    public  Optional<MayBayDTO> updateMayBay(MayBayDTO mayBayDTO){
        Optional<MayBay> existingMB = mbRepo.findById(mayBayDTO.getIdMayBay());
        if(existingMB.isPresent()){
            MayBay mb = MayBayMapper.toEntity(mayBayDTO);
            MayBay mbSaved = mbRepo.save(mb);
            return Optional.of(MayBayMapper.toDTO(mbSaved));
        }else {
            System.err.println("Plane does not existing!!!");
            return Optional.empty();
        }
    }
    @Override
    public Optional<MayBayDTO> blockMayBay(int id){
        Optional<MayBay> existingMB = mbRepo.findById(id);
        if(existingMB.isPresent()){
            MayBay mb = existingMB.get();
            mb.setTrangThaiActive(ActiveEnum.IN_ACTIVE);
            MayBay savedMB = mbRepo.save(mb);
            return Optional.of(MayBayMapper.toDTO(savedMB));
        } else {
            System.err.println("Plane does not existing!!!");
            return Optional.empty();
        }
    }
    @Override
    public Optional<MayBayDTO> unblockMayBay(int id){
        Optional<MayBay> existingMB = mbRepo.findById(id);
        if(existingMB.isPresent()){
            MayBay mb = existingMB.get();
            mb.setTrangThaiActive(ActiveEnum.ACTIVE);
            MayBay savedMB = mbRepo.save(mb);
            return Optional.of(MayBayMapper.toDTO(savedMB));
        } else {
            System.err.println("Plane does not existing!!!");
            return Optional.empty();
        }
    }
}
