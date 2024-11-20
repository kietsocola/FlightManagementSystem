package com.project.flightManagement.Controller;

import com.project.flightManagement.DTO.ChiTietHoaDonDTO.ChiTietHoaDonDTO;
import com.project.flightManagement.DTO.HangHoaDTO.HangHoaDTO;
import com.project.flightManagement.DTO.HoaDonDTO.HoaDonCreateDTO;
import com.project.flightManagement.DTO.HoaDonDTO.HoaDonDTO;

import com.project.flightManagement.Enum.HoaDonEnum;
import com.project.flightManagement.Mapper.HoaDonMapper;
import com.project.flightManagement.Payload.ResponseData;
import com.project.flightManagement.Service.ChiTietHoaDonService;
import com.project.flightManagement.Service.HangHoaService;
import com.project.flightManagement.Service.HoaDonService;
import jakarta.validation.Valid;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.parameters.P;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.*;

import java.util.*;

@RestController
@CrossOrigin(origins = "http://localhost:5173")
public class HoaDonController {
    @Autowired
    private HoaDonService hoaDonService;
    @Autowired
    private ChiTietHoaDonService chiTietHoaDonService;
    @Autowired
    HangHoaService hangHoaService;
    private ResponseData response = new ResponseData();

    @GetMapping("/getAllHoaDon")
    public ResponseEntity<ResponseData> getAllHoaDon() {
        Iterable<HoaDonDTO> listHoaDonDTO = hoaDonService.getAllHoaDon();
        if (listHoaDonDTO.iterator().hasNext()) {
            response.setMessage("get list Hoa Don success!");
            response.setData(listHoaDonDTO);
            response.setStatusCode(200);
            return new ResponseEntity<>(response, HttpStatus.OK);
        } else {
            response.setMessage("get list Hoa Don unsuccess!");
            response.setData(null);
            response.setStatusCode(204);
            return new ResponseEntity<>(response, HttpStatus.NO_CONTENT);
        }
    }

    @GetMapping("/getHoaDonById/{idHD}")
    public ResponseEntity<ResponseData> getHoaDonById(@PathVariable("idHD") Integer idHD) {
        Optional<HoaDonDTO> HoaDonDTO = hoaDonService.getHoaDonById(idHD);
        if (HoaDonDTO.isPresent()) {
            response.setMessage("get Hoa Don by ID success!");
            response.setData(HoaDonDTO);
            response.setStatusCode(200);
            return new ResponseEntity<>(response, HttpStatus.OK);
        } else {
            response.setMessage("get Hoa Don by ID unsuccess!");
            response.setData(null);
            response.setStatusCode(200);
            return new ResponseEntity<>(response, HttpStatus.NO_CONTENT);
        }
    }

    @PostMapping("/addHoaDon")
    public ResponseEntity<ResponseData> addHoaDon(@Valid @RequestBody HoaDonDTO hdDTO, BindingResult bindingResult) {
        if (bindingResult.hasErrors()) {
            Map<String, String> fieldErrors =  new HashMap<>();
            bindingResult.getFieldErrors().forEach(error -> fieldErrors.put(error.getField(), error.getDefaultMessage()));
            response.setStatusCode(400);
            response.setData(fieldErrors);
            response.setMessage("There are some fields invalid!");
            return new ResponseEntity<>(response, HttpStatus.BAD_REQUEST);
        }

        if (hdDTO != null && (hdDTO.getKhachHang() != null && hdDTO.getLoaiHoaDon() != null && hdDTO.getNhanVien() != null && hdDTO.getPhuongThucThanhToan() != null)) {
            Optional<HoaDonDTO> savedHoaDon = hoaDonService.addHoaDon(hdDTO);
            if (savedHoaDon.isPresent()) {
                response.setMessage("Save Hoa Don successfully!");
                response.setData(savedHoaDon.get());
                response.setStatusCode(200);
                return new ResponseEntity<>(response, HttpStatus.CREATED);
            } else {
                response.setMessage("Save Hoa Don unsuccessfully!");
                response.setData(null);
                response.setStatusCode(500);
                return new ResponseEntity<>(response, HttpStatus.INTERNAL_SERVER_ERROR);
            }
        } else {
            response.setMessage("Invalid Hoa Don data!");
            response.setData(hdDTO);
            response.setStatusCode(400);
            return new ResponseEntity<>(response, HttpStatus.BAD_REQUEST);
        }
    }

