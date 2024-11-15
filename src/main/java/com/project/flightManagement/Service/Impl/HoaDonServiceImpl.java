package com.project.flightManagement.Service.Impl;

import com.project.flightManagement.DTO.ChiTietHoaDonDTO.ChiTietHoaDonDTO;
import com.project.flightManagement.DTO.HoaDonDTO.HoaDonDTO;
import com.project.flightManagement.DTO.PTTTDTO.PTTTDTO;
import com.project.flightManagement.Mapper.ChiTietHoaDonMapper;
import com.project.flightManagement.Mapper.HoaDonMapper;
import com.project.flightManagement.Mapper.PTTTMapper;
import com.project.flightManagement.Model.ChiTietHoaDon;
import com.project.flightManagement.Model.HoaDon;
import com.project.flightManagement.Repository.HoaDonRepository;
import com.project.flightManagement.Service.HoaDonService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Sort;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;
import java.time.temporal.TemporalAdjusters;
import java.util.Collections;
import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;
import java.util.stream.StreamSupport;

@Service
public class HoaDonServiceImpl implements HoaDonService {
    @Autowired
    private HoaDonRepository hdRepo;

    @Override
    public Iterable<HoaDonDTO> getAllHoaDon() {
        try {
            Iterable<HoaDon> listHD = hdRepo.findAll();
            Iterable<HoaDonDTO> listHDDTO = StreamSupport.stream(listHD.spliterator(), false)
                    .map(HoaDonMapper::toDTO)
                    .toList();
            return listHDDTO;
        } catch (Exception e) {
            System.err.println("Error occurred while fetching Bills: " + e.getMessage());
            return Collections.emptyList();
        }
    }

    @Override
    public Optional<HoaDonDTO> getHoaDonById(int idHD) {
        try {
            Optional<HoaDon> hd = hdRepo.findById(idHD);
            Optional<HoaDonDTO> hdDTO = hd.map(HoaDonMapper::toDTO);
            return hdDTO;
        } catch (Exception e) {
            System.err.println("Error occurred while get Hoa Don by ID");
            return null;
        }
    }

    @Override
    public Optional<HoaDonDTO> addHoaDon(HoaDonDTO hoaDonDTO) {
        Optional<HoaDon> existingHD = hdRepo.findById(hoaDonDTO.getIdHoaDon());
        if (!existingHD.isPresent()) {
            HoaDon hd = HoaDonMapper.toEntity(hoaDonDTO);
            HoaDon savedHD = hdRepo.save(hd);
            return Optional.of(HoaDonMapper.toDTO(savedHD));
        } else {
            System.err.println("Hoa Don exist!");
            return null;
        }
    }

    @Override
    public Optional<HoaDonDTO> updateHoaDon(HoaDonDTO hoaDonDTO) {
        Optional<HoaDon> existingHD = hdRepo.findById((hoaDonDTO.getIdHoaDon()));
        if (existingHD.isPresent()) {
            HoaDon hd = HoaDonMapper.toEntity(hoaDonDTO);
            HoaDon updateHoaDon = hdRepo.save(hd);
            return Optional.of(HoaDonMapper.toDTO(updateHoaDon));
        } else {
            System.err.println("Hoa Don does not exist!");
            return Optional.empty();
        }
    }

    @Override
    public List<ChiTietHoaDonDTO> getChiTietHoaDon(int idHD) {
        try {
            HoaDon hoaDon = hdRepo.findById(idHD)
                    .orElseThrow(() -> new RuntimeException("Không tìm thấy hóa đơn với id: " + idHD));

            List<ChiTietHoaDon> chiTietHoaDonList = hoaDon.getChiTietHoaDonList();

            if (chiTietHoaDonList == null) {
                return Collections.emptyList();
            }

            return chiTietHoaDonList.stream()
                    .map(ChiTietHoaDonMapper::toDTO)
                    .collect(Collectors.toList());

        } catch (Exception e) {
            System.err.println("Error: " + e);
            return Collections.emptyList();
        }
    }

    @Override
    public List<HoaDonDTO> getHoaDonByKeyWord(String keyWord) {
        try {
            List<HoaDon> hoaDonList = hdRepo.getHoaDonByKeyWord(keyWord);
            if (hoaDonList.isEmpty()) {
                System.out.println("Không tìm thấy hóa đơn");
            } else {
                System.out.println("Tìm thấy hóa đơn");
            }
            List<HoaDonDTO> hoaDonDTOList = hoaDonList.stream().map(HoaDonMapper::toDTO).collect(Collectors.toList());
            return hoaDonDTOList;
        } catch (Exception e) {
            System.err.println("Error: " + e);
            return null;
        }
    }

