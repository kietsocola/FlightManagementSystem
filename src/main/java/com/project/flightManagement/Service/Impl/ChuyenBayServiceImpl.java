package com.project.flightManagement.Service.Impl;

import com.project.flightManagement.DTO.ChuyenBayDTO.ChuyenBayDTO;
import com.project.flightManagement.Enum.ChuyenBayEnum;
import com.project.flightManagement.Mapper.ChuyenBayMapper;
import com.project.flightManagement.Mapper.NhanVienMapper;
import com.project.flightManagement.Model.ChuyenBay;
import com.project.flightManagement.Model.MayBay;
import com.project.flightManagement.Repository.ChuyenBayRepository;
import com.project.flightManagement.Service.ChuyenBayService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Sort;
import org.springframework.stereotype.Service;

import java.time.Duration;
import java.time.LocalDate;
import java.time.LocalDateTime;
import java.util.*;
import java.util.stream.Collectors;
import java.util.stream.StreamSupport;

@Service
public class ChuyenBayServiceImpl implements ChuyenBayService {
    @Autowired
    private ChuyenBayRepository repo;
    @Override
    public List<ChuyenBayDTO> searchFlights(String departureCity, String arrivalCity, Date departureDate, int numberOfTickets) {

        List<ChuyenBay> cb = repo.findFlightsOneWay(departureCity, arrivalCity, departureDate, numberOfTickets);
        if(cb!=null) return cb.stream().map(ChuyenBayMapper::toDTO).collect(Collectors.toList());
        return Collections.emptyList();
    }
    @Override
    public Optional<ChuyenBayDTO> addChuyenBay(ChuyenBayDTO cbDTO) {
        ChuyenBay cb = ChuyenBayMapper.toEntity(cbDTO);
        ChuyenBay savecb = repo.save(cb);
        return Optional.of(ChuyenBayMapper. toDTO(savecb));
    }

    @Override
    public Optional<ChuyenBayDTO> updateChuyenBay(ChuyenBayDTO cbDTO) {
        ChuyenBay cb = ChuyenBayMapper.toEntity(cbDTO);
        ChuyenBay updatecb = repo.save(cb);
        return Optional.of(ChuyenBayMapper.toDTO(updatecb));
    }

    @Override
    public Iterable<ChuyenBayDTO> getAllChuyenBay() {
        try{
            Iterable<ChuyenBay> listcb =  repo.findAll();
            Iterable<ChuyenBayDTO> listcbDTO = StreamSupport.stream(listcb.spliterator(),false)
                    .map(ChuyenBayMapper::toDTO)
                    .toList();
            return listcbDTO;
        }catch (Exception e){
            System.err.println("error occurred while fetching customers :" + e.getMessage());
            return Collections.emptyList();
        }
    }

    @Override
    public List<ChuyenBayDTO> getAllChuyenBaySorted(String sortField, String sordOrder) {
        try{
            // Kiểm tra hướng sắp xếp
            Sort.Direction direction = Sort.Direction.ASC; // Mặc định là ASC
            if ("desc".equalsIgnoreCase(sordOrder)) {
                direction = Sort.Direction.DESC; // Thay đổi thành DESC nếu yêu cầu
            }
            List<ChuyenBay> listcb = repo.findAll(Sort.by( direction , sortField));
            List<ChuyenBayDTO> listcbDTO = listcb.stream().map(ChuyenBayMapper::toDTO).collect(Collectors.toList());
            return listcbDTO;
        }catch (IllegalArgumentException e){

            System.err.println("Invalid sorting field: " + sortField);
            return Collections.emptyList();
        }catch (Exception e) {
            System.err.println("An error occurred while fetching sorted chuyen bay: " + e.getMessage());
            return Collections.emptyList();
        }
    }

    @Override
    public Optional<ChuyenBayDTO> getChuyenBayById(int id) {
        Optional<ChuyenBay> cb = repo.findById(id);
        return cb.map(ChuyenBayMapper::toDTO);
    }

    @Override
    public ChuyenBay getChuyenBayEntityById(int idChuyenBay) {
        Optional<ChuyenBay> chuyenBayOptional = repo.findById(idChuyenBay);
        return chuyenBayOptional.get();
    }