    @PutMapping("/updateHoaDon/{idHD}")
    public ResponseEntity<ResponseData> updateHoaDon(@PathVariable("idHD") Integer idHD, @Valid @RequestBody HoaDonDTO hoaDonDTO, BindingResult bindingResult) {
        ResponseData responseData = new ResponseData();

        if (bindingResult.hasErrors()) {
            Map<String, String> fieldErrors = new HashMap<>();
            bindingResult.getFieldErrors().forEach(error -> fieldErrors.put(error.getField(), error.getDefaultMessage()));
            responseData.setStatusCode(400);
            responseData.setData(fieldErrors);
            responseData.setMessage("There are some fields invalid!");
            return new ResponseEntity<>(responseData, HttpStatus.BAD_REQUEST);
        }

        if (hoaDonDTO != null) {
            Optional<HoaDonDTO> existinghd = hoaDonService.getHoaDonById(idHD);

            if (!existinghd.isPresent()) {
                responseData.setMessage("Hoa Don not found");
                responseData.setData(null);
                responseData.setStatusCode(400);
                return new ResponseEntity<>(responseData, HttpStatus.NOT_FOUND);
            } else {
                hoaDonDTO.setIdHoaDon(idHD);

                Optional<HoaDonDTO> updatedhd = hoaDonService.updateHoaDon(hoaDonDTO);
                if (updatedhd.isPresent()) {
                    responseData.setMessage("Update Hoa Don succesfully!");
                    responseData.setData(updatedhd.get());
                    responseData.setStatusCode(200);
                    return new ResponseEntity<>(responseData, HttpStatus.OK);
                } else {
                    System.out.println(updatedhd);
                    responseData.setMessage("Update Hoa Don Unsuccessfully!");
                    responseData.setData(null);
                    responseData.setStatusCode(500);
                    return new ResponseEntity<>(responseData, HttpStatus.INTERNAL_SERVER_ERROR);
                }
            }
        } else {
            // Dữ liệu không hợp lệ
            response.setMessage("Invalid customer data!!");
            response.setData(null);
            response.setStatusCode(400); // Bad Request
            return new ResponseEntity<>(response, HttpStatus.BAD_REQUEST);
        }
    }

    @GetMapping("/getListChiTietHoaDon/{idHD}")
    public ResponseEntity<ResponseData> getListChiTietHoaDon(@PathVariable("idHD") Integer idHD) {
        List<ChiTietHoaDonDTO> chiTietHoaDonList = hoaDonService.getChiTietHoaDon(idHD);
        if (chiTietHoaDonList.iterator().hasNext()) {
            response.setMessage("Lấy thành công danh sách chi tiết hoóa đơn");
            response.setData(chiTietHoaDonList);
            response.setStatusCode(200);

            return new ResponseEntity<>(response, HttpStatus.OK);
        } else {
            response.setMessage("Lấy không thành công danh sách chi tiết hóa đơn");
            response.setData(null);
            response.setStatusCode(204);

            return new ResponseEntity<>(response, HttpStatus.NO_CONTENT);
        }
    }

    @GetMapping("/getHoaDonByKeyWord")
    public ResponseEntity<ResponseData> getHoaDonByKeyWord(@RequestParam String keyWord) {
        Iterable<HoaDonDTO> listHoaDonDTO = hoaDonService.getHoaDonByKeyWord(keyWord);
        if (listHoaDonDTO.iterator().hasNext()) {
            response.setStatusCode(200);
            response.setMessage("Lấy thành công danh sách hoóa đơn");
            response.setData(listHoaDonDTO);
            return new ResponseEntity<>(response, HttpStatus.OK);
        } else {
            response.setData(null);
            response.setStatusCode(404);
            response.setMessage("Lấy không thành công danh sách hóa đơn");
            return new ResponseEntity<>(response, HttpStatus.NOT_FOUND);
        }
    }

