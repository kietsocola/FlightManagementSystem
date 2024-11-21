package com.project.flightManagement.Controller;

import com.project.flightManagement.DTO.ChuyenBayDTO.ChuyenBayDTO;
import com.project.flightManagement.DTO.NhanVienDTO.NhanVienDTO;
import com.project.flightManagement.DTO.TuyenBayDTO.TuyenBayDTO;
import com.project.flightManagement.DTO.VeDTO.VeDTO;
import com.project.flightManagement.Enum.ChuyenBayEnum;
import com.project.flightManagement.Enum.VeEnum;
import com.project.flightManagement.Payload.ResponseData;
import com.project.flightManagement.Service.*;
import jakarta.validation.Valid;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.format.annotation.DateTimeFormat;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.*;

import java.time.Duration;
import java.time.LocalDate;
import java.time.LocalDateTime;
import java.time.temporal.TemporalAdjusters;
import java.util.*;
import java.util.LinkedHashMap;
import java.util.Map;
import java.util.Optional;
import java.util.stream.StreamSupport;

@RestController
@RequestMapping("/admin/chuyenbay")
@CrossOrigin(origins = "http://localhost:5173")

public class ChuyenBayController {

    @Autowired
    private ChuyenBayService cbservice;
    @Autowired
    private VeService veService;
    @Autowired
    private NhanVienService nhanVienService;
    @Autowired
    private TuyenBayService tuyenBayService;
    @Autowired
    private SanBayService sanBayService ;
    private ResponseData response =  new ResponseData();

    @GetMapping("/search")
    public ResponseEntity<ResponseData> searchFlights(
            @RequestParam String departureLocation,
            @RequestParam String arrivalLocation,
            @RequestParam @DateTimeFormat(iso = DateTimeFormat.ISO.DATE) Date departureDate,
            @RequestParam(required = false) @DateTimeFormat(iso = DateTimeFormat.ISO.DATE) Date returnDate,
            @RequestParam int numberOfTickets) {
        ResponseData response = new ResponseData();
        List<ChuyenBayDTO> flightsDi = cbservice.searchFlights(departureLocation, arrivalLocation, departureDate, numberOfTickets);
        List<ChuyenBayDTO> flightsVe = cbservice.searchFlights(arrivalLocation, departureLocation, returnDate, numberOfTickets);

        Map<String, Object> flightMap = new LinkedHashMap<>();

// Kiểm tra chuyến đi
        if (flightsDi != null && !flightsDi.isEmpty()) {
            flightMap.put("chuyenbaydi", Map.of("status", "found", "data", flightsDi));
        } else {
            flightMap.put("chuyenbaydi", Map.of("status", "not_found", "data", Collections.emptyList()));
        }

// Kiểm tra chuyến về
        if (flightsVe != null && !flightsVe.isEmpty()) {
            flightMap.put("chuyenbayve", Map.of("status", "found", "data", flightsVe));
        } else {
            flightMap.put("chuyenbayve", Map.of("status", "not_found", "data", Collections.emptyList()));
        }

// Xử lý phản hồi tổng thể
        if (!"not_found".equals(((Map<String, Object>) flightMap.get("chuyenbaydi")).get("status"))
                || !"not_found".equals(((Map<String, Object>) flightMap.get("chuyenbayve")).get("status"))) {
            response.setMessage("Flight search completed with partial or full results.");
            response.setData(flightMap);
            response.setStatusCode(200);
            return new ResponseEntity<>(response, HttpStatus.OK);
        } else {
            response.setMessage("No flights found for both departure and return routes.");
            response.setData(flightMap); // Trả về map dù không tìm thấy
            response.setStatusCode(404);
            return new ResponseEntity<>(response, HttpStatus.NOT_FOUND);
        }

    }
    @GetMapping("/getallchuyenbay")
    public ResponseEntity<ResponseData> getAllChuyenBay(){
        Iterable<ChuyenBayDTO> listcb = cbservice.getAllChuyenBay();
        if(listcb.iterator().hasNext()){
            response.setMessage("Tim thay chuyen bay");
            response.setData(listcb);
            response.setStatusCode(200);
            return new ResponseEntity<>(response, HttpStatus.OK);
        }else{
            response.setMessage("get list Chuyen Bay unsuccess!!");
            response.setData(null);
            response.setStatusCode(204);
            return new ResponseEntity<>(HttpStatus.NO_CONTENT);
        }
    }

    @GetMapping("/getallchuyenbaysorted")
    public ResponseEntity<ResponseData> getAllNhanVien(@RequestParam(defaultValue = "idChuyenBay") String sortField ,@RequestParam(defaultValue = "asc") String sortOrder){
        Iterable<ChuyenBayDTO> listncbDTO = cbservice.getAllChuyenBaySorted(sortField,sortOrder);
        if(listncbDTO.iterator().hasNext()){
            response.setMessage("get list Chuyen Bay success!!");
            response.setData(listncbDTO);
            response.setStatusCode(200);
            return new ResponseEntity<>(response, HttpStatus.OK);
        } else {
            response.setMessage("get list Chuyen Bay unsuccess!!");
            response.setData(null);
            response.setStatusCode(204);
            return new ResponseEntity<>(HttpStatus.NO_CONTENT);
        }
    }

    @PostMapping("/addchuyenbay")
    public ResponseEntity<ResponseData> addChuyenBay(@Valid @RequestBody ChuyenBayDTO cbDTO, BindingResult bindingResult) {

        if (bindingResult.hasErrors()) {
            Map<String, String> fieldErrors = new LinkedHashMap<>();
            bindingResult.getFieldErrors().forEach(error ->
                    fieldErrors.put(error.getField(), error.getDefaultMessage()));
            response.setStatusCode(400);
            response.setData(fieldErrors);
            response.setMessage("There are some fields invalid");
            return new ResponseEntity<>(response, HttpStatus.BAD_REQUEST);
        }

        Iterable<ChuyenBayDTO> listCB = cbservice.getAllChuyenBay();
        ChuyenBayEnum scheduled = ChuyenBayEnum.SCHEDULED;
        ChuyenBayEnum cancled = ChuyenBayEnum.CANCELED;
        ChuyenBayEnum completed = ChuyenBayEnum.COMPLETED;
        ChuyenBayEnum in_flight = ChuyenBayEnum.IN_FLIGHT;

        for(ChuyenBayDTO cb : listCB){
            ChuyenBayEnum status = cb.getTrangThai() ;
            if (cb.getIdChuyenBay() != cbDTO.getIdChuyenBay()
                    && cb.getTuyenBay().getIdTuyenBay() == cbDTO.getTuyenBay().getIdTuyenBay()
                    && !isDifferenceGreaterThanTwoHour(cb.getThoiGianBatDauDuTinh() , cbDTO.getThoiGianBatDauThucTe())
                    && !completed.name().equals(status.name())
                    && !cancled.name().equals(status.name())){
                String sanbaybatdau = sanBayService.getSanBayById(cb.getTuyenBay().getIdSanBayBatDau()).get().getTenSanBay();
                String sanbayketthuc = sanBayService.getSanBayById(cb.getTuyenBay().getIdSanBayKetThuc()).get().getTenSanBay();
                System.out.println("id chuyen bay : " +  cb.getIdChuyenBay());
                System.out.println("khoang thoi gian >  2 gio : " + !isDifferenceGreaterThanTwoHour(cb.getThoiGianBatDauDuTinh() , cbDTO.getThoiGianBatDauThucTe()));
                response.setMessage("Chuyến bay này gần với thời gian của một chuyến bay từ " +sanbaybatdau+ "->" + sanbayketthuc  + " có cùng tuyến bay : " + cb.getThoiGianBatDauDuTinh() + ".Phải cách nhau hơn 2 tiếng");
                response.setData(null);
                response.setStatusCode(409);
                return new ResponseEntity<>(response, HttpStatus.CONFLICT);
            }
        }

        for(ChuyenBayDTO cb : listCB) {
            ChuyenBayEnum status = cb.getTrangThai();
            if (cb.getIdChuyenBay() != cbDTO.getIdChuyenBay()
                    && cb.getMayBay().getIdMayBay() == cbDTO.getMayBay().getIdMayBay()
                    && !isDifferenceGreaterThanTwoHour(cb.getThoiGianKetThucThucTe(), cbDTO.getThoiGianBatDauThucTe())
                    && completed.name().equals(status.name())) {
                String sanbaybatdau = sanBayService.getSanBayById(cb.getTuyenBay().getIdSanBayBatDau()).get().getTenSanBay();
                String sanbayketthuc = sanBayService.getSanBayById(cb.getTuyenBay().getIdSanBayKetThuc()).get().getTenSanBay();
                System.out.println("id chuyen bay : " + cb.getIdChuyenBay());
                System.out.println("khoang thoi gian >  2 gio : " + !isDifferenceGreaterThanTwoHour(cb.getThoiGianKetThucThucTe(), cbDTO.getThoiGianBatDauThucTe()));
                response.setMessage("Đã của một chuyến bay từ " +sanbaybatdau+ "->" + sanbayketthuc  + " với thời gian : " + cb.getThoiGianBatDauDuTinh() + ".Hai chuyến bay có cùng tuyến bay phải cách nhau hơn 2 tiếng");
                response.setData(null);
                response.setStatusCode(409);
                return new ResponseEntity<>(response, HttpStatus.CONFLICT);
            }
        }

        Optional<ChuyenBayDTO> saveCB = cbservice.addChuyenBay(cbDTO);
        if (saveCB.isPresent()) {
            int idChuyenBay = saveCB.get().getIdChuyenBay();
            veService.createAutoVeByIdChuyenBay(idChuyenBay, cbDTO.getGiaVeThuong(), cbDTO.getGiaVeThuongGia());
            response.setMessage("Save Nhan vien successfully!!");
            response.setData(saveCB.get()); // Trả về dữ liệu của khách hàng đã lưu
            response.setStatusCode(201); // Created
            return new ResponseEntity<>(response, HttpStatus.CREATED);
        } else {
            // Xử lý lỗi khi lưu không thành công
            response.setMessage("Save nhan vien unsuccessfully!!");
            response.setData(null);
            response.setStatusCode(500); // Internal Server Error
            return new ResponseEntity<>(response, HttpStatus.INTERNAL_SERVER_ERROR);
        }
    }

