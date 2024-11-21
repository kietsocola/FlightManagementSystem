package com.project.flightManagement.Service.Impl;

import com.project.flightManagement.DTO.TuyenBayDTO.TuyenBayDTO;
import com.project.flightManagement.Enum.ActiveEnum;
import com.project.flightManagement.Mapper.TuyenBayMapper;
import com.project.flightManagement.Model.TuyenBay;
import com.project.flightManagement.Model.SanBay;
import com.project.flightManagement.Repository.TuyenBayRepository;
import com.project.flightManagement.Repository.SanBayRepository;
import com.project.flightManagement.Service.TuyenBayService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Sort;
import org.springframework.stereotype.Service;

import java.time.LocalDate;
import java.util.*;
import java.util.stream.Collectors;
import java.util.stream.StreamSupport;

@Service
public class TuyenBayServiceImpl implements TuyenBayService {

    @Autowired
    private TuyenBayRepository tbRepo;
    @Autowired
    private  SanBayRepository sanBayRepository;

    @Override
    public List<TuyenBayDTO> getAllTuyenBay() {
        try {
            Iterable<TuyenBay> listTB = tbRepo.findAll();
            return StreamSupport.stream(listTB.spliterator(), false)
                    .map(TuyenBayMapper::toDTO)
                    .collect(Collectors.toList());
        } catch (Exception e) {
            System.err.println("Error occurred while fetching routes: " + e);
            return Collections.emptyList();
        }
    }

    @Override
    public Optional<TuyenBayDTO> getTuyenBayByIdTuyenBay(int idTB) {
        try {
            return tbRepo.findById(idTB).map(TuyenBayMapper::toDTO);
        } catch (Exception e) {
            System.err.println("Error occurred while fetching route by ID: " + e.getMessage());
            // Bạn có thể xử lý thêm ở đây
            return Optional.empty();
        }
    }

    @Override
    public Optional<TuyenBayDTO> addNewTuyenBay(TuyenBayDTO tuyenBayDTO) {
        try {
            // Tìm và lấy đối tượng SanBay từ cơ sở dữ liệu
            Optional<SanBay> existingSanBayBatDau = sanBayRepository.findById(tuyenBayDTO.getIdSanBayBatDau());
            Optional<SanBay> existingSanBayKetThuc = sanBayRepository.findById(tuyenBayDTO.getIdSanBayKetThuc());

            // Kiểm tra nếu sân bay bắt đầu không tồn tại
            if (existingSanBayBatDau.isEmpty()) {
                System.err.println("Sân bay bắt đầu không tìm thấy: ID = " + tuyenBayDTO.getIdSanBayBatDau());
                return Optional.empty();
            }

            // Kiểm tra nếu sân bay kết thúc không tồn tại
            if (existingSanBayKetThuc.isEmpty()) {
                System.err.println("Sân bay kết thúc không tìm thấy: ID = " + tuyenBayDTO.getIdSanBayKetThuc());
                return Optional.empty();
            }

            // Nếu cả hai sân bay đều tồn tại, lấy đối tượng SanBay
            SanBay sanBayBatDau = existingSanBayBatDau.get();
            SanBay sanBayKetThuc = existingSanBayKetThuc.get();

            // Kiểm tra xem tuyến bay có tồn tại trong database hay không
            Optional<TuyenBay> existingTuyenBay = tbRepo.findBySanBayBatDauAndSanBayKetThuc(sanBayBatDau, sanBayKetThuc);

            if (existingTuyenBay.isPresent()) {
                System.err.println("Tuyến bay đã tồn tại: Bắt đầu = " + sanBayBatDau.getTenSanBay() +
                        ", Kết thúc = " + sanBayKetThuc.getTenSanBay());
                return Optional.empty();
            }

            // Chuyển đổi DTO sang thực thể TuyenBay
            TuyenBay tb = TuyenBayMapper.toEntity(tuyenBayDTO);
            tb.setSanBayBatDau(sanBayBatDau);
            tb.setSanBayKetThuc(sanBayKetThuc);

            // Lưu đối tượng TuyenBay
            TuyenBay savedTuyenBay = tbRepo.save(tb);
            return Optional.of(TuyenBayMapper.toDTO(savedTuyenBay));
        } catch (Exception e) {
            System.err.println("Error occurred while adding new route: " + e.getMessage());
            return Optional.empty(); // Trả về Optional.empty() thay vì null
        }
    }



    @Override
    public Optional<TuyenBayDTO> updateTuyenBay(Integer idTuyenBay, TuyenBayDTO tuyenBayDTO) {
        Optional<TuyenBay> existingTB = tbRepo.findById(idTuyenBay);
        if (existingTB.isPresent()) {
            TuyenBay tuyenBay = TuyenBayMapper.toEntity(tuyenBayDTO);
            tuyenBay.setIdTuyenBay(idTuyenBay);
            TuyenBay updatedTuyenBay = tbRepo.save(tuyenBay);
            return Optional.of(TuyenBayMapper.toDTO(updatedTuyenBay));
        } else {
            System.err.println("Route does not exist!");
            return Optional.empty();
        }
    }