    @Override
    public Iterable<HoaDonDTO> getAllHoaDonSorted(String sortBy, String direction) {
        try {
            List<HoaDon> hoaDonList;
            if(direction.equals("asc")) {
                hoaDonList = hdRepo.findAll(Sort.by(Sort.Direction.ASC, sortBy));
            } else {
                hoaDonList = hdRepo.findAll(Sort.by(Sort.Direction.DESC, sortBy));
            }
            List<HoaDonDTO> hoaDonListDTO = hoaDonList.stream()
                    .map(HoaDonMapper::toDTO)
                    .collect(Collectors.toList());
            return hoaDonListDTO;

        } catch (IllegalArgumentException e) {
            // Xử lý lỗi nếu tham số sortBy không hợp lệ hoặc có lỗi khác liên quan đến tham số
            System.err.println("Invalid sorting field: " + sortBy);
            return Collections.emptyList(); // Trả về danh sách rỗng

        } catch (Exception e) {
            // Xử lý các lỗi không lường trước khác
            System.err.println("An error occurred while fetching sorted bills: " + e.getMessage());
            return Collections.emptyList(); // Trả về danh sách rỗng nếu có lỗi
        }
    }
    @Override
    public List<HoaDonDTO> getHoaDonByNV(int idNV) {
        try {
            List<HoaDon> listHD = hdRepo.findHoaDonByNhanVien(idNV);
            List<HoaDonDTO> listHDDTO = StreamSupport.stream(listHD.spliterator(), false)
                    .map(HoaDonMapper::toDTO)
                    .toList();
            return listHDDTO;
        } catch (Exception e) {
            System.err.println("Error occurred while get Hoa Don by ID NV");
            return Collections.emptyList();
        }
    }

    @Override
    public List<HoaDonDTO> getHoaDonByKH(int idKH) {
        try {
            List<HoaDon> listHD = hdRepo.findHoaDonByKhachHang(idKH);
            List<HoaDonDTO> listHDDTO = StreamSupport.stream(listHD.spliterator(), false).
                    map(HoaDonMapper::toDTO).
                    toList();
            return listHDDTO;
        } catch (Exception e) {
            System.err.println("Error occurred while get Hoa Don by Id KH");
            return Collections.emptyList();
        }
    }

    @Override
    public List<HoaDonDTO> getHoaDonByPTTT(int idPTTT) {
        try {
            List<HoaDon> listHD = hdRepo.findHoaDonByPTTT(idPTTT);
            List<HoaDonDTO> listHDDTO = StreamSupport.stream(listHD.spliterator(), false).
                    map(HoaDonMapper::toDTO).toList();
            return listHDDTO;
        } catch (Exception e) {
            System.err.println("Error occurred while get Hoa Don by ID PTTT");
            return Collections.emptyList();
        }
    }

    @Override
    public List<HoaDonDTO> getHoaDonByLoaiHD(int idLoaiHD) {
        try {
            List<HoaDon> listHD = hdRepo.findHoaDonByLoaiHD(idLoaiHD);
            List<HoaDonDTO> listHDDTO = StreamSupport.stream(listHD.spliterator(), false).
                    map(HoaDonMapper::toDTO).toList();
            return listHDDTO;
        } catch (Exception e) {
            System.err.println("Error occurred while get Hoa Don by ID PTTT");
            return Collections.emptyList();
        }
    }
    @Override
    public boolean markDanhGia(int idHoaDon) {
        try {
            Optional<HoaDon> hd = hdRepo.findById(idHoaDon);
            hd.get().setDanhGia(true);
            hdRepo.save(hd.get());
            return true;
        } catch (Exception e) {
            return false;
        }
    }

    @Override
    public Double getRevenueByMonth(int month, int year) {
        return hdRepo.findRevenueByMonth(month, year);
    }

    @Override
    public Double getRevenueByQuarter(int quarter, int year) {
        return hdRepo.findRevenueByQuarter(quarter, year);
    }

    @Override
    public Double getRevenueByYear(int year) {
        return hdRepo.findRevenueByYear(year);
    }

    @Override
    public Double getRevenueBetweenMonths(int startMonth, int startYear, int endMonth, int endYear) {
        LocalDateTime startDate = LocalDateTime.of(startYear, startMonth, 1, 0, 0);
        LocalDateTime endDate = LocalDateTime.of(endYear, endMonth, 1, 0, 0).with(TemporalAdjusters.lastDayOfMonth());
        return hdRepo.findRevenueBetweenDates(startDate, endDate);
    }

    @Override
    public Double getRevenueBetweenQuarters(int startQuarter, int startYear, int endQuarter, int endYear) {
        LocalDateTime startDate = getStartOfQuarter(startQuarter, startYear);
        LocalDateTime endDate = getEndOfQuarter(endQuarter, endYear);
        return hdRepo.findRevenueBetweenDates(startDate, endDate);
    }

    @Override
    public Double getRevenueBetweenYears(int startYear, int endYear) {
        LocalDateTime startDate = LocalDateTime.of(startYear, 1, 1, 0, 0);
        LocalDateTime endDate = LocalDateTime.of(endYear, 12, 31, 23, 59);
        return hdRepo.findRevenueBetweenDates(startDate, endDate);
    }

    // Helper methods
    private LocalDateTime getStartOfQuarter(int quarter, int year) {
        int startMonth = (quarter - 1) * 3 + 1;
        return LocalDateTime.of(year, startMonth, 1, 0, 0);
    }

    private LocalDateTime getEndOfQuarter(int quarter, int year) {
        int endMonth = quarter * 3;
        return LocalDateTime.of(year, endMonth, 1, 0, 0).with(TemporalAdjusters.lastDayOfMonth());
    }
}