    @GetMapping("/getchuyenbaybyid/{idChuyenBay}")
    public ResponseEntity<ResponseData> getchuyenbaybyid(@PathVariable int idChuyenBay){
        Optional<ChuyenBayDTO> cbDTO = cbservice.getChuyenBayById(idChuyenBay);
        if (cbDTO.isPresent()) {
            response.setMessage("Save Nhan vien successfully!!");
            response.setData(cbDTO.get()); // Trả về dữ liệu của khách hàng đã lưu
            response.setStatusCode(201); // Created
            return new ResponseEntity<>(response, HttpStatus.CREATED);
        } else {
            // Xử lý lỗi khi lưu không thành công
            response.setMessage("Save nhan vien unsuccessfully!!");
            response.setData(null);
            response.setStatusCode(500); // Internal Server Error
            return new ResponseEntity<>(response, HttpStatus.INTERNAL_SERVER_ERROR);
        }
    }

    @PutMapping("/updatechuyenbay/{idChuyenBay}")
    public ResponseEntity<ResponseData> updatechuyenbay(@PathVariable("idChuyenBay") Integer idChuyenBay , @Valid @RequestBody ChuyenBayDTO cbDTO, BindingResult bindingResult){
        if(bindingResult.hasErrors()){
            Map<String, String> fieldErrors = new HashMap<>();
            bindingResult.getFieldErrors().forEach(error ->
                    fieldErrors.put(error.getField(), error.getDefaultMessage()));
            response.setStatusCode(400);
            response.setData(fieldErrors);
            response.setMessage("There are some fields invalid");
            return new ResponseEntity<>(response, HttpStatus.BAD_REQUEST);
        }

        Iterable<ChuyenBayDTO> listCB = cbservice.getAllChuyenBay();
        ChuyenBayEnum scheduled = ChuyenBayEnum.SCHEDULED;
        ChuyenBayEnum cancled = ChuyenBayEnum.CANCELED;
        ChuyenBayEnum completed = ChuyenBayEnum.COMPLETED;
        ChuyenBayEnum in_flight = ChuyenBayEnum.IN_FLIGHT;

        for(ChuyenBayDTO cb : listCB){
            ChuyenBayEnum status = cb.getTrangThai() ;
            if (cb.getIdChuyenBay() != cbDTO.getIdChuyenBay()
                    && cb.getTuyenBay().getIdTuyenBay() == cbDTO.getTuyenBay().getIdTuyenBay()
                    && !isDifferenceGreaterThanTwoHour(cb.getThoiGianBatDauDuTinh() , cbDTO.getThoiGianBatDauThucTe())
                    && !completed.name().equals(status.name())
                    && !cancled.name().equals(status.name())){
                String sanbaybatdau = sanBayService.getSanBayById(cb.getTuyenBay().getIdSanBayBatDau()).get().getTenSanBay();
                String sanbayketthuc = sanBayService.getSanBayById(cb.getTuyenBay().getIdSanBayKetThuc()).get().getTenSanBay();
                System.out.println("id chuyen bay : " +  cb.getIdChuyenBay());
                System.out.println("khoang thoi gian >  2 gio : " + !isDifferenceGreaterThanTwoHour(cb.getThoiGianBatDauDuTinh() , cbDTO.getThoiGianBatDauThucTe()));
                response.setMessage("Đã của một chuyến bay từ " +sanbaybatdau+ "->" + sanbayketthuc  + " với thời gian : " + cb.getThoiGianBatDauDuTinh() + ".Hai chuyến bay có cùng tuyến bay phải cách nhau hơn 2 tiếng");
                response.setData(null);
                response.setStatusCode(409);
                return new ResponseEntity<>(response, HttpStatus.CONFLICT);
            }
        }

        for(ChuyenBayDTO cb : listCB){
            ChuyenBayEnum status = cb.getTrangThai() ;
            if (cb.getIdChuyenBay() != cbDTO.getIdChuyenBay()
                    && cb.getMayBay().getIdMayBay() == cbDTO.getMayBay().getIdMayBay()
                    && !isDifferenceGreaterThanTwoHour(cb.getThoiGianKetThucThucTe() , cbDTO.getThoiGianBatDauThucTe())
                    && completed.name().equals(status.name())){
                System.out.println("id chuyen bay : " +  cb.getIdChuyenBay());
                System.out.println("khoang thoi gian >  2 gio : " + !isDifferenceGreaterThanTwoHour(cb.getThoiGianKetThucThucTe() , cbDTO.getThoiGianBatDauThucTe()));
                response.setMessage("Máy bay này vừa kết thúc chuyến bay luc : " +  cb.getThoiGianKetThucThucTe()+ ".Hãy đợi 2 tiếng để có thể tạo chuyến bay với máy bay này");
                response.setData(null);
                response.setStatusCode(409);
                return new ResponseEntity<>(response, HttpStatus.CONFLICT);
            }
        }

        Optional<ChuyenBayDTO> cbOld = cbservice.getChuyenBayById(idChuyenBay);

        ChuyenBayEnum status = cbOld.get().getTrangThai();
        LocalDateTime timeCurrent = LocalDateTime.now();
        if(status.name().equals(cancled.name()) || status.name().equals(completed.name()) || status.name().equals(in_flight.name())){
            if(status.name().equals(cancled.name()))
            {
                response.setStatusCode(200);
                response.setData(null);
                response.setMessage("Chuyến bay đã huỷ.Không thể thay đổi thông tin");
                return new ResponseEntity<>(response, HttpStatus.OK);
            }
            else if(status.name().equals(completed.name())){
                response.setStatusCode(200);
                response.setData(null);
                response.setMessage("Chuyến bay đã hoàn thành.Không thể thay đổi thông tin");
                return new ResponseEntity<>(response, HttpStatus.OK);
            }
            else if(status.name().equals(in_flight.name()) && timeCurrent.isBefore(cbOld.get().getThoiGianKetThucThucTe())){
                response.setStatusCode(200);
                response.setData(null);
                response.setMessage("Chuyến bay đang diễn ra.Không thể thay đổi thông tin");
                return new ResponseEntity<>(response, HttpStatus.OK);
            }
        }

        Optional<ChuyenBayDTO> saveCb = cbservice.updateChuyenBay(cbDTO);
        if (saveCb.isPresent()) {
            response.setMessage("Sửa chuyến bay thành công");
            veService.updateAutoGiaVeByIdChuyenBay(saveCb.get().getIdChuyenBay(), cbDTO.getGiaVeThuong(), cbDTO.getGiaVeThuongGia());
            response.setData(saveCb.get()); // Trả về dữ liệu của khách hàng đã lưu
            response.setStatusCode(201); // Created
            return new ResponseEntity<>(response, HttpStatus.CREATED);
        } else {
            // Xử lý lỗi khi lưu không thành công
            response.setMessage("Sửa chuyến bay thaatts bại ");
            response.setData(null);
            response.setStatusCode(500); // Internal Server Error
            return new ResponseEntity<>(response, HttpStatus.INTERNAL_SERVER_ERROR);
        }
    }