    @Override
    public List<ChuyenBayDTO> getFilterChuyenBay(ChuyenBayEnum trangThai, LocalDateTime thoiGianBatDau , LocalDateTime thoiGianKetThuc) {
            List<ChuyenBay> cb = repo.filterChuyenBay(trangThai,thoiGianBatDau ,thoiGianKetThuc);
            return cb.stream().map(ChuyenBayMapper::toDTO).collect(Collectors.toList());
    }
    @Override
    public List<ChuyenBayDTO> getChuyenBayByMayBay(MayBay mb) {
        List<ChuyenBay> listCB = repo.findByMayBay(mb.getIdMayBay());
        return listCB.stream().map(ChuyenBayMapper::toDTO).collect(Collectors.toList());
    }
    @Override
    public String getHoursOfFlight(int idChuyenBay) {
        Optional<ChuyenBay> cb = repo.findById(idChuyenBay);
        if(cb.isPresent()) {
            LocalDateTime start = cb.get().getThoiGianBatDauThucTe();
            LocalDateTime end = cb.get().getThoiGianKetThucThucTe();
            Duration duration = Duration.between(start, end);
            long hours = duration.toHours();
            long minutes = duration.toMinutes() % 60;
            long seconds = duration.getSeconds() % 60;

            return String.format("%02d:%02d:%02d", hours, minutes, seconds);
        } else {
            System.out.println("Cant found flight!!");
            return "00:00:00";
        }
    }

    @Override
    public Iterable<ChuyenBayDTO> getChuyenBayByYear(int nam) {
        try{
            Iterable<ChuyenBay> listcb =  repo.findChuyenBaysByYear(nam);
            Iterable<ChuyenBayDTO> listcbDTO = StreamSupport.stream(listcb.spliterator(),false)
                    .map(ChuyenBayMapper::toDTO)
                    .toList();
            return listcbDTO;
        }catch (Exception e){
            System.err.println("error occurred while fetching customers :" + e.getMessage());
            return Collections.emptyList();
        }
    }

    @Override
    public Iterable<ChuyenBayDTO> getChuyenBayByYearAndMonth(int year, int month) {
        try{
            Iterable<ChuyenBay> listcb =  repo.findChuyenBayByYearAndMonth(year , month);
            Iterable<ChuyenBayDTO> listcbDTO = StreamSupport.stream(listcb.spliterator(),false)
                    .map(ChuyenBayMapper::toDTO)
                    .toList();
            return listcbDTO;
        }catch (Exception e){
            System.err.println("error occurred while fetching customers :" + e.getMessage());
            return Collections.emptyList();
        }
    }

    // Hàm trong service để lọc chuyến bay theo quý và năm
    public Iterable<ChuyenBayDTO> filterChuyenBayByQuyAndNam(int nam, int quy) {

        try{
            // Lấy danh sách tất cả các chuyến bay trong năm
            Iterable<ChuyenBay> chuyenBayList = repo.findAll();

            // Tạo danh sách để chứa các chuyến bay theo quý
            List<ChuyenBay> result = new ArrayList<>();

            // Duyệt qua các chuyến bay và phân loại theo quý
            for (ChuyenBay chuyenBay : chuyenBayList) {
                int thang = chuyenBay.getNgayBay().toLocalDate().getMonthValue();  // Lấy tháng từ ngày bay

                // Xác định quý của chuyến bay
                int quyChuyenBay = getQuyFromThang(thang);

                // Kiểm tra nếu quý và năm của chuyến bay trùng khớp với quý và năm yêu cầu
                if (chuyenBay.getNgayBay().toLocalDate().getYear() == nam && quyChuyenBay == quy) {
                    result.add(chuyenBay);  // Thêm vào danh sách kết quả
                }
            }
            Iterable<ChuyenBayDTO> listcbDTO = StreamSupport.stream(result.spliterator(),false)
                    .map(ChuyenBayMapper::toDTO)
                    .toList();
            return listcbDTO;
        }catch (Exception e){
            System.err.println("error occurred while fetching customers :" + e.getMessage());
            return Collections.emptyList();
        }
    }

    // Hàm xác định quý từ tháng
    private int getQuyFromThang(int thang) {
        if (thang >= 1 && thang <= 3) {
            return 1; // Quý 1
        } else if (thang >= 4 && thang <= 6) {
            return 2; // Quý 2
        } else if (thang >= 7 && thang <= 9) {
            return 3; // Quý 3
        } else {
            return 4; // Quý 4
        }
    }
}