    @GetMapping("/getAllHoaDonSorted")
    public ResponseEntity<ResponseData> getAllHoaDonSorted(@RequestParam(defaultValue = "idHoaDon") String sortBy, @RequestParam(defaultValue = "asc") String order) {
        Iterable<HoaDonDTO> hoaDonDTO = hoaDonService.getAllHoaDonSorted(sortBy, order);
        if (hoaDonDTO.iterator().hasNext()) {
            response.setMessage("get list hoa don success!!");
            response.setData(hoaDonDTO);
            response.setStatusCode(200);
            return new ResponseEntity<>(response, HttpStatus.OK);
        } else {
            response.setMessage("get list hoa don unsuccess!!");
            response.setData(null);
            response.setStatusCode(204);
            return new ResponseEntity<>(HttpStatus.NO_CONTENT);
        }
    }

    @GetMapping("/getHoaDonByField")
    public ResponseEntity<ResponseData> getHoaDonByField(@RequestParam String field, @RequestParam int input) {
        System.out.println(field+ " "  + input);
        Iterable<HoaDonDTO> listHoaDonDTO = null;

        if (field.equals("") || input == 0) {
            listHoaDonDTO = hoaDonService.getAllHoaDon();
        } else {
            switch (field) {
                case "nhanVien":
                    listHoaDonDTO = hoaDonService.getHoaDonByNV(input);
                    break;
                case "khachHang":
                    listHoaDonDTO = hoaDonService.getHoaDonByKH(input);
                    break;
                case "phuongThucThanhToan":
                    listHoaDonDTO = hoaDonService.getHoaDonByPTTT(input);
                    break;
                case "loaiHoaDon":
                    listHoaDonDTO = hoaDonService.getHoaDonByLoaiHD(input);
                    break;
            }
        }
        if (listHoaDonDTO.iterator().hasNext()) {
            response.setMessage("get list hoa don success!!");
            response.setData(listHoaDonDTO);
            response.setStatusCode(200);
            return new ResponseEntity<>(response, HttpStatus.OK);
        } else {
            response.setMessage("get list hoa don unsuccess!!");
            response.setData(null);
            response.setStatusCode(204);
            return new ResponseEntity<>(HttpStatus.NO_CONTENT);
        }

    }


    @PutMapping("/updateHoaDonState/{idHD}")
    public ResponseEntity<ResponseData> updateHoaDonState(@PathVariable("idHD") Integer idHD, @RequestBody Map<String, String> request) {
        ResponseData responseData = new ResponseData();

        // Lấy trạng thái mới từ request body
        String statusStr = request.get("status");
        if (statusStr == null || statusStr.isEmpty()) {
            responseData.setStatusCode(400);
            responseData.setMessage("Trạng thái không được để trống.");
            responseData.setData(null);
            return new ResponseEntity<>(responseData, HttpStatus.BAD_REQUEST);
        }

        // Chuyển chuỗi trạng thái sang enum HoaDonStatus
        HoaDonEnum newStatus;
        try {
            newStatus = HoaDonEnum.valueOf(statusStr.toUpperCase());
        } catch (IllegalArgumentException e) {
            responseData.setStatusCode(400);
            responseData.setMessage("Trạng thái không hợp lệ.");
            responseData.setData(null);
            return new ResponseEntity<>(responseData, HttpStatus.BAD_REQUEST);
        }

        // Kiểm tra xem hóa đơn có tồn tại không
        Optional<HoaDonDTO> existingHoaDon = hoaDonService.getHoaDonById(idHD);
        if (!existingHoaDon.isPresent()) {
            responseData.setStatusCode(404);
            responseData.setMessage("Không tìm thấy hóa đơn.");
            responseData.setData(null);
            return new ResponseEntity<>(responseData, HttpStatus.NOT_FOUND);
        }

        // Cập nhật trạng thái hóa đơn
        existingHoaDon.get().setStatus(newStatus);
        Optional<HoaDonDTO> updatedHoaDon = hoaDonService.updateHoaDon(existingHoaDon.get());

        if (updatedHoaDon.isPresent()) {
            responseData.setStatusCode(200);
            responseData.setMessage("Cập nhật trạng thái hóa đơn thành công.");
            responseData.setData(updatedHoaDon.get());
            return new ResponseEntity<>(responseData, HttpStatus.OK);
        } else {
            responseData.setStatusCode(500);
            responseData.setMessage("Cập nhật trạng thái hóa đơn thất bại.");
            responseData.setData(null);
            return new ResponseEntity<>(responseData, HttpStatus.INTERNAL_SERVER_ERROR);
        }
    }