    @GetMapping("/filter")
    public ResponseEntity<ResponseData> filterChuyenBay(
            @RequestParam(value = "trangThai", required = false) ChuyenBayEnum trangThai,
            @RequestParam(value = "thoiGianBatDau", required = false) String thoiGianBatDauStr,
            @RequestParam(value = "thoiGianKetThuc", required = false) String thoiGianKetThucStr) {

        LocalDateTime thoiGianBatDau = null;
        LocalDateTime thoiGianKetThuc = null;

        // Chuyển đổi từ String sang LocalDateTime
        if (thoiGianBatDauStr != null && !thoiGianBatDauStr.isEmpty()) {
            thoiGianBatDau = LocalDateTime.parse(thoiGianBatDauStr);
        }

        if (thoiGianKetThucStr != null && !thoiGianKetThucStr.isEmpty()) {
            thoiGianKetThuc = LocalDateTime.parse(thoiGianKetThucStr);
        }
        Iterable<ChuyenBayDTO> chuyenBayList = cbservice.getFilterChuyenBay(trangThai, thoiGianBatDau , thoiGianKetThuc);
        if(chuyenBayList.iterator().hasNext()){
            response.setMessage("get list chuyen bay success");
            response.setData(chuyenBayList);
            response.setStatusCode(200);
            return new ResponseEntity<>(response, HttpStatus.OK);
        } else {
            response.setMessage("get list chuyen bay unsuccess!!");
            response.setData(null);
            response.setStatusCode(404);
            return new ResponseEntity<>(response, HttpStatus.NOT_FOUND);
        }
    }

    public static boolean isDifferenceGreaterThanTwoHour(LocalDateTime a, LocalDateTime b) {
        // Tính khoảng thời gian giữa hai LocalDateTime
        Duration duration = Duration.between(a, b);
        // Kiểm tra nếu chênh lệch tuyệt đối lớn hơn 2 giờ
        return Math.abs(duration.toHours()) >= 2;
    }

    @GetMapping("/getHoursFlightOfFlight/{idChuyenBay}")
    public ResponseEntity<ResponseData> getSoGioBayCuaChuyenBay (@PathVariable int idChuyenBay) {
        Optional<ChuyenBayDTO> cb = cbservice.getChuyenBayById(idChuyenBay);
        if (cb.isPresent()) {
            String hours = cbservice.getHoursOfFlight(cb.get().getIdChuyenBay());
            if (hours != "00:00:00") {
                response.setMessage("Get time of flight success!!");
                response.setStatusCode(200);
                response.setData(hours);
                return new ResponseEntity<>(response, HttpStatus.OK);
            } else {
                response.setData("00:00:00");
                response.setStatusCode(404);
                response.setMessage("Error to get time of flight!!");
                return new ResponseEntity<>(response, HttpStatus.BAD_REQUEST);
            }
        } else {
            response.setMessage("Cant found flght!!");
            response.setData("00:00:00");
            response.setStatusCode(404);
            return new ResponseEntity<>(response, HttpStatus.NOT_FOUND);
        }
    }

    @GetMapping("/thongKeGioBayNhanVienByYear")
    public ResponseEntity<ResponseData> thongkegiobaynhanvienbyyear(){
        int namBatDau = 2017 ;
        int namKetThuc = 2024 ;
        Iterable<NhanVienDTO> listNhanVien = nhanVienService.getAllNhanVien();
        List<Map<String , Object>> danhSachNhanVien = new ArrayList<>();

        for(NhanVienDTO nhanvienDTO : listNhanVien){
            Map<String, Integer> soGioBayTheoNamDefault = new LinkedHashMap<>();
            for(int nam  = namBatDau ; nam <= namKetThuc ; nam++)
                soGioBayTheoNamDefault.put("Year:" +nam, 0);

            Map<String, Object> nhanVien1 = new LinkedHashMap<>();
            nhanVien1.put("idNhanVien", nhanvienDTO.getIdNhanVien());
            nhanVien1.put("tenNhanVien", nhanvienDTO.getHoTen());
            nhanVien1.put("email", nhanvienDTO.getEmail());
            nhanVien1.put("soGioBay", soGioBayTheoNamDefault);
            danhSachNhanVien.add(nhanVien1);
        }

        for(int nam  = namBatDau ; nam <= namKetThuc ; nam++)
        {
            Iterable<ChuyenBayDTO>  listChuyenBay = cbservice.getChuyenBayByYear(nam);
            for(ChuyenBayDTO chuyenBayDTO  : listChuyenBay){
                if(!chuyenBayDTO.getTrangThai().name().equals("COMPLETED"))
                    continue;
                for(NhanVienDTO nhanvienDTO  : listNhanVien){
                    String[] temp = chuyenBayDTO.getNvhk().split("/");
                    String[] temp1 = temp[2].split("-");
                    String[] danhSachIdNhanVien = new String[0];
                    danhSachIdNhanVien = Arrays.copyOf(danhSachIdNhanVien ,danhSachIdNhanVien.length + 1);
                    danhSachIdNhanVien[danhSachIdNhanVien.length-1] = temp[0];
                    danhSachIdNhanVien = Arrays.copyOf(danhSachIdNhanVien ,danhSachIdNhanVien.length + 1);
                    danhSachIdNhanVien[danhSachIdNhanVien.length-1] = temp[1];
                    for(String item : temp1){
                        danhSachIdNhanVien = Arrays.copyOf(danhSachIdNhanVien ,danhSachIdNhanVien.length + 1);
                        danhSachIdNhanVien[danhSachIdNhanVien.length-1] = item;
                    }
                    for(String id : danhSachIdNhanVien){
                        int numId = Integer.parseInt(id);
                        if(numId == nhanvienDTO.getIdNhanVien()){
                            for(Map<String , Object> item : danhSachNhanVien)
                                if((int)item.get("idNhanVien") == nhanvienDTO.getIdNhanVien())
                                {
                                    Map<String, Integer> soGioBayTheoNam = (Map<String, Integer>) item.get("soGioBay");
                                    System.out.println("so gio bay theo nam " + nam + "  : " + (soGioBayTheoNam.get("Year:" +nam) + chuyenBayDTO.getTuyenBay().getThoiGianChuyenBay()));
                                    soGioBayTheoNam.put("Year:" + nam , (int)soGioBayTheoNam.get("Year:" + nam) + (int)chuyenBayDTO.getTuyenBay().getThoiGianChuyenBay());
                                }
                        }
                    }
                }
            }
        }

        if (danhSachNhanVien.size() > 0) {
            response.setMessage("Thông kế thành công");
            response.setData(danhSachNhanVien); // Trả về dữ liệu của khách hàng đã lưu
            response.setStatusCode(201); // Created
            return new ResponseEntity<>(response, HttpStatus.CREATED);
        } else {
            // Xử lý lỗi khi lưu không thành công
            response.setMessage("Khong co ket qua");
            response.setData(null);
            response.setStatusCode(500); // Internal Server Error
            return new ResponseEntity<>(response, HttpStatus.INTERNAL_SERVER_ERROR);
        }
    }

