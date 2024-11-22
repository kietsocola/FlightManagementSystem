package com.project.flightManagement.Controller;

import com.nimbusds.jose.util.Pair;
import com.project.flightManagement.DTO.ChoNgoiDTO.ChoNgoiDTO;
import com.project.flightManagement.DTO.HangBayDTO.HangBayDTO;
import com.project.flightManagement.DTO.MayBayDTO.MayBayDTO;
import com.project.flightManagement.DTO.QuocGiaDTO.QuocGiaDTO;
import com.project.flightManagement.DTO.SanBayDTO.SanBayDTO;
import com.project.flightManagement.Enum.ActiveEnum;
import com.project.flightManagement.Mapper.HangBayMapper;
import com.project.flightManagement.Mapper.HangVeMapper;
import com.project.flightManagement.Mapper.MayBayMapper;
import com.project.flightManagement.Mapper.SanBayMapper;
import com.project.flightManagement.Model.*;
import com.project.flightManagement.Payload.ResponseData;
import com.project.flightManagement.Repository.HangBayRepository;
import com.project.flightManagement.Service.*;
import jakarta.validation.Valid;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.*;

import java.util.*;

@RestController
@CrossOrigin(origins = "http://localhost:5173")
@RequestMapping("/admin/maybay")
public class MayBayController {
    @Autowired
    private MayBayService mayBayService;
    @Autowired
    private HangBayService hangBayService;
    @Autowired
    private HangVeService hangVeService;
    @Autowired
    private ChoNgoiService choNgoiService;
    @Autowired
    private SanBayService sanBayService;
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
        Iterable<MayBayDTO> mbDTOList = mayBayService.getAllMayBay();
        if(mbDTOList.iterator().hasNext()) {
            response.setData(mbDTOList);
            response.setStatusCode(200);
            response.setMessage("Get all plane success!!");
            return new ResponseEntity<>(response, HttpStatus.OK);
        } else {
            response.setStatusCode(404);
            response.setMessage("Get all plane failed!!");
            response.setData(null);
            return new ResponseEntity<>(response, HttpStatus.NOT_FOUND);
        }
    }
    @GetMapping("/findPlane")
    public ResponseEntity<ResponseData> findMayBayByTenMayBay(@RequestParam String keyword) {
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
    @GetMapping("/getAllPlaneSorted")
    public ResponseEntity<ResponseData> getAllKhachHangSorted(@RequestParam(defaultValue = "idKhachHang") String sortBy,
                                                              @RequestParam(defaultValue = "asc") String order){
        Iterable<MayBayDTO> listMbDTO = mayBayService.getAllMayBaySorted(sortBy, order);
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
    @GetMapping("/getPlaneByAirline/{idHangBay}")
    public ResponseEntity<ResponseData> getPlaneByAirLine(@PathVariable int idHangBay) {
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
    @GetMapping("/getPlaneByAirport/{idSanBay}")
    public ResponseEntity<ResponseData> getMayBayBySanBay(@PathVariable int idSanBay) {
        Optional<SanBayDTO> sanBayDTO = sanBayService.getSanBayById(idSanBay);
        if (sanBayDTO.isPresent()) {
            SanBay sanBay = sanBayDTO.map(SanBayMapper::toEntity).get();
            System.out.println(sanBay.getIdSanBay());
            List<MayBayDTO> listMbDTO = mayBayService.findMayBayBySanBay(sanBay);
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
        if(bindingResult.hasErrors()){
            Map<String, String> fieldErrors = new HashMap<>();
            bindingResult.getFieldErrors().forEach(error ->
                    fieldErrors.put(error.getField(), error.getDefaultMessage()));
            response.setStatusCode(400);
            response.setData(fieldErrors);
            response.setMessage("There are some fields invalid");
            return new ResponseEntity<>(response, HttpStatus.BAD_REQUEST);
        }
        if (mbDTO != null && mbDTO.getSoHieu()!= null) {
            ResponseEntity<ResponseData> rs = checkExistSoHieu(mbDTO.getSoHieu());
            if(rs!=null){
                return rs;
            }
            Optional<MayBayDTO> savedMB = mayBayService.addNewMayBay(mbDTO);
            try {
                // id = 2 : ghế phổ thông
                for(int iE = 1 ; iE <= savedMB.get().getSoHangGheThuong() ; iE++){
                    for (int jE = 0 ; jE < savedMB.get().getSoCotGheThuong().length() ; jE++){
                        ChoNgoiDTO cnDTO = new ChoNgoiDTO(HangVeMapper.toEntity(hangVeService.getHangVeById(2).get()),
                                MayBayMapper.toEntity(savedMB.get()),
                                iE,
                                savedMB.get().getSoCotGheThuong().charAt(jE),
                                ActiveEnum.ACTIVE
                        );
                        choNgoiService.addNewChoNgoi(cnDTO);
                    }
                }
                // id = 1 : ghế thương gia
                for(int iE = 1 ; iE <= savedMB.get().getSoHangGheVip() ; iE++){
                    for (int jE = 0 ; jE < savedMB.get().getSoCotGheVip().length() ; jE++){
                        ChoNgoiDTO cnDTO = new ChoNgoiDTO(HangVeMapper.toEntity(hangVeService.getHangVeById(1).get()),
                                MayBayMapper.toEntity(savedMB.get()),
                                iE,
                                savedMB.get().getSoCotGheVip().charAt(jE),
                                ActiveEnum.ACTIVE
                        );
                        choNgoiService.addNewChoNgoi(cnDTO);
                    }
                }
            } catch (Exception e) {
                System.out.println("Cant add seat when add plane");
                response.setStatusCode(400);
                response.setMessage("Cant add seat when add plane");
                response.setData(null);
                return new ResponseEntity<>(response, HttpStatus.BAD_REQUEST);
            }
            if (savedMB.isPresent()) {
                response.setMessage("Save plane successfully!!");
                response.setData(savedMB.get());
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
            response.setMessage("Invalid plane data!!");
            response.setData(null);
            response.setStatusCode(400); // Bad Request
            return new ResponseEntity<>(response, HttpStatus.BAD_REQUEST);
        }
    }
    @PutMapping("/updatePlane/{idMB}")
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
        if (mbDTO != null && idMB != null) {
            Optional<MayBayDTO> existingMB = mayBayService.getMayBayById(idMB);
            if (existingMB.isPresent()) {
                mbDTO.setIdMayBay(idMB);
                if (mbDTO.getSoHieu() != null && !Objects.equals(existingMB.get().getSoHieu(), mbDTO.getSoHieu()) ) {
                    ResponseEntity<ResponseData> rs = checkExistSoHieu(mbDTO.getSoHieu());
                    if(rs!=null){
                        return rs;
                    }
                }

                Optional<MayBayDTO> updatedMB = mayBayService.updateMayBay(mbDTO);
                try {
                    for(int iE  = 1 ; iE <= updatedMB.get().getSoHangGheThuong() ; iE++){
                        for (int jE = 0 ; jE < updatedMB.get().getSoCotGheThuong().length() ; jE++){
                            ChoNgoiDTO cnDTO = new ChoNgoiDTO(HangVeMapper.toEntity(hangVeService.getHangVeById(2).get()),
                                    MayBayMapper.toEntity(updatedMB.get()),
                                    iE,
                                    updatedMB.get().getSoCotGheThuong().charAt(jE),
                                    ActiveEnum.ACTIVE
                            );
                        }
                    }
                    for(int iE = 1 ; iE <= updatedMB.get().getSoHangGheVip() ; iE++){
                        for (int jE = 0 ; jE < updatedMB.get().getSoCotGheVip().length() ; jE++){
                            ChoNgoiDTO cnDTO = new ChoNgoiDTO(HangVeMapper.toEntity(hangVeService.getHangVeById(1).get()),
                                    MayBayMapper.toEntity(updatedMB.get()),
                                    iE,
                                    updatedMB.get().getSoCotGheThuong().charAt(jE),
                                    ActiveEnum.ACTIVE
                            );
                            choNgoiService.addNewChoNgoi(cnDTO);
                        }
                    }
                } catch (Exception e) {
                    System.out.println("Cant add seat when add plane");
                    response.setStatusCode(400);
                    response.setMessage("Cant add seat when add plane");
                    response.setData(null);
                    return new ResponseEntity<>(response, HttpStatus.BAD_REQUEST);
                }

                if (updatedMB.isPresent()) {
                    response.setMessage("Update plane successfully!!");
                    response.setData(updatedMB.get());
                    response.setStatusCode(200); // OK
                    return new ResponseEntity<>(response, HttpStatus.OK);
                } else {
                    response.setMessage("Update plane unsuccessfully!!");
                    response.setData(null);
                    response.setStatusCode(500); // Internal Server Error
                    return new ResponseEntity<>(response, HttpStatus.INTERNAL_SERVER_ERROR);
                }
            } else {
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

    public ResponseEntity<ResponseData> checkExistSoHieu(String soHieu){
        Optional<MayBayDTO> existingMBBySoHieu = mayBayService.getMayBayBySoHieu(soHieu);
        if (existingMBBySoHieu.isPresent()) {
            response.setMessage("Plane with this flight number already exists!!");
            Map<String, String> errorMessage = new HashMap<>();
            errorMessage.put("So hieu", "Customer with this flight number already exists!!");
            response.setData(errorMessage);
            response.setStatusCode(409); // Conflict
            return new ResponseEntity<>(response, HttpStatus.CONFLICT);
        }
        return null;
    }
    @PutMapping("/blockPlane/{idMayBay}")
    public ResponseEntity<ResponseData> blockMayBay(@PathVariable int idMayBay){
        Optional<MayBayDTO> existingMB = mayBayService.getMayBayById(idMayBay);
        if(existingMB.isPresent()){
            if(existingMB.get().getTrangThaiActive() == ActiveEnum.ACTIVE){
                Optional<MayBayDTO> blockMB = mayBayService.blockMayBay(existingMB.get().getIdMayBay());

                if(blockMB.isPresent()){
                    Iterable<ChoNgoiDTO> listCN = choNgoiService.getChoNgoiByMayBay(blockMB.get());
                    for(ChoNgoiDTO cn : listCN) {
                        choNgoiService.blockChoNgoi(cn.getIdChoNgoi());
                    }
                    response.setMessage("Block plane successfully!!");
                    response.setData(blockMB.get());
                    response.setStatusCode(200); // OK
                    return new ResponseEntity<>(response, HttpStatus.OK);
                } else {
                    // Xử lý lỗi khi cập nhật không thành công
                    response.setMessage("Block plane unsuccessfully!!");
                    response.setData(null);
                    response.setStatusCode(500); // Internal Server Error
                    return new ResponseEntity<>(response, HttpStatus.INTERNAL_SERVER_ERROR);
                }
            } else {
                Optional<MayBayDTO> unblockMB = mayBayService.unblockMayBay(existingMB.get().getIdMayBay());

                if(unblockMB.isPresent()){
                    Iterable<ChoNgoiDTO> listCN = choNgoiService.getChoNgoiByMayBay(unblockMB.get());
                    for(ChoNgoiDTO cn : listCN) {
                        choNgoiService.unblockChoNgoi(cn.getIdChoNgoi());
                    }
                    response.setMessage("Unblock plane successfully!!");
                    response.setData(unblockMB.get());
                    response.setStatusCode(200); // OK
                    return new ResponseEntity<>(response, HttpStatus.OK);
                } else {
                    // Xử lý lỗi khi cập nhật không thành công
                    response.setMessage("Unblock plane unsuccessfully!!");
                    response.setData(null);
                    response.setStatusCode(500); // Internal Server Error
                    return new ResponseEntity<>(response, HttpStatus.INTERNAL_SERVER_ERROR);
                }
            }
        } else {
            response.setMessage("Plane not found!!");
            response.setData(null);
            response.setStatusCode(404); // Not Found
            return new ResponseEntity<>(response, HttpStatus.NOT_FOUND);
        }
    }
    @GetMapping("/getHoursOfPlane/{idMayBay}")
    public ResponseEntity<ResponseData> getSoGioByMayBay(@PathVariable int idMayBay) {
        Optional<MayBayDTO> mayBayDTO = mayBayService.getMayBayById(idMayBay);
        if (mayBayDTO.isPresent()) {
            String hours = mayBayService.getHoursOfPlane(mayBayDTO.get().getIdMayBay());
            if (hours != "00:00:00") {
                response.setMessage("Get time of plane success!!");
                response.setData(hours);
                response.setStatusCode(200);
                return new ResponseEntity<>(response, HttpStatus.OK);
            } else {
                response.setMessage("Error to get hours of plane!!");
                response.setData("00:00:00");
                response.setStatusCode(500);
                return new ResponseEntity<>(response, HttpStatus.BAD_REQUEST);
            }
        } else {
            response.setStatusCode(404);
            response.setData("00:00:00");
            response.setMessage("Cant found plane!!");
            return new ResponseEntity<>(response, HttpStatus.NOT_FOUND);
        }
    }
    @GetMapping("/calculateHourOfPlane")
    public ResponseEntity<ResponseData> calculateHourOfPlane(
            @RequestParam(required = false) String period,
            @RequestParam(required = false) Integer month,
            @RequestParam(required = false) Integer quarter,
            @RequestParam(required = true) Integer year) {
        List<Pair<String, Double>> list = mayBayService.calculateHoursOfPlane(period, month, quarter, year);
        ResponseData response = new ResponseData();

        if (list.isEmpty()) {
            response.setMessage("Can not get list plane has hour flight!!");
            response.setStatusCode(500);
            response.setData(null);
            return new ResponseEntity<>(response, HttpStatus.BAD_REQUEST);
        } else {
            response.setData(list);
            response.setStatusCode(200);
            response.setMessage("Get list plane has hour flight success!!");
            return new ResponseEntity<>(response, HttpStatus.OK);
        }
    }

    @GetMapping("/top5PlaneHasHighestFlightHours")
    public ResponseEntity<ResponseData> getTop5PlaneHasHighestFlightHours (@RequestParam int month, @RequestParam int year) {
        List<Pair<String, Double>> list = mayBayService.getTop5PlaneHasHighestFlightHours(month, year);
        if (list.isEmpty()) {
            response.setMessage("Can not get top 5 plane has highest hour flight!!");
            response.setStatusCode(500);
            response.setData(null);
            return new ResponseEntity<>(response, HttpStatus.BAD_REQUEST);
        } else {
            response.setData(list);
            response.setStatusCode(200);
            response.setMessage("Get top 5 plane has highest hour flight success!!");
            return new ResponseEntity<>(response, HttpStatus.OK);
        }
    }
}