    @PostMapping("/createHoaDon")
    public ResponseEntity<ResponseData> createHoaDon(@Valid @RequestBody HoaDonCreateDTO hoaDonCreateDTO, BindingResult bindingResult) {
        if (bindingResult.hasErrors()) {
            Map<String, String> fieldErrors = new HashMap<>();
            bindingResult.getFieldErrors().forEach(error -> fieldErrors.put(error.getField(), error.getDefaultMessage()));
            response.setStatusCode(400);
            response.setData(fieldErrors);
            response.setMessage("There are some fields invalid!");
            return new ResponseEntity<>(response, HttpStatus.BAD_REQUEST);
        }
        double tongTien = 0;
        int soLuongVe = 0;
        // Tiến hành lưu hóa đơn
        Optional<HoaDonDTO> savedHoaDon = hoaDonService.addHoaDon(hoaDonCreateDTO.getHoaDonDTO());
        if (savedHoaDon.isPresent()) {
            // Thêm chi tiết hóa đơn
            if (hoaDonCreateDTO.getChiTietHoaDonDTOList() != null) {
                for (ChiTietHoaDonDTO chiTietHoaDonDTO : hoaDonCreateDTO.getChiTietHoaDonDTOList()) {
                    System.out.println(chiTietHoaDonDTO);
                    chiTietHoaDonDTO.setHoaDon(HoaDonMapper.toEntity(savedHoaDon.get())); // Gán ID hóa đơn cho chi tiết
                    Optional<ChiTietHoaDonDTO> savedCTHD =  chiTietHoaDonService.addChiTietHoaDon(chiTietHoaDonDTO); // Gọi service để thêm chi tiết hóa đơn

                    if (savedCTHD.get().getVe() != null) {
                        soLuongVe++;
                    }
                    tongTien+=savedCTHD.get().getSoTien();
                }

            }
            System.out.println("tong tien: "+tongTien);
            savedHoaDon = hoaDonService.getHoaDonById(savedHoaDon.get().getIdHoaDon());
            savedHoaDon.get().setTongTien(tongTien);
            savedHoaDon.get().setSoLuongVe(soLuongVe);
            hoaDonService.updateHoaDon(savedHoaDon.get());

            response.setMessage("Save Hoa Don successfully!");
            response.setData(savedHoaDon.get());
            response.setStatusCode(200);
            return new ResponseEntity<>(response, HttpStatus.CREATED);
        } else {
            response.setMessage("Save Hoa Don unsuccessfully!");
            response.setData(null);
            response.setStatusCode(500);
            return new ResponseEntity<>(response, HttpStatus.INTERNAL_SERVER_ERROR);
        }

    }
    @PutMapping("/markDanhGia/{idHoaDon}")
    public ResponseEntity<ResponseData> markDanhGia(@PathVariable int idHoaDon) {
        Optional<HoaDonDTO> hoaDonDTO = hoaDonService.getHoaDonById(idHoaDon);
        if(hoaDonDTO.isPresent()) {
            if (hoaDonService.markDanhGia(idHoaDon)) {
                response.setMessage("Save Hoa Don successfully!");
                response.setData(true);
                response.setStatusCode(200);
                return new ResponseEntity<>(response, HttpStatus.CREATED);
            } else {
                response.setMessage("Save Hoa Don unsuccessfully!");
                response.setData(null);
                response.setStatusCode(500);
                return new ResponseEntity<>(response, HttpStatus.INTERNAL_SERVER_ERROR);
            }
        } else {
            response.setMessage("Can not found Hoa Don!");
            response.setData(false);
            response.setStatusCode(404);
            return new ResponseEntity<>(response, HttpStatus.INTERNAL_SERVER_ERROR);
        }
    }