    @GetMapping("/thongKeGioBayNhanVienByYearAndMonth")
    public ResponseEntity<ResponseData> thongkegiobaynhanvienbyyearandmonth(){
        int namBatDau = 2017 ;
        int namKetThuc = 2024 ;
        Iterable<NhanVienDTO> listNhanVien = nhanVienService.getAllNhanVien();
        List<Map<String , Object>> danhSachNhanVien = new ArrayList<>();

        for(NhanVienDTO nhanvienDTO : listNhanVien){
            Map<String, Map<String, Integer>> soGioBayTheoNamDefault = new LinkedHashMap<>();
            for(int nam  = namBatDau ; nam <= namKetThuc ; nam++)
            {
                Map<String, Integer> soGioBayTheoThangDefault = new LinkedHashMap<>();
                for(int i = 1 ; i <=12  ; i++)
                    soGioBayTheoThangDefault.put("Month:" + i , 0);
                soGioBayTheoNamDefault.put("Year:" + nam, soGioBayTheoThangDefault);
            }


            Map<String, Object> nhanVien1 = new LinkedHashMap<>();
            nhanVien1.put("idNhanVien", nhanvienDTO.getIdNhanVien());
            nhanVien1.put("tenNhanVien", nhanvienDTO.getHoTen());
            nhanVien1.put("email", nhanvienDTO.getEmail());
            nhanVien1.put("soGioBay", soGioBayTheoNamDefault);
            danhSachNhanVien.add(nhanVien1);
        }

        for(int nam  = namBatDau ; nam <= namKetThuc ; nam++)
        {
            for(int thang = 1 ; thang <= 12 ; thang++)
            {
                Iterable<ChuyenBayDTO>  listChuyenBay = cbservice.getChuyenBayByYearAndMonth(nam , thang);
                for(ChuyenBayDTO chuyenBayDTO  : listChuyenBay){
                    if(!chuyenBayDTO.getTrangThai().name().equals("COMPLETED"))
                        continue;
                    for(NhanVienDTO nhanvienDTO  : listNhanVien){
                        String[] temp = chuyenBayDTO.getNvhk().split("/");
                        String[] temp1 = temp[2].split("-");
                        String[] danhSachIdNhanVien = new String[0];
                        danhSachIdNhanVien = Arrays.copyOf(danhSachIdNhanVien ,danhSachIdNhanVien.length + 1);
                        danhSachIdNhanVien[danhSachIdNhanVien.length-1] = temp[0];
                        danhSachIdNhanVien = Arrays.copyOf(danhSachIdNhanVien ,danhSachIdNhanVien.length + 1);
                        danhSachIdNhanVien[danhSachIdNhanVien.length-1] = temp[1];
                        for(String item : temp1){
                            danhSachIdNhanVien = Arrays.copyOf(danhSachIdNhanVien ,danhSachIdNhanVien.length + 1);
                            danhSachIdNhanVien[danhSachIdNhanVien.length-1] = item;
                        }
                        for(String id : danhSachIdNhanVien){
                            int numId = Integer.parseInt(id);
                            if(numId == nhanvienDTO.getIdNhanVien()){
                                for(Map<String , Object> item : danhSachNhanVien)
                                    if((int)item.get("idNhanVien") == nhanvienDTO.getIdNhanVien())
                                    {
                                        Map<Integer, Map<Integer, Integer>> soGioBayTheoNam = (Map<Integer, Map<Integer, Integer>>) item.get("soGioBay");
                                        Map<Integer , Integer> soGioBayTheoThang = (Map<Integer , Integer>) soGioBayTheoNam.get(nam);
                                        soGioBayTheoThang.put(thang , (int)soGioBayTheoThang.get(thang) + (int)chuyenBayDTO.getTuyenBay().getThoiGianChuyenBay());
//                                        soGioBayTheoNam.put()
                                    }
                            }
                        }
                    }
                }
            }
        }

        if (danhSachNhanVien.size() > 0) {
            response.setMessage("Thông kế thành công");
            response.setData(danhSachNhanVien); // Trả về dữ liệu của khách hàng đã lưu
            response.setStatusCode(201); // Created
            return new ResponseEntity<>(response, HttpStatus.CREATED);
        } else {
            // Xử lý lỗi khi lưu không thành công
            response.setMessage("Khong co ket qua");
            response.setData(null);
            response.setStatusCode(500); // Internal Server Error
            return new ResponseEntity<>(response, HttpStatus.INTERNAL_SERVER_ERROR);
        }
    }

    @GetMapping("/thongKeGioBayNhanVienByYearAndQuy")
    public ResponseEntity<ResponseData> thongkegiobaynhanvienbyyearandquy(){
        int namBatDau = 2017 ;
        int namKetThuc = 2024 ;
        Iterable<NhanVienDTO> listNhanVien = nhanVienService.getAllNhanVien();
        List<Map<String , Object>> danhSachNhanVien = new ArrayList<>();

        for(NhanVienDTO nhanvienDTO : listNhanVien){
            Map<String, Map<String, Integer>> soGioBayTheoNamDefault = new LinkedHashMap<>();
            for(int nam  = namBatDau ; nam <= namKetThuc ; nam++)
            {
                Map<String, Integer> soGioBayTheoQuyDefault = new LinkedHashMap<>();
                for(int i = 1 ; i <=4  ; i++)
                    soGioBayTheoQuyDefault.put("Quy:" +i , 0);
                soGioBayTheoNamDefault.put("Year:" + nam, soGioBayTheoQuyDefault);
            }


            Map<String, Object> nhanVien1 = new LinkedHashMap<>();
            nhanVien1.put("idNhanVien", nhanvienDTO.getIdNhanVien());
            nhanVien1.put("tenNhanVien", nhanvienDTO.getHoTen());
            nhanVien1.put("email", nhanvienDTO.getEmail());
            nhanVien1.put("soGioBay", soGioBayTheoNamDefault);
            danhSachNhanVien.add(nhanVien1);
        }

        for(int nam  = namBatDau ; nam <= namKetThuc ; nam++)
        {
            for(int quy = 1 ; quy <= 4 ; quy++)
            {
                Iterable<ChuyenBayDTO>  listChuyenBay = cbservice.filterChuyenBayByQuyAndNam(nam ,quy);
                for(ChuyenBayDTO chuyenBayDTO  : listChuyenBay){
                    if(!chuyenBayDTO.getTrangThai().name().equals("COMPLETED"))
                        continue;
                    for(NhanVienDTO nhanvienDTO  : listNhanVien){
                        String[] temp = chuyenBayDTO.getNvhk().split("/");
                        String[] temp1 = temp[2].split("-");
                        String[] danhSachIdNhanVien = new String[0];
                        danhSachIdNhanVien = Arrays.copyOf(danhSachIdNhanVien ,danhSachIdNhanVien.length + 1);
                        danhSachIdNhanVien[danhSachIdNhanVien.length-1] = temp[0];
                        danhSachIdNhanVien = Arrays.copyOf(danhSachIdNhanVien ,danhSachIdNhanVien.length + 1);
                        danhSachIdNhanVien[danhSachIdNhanVien.length-1] = temp[1];
                        for(String item : temp1){
                            danhSachIdNhanVien = Arrays.copyOf(danhSachIdNhanVien ,danhSachIdNhanVien.length + 1);
                            danhSachIdNhanVien[danhSachIdNhanVien.length-1] = item;
                        }
                        for(String id : danhSachIdNhanVien){
                            int numId = Integer.parseInt(id);
                            if(numId == nhanvienDTO.getIdNhanVien()){
                                for(Map<String , Object> item : danhSachNhanVien)
                                    if((int)item.get("idNhanVien") == nhanvienDTO.getIdNhanVien())
                                    {
                                        Map<String, Map<String, Integer>> soGioBayTheoNam = (Map<String, Map<String, Integer>>) item.get("soGioBay");
                                        Map<String , Integer> soGioBayTheoQuy = (Map<String , Integer>) soGioBayTheoNam.get("Year:" + nam);
                                        soGioBayTheoQuy.put("Quy:" + quy , (int)soGioBayTheoQuy.get("Quy:" + quy) + (int)chuyenBayDTO.getTuyenBay().getThoiGianChuyenBay());
                                    }
                            }
                        }
                    }
                }
            }
        }

        if (danhSachNhanVien.size() > 0) {
            response.setMessage("Thông kế thành công");
            response.setData(danhSachNhanVien); // Trả về dữ liệu của khách hàng đã lưu
            response.setStatusCode(201); // Created
            return new ResponseEntity<>(response, HttpStatus.CREATED);
        } else {
            // Xử lý lỗi khi lưu không thành công
            response.setMessage("Khong co ket qua");
            response.setData(null);
            response.setStatusCode(500); // Internal Server Error
            return new ResponseEntity<>(response, HttpStatus.INTERNAL_SERVER_ERROR);
        }
    }