    @Override
    public Optional<TuyenBayDTO> blockTuyenBay(int id) {
        Optional<TuyenBay> existingTB = tbRepo.findById(id);
        if (existingTB.isPresent()) {
            TuyenBay tuyenBay = existingTB.get();
            tuyenBay.setStatus(ActiveEnum.IN_ACTIVE);
            TuyenBay updatedTuyenBay = tbRepo.save(tuyenBay);
            return Optional.of(TuyenBayMapper.toDTO(updatedTuyenBay));
        } else {
            System.err.println("Route does not exist!!!");
            return Optional.empty();
        }
    }

    @Override
    public Optional<TuyenBayDTO> unblockTuyenBay(int id) {
        Optional<TuyenBay> existingTB = tbRepo.findById(id);
        if (existingTB.isPresent()) {
            TuyenBay tuyenBay = existingTB.get();
            tuyenBay.setStatus(ActiveEnum.ACTIVE);
            TuyenBay updatedTuyenBay = tbRepo.save(tuyenBay);
            return Optional.of(TuyenBayMapper.toDTO(updatedTuyenBay));
        } else {
            System.err.println("Route does not exist!!!");
            return Optional.empty();
        }
    }


    @Override
    public Iterable<TuyenBayDTO> getAllTuyenBaySorted(String sortBy, String direction) {
        // Default to idTuyenBay and ASC if invalid inputs
        if (sortBy == null || sortBy.isEmpty()) sortBy = "idTuyenBay";
        if (direction == null || (!direction.equalsIgnoreCase("asc") && !direction.equalsIgnoreCase("desc")))
            direction = "asc";

        // Validate if the sortBy field exists in TuyenBay class
        List<String> validFields = List.of("idTuyenBay", "thoiGianChuyenBay", "sanBayBatDau.tenSanBay", "sanBayKetThuc.tenSanBay");
        if (!validFields.contains(sortBy)) {
            System.err.println("Invalid sortBy field: " + sortBy);
            sortBy = "idTuyenBay"; // fallback to default
        }

        try {
            Sort sort = Sort.by(direction.equalsIgnoreCase("asc") ? Sort.Direction.ASC : Sort.Direction.DESC, sortBy);
            return tbRepo.findAll(sort).stream()
                    .map(TuyenBayMapper::toDTO)
                    .collect(Collectors.toList());
        } catch (Exception e) {
            System.err.println("Error occurred while fetching sorted routes: " + e.getMessage());
            return Collections.emptyList();
        }
    }

    @Override
    public Map<String, List<Map<String, Object>>> getTop5FlightRoutesByTimePeriod(String period) {
        Map<String, List<Map<String, Object>>> statistics = new LinkedHashMap<>();
        LocalDate now = LocalDate.now();

        switch (period.toLowerCase()) {
            case "monthly":
                for (int i = 1; i <= 12; i++) {
                    LocalDate startOfMonth = now.withMonth(i).withDayOfMonth(1);
                    LocalDate endOfMonth = startOfMonth.withDayOfMonth(startOfMonth.lengthOfMonth());

                    List<Map<String, Object>> routesStats = processTop5Routes(startOfMonth, endOfMonth);
                    statistics.put("Month " + i, routesStats);
                }
                break;

            case "quarterly":
                for (int i = 1; i <= 4; i++) {
                    // Tính ngày bắt đầu và ngày kết thúc cho từng quý
                    LocalDate startOfQuarter = now.withMonth((i - 1) * 3 + 1).withDayOfMonth(1);
                    LocalDate endOfQuarter = startOfQuarter.plusMonths(2).withDayOfMonth(startOfQuarter.plusMonths(2).lengthOfMonth());

                    List<Map<String, Object>> routesStats = processTop5Routes(startOfQuarter, endOfQuarter);
                    statistics.put("Quarter " + i, routesStats);
                }
                break;

            case "yearly":
                for (int i = now.getYear() - 5; i <= now.getYear(); i++) {
                    LocalDate startOfYear = LocalDate.of(i, 1, 1);
                    LocalDate endOfYear = startOfYear.withDayOfYear(startOfYear.lengthOfYear());

                    List<Map<String, Object>> routesStats = processTop5Routes(startOfYear, endOfYear);
                    statistics.put("Year " + i, routesStats);
                }
                break;

            default:
                throw new IllegalArgumentException("Invalid period. Use 'monthly', 'quarterly', or 'yearly'.");
        }

        return statistics;
    }

    private List<Map<String, Object>> processTop5Routes(LocalDate startDate, LocalDate endDate) {
        List<Object[]> rawStats = tbRepo.getTop5FlightRoutesByFrequency(startDate, endDate);

        List<Map<String, Object>> stats = new ArrayList<>();
        for (Object[] record : rawStats) {
            Map<String, Object> routeData = new LinkedHashMap<>();
            routeData.put("route", record[0] + "-" + record[1]);

            routeData.put("countFlight", record[2]);
            stats.add(routeData);
        }
        return stats;
    }


}