    @GetMapping("/thongke/thang")
    public ResponseEntity<ResponseData> getRevenueByMonth(@RequestParam int month, @RequestParam int year) {
        Double revenue = hoaDonService.getRevenueByMonth(month, year);
        if (revenue == null) {
            revenue = 0.0;
        }
        response.setData(revenue);
        response.setStatusCode(200);
        response.setMessage("Lấy doanh thu theo tháng "+ month +"/"+ year + " thành công");
        return new ResponseEntity<>(response, HttpStatus.OK);
    }

    // Thống kê doanh thu theo quý
    @GetMapping("/thongke/quy")
    public ResponseEntity<ResponseData> getRevenueByQuarter(@RequestParam int quarter, @RequestParam int year) {
        Double revenue = hoaDonService.getRevenueByQuarter(quarter, year);
        if (revenue == null) {
            revenue = 0.0;
        }
        response.setData(revenue);
        response.setStatusCode(200);
        response.setMessage("Lấy doanh thu theo quý "+ quarter +"/"+ year + " thành công");
        return new ResponseEntity<>(response, HttpStatus.OK);
    }

    // Thống kê doanh thu theo năm
    @GetMapping("/thongke/nam")
    public ResponseEntity<ResponseData> getRevenueByYear(@RequestParam int year) {
        Double revenue = hoaDonService.getRevenueByYear(year);
        if (revenue == null) {
            revenue = 0.0;
        }
        response.setData(revenue);
        response.setStatusCode(200);
        response.setMessage("Lấy doanh thu theo năm "+ year + " thành công");
        return new ResponseEntity<>(response, HttpStatus.OK);
    }

    @GetMapping("/thongke/giua-thang")
    public ResponseEntity<ResponseData> getRevenueBetweenMonths(
            @RequestParam int startMonth,
            @RequestParam int startYear,
            @RequestParam int endMonth,
            @RequestParam int endYear) {

        Double revenue = hoaDonService.getRevenueBetweenMonths(startMonth, startYear, endMonth, endYear);
        if (revenue == null) {
            revenue = 0.0;
        }
        response.setData(revenue);
        response.setStatusCode(200);
        response.setMessage("Lấy doanh thu từ tháng "+startMonth+"/"+startYear+" đến tháng"+endMonth+"/"+endYear+ " thành công");
        return new ResponseEntity<>(response, HttpStatus.OK);
    }

    // Thống kê doanh thu giữa 2 quý
    @GetMapping("/thongke/giua-quy")
    public ResponseEntity<ResponseData> getRevenueBetweenQuarters(
            @RequestParam int startQuarter,
            @RequestParam int startYear,
            @RequestParam int endQuarter,
            @RequestParam int endYear) {

        Double revenue = hoaDonService.getRevenueBetweenQuarters(startQuarter, startYear, endQuarter, endYear);
        if (revenue == null) {
            revenue = 0.0;
        }
        response.setData(revenue);
        response.setStatusCode(200);
        response.setMessage("Lấy doanh thu từ quý "+startQuarter+"-"+startYear+" đến quý "+endQuarter+"-"+endYear+ " thành công");
        return new ResponseEntity<>(response, HttpStatus.OK);
    }