    @GetMapping("/thongKeChuyenBayTheoTrangThaiByYear")
    public ResponseEntity<ResponseData> thongkechuyenbaytheotrangthaibyyear(){

        int namBatDau = 2017;
        int namKetThuc = LocalDate.now().getYear();
        List<Map<String , Map<String , Integer>>> result = new ArrayList<>();

        // loc theo nam
        for(int nam = namBatDau ;  nam <= namKetThuc ; nam++){

            Map<String , Integer> trangThai = new LinkedHashMap<>();
            int tong = 0;

            Iterable<ChuyenBayDTO> listChuyenBay = cbservice.getFilterChuyenBay(ChuyenBayEnum.CANCELED , LocalDateTime.parse(nam+"-01-01T00:00:00"),LocalDateTime.parse(nam+"-12-31T23:59:59"));
            int count= (int) StreamSupport.stream(listChuyenBay.spliterator(), false).count();
            trangThai.put("CANCELED" , count);
            tong+=count;

            listChuyenBay = cbservice.getFilterChuyenBay(ChuyenBayEnum.COMPLETED , LocalDateTime.parse(nam+"-01-01T00:00:00"),LocalDateTime.parse(nam+"-12-31T23:59:59"));

            count = 0 ;
            for(ChuyenBayDTO cb : listChuyenBay)
                if(cb.getDelay() == 0)
                    ++count ;
            trangThai.put("SCHEDULED" , count);
            tong+=count;


            count = 0 ;
            for(ChuyenBayDTO cb : listChuyenBay)
                if(cb.getDelay() > 0)
                    ++count ;
            trangThai.put("DELAYED", count);
            tong+=count;


            trangThai.put("TONG" , tong);

            Map<String ,  Map<String , Integer>>  namvatrangthai = new LinkedHashMap<>();
            namvatrangthai.put("Year:" + nam , trangThai);
            result.add(namvatrangthai);
        }

        if (result.size() > 0) {
            response.setMessage("Thông kế thành công");
            response.setData(result); // Trả về dữ liệu của khách hàng đã lưu
            response.setStatusCode(201); // Created
            return new ResponseEntity<>(response, HttpStatus.CREATED);
        } else {
            // Xử lý lỗi khi lưu không thành công
            response.setMessage("Khong co ket qua");
            response.setData(null);
            response.setStatusCode(500); // Internal Server Error
            return new ResponseEntity<>(response, HttpStatus.INTERNAL_SERVER_ERROR);
        }
    }

    @GetMapping("/thongKeChuyenBayTheoTrangThaiByYearAndMonth")
    public ResponseEntity<ResponseData> thongkechuyenbaytheotrangthaibyyearandmonth(){

        int namBatDau = 2017;
        int namKetThuc = 2024 ;
        List<Map<String , Map<String , Map<String , Integer>>>> result = new ArrayList<>();


        // loc theo nam
        for(int nam = namBatDau ;  nam <= namKetThuc ; nam++){
            Map<String ,  Map<String , Integer>>  thangvatrangthai = new LinkedHashMap<>();
            Map<String , Map<String ,  Map<String , Integer>>> namthangvatrangthai = new LinkedHashMap<>();

            for(int thang = 1 ; thang <= 12 ; thang++)
            {
                Map<String , Integer> trangThai = new LinkedHashMap<>();
                int tong = 0;

                // Tạo LocalDateTime cho đầu tháng
                LocalDateTime startOfMonth = LocalDateTime.of(nam, thang, 1, 0, 0, 0);

// Tạo LocalDateTime cho cuối tháng (ngày cuối cùng của tháng)
                LocalDateTime endOfMonth = LocalDateTime.of(nam, thang, 1, 23, 59, 59)
                        .with(TemporalAdjusters.lastDayOfMonth());
                Iterable<ChuyenBayDTO> listChuyenBay = cbservice.getFilterChuyenBay(ChuyenBayEnum.CANCELED , startOfMonth,endOfMonth);
                int count= (int) StreamSupport.stream(listChuyenBay.spliterator(), false).count();
                trangThai.put("CANCELED" , count);
                tong+=count;

                listChuyenBay = cbservice.getFilterChuyenBay(ChuyenBayEnum.COMPLETED, startOfMonth,endOfMonth);

                count = 0 ;
                for(ChuyenBayDTO cb : listChuyenBay)
                    if(cb.getDelay() == 0)
                        ++count ;
                trangThai.put("SCHEDULED" , count);
                tong+=count;


                count = 0 ;
                for(ChuyenBayDTO cb : listChuyenBay)
                    if(cb.getDelay() > 0)
                        ++count ;
                trangThai.put("DELAYED", count);
                tong+=count;


                trangThai.put("TONG " , tong);

                thangvatrangthai.put("Month:" + thang , trangThai);
            }
            namthangvatrangthai.put("Year:" + nam ,  thangvatrangthai);
            result.add(namthangvatrangthai);
        }

        if (!result.isEmpty()) {
            response.setMessage("Thông kế thành công");
            response.setData(result); // Trả về dữ liệu của khách hàng đã lưu
            response.setStatusCode(201); // Created
            return new ResponseEntity<>(response, HttpStatus.CREATED);
        } else {
            // Xử lý lỗi khi lưu không thành công
            response.setMessage("Khong co ket qua");
            response.setData(null);
            response.setStatusCode(500); // Internal Server Error
            return new ResponseEntity<>(response, HttpStatus.INTERNAL_SERVER_ERROR);
        }
    }

    @GetMapping("/thongKeChuyenBayTheoTrangThaiByYearAndQuy")
    public ResponseEntity<ResponseData> thongkechuyenbaytheotrangthaibyyearandquy(){

        int namBatDau = 2017;
        int namKetThuc = 2024 ;
        List<Map<String , Map<String , Map<String , Integer>>>> result = new ArrayList<>();


        // loc theo nam
        for(int nam = namBatDau ;  nam <= namKetThuc ; nam++){
            Map<String ,  Map<String , Integer>>  quyvatrangthai = new LinkedHashMap<>();
            Map<String , Map<String,  Map<String , Integer>>> namthangvatrangthai = new LinkedHashMap<>();

            for(int quy = 1 ; quy <= 4 ; quy++)
            {
                Map<String , Integer> trangThai = new LinkedHashMap<>();
                int tong = 0;

                // Tạo LocalDateTime cho đầu tháng
                LocalDateTime startOfMonth = LocalDateTime.of(nam, quy*3 -2, 1, 0, 0, 0);

// Tạo LocalDateTime cho cuối tháng (ngày cuối cùng của tháng)
                LocalDateTime endOfMonth = LocalDateTime.of(nam, quy*3, 1, 23, 59, 59)
                        .with(TemporalAdjusters.lastDayOfMonth());
                Iterable<ChuyenBayDTO> listChuyenBay = cbservice.getFilterChuyenBay(ChuyenBayEnum.CANCELED , startOfMonth,endOfMonth);
                int count= (int) StreamSupport.stream(listChuyenBay.spliterator(), false).count();
                trangThai.put("CANCELED" , count);
                tong+=count;

                listChuyenBay = cbservice.getFilterChuyenBay(ChuyenBayEnum.COMPLETED, startOfMonth,endOfMonth);

                count = 0 ;
                for(ChuyenBayDTO cb : listChuyenBay)
                    if(cb.getDelay() == 0)
                        ++count ;
                trangThai.put("SCHEDULED" , count);
                tong+=count;


                count = 0 ;
                for(ChuyenBayDTO cb : listChuyenBay)
                    if(cb.getDelay() > 0)
                        ++count ;
                trangThai.put("DELAYED", count);
                tong+=count;


                trangThai.put("TONG" , tong);

                quyvatrangthai.put("Quy:" +quy , trangThai);
            }
            namthangvatrangthai.put("Year:" +nam ,  quyvatrangthai);
            result.add(namthangvatrangthai);
        }

        if (!result.isEmpty()) {
            response.setMessage("Thông kế thành công");
            response.setData(result); // Trả về dữ liệu của khách hàng đã lưu
            response.setStatusCode(201); // Created
            return new ResponseEntity<>(response, HttpStatus.CREATED);
        } else {
            // Xử lý lỗi khi lưu không thành công
            response.setMessage("Khong co ket qua");
            response.setData(null);
            response.setStatusCode(500); // Internal Server Error
            return new ResponseEntity<>(response, HttpStatus.INTERNAL_SERVER_ERROR);
        }
    }

