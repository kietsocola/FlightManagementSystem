package com.project.flightManagement.Service.Impl;

import com.project.flightManagement.DTO.HangBayDTO.HangBayDTO;
import com.project.flightManagement.DTO.MayBayDTO.MayBayDTO;
import com.project.flightManagement.Enum.ActiveEnum;
import com.project.flightManagement.Mapper.MayBayMapper;
import com.project.flightManagement.Model.ChuyenBay;
import com.project.flightManagement.Model.HangBay;
import com.project.flightManagement.Model.MayBay;
import com.project.flightManagement.Model.SanBay;
import com.project.flightManagement.Repository.ChuyenBayRepository;
import com.project.flightManagement.Repository.MayBayRepository;
import com.project.flightManagement.Service.MayBayService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Sort;
import org.springframework.stereotype.Service;

import java.time.Duration;
import java.time.LocalDateTime;
import java.util.Collections;
import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;
import java.util.stream.StreamSupport;

@Service
public class MayBayServiceImpl implements MayBayService {
    @Autowired
    private MayBayRepository mbRepo;
    @Autowired
    private ChuyenBayRepository cbRepo;

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
            System.out.println("add plane success");
            System.out.println(mbDTO.toString());
            MayBay mb = MayBayMapper.toEntity(mbDTO);
            MayBay mbSaved = mbRepo.save(mb);
            return Optional.of(MayBayMapper.toDTO(mbSaved));
        }catch (Exception e){
            System.out.println("add plane failed");
            System.out.println(mbDTO.toString());
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
    @Override
    public List<MayBayDTO> findMayBayBySanBay(SanBay sanBay) {
        List<MayBay> listMb = mbRepo.findMayBayBySanBay(sanBay);
        return listMb.stream().map(MayBayMapper::toDTO).collect(Collectors.toList());
    }
    @Override
    public String getHoursOfPlane(int id) {
        Optional<MayBay> mb = mbRepo.findById(id);
        if (mb.isPresent()) {
            List<ChuyenBay> listCB = cbRepo.findByMayBay(mb.get().getIdMayBay());
            if (!listCB.isEmpty()) {
                long totalSeconds = 0; // Biến để lưu tổng số giây

                for (ChuyenBay cb : listCB) {
                    LocalDateTime start = cb.getThoiGianBatDauThucTe();
                    LocalDateTime end = cb.getThoiGianKetThucThucTe();
                    Duration duration = Duration.between(start, end);
                    totalSeconds += duration.getSeconds(); // Cộng dồn tổng số giây
                }

                // Tính toán giờ, phút, giây từ tổng số giây
                long hours = totalSeconds / 3600;
                long minutes = (totalSeconds % 3600) / 60;
                long seconds = totalSeconds % 60;

                // Trả về kết quả dưới dạng chuỗi "giờ:phút:giây"
                return String.format("%02d:%02d:%02d", hours, minutes, seconds);
            } else {
                System.out.println("The plane had not flight time yet!!");
                return "00:00:00"; // Trả về 0 giờ nếu không có chuyến bay
            }
        } else {
            System.out.println("Can't find plane!!");
            return "00:00:00"; // Trả về 0 giờ nếu không tìm thấy máy bay
        }
    }
}