    // Thống kê doanh thu giữa 2 năm
    @GetMapping("/thongke/giua-nam")
    public ResponseEntity<ResponseData> getRevenueBetweenYears(
            @RequestParam int startYear,
            @RequestParam int endYear) {

        Double revenue = hoaDonService.getRevenueBetweenYears(startYear, endYear);
        if (revenue == null) {
            revenue = 0.0;
        }
        response.setData(revenue);
        response.setStatusCode(200);
        response.setMessage("Lấy doanh thu từ "+startYear+" đến "+endYear+ " thành công");
        return new ResponseEntity<>(response, HttpStatus.OK);
    }

    @GetMapping("/thongke/theothang")
    public ResponseEntity<ResponseData> getListMonthlyRevenueByYear(@RequestParam int year) {
        // Khai báo một danh sách để chứa doanh thu của tất cả các tháng trong năm
        List<Double> revenues = new ArrayList<>();

        // Lặp qua tất cả các tháng từ 1 đến 12
        for (int month = 1; month <= 12; month++) {
            // Gọi service để lấy doanh thu theo từng tháng
            Double revenue = hoaDonService.getRevenueByMonth(month, year);

            // Nếu không có doanh thu cho tháng này, gán giá trị là 0
            if (revenue == null) {
                revenue = 0.0;
            }

            // Thêm doanh thu của tháng vào danh sách
            revenues.add(revenue);
        }

        // Khởi tạo đối tượng ResponseData để trả về kết quả
        ResponseData response = new ResponseData();
        response.setData(revenues);  // Dữ liệu là danh sách doanh thu của 12 tháng
        response.setStatusCode(200); // Mã trạng thái thành công
        response.setMessage("Lấy doanh thu theo tháng cho năm " + year + " thành công");

        // Trả về ResponseEntity với mã trạng thái HTTP 200 OK
        return new ResponseEntity<>(response, HttpStatus.OK);
    }

    @GetMapping("/thongke/theoquy")
    public ResponseEntity<ResponseData> getListQuarterRevenueByYear(@RequestParam int year) {
        List<Double> revenues = new ArrayList<>();

        for (int quarter = 1; quarter <= 4; quarter++) {
            // Gọi service để lấy doanh thu theo từng tháng
            Double revenue = hoaDonService.getRevenueByQuarter(quarter, year);

            // Nếu không có doanh thu cho tháng này, gán giá trị là 0
            if (revenue == null) {
                revenue = 0.0;
            }

            // Thêm doanh thu của tháng vào danh sách
            revenues.add(revenue);
        }

        // Khởi tạo đối tượng ResponseData để trả về kết quả
        ResponseData response = new ResponseData();
        response.setData(revenues);  // Dữ liệu là danh sách doanh thu của 12 tháng
        response.setStatusCode(200); // Mã trạng thái thành công
        response.setMessage("Lấy doanh thu theo quý cho năm " + year + " thành công");

        // Trả về ResponseEntity với mã trạng thái HTTP 200 OK
        return new ResponseEntity<>(response, HttpStatus.OK);
    }

    @GetMapping("/thongke/theonam")
    public ResponseEntity<ResponseData> getRevenueByAllYears() {
        // Lấy doanh thu cho tất cả các năm
        Map<Integer, Double> revenueByYear = hoaDonService.getRevenueForAllYears();

        ResponseData response = new ResponseData();
        response.setData(revenueByYear);
        response.setStatusCode(200);
        response.setMessage("Lấy doanh thu theo tất cả các năm thành công");

        return new ResponseEntity<>(response, HttpStatus.OK);
    }

    @GetMapping("/thongke/namhoadon")
    public ResponseEntity<ResponseData> getAllYears() {
        List<Integer> year = hoaDonService.getAllYears();

        ResponseData response = new ResponseData();
        response.setData(year);
        response.setStatusCode(200);
        response.setMessage("Lấy tất cả các năm thành công");

        return new ResponseEntity<>(response, HttpStatus.OK);
    }
}