    @GetMapping("/thongkeTuyenBayByYear")
    public ResponseEntity<ResponseData> thongketuyenbaybyyear() {

        int namBatDau = 2017;
        int namKetThuc = LocalDate.now().getYear();

        // Lấy danh sách tất cả các tuyến bay
        Iterable<TuyenBayDTO> listTuyenBay = tuyenBayService.getAllTuyenBay();
        List<Map<String, Object>> result = new ArrayList<>();

        // Khởi tạo cấu trúc dữ liệu cho mỗi năm và tuyến bay
        for (int nam = namBatDau; nam <= namKetThuc; nam++) {
            Map<String, Map<String, Object>> idandinfo = new LinkedHashMap<>();
            for (TuyenBayDTO itemTuyenBay : listTuyenBay) {
                Map<String, Object> everyTuyenBay = new LinkedHashMap<>();
                everyTuyenBay.put("SanBayBatDau", sanBayService.getSanBayById(itemTuyenBay.getIdSanBayBatDau()).get().getTenSanBay());
                everyTuyenBay.put("SanBayKetThuc", sanBayService.getSanBayById(itemTuyenBay.getIdSanBayKetThuc()).get().getTenSanBay());

                // Khởi tạo bộ đếm trạng thái chuyến bay cho từng tuyến bay
                Map<String, Integer> everyTuyenBayStatus = new LinkedHashMap<>();
                everyTuyenBayStatus.put("COMPLETED", 0);
                everyTuyenBayStatus.put("CANCELED", 0);
                everyTuyenBay.put("SoTuyenBay", everyTuyenBayStatus);

                // Thêm thông tin vào danh sách tuyến bay theo năm
                idandinfo.put("Id tuyen bay: " +  itemTuyenBay.getIdTuyenBay(), everyTuyenBay);
            }
            Map<String, Object> item = new LinkedHashMap<>();
            item.put( "Year:" + nam, idandinfo);
            result.add(item);
        }

        // Duyệt qua từng năm và đếm số lượng chuyến bay hoàn thành và hủy theo từng tuyến bay
        for (int nam = namBatDau; nam <= namKetThuc; nam++) {
            Iterable<ChuyenBayDTO> listChuyenBay = cbservice.getChuyenBayByYear(nam);
            for (ChuyenBayDTO itemChuyenBay : listChuyenBay) {
                int idTuyenBay = itemChuyenBay.getTuyenBay().getIdTuyenBay();

                // Tìm thông tin tuyến bay trong kết quả và cập nhật bộ đếm
                for (Map<String, Object> yearItem : result) {
                    if (yearItem.containsKey("Year:" + nam)) {
                        Map<String, Map<String, Object>> idandinfo = (Map<String, Map<String, Object>>) yearItem.get("Year:" + nam);
                        Map<String, Object> everyTuyenBay = idandinfo.get("Id tuyen bay: " + idTuyenBay);

                        if (everyTuyenBay != null) {
                            Map<String, Integer> everyTuyenBayStatus = (Map<String, Integer>) everyTuyenBay.get("SoTuyenBay");

                            // Tăng giá trị tương ứng dựa trên trạng thái chuyến bay
                            if (itemChuyenBay.getTrangThai() == ChuyenBayEnum.COMPLETED) {
                                everyTuyenBayStatus.put("COMPLETED", everyTuyenBayStatus.get("COMPLETED") + 1);
                            } else if (itemChuyenBay.getTrangThai() == ChuyenBayEnum.CANCELED) {
                                everyTuyenBayStatus.put("CANCELED", everyTuyenBayStatus.get("CANCELED") + 1);
                            }
                        }
                    }
                }
            }
        }

        // Trả về kết quả
        if (!result.isEmpty()) {
            response.setMessage("Thống kê thành công");
            response.setData(result);
            response.setStatusCode(201); // Created
            return new ResponseEntity<>(response, HttpStatus.CREATED);
        } else {
            response.setMessage("Không có kết quả");
            response.setData(null);
            response.setStatusCode(500); // Internal Server Error
            return new ResponseEntity<>(response, HttpStatus.INTERNAL_SERVER_ERROR);
        }
    }

    @GetMapping("/thongkeTuyenBayByYearAndQuy")
    public ResponseEntity<ResponseData> thongketuyenbaybyyearandquy() {

        int namBatDau = 2017;
        int namKetThuc = 2024;

        // Lấy danh sách tất cả các tuyến bay
        Iterable<TuyenBayDTO> listTuyenBay = tuyenBayService.getAllTuyenBay();
        List<Map<String,Object>> result = new ArrayList<>();

        // Khởi tạo cấu trúc dữ liệu cho mỗi năm, tháng và tuyến bay
        for (int nam = namBatDau; nam <= namKetThuc; nam++) {
            Map<String, Object> yearData = new LinkedHashMap<>();

            for (int thang = 1; thang <= 4; thang++) {
                Map<String, Map<String, Object>> monthData = new LinkedHashMap<>();

                for (TuyenBayDTO itemTuyenBay : listTuyenBay) {
                    Map<String, Object> everyTuyenBay = new LinkedHashMap<>();
                    everyTuyenBay.put("SanBayBatDau", sanBayService.getSanBayById(itemTuyenBay.getIdSanBayBatDau()).get().getTenSanBay());
                    everyTuyenBay.put("SanBayKetThuc", sanBayService.getSanBayById(itemTuyenBay.getIdSanBayKetThuc()).get().getTenSanBay());

                    // Khởi tạo bộ đếm trạng thái chuyến bay cho từng tuyến bay
                    Map<String, Integer> everyTuyenBayStatus = new LinkedHashMap<>();
                    everyTuyenBayStatus.put("COMPLETED", 0);
                    everyTuyenBayStatus.put("CANCELED", 0);
                    everyTuyenBay.put("SoTuyenBay", everyTuyenBayStatus);

                    // Thêm thông tin vào danh sách tuyến bay theo tháng
                    monthData.put("Id tuyen bay: " + itemTuyenBay.getIdTuyenBay(), everyTuyenBay);
                }

                // Thêm thông tin tuyến bay vào tháng
                yearData.put("Quy:" + thang, monthData);
            }

            // Thêm dữ liệu tháng vào năm
            Map<String, Object> item = new LinkedHashMap<>();
            item.put("Year:"+ nam, yearData);
            result.add(item);
        }

        // Duyệt qua từng năm và tháng để đếm số lượng chuyến bay hoàn thành và hủy theo từng tuyến bay
        for (int nam = namBatDau; nam <= namKetThuc; nam++) {
            for (int quy = 1; quy <= 4; quy++) {

                Iterable<ChuyenBayDTO> listChuyenBay = cbservice.filterChuyenBayByQuyAndNam(nam, quy);

                for (ChuyenBayDTO itemChuyenBay : listChuyenBay) {
                    int idTuyenBay = itemChuyenBay.getTuyenBay().getIdTuyenBay();

                    // Tìm thông tin tuyến bay trong kết quả và cập nhật bộ đếm
                    for (Map<String, Object> yearItem : result) {
                        if (yearItem.containsKey("Year:"+nam)) {
                            Map<String, Object> yearData = (Map<String, Object>) yearItem.get("Year:" + nam);

                            if (yearData.containsKey("Quy:"+quy)) {
                                Map<String, Object> monthData = (Map<String, Object>) yearData.get("Quy:" + quy);
                                Map<String, Object> everyTuyenBay = (Map<String, Object>) monthData.get("Id tuyen bay: " + idTuyenBay);

                                if (everyTuyenBay != null) {
                                    Map<String, Integer> everyTuyenBayStatus = (Map<String, Integer>) everyTuyenBay.get("SoTuyenBay");

                                    // Tăng giá trị tương ứng dựa trên trạng thái chuyến bay
                                    if (itemChuyenBay.getTrangThai() == ChuyenBayEnum.COMPLETED) {
                                        everyTuyenBayStatus.put("COMPLETED", everyTuyenBayStatus.get("COMPLETED") + 1);
                                    } else if (itemChuyenBay.getTrangThai() == ChuyenBayEnum.CANCELED) {
                                        everyTuyenBayStatus.put("CANCELED", everyTuyenBayStatus.get("CANCELED") + 1);
                                    }
                                }
                            }
                        }
                    }
                }
            }
        }

        // Trả về kết quả
        if (!result.isEmpty()) {
            response.setMessage("Thống kê thành công");
            response.setData(result);
            response.setStatusCode(201); // Created
            return new ResponseEntity<>(response, HttpStatus.CREATED);
        } else {
            response.setMessage("Không có kết quả");
            response.setData(null);
            response.setStatusCode(500); // Internal Server Error
            return new ResponseEntity<>(response, HttpStatus.INTERNAL_SERVER_ERROR);
        }
    }

