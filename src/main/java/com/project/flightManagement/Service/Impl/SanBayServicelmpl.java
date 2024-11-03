package com.project.flightManagement.Service.Impl;

import com.project.flightManagement.DTO.MayBayDTO.MayBayDTO;
import com.project.flightManagement.DTO.SanBayDTO.SanBayDTO;
import com.project.flightManagement.DTO.ThanhPhoDTO.ThanhPhoDTO;
import com.project.flightManagement.Enum.ActiveEnum;
import com.project.flightManagement.Mapper.MayBayMapper;
import com.project.flightManagement.Mapper.SanBayMapper;
import com.project.flightManagement.Mapper.ThanhPhoMapper;
import com.project.flightManagement.Model.MayBay;
import com.project.flightManagement.Model.QuocGia;
import com.project.flightManagement.Model.SanBay;
import com.project.flightManagement.Model.ThanhPho;
import com.project.flightManagement.Repository.QuocGiaRepository;
import com.project.flightManagement.Repository.SanBayRepository;
import com.project.flightManagement.Repository.ThanhPhoRepository;
import com.project.flightManagement.Service.SanBayService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Sort;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;
import java.util.stream.StreamSupport;

@Service
public class SanBayServicelmpl implements SanBayService {
    @Autowired
    SanBayRepository sanBayRepository;
    @Autowired
    ThanhPhoRepository thanhPhoRepository;
    @Autowired
    QuocGiaRepository quocGiaRepository;
    @Override
    public Optional<SanBayDTO> getSanBayById(int id) {
        try{
            Optional<SanBay> sanBay = sanBayRepository.findById(id);
            Optional<SanBayDTO> sanBayDTO = sanBay.map(SanBayMapper::toDTO);
            return sanBayDTO;
        } catch (Exception e) {
            System.err.println("Error occurred while get airport: " + e.getMessage());
            return null;
        }
    }
    @Override
    public Iterable<SanBayDTO> getAllSanBay() {
        try {
            Iterable<SanBay> sanBayList = sanBayRepository.findAll();
            Iterable<SanBayDTO> sanBayDTOList = StreamSupport.stream(sanBayList.spliterator(), false).map(SanBayMapper::toDTO).toList();
            return sanBayDTOList;
        } catch (Exception e) {
            System.err.println("Error occurred while fetching airport: " + e.getMessage());
            return Collections.emptyList();
        }
    }
    @Override
    public List<SanBayDTO> findSanBayByKeyword(String keyword) {
        List<SanBay> sanBayList = sanBayRepository.findByKeywordContainingIgnoreCase(keyword);
        if(sanBayList.isEmpty()){
            System.out.println("No airports found with keyword");
        } else {
            System.out.println("Airports found with keyword");
        }
        return sanBayList.stream().map(SanBayMapper::toDTO).collect(Collectors.toList());
    }
    @Override
    public List<SanBayDTO> getAllSanBaySorted(String sortBy, String order) {
        try{
            List<SanBay> sanBayList;
            if(order.equals("asc")){
                sanBayList = sanBayRepository.findAll(Sort.by(Sort.Direction.ASC, sortBy));
            } else {
                sanBayList = sanBayRepository.findAll(Sort.by(Sort.Direction.DESC, sortBy));
            }
            List<SanBayDTO> sanBayDTOList = sanBayList.stream().map(SanBayMapper::toDTO).collect(Collectors.toList());
            return sanBayDTOList;
        } catch (IllegalArgumentException e) {
            // Xử lý lỗi nếu tham số sortBy không hợp lệ hoặc có lỗi khác liên quan đến tham số
            System.err.println("Invalid sorting field: " + sortBy);
            return Collections.emptyList(); // Trả về danh sách rỗng
        } catch (Exception e) {
            // Xử lý các lỗi không lường trước khác
            System.err.println("An error occurred while fetching sorted aiports: " + e.getMessage());
            return Collections.emptyList(); // Trả về danh sách rỗng nếu có lỗi
        }
    }
    @Override
    public List<SanBayDTO> getSanBayByThanhPho(ThanhPho thanhPho){
        List<SanBay> sanBayList = sanBayRepository.findSanBayByThanhPho(thanhPho);
        return sanBayList.stream().map(SanBayMapper::toDTO).collect(Collectors.toList());
    }
    @Override
    public List<SanBayDTO> getSanBayByQuocGia(QuocGia quocGia) {
        List<ThanhPho> listTP = thanhPhoRepository.findByQuocGia(quocGia);
        return listTP.stream()
                .flatMap(tp -> sanBayRepository.findSanBayByThanhPho(tp).stream())
                .map(SanBayMapper::toDTO)
                .collect(Collectors.toList());
    }

    @Override
    public Optional<SanBayDTO> addNewSanBay(SanBayDTO sanBayDTO){
        try{
            SanBay sb = SanBayMapper.toEntity(sanBayDTO);
            SanBay savedSb = sanBayRepository.save(sb);
            return Optional.of(SanBayMapper.toDTO(savedSb));
        } catch (Exception e) {
            return Optional.empty();
        }
    }
    @Override
    public Optional<SanBayDTO> updateSanBay(SanBayDTO sanBayDTO){
        Optional<SanBay> existingSB = sanBayRepository.findById(sanBayDTO.getIdSanBay());
        if(existingSB.isPresent()){
            SanBay sb = SanBayMapper.toEntity(sanBayDTO);
            SanBay savedSB = sanBayRepository.save(sb);
            return Optional.of(SanBayMapper.toDTO(savedSB));
        }else {
            System.err.println("Airport does not existing!!!");
            return Optional.empty();
        }
    }
    @Override
    public Optional<SanBayDTO> getSanBayByIcaoSanBay(String icaoSanBay){
        try {
            SanBay sb = sanBayRepository.findSanBayByIcaoSanBay(icaoSanBay);
            return Optional.ofNullable(SanBayMapper.toDTO(sb));
        } catch (Exception e) {
            return Optional.empty();
        }
    }
    @Override
    public Optional<SanBayDTO> getSanBayByIataSanBay(String iataSanBay){
        try{
            SanBay sb = sanBayRepository.findSanBayByIataSanBay(iataSanBay);
            return Optional.ofNullable(SanBayMapper.toDTO(sb));
        } catch (Exception e) {
            return  Optional.empty();
        }
    }
    @Override
    public Optional<SanBayDTO> blockSanBay(int id){
        Optional<SanBay> existingSB = sanBayRepository.findById(id);
        if(existingSB.isPresent()){
            SanBay sb = existingSB.get();
            sb.setTrangThaiActive(ActiveEnum.IN_ACTIVE);
            SanBay savedSB = sanBayRepository.save(sb);
            return Optional.of(SanBayMapper.toDTO(savedSB));
        } else {
            System.err.println("Airport does not existing!!!");
            return Optional.empty();
        }
    }
    @Override
    public Optional<SanBayDTO> unblockSanBay(int id){
        Optional<SanBay> existingSB = sanBayRepository.findById(id);
        if(existingSB.isPresent()){
            SanBay sb = existingSB.get();
            sb.setTrangThaiActive(ActiveEnum.ACTIVE);
            SanBay savedSB = sanBayRepository.save(sb);
            return Optional.of(SanBayMapper.toDTO(savedSB));
        } else {
            System.err.println("Airport does not existing!!!");
            return Optional.empty();
        }
    }
}
