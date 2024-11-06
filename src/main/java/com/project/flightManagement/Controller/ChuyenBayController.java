package com.project.flightManagement.Controller;

import com.project.flightManagement.DTO.ChuyenBayDTO.ChuyenBayDTO;
import com.project.flightManagement.Enum.ChuyenBayEnum;
import com.project.flightManagement.Model.ChuyenBay;
import com.project.flightManagement.Payload.ResponseData;
import com.project.flightManagement.Service.ChuyenBayService;
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
import java.time.LocalDateTime;
import java.util.*;
import java.util.HashMap;
import java.util.Map;
import java.util.Optional;

@RestController
@RequestMapping("/admin/chuyenbay")
@CrossOrigin(origins = "http://localhost:5173")

public class ChuyenBayController {

    @Autowired
    private ChuyenBayService cbservice;
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

        Map<String, Object> flightMap = new HashMap<>();

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

        for(ChuyenBayDTO cb : listCB){
            ChuyenBayEnum status = cb.getTrangThai() ;
            if (cb.getIdChuyenBay() != cbDTO.getIdChuyenBay()
                    && cb.getTuyenBay().getIdTuyenBay() == cbDTO.getTuyenBay().getIdTuyenBay()
                    && !isDifferenceGreaterThanTwoHour(cb.getThoiGianBatDauDuTinh() , cbDTO.getThoiGianBatDauThucTe())
                    && scheduled.name().equals(status.name())){
                System.out.println("id chuyen bay : " +  cb.getIdChuyenBay());
                System.out.println("khoang thoi gian >  2 gio : " + !isDifferenceGreaterThanTwoHour(cb.getThoiGianBatDauDuTinh() , cbDTO.getThoiGianBatDauThucTe()));
                response.setMessage("Thời gian xảy ra gần với chuyến bay khác");
                response.setData(null);
                response.setStatusCode(409);
                return new ResponseEntity<>(response, HttpStatus.CONFLICT);
            }
        }

        Optional<ChuyenBayDTO> saveCB = cbservice.addChuyenBay(cbDTO);
        if (saveCB.isPresent()) {
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

        for(ChuyenBayDTO cb : listCB){
            ChuyenBayEnum status = cb.getTrangThai() ;
            if (cb.getIdChuyenBay() != cbDTO.getIdChuyenBay()
                    && cb.getTuyenBay().getIdTuyenBay() == cbDTO.getTuyenBay().getIdTuyenBay()
                    && !isDifferenceGreaterThanTwoHour(cb.getThoiGianBatDauDuTinh() , cbDTO.getThoiGianBatDauThucTe())
                    && scheduled.name().equals(status.name())){
                System.out.println("id chuyen bay : " +  cb.getIdChuyenBay());
                System.out.println("khoang thoi gian >  2 gio : " + !isDifferenceGreaterThanTwoHour(cb.getThoiGianBatDauDuTinh() , cbDTO.getThoiGianBatDauThucTe()));
                response.setMessage("Tuyến bay và thời gian này gần với chuyến bay : " + cb.getIdChuyenBay());
                response.setData(null);
                response.setStatusCode(409);
                return new ResponseEntity<>(response, HttpStatus.CONFLICT);
            }
        }

        Optional<ChuyenBayDTO> cbOld = cbservice.getChuyenBayById(idChuyenBay);


        ChuyenBayEnum cancled = ChuyenBayEnum.CANCELED;
        ChuyenBayEnum completed = ChuyenBayEnum.COMPLETED;
        ChuyenBayEnum in_flight = ChuyenBayEnum.IN_FLIGHT;
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
            response.setMessage("Save Chuyen Bay successfully!!");
            response.setData(saveCb.get()); // Trả về dữ liệu của khách hàng đã lưu
            response.setStatusCode(201); // Created
            return new ResponseEntity<>(response, HttpStatus.CREATED);
        } else {
            // Xử lý lỗi khi lưu không thành công
            response.setMessage("Save Chuyen Bay unsuccessfully!!");
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
}