    @GetMapping("/thongkeTuyenBayByYearAndMonth")
    public ResponseEntity<ResponseData> thongketuyenbaybyyearandmonth() {

        int namBatDau = 2017;
        int namKetThuc = 2024;

        // Lấy danh sách tất cả các tuyến bay
        Iterable<TuyenBayDTO> listTuyenBay = tuyenBayService.getAllTuyenBay();
        List<Map<String,Object>> result = new ArrayList<>();

        // Khởi tạo cấu trúc dữ liệu cho mỗi năm, tháng và tuyến bay
        for (int nam = namBatDau; nam <= namKetThuc; nam++) {
            Map<String, Object> yearData = new LinkedHashMap<>();

            for (int thang = 1; thang <= 12; thang++) {
                Map<String, Map<String, Object>> monthData = new LinkedHashMap<>();

                for (TuyenBayDTO itemTuyenBay : listTuyenBay) {
                    Map<String, Object> everyTuyenBay = new LinkedHashMap<>();
                    everyTuyenBay.put("SanBayBatDau", sanBayService.getSanBayById(itemTuyenBay.getIdSanBayBatDau()).get().getTenSanBay());
                    everyTuyenBay.put("SanBayKetThuc", sanBayService.getSanBayById(itemTuyenBay.getIdSanBayKetThuc()).get().getTenSanBay());

                    // Khởi tạo bộ đếm trạng thái chuyến bay cho từng tuyến bay
                    Map<String, Integer> everyTuyenBayStatus = new LinkedHashMap<>();
                    everyTuyenBayStatus.put("COMPLETED", 0);
                    everyTuyenBayStatus.put("CANCELED", 0);
                    everyTuyenBay.put("SoTuyenBay", everyTuyenBayStatus);

                    // Thêm thông tin vào danh sách tuyến bay theo tháng
                    monthData.put("Id tuyen bay: " + itemTuyenBay.getIdTuyenBay(), everyTuyenBay);
                }

                // Thêm thông tin tuyến bay vào tháng
                yearData.put("Month:" +  thang, monthData);
            }

            // Thêm dữ liệu tháng vào năm
            Map<String, Object> item = new LinkedHashMap<>();
            item.put("Year:" + nam, yearData);
            result.add(item);
        }

        // Duyệt qua từng năm và tháng để đếm số lượng chuyến bay hoàn thành và hủy theo từng tuyến bay
        for (int nam = namBatDau; nam <= namKetThuc; nam++) {
            for (int thang = 1; thang <= 12; thang++) {

                // Tạo LocalDateTime cho đầu tháng và cuối tháng
                LocalDateTime startOfMonth = LocalDateTime.of(nam, thang, 1, 0, 0, 0);
                LocalDateTime endOfMonth = startOfMonth.with(TemporalAdjusters.lastDayOfMonth()).withHour(23).withMinute(59).withSecond(59);

                Iterable<ChuyenBayDTO> listChuyenBay = cbservice.getChuyenBayByYearAndMonth(nam,thang);

                for (ChuyenBayDTO itemChuyenBay : listChuyenBay) {
                    int idTuyenBay = itemChuyenBay.getTuyenBay().getIdTuyenBay();

                    // Tìm thông tin tuyến bay trong kết quả và cập nhật bộ đếm
                    for (Map<String, Object> yearItem : result) {
                        if (yearItem.containsKey("Year:" + nam)) {
                            Map<String, Object> yearData = (Map<String, Object>) yearItem.get("Year:" + nam);

                            if (yearData.containsKey(thang)) {
                                Map<String, Object> monthData = (Map<String, Object>) yearData.get("Month:" + thang);
                                Map<String, Object> everyTuyenBay = (Map<String, Object>) monthData.get("Id tuyen bay:" + idTuyenBay);

                                if (everyTuyenBay != null) {
                                    Map<String, Integer> everyTuyenBayStatus = (Map<String, Integer>) everyTuyenBay.get("SoTuyenBay");

                                    // Tăng giá trị tương ứng dựa trên trạng thái chuyến bay
                                    if (itemChuyenBay.getTrangThai() == ChuyenBayEnum.COMPLETED) {
                                        everyTuyenBayStatus.put("COMPLETED", everyTuyenBayStatus.get("COMPLETED") + 1);
                                    } else if (itemChuyenBay.getTrangThai() == ChuyenBayEnum.CANCELED) {
                                        everyTuyenBayStatus.put("CANCELED", everyTuyenBayStatus.get("CANCELED") + 1);
                                    }
                                }
                            }
                        }
                    }
                }
            }
        }

        // Trả về kết quả
        if (!result.isEmpty()) {
            response.setMessage("Thống kê thành công");
            response.setData(result);
            response.setStatusCode(201); // Created
            return new ResponseEntity<>(response, HttpStatus.CREATED);
        } else {
            response.setMessage("Không có kết quả");
            response.setData(null);
            response.setStatusCode(500); // Internal Server Error
            return new ResponseEntity<>(response, HttpStatus.INTERNAL_SERVER_ERROR);
        }
    }


    @GetMapping("/thongKeVeDaDungByYear")
    public ResponseEntity<ResponseData> thongkevedadungbyyear() {
        // Trả về kết quả
        int namBatDau = 2017;
        int namKetThuc = 2024;

        List<Map<String, Map<String, Map<String, Integer>>>> result = new ArrayList<>();

        for (int nam = namBatDau; nam <= namKetThuc; nam++) {
            Iterable<ChuyenBayDTO> listChuyenBay = cbservice.getChuyenBayByYear(nam);
            Map<String, Map<String, Integer>> idAndInfo = new LinkedHashMap<>();

            for (ChuyenBayDTO item : listChuyenBay) {
                if(!(item.getTrangThai().name().equals("COMPLETED") || item.getTrangThai().name().equals("IN_FLIGHT")))
                    continue ;
                Map<String, Integer> info = new LinkedHashMap<>();
                info.put("So ghe da dat", 0);
                info.put("So ghe tong", item.getSoGhe());
                idAndInfo.put("Chuyen Bay:" + item.getIdChuyenBay(), info);
            }
            Map<String, Map<String, Map<String, Integer>>> yearAndId = new LinkedHashMap<>();
            yearAndId.put("Year:" + nam, idAndInfo);
            result.add(yearAndId);
        }

        for (int nam = namBatDau; nam <= namKetThuc; nam++) {
            Iterable<ChuyenBayDTO> listChuyenBay = cbservice.getChuyenBayByYear(nam);
            for (ChuyenBayDTO itemChuyenBay : listChuyenBay) {
                Iterable<VeDTO> listVe = veService.getAllByIdChuyenBayByLam(itemChuyenBay.getIdChuyenBay());

                // Chỉ lấy các vé có trạng thái "booked"
                int soGheDaDat = 0;
                for (VeDTO ve : listVe) {
                    if ("used".equalsIgnoreCase(ve.getTrangThai().name())) {
                        soGheDaDat++;
                    }
                }

                // Cập nhật số ghế đã đặt cho chuyến bay trong kết quả
                for (Map<String, Map<String, Map<String, Integer>>> yearAndId : result) {
                    if (yearAndId.containsKey("Year:" + nam)) {
                        Map<String, Map<String, Integer>> idAndInfo = yearAndId.get("Year:" + nam);
                        if (idAndInfo.containsKey("Chuyen Bay:" + itemChuyenBay.getIdChuyenBay())) {
                            idAndInfo.get("Chuyen Bay:" + itemChuyenBay.getIdChuyenBay()).put("So ghe da dat", soGheDaDat);
                        }
                    }
                }
            }
        }

        if (!result.isEmpty()) {
            response.setMessage("Thống kê thành công");
            response.setData(result);
            response.setStatusCode(201); // Created
            return new ResponseEntity<>(response, HttpStatus.CREATED);
        } else {
            response.setMessage("Không có kết quả");
            response.setData(null);
            response.setStatusCode(500); // Internal Server Error
            return new ResponseEntity<>(response, HttpStatus.INTERNAL_SERVER_ERROR);
        }
    }

