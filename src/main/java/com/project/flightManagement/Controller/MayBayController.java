package com.project.flightManagement.Controller;

import com.project.flightManagement.DTO.HangBayDTO.HangBayDTO;
import com.project.flightManagement.DTO.MayBayDTO.MayBayDTO;
import com.project.flightManagement.Mapper.HangBayMapper;
import com.project.flightManagement.Mapper.MayBayMapper;
import com.project.flightManagement.Model.HangBay;
import com.project.flightManagement.Model.KhachHang;
import com.project.flightManagement.Model.MayBay;
import com.project.flightManagement.Payload.ResponseData;
import com.project.flightManagement.Repository.HangBayRepository;
import com.project.flightManagement.Service.HangBayService;
import com.project.flightManagement.Service.MayBayService;
import jakarta.validation.Valid;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.*;

import java.util.*;

@RestController
@CrossOrigin(origins = "http://localhost:5175")
public class MayBayController {
    @Autowired
    private MayBayService mayBayService;
    @Autowired
    private HangBayService hangBayService;
    private ResponseData response = new ResponseData();

    @GetMapping("/getPlane/{id}")
    public ResponseEntity<ResponseData> getPlaneById(@PathVariable int id) {
        Optional<MayBayDTO> mbDTO = mayBayService.getMayBayById(id);
        if (mbDTO.isPresent()) {
            System.out.println(mbDTO.map(MayBayMapper::toEntity).get().getIdMayBay());
            response.setMessage("Get plane by ID success!!");
            response.setData(mbDTO);
            response.setStatusCode(200);
            return new ResponseEntity<>(response, HttpStatus.OK);
        } else {
            System.out.println("Not found");
            response.setMessage("Plane not found!!");
            response.setData(null);
            response.setStatusCode(404);
            return new ResponseEntity<>(response, HttpStatus.NOT_FOUND);
        }
    }
    @GetMapping("/getAllPlane")
    public ResponseEntity<ResponseData> getAllPlane() {
        Iterable<MayBayDTO> mbDTO = mayBayService.getAllMayBay();
        if(mbDTO.iterator().hasNext()) {
            response.setMessage("Get all plane success!!");
            response.setData(mbDTO);
            response.setStatusCode(200);
            return new ResponseEntity<>(response, HttpStatus.OK);
        }else {
            response.setMessage("Get all plane failed!!");
            response.setData(null);
            response.setStatusCode(404);
            return new ResponseEntity<>(response, HttpStatus.NOT_FOUND);
        }
    }
    @GetMapping("/findPlane/{keyword}")
    public ResponseEntity<ResponseData> findMayBayByTenMayBay(@PathVariable String keyword) {
        System.out.println("Searching for: " + keyword);
        Iterable<MayBayDTO> listMbDTO = mayBayService.findMayBayByTenMayBay(keyword);
        if(listMbDTO.iterator().hasNext()){
            System.out.println("Searching for 1: " + keyword);
            response.setMessage("get list plane success!!");
            response.setData(listMbDTO);
            response.setStatusCode(200);
            return new ResponseEntity<>(response, HttpStatus.OK);
        } else {
            System.out.println("Searching for 2: " + keyword);
            response.setMessage("get list plane unsuccess!!");
            response.setData(listMbDTO);
            response.setStatusCode(404);
            return new ResponseEntity<>(response, HttpStatus.NOT_FOUND);
        }
    }
    @GetMapping("/getAllPlaneSorted/{sortBy}")
    public ResponseEntity<ResponseData> getAllKhachHang(@RequestParam(defaultValue = "idMayBay") String sortBy){
        Iterable<MayBayDTO> listMbDTO = mayBayService.getAllMayBaySorted(sortBy);
        if(listMbDTO.iterator().hasNext()){
            response.setMessage("get list plane success!!");
            response.setData(listMbDTO);
            response.setStatusCode(200);
            return new ResponseEntity<>(response, HttpStatus.OK);
        } else {
            response.setMessage("get list plane unsuccess!!");
            response.setData(null);
            response.setStatusCode(204);
            return new ResponseEntity<>(HttpStatus.NO_CONTENT);
        }
    }
    @GetMapping("/getPlaneByAirLine/{idHangBay}")
    public ResponseEntity<ResponseData> getPlaneByAirLine(@PathVariable int idHangBay) {
//        response.setMessage("hello");
//        response.setStatusCode(200);
//        response.setData(null);
//        return new ResponseEntity<>(response, HttpStatus.NOT_FOUND);
        Optional<HangBayDTO> hangBayDTO = hangBayService.getHangBayById(idHangBay);
        if (hangBayDTO.isPresent()) {
            HangBay hangBay = hangBayDTO.map(HangBayMapper::toEntity).get();
            System.out.println(hangBay.getIdHangBay());
            List<MayBayDTO> listMbDTO = mayBayService.findMayBayByHangBay(hangBay);
            if(listMbDTO.iterator().hasNext()){
                response.setMessage("get list plane success!!");
                response.setData(listMbDTO);
                response.setStatusCode(200);
                return new ResponseEntity<>(response, HttpStatus.OK);
            } else {
                response.setMessage("get list plane unsuccess!!");
                response.setData(listMbDTO);
                response.setStatusCode(404);
                return new ResponseEntity<>(response, HttpStatus.NOT_FOUND);
            }

        } else {
            System.out.println("Not found airline");
            response.setMessage("AirLine not found!!");
            response.setData(null);
            response.setStatusCode(404);
            return new ResponseEntity<>(response, HttpStatus.NOT_FOUND);
        }
    }
    @PostMapping("/addPlane")
    public ResponseEntity<ResponseData> addPlane(@Valid @RequestBody MayBayDTO mbDTO, BindingResult bindingResult) {
        ResponseData response = new ResponseData();
        if(bindingResult.hasErrors()){
            Map<String, String> fieldErrors = new HashMap<>();
            bindingResult.getFieldErrors().forEach(error ->
                    fieldErrors.put(error.getField(), error.getDefaultMessage()));
            response.setStatusCode(400);
            response.setData(fieldErrors);
            response.setMessage("There are some fields invalid");
            return new ResponseEntity<>(response, HttpStatus.BAD_REQUEST);
        }
        if (mbDTO != null && (mbDTO.getSoHieu() != null || mbDTO.getIcaoMayBay() != null)) {
            // Kiểm tra sự tồn tại theo email
            if (mbDTO.getSoHieu() != null) {
                Optional<MayBayDTO> existingMBBySoHieu = mayBayService.getMayBayBySoHieu(mbDTO.getSoHieu());
                if (existingMBBySoHieu.isPresent()) {
                    response.setMessage("Plane with this flight number already exists!!");
                    response.setData(null);
                    response.setStatusCode(409); // Conflict
                    return new ResponseEntity<>(response, HttpStatus.CONFLICT);
                }
            }
            if (mbDTO.getIcaoMayBay() != null) {
                Optional<MayBayDTO> existingMBByIcao = mayBayService.getMayBayByIcaoMayBay(mbDTO.getIcaoMayBay());
                if (existingMBByIcao.isPresent()) {
                    response.setMessage("Plane with this ICAO already exists!!");
                    response.setData(null);
                    response.setStatusCode(409); // Conflict
                    return new ResponseEntity<>(response, HttpStatus.CONFLICT);
                }
            }

            // Nếu không có thông tin nào tồn tại, tiến hành lưu khách hàng mới
            Optional<MayBayDTO> savedMB = mayBayService.addNewMayBay(mbDTO);
            if (savedMB.isPresent()) {
                response.setMessage("Save plane successfully!!");
                response.setData(savedMB.get()); // Trả về dữ liệu của khách hàng đã lưu
                response.setStatusCode(201); // Created
                return new ResponseEntity<>(response, HttpStatus.CREATED);
            } else {
                // Xử lý lỗi khi lưu không thành công
                response.setMessage("Save plane unsuccessfully!!");
                response.setData(null);
                response.setStatusCode(500); // Internal Server Error
                return new ResponseEntity<>(response, HttpStatus.INTERNAL_SERVER_ERROR);
            }
        } else {
            // Xử lý trường hợp dữ liệu khách hàng không hợp lệ
            response.setMessage("Invalid plane data!!");
            response.setData(null);
            response.setStatusCode(400); // Bad Request
            return new ResponseEntity<>(response, HttpStatus.BAD_REQUEST);
        }
    }
    @PutMapping("/updatePlane")
    public ResponseEntity<ResponseData> updatePlane(@PathVariable("idMB") Integer idMB,@Valid @RequestBody MayBayDTO mbDTO, BindingResult bindingResult) {
        ResponseData response = new ResponseData();
        if(bindingResult.hasErrors()){
            Map<String, String> fieldErrors = new HashMap<>();
            bindingResult.getFieldErrors().forEach(error ->
                    fieldErrors.put(error.getField(), error.getDefaultMessage()));
            response.setStatusCode(400);
            response.setData(fieldErrors);
            response.setMessage("There are some fields invalid");
            return new ResponseEntity<>(response, HttpStatus.BAD_REQUEST);
        }
        // Kiểm tra xem đối tượng khDTO có khác null và ID có hợp lệ không
        if (mbDTO != null && idMB != null) {
            // Kiểm tra xem khách hàng có tồn tại hay không
            Optional<MayBayDTO> existingMB = mayBayService.getMayBayById(idMB);
            if (existingMB.isPresent()) {
                // Cập nhật thông tin khách hàng
                mbDTO.setIdMayBay(idMB); // Đảm bảo rằng ID của khách hàng được thiết lập
                // Kiểm tra xem email, số điện thoại, CCCD đã tồn tại hay chưa (nếu có sự thay đổi)
                if (mbDTO.getSoHieu() != null && !Objects.equals(existingMB.get().getSoHieu(), mbDTO.getSoHieu()) ) {
                    Optional<MayBayDTO> existingMBBySoHieu = mayBayService.getMayBayBySoHieu(mbDTO.getSoHieu());
                    if (existingMBBySoHieu.isPresent()) {
                        response.setMessage("Plane with this flight number already exists!!");
                        response.setData(null);
                        response.setStatusCode(409); // Conflict
                        return new ResponseEntity<>(response, HttpStatus.CONFLICT);
                    }
                }
                if (mbDTO.getIcaoMayBay() != null && !Objects.equals(existingMB.get().getIcaoMayBay(), mbDTO.getIcaoMayBay())) {
                    Optional<MayBayDTO> existingMBByIcao = mayBayService.getMayBayByIcaoMayBay(mbDTO.getIcaoMayBay());
                    if (existingMBByIcao.isPresent()) {
                        response.setMessage("Plane with this ICAO already exists!!");
                        response.setData(null);
                        response.setStatusCode(409); // Conflict
                        return new ResponseEntity<>(response, HttpStatus.CONFLICT);
                    }
                }

                // Cập nhật khách hàng
                Optional<MayBayDTO> updatedMB = mayBayService.updateMayBay(mbDTO);
                if (updatedMB.isPresent()) {
                    response.setMessage("Update plane successfully!!");
                    response.setData(updatedMB.get()); // Trả về thông tin khách hàng đã cập nhật
                    response.setStatusCode(200); // OK
                    return new ResponseEntity<>(response, HttpStatus.OK);
                } else {
                    // Xử lý lỗi khi cập nhật không thành công
                    response.setMessage("Update plane unsuccessfully!!");
                    response.setData(null);
                    response.setStatusCode(500); // Internal Server Error
                    return new ResponseEntity<>(response, HttpStatus.INTERNAL_SERVER_ERROR);
                }
            } else {
                // Khách hàng không tồn tại
                response.setMessage("Plane not found!!");
                response.setData(null);
                response.setStatusCode(404); // Not Found
                return new ResponseEntity<>(response, HttpStatus.NOT_FOUND);
            }
        } else {
            // Dữ liệu không hợp lệ
            response.setMessage("Invalid plane data!!");
            response.setData(null);
            response.setStatusCode(400); // Bad Request
            return new ResponseEntity<>(response, HttpStatus.BAD_REQUEST);
        }
    }
}