    @GetMapping("/thongKeVeDaDungByYearAndMonth")
    public ResponseEntity<ResponseData> thongkevedadungbyyearandmonth() {
        // Trả về kết quả
        int namBatDau = 2017;
        int namKetThuc = 2024;

        List<Map<String, Map<String, Map<String, Map<String, Integer>>>>> result = new ArrayList<>();

        // Khởi tạo cấu trúc dữ liệu cho các năm và tháng
        for (int nam = namBatDau; nam <= namKetThuc; nam++) {
            Map<String, Map<String, Map<String, Integer>>> monthData = new LinkedHashMap<>();
            for (int thang = 1; thang <= 12; thang++) {
                Iterable<ChuyenBayDTO> listChuyenBay = cbservice.getChuyenBayByYearAndMonth(nam, thang);
                Map<String, Map<String, Integer>> idAndInfo = new LinkedHashMap<>();
                for (ChuyenBayDTO item : listChuyenBay) {
                    if(!(item.getTrangThai().name().equals("COMPLETED") || item.getTrangThai().name().equals("IN_FLIGHT")))
                        continue ;
                    Map<String, Integer> info = new LinkedHashMap<>();
                    info.put("So ghe da dat", 0); // Khởi tạo với số ghế đã đặt là 0
                    info.put("So ghe tong", item.getSoGhe());
                    idAndInfo.put("Chuyen Bay:" + item.getIdChuyenBay(), info);
                }
                monthData.put("Month:" + thang, idAndInfo);
            }
            Map<String, Map<String, Map<String, Map<String, Integer>>>> yearData = new LinkedHashMap<>();
            yearData.put("Year:" + nam, monthData);
            result.add(yearData);
        }

        // Tính số ghế đã đặt cho từng chuyến bay trong mỗi tháng của từng năm
        for (int nam = namBatDau; nam <= namKetThuc; nam++) {
            for (int thang = 1; thang <= 12; thang++) {
                Iterable<ChuyenBayDTO> listChuyenBay = cbservice.getChuyenBayByYearAndMonth(nam, thang);
                for (ChuyenBayDTO itemChuyenBay : listChuyenBay) {
                    Iterable<VeDTO> listVe = veService.getAllByIdChuyenBayByLam(itemChuyenBay.getIdChuyenBay());

                    // Chỉ tính vé có trạng thái "booked"
                    int soGheDaDat = 0;
                    for (VeDTO ve : listVe) {
                        if ("used".equalsIgnoreCase(ve.getTrangThai().name())) {
                            soGheDaDat++;
                        }
                    }

                    // Cập nhật số ghế đã đặt trong cấu trúc dữ liệu cho kết quả
                    for (Map<String, Map<String, Map<String, Map<String, Integer>>>> yearAndMonth : result) {
                        if (yearAndMonth.containsKey("Year:" + nam)) {
                            Map<String, Map<String, Map<String, Integer>>> monthMap = yearAndMonth.get("Year:" + nam);
                            if (monthMap.containsKey("Month:" + thang)) {
                                Map<String, Map<String, Integer>> idAndInfo = monthMap.get("Month:" + thang);
                                if (idAndInfo.containsKey("Chuyen Bay:" + itemChuyenBay.getIdChuyenBay())) {
                                    idAndInfo.get("Chuyen Bay:" + itemChuyenBay.getIdChuyenBay())
                                            .put("So ghe da dat", soGheDaDat);
                                }
                            }
                        }
                    }
                }
            }
        }

        // Kiểm tra và trả về phản hồi
        if (!result.isEmpty()) {
            response.setMessage("Thống kê thành công");
            response.setData(result);
            response.setStatusCode(201); // Created
            return new ResponseEntity<>(response, HttpStatus.CREATED);
        } else {
            response.setMessage("Không có kết quả");
            response.setData(null);
            response.setStatusCode(500); // Internal Server Error
            return new ResponseEntity<>(response, HttpStatus.INTERNAL_SERVER_ERROR);
        }
    }


    @GetMapping("/thongKeVeDaDungByYearAndQuy")
    public ResponseEntity<ResponseData> thongkevedadungbyyearandquy() {
        // Trả về kết quả
        int namBatDau = 2017;
        int namKetThuc = 2024;

        List<Map<String, Map<String, Map<String, Map<String, Integer>>>>> result = new ArrayList<>();

        // Khởi tạo cấu trúc dữ liệu cho các năm và tháng
        for (int nam = namBatDau; nam <= namKetThuc; nam++) {
            Map<String, Map<String, Map<String, Integer>>> quyData = new LinkedHashMap<>();
            for (int quy = 1; quy <= 4; quy++) {
                Iterable<ChuyenBayDTO> listChuyenBay = cbservice.filterChuyenBayByQuyAndNam(nam,quy);
                Map<String, Map<String, Integer>> idAndInfo = new LinkedHashMap<>();
                for (ChuyenBayDTO item : listChuyenBay) {
                    if(!(item.getTrangThai().name().equals("COMPLETED") || item.getTrangThai().name().equals("IN_FLIGHT")))
                        continue ;
                    Map<String, Integer> info = new LinkedHashMap<>();
                    info.put("So ghe da dat", 0); // Khởi tạo với số ghế đã đặt là 0
                    info.put("So ghe tong", item.getSoGhe());
                    idAndInfo.put("Chuyen Bay:" + item.getIdChuyenBay(), info);
                }
                quyData.put("Quy:" + quy, idAndInfo);
            }
            Map<String, Map<String, Map<String, Map<String, Integer>>>> yearData = new LinkedHashMap<>();
            yearData.put("Year:" + nam, quyData);
            result.add(yearData);
        }

        // Tính số ghế đã đặt cho từng chuyến bay trong mỗi tháng của từng năm
        for (int nam = namBatDau; nam <= namKetThuc; nam++) {
            for (int quy = 1; quy <= 4; quy++) {
                Iterable<ChuyenBayDTO> listChuyenBay = cbservice.filterChuyenBayByQuyAndNam(nam, quy);
                for (ChuyenBayDTO itemChuyenBay : listChuyenBay) {
                    Iterable<VeDTO> listVe = veService.getAllByIdChuyenBayByLam(itemChuyenBay.getIdChuyenBay());

                    // Chỉ tính vé có trạng thái "booked"
                    int soGheDaDat = 0;
                    for (VeDTO ve : listVe) {
                        if ("used".equalsIgnoreCase(ve.getTrangThai().name())) {
                            soGheDaDat++;
                        }
                    }

                    // Cập nhật số ghế đã đặt trong cấu trúc dữ liệu cho kết quả
                    for (Map<String, Map<String, Map<String, Map<String, Integer>>>> yearAndMonth : result) {
                        if (yearAndMonth.containsKey("Year:" + nam)) {
                            Map<String, Map<String, Map<String, Integer>>> monthMap = yearAndMonth.get("Year:" + nam);
                            if (monthMap.containsKey("Quy:" + quy)) {
                                Map<String, Map<String, Integer>> idAndInfo = monthMap.get("Quy:" + quy);
                                if (idAndInfo.containsKey("Chuyen Bay:" + itemChuyenBay.getIdChuyenBay())) {
                                    idAndInfo.get("Chuyen Bay:" + itemChuyenBay.getIdChuyenBay())
                                            .put("So ghe da dat", soGheDaDat);
                                }
                            }
                        }
                    }
                }
            }
        }

        // Kiểm tra và trả về phản hồi
        if (!result.isEmpty()) {
            response.setMessage("Thống kê thành công");
            response.setData(result);
            response.setStatusCode(201); // Created
            return new ResponseEntity<>(response, HttpStatus.CREATED);
        } else {
            response.setMessage("Không có kết quả");
            response.setData(null);
            response.setStatusCode(500); // Internal Server Error
            return new ResponseEntity<>(response, HttpStatus.INTERNAL_SERVER_ERROR);
        }
    }

    @GetMapping("/getSoGheTrong")
    public ResponseEntity<ResponseData> getSoGheTrong(@RequestParam int idChuyenBay) {
        // Giả sử bạn có một service để lấy thông tin chuyến bay và hóa đơn
        int soGheTrong = 0;
        List<VeDTO> veDTOList = veService.getAllVeByIdChuyenBayNotPaging(idChuyenBay);
        System.out.println(veDTOList);
        for (VeDTO veDTO : veDTOList) {
            System.out.println(veDTO.getTrangThai());
            if (veDTO.getTrangThai() == VeEnum.EMPTY) {
                soGheTrong++;
            }
        }
        System.out.println(soGheTrong);

        ResponseData responseData = new ResponseData();
        responseData.setMessage("Thành công");
        responseData.setData(soGheTrong);

        return ResponseEntity.ok(responseData);
    }
}
