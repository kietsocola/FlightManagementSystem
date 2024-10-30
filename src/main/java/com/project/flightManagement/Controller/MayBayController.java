package com.project.flightManagement.Controller;

import com.project.flightManagement.DTO.ChoNgoiDTO.ChoNgoiDTO;
import com.project.flightManagement.DTO.HangBayDTO.HangBayDTO;
import com.project.flightManagement.DTO.MayBayDTO.MayBayDTO;
import com.project.flightManagement.DTO.QuocGiaDTO.QuocGiaDTO;
import com.project.flightManagement.Enum.ActiveEnum;
import com.project.flightManagement.Mapper.HangBayMapper;
import com.project.flightManagement.Mapper.HangVeMapper;
import com.project.flightManagement.Mapper.MayBayMapper;
import com.project.flightManagement.Model.*;
import com.project.flightManagement.Payload.ResponseData;
import com.project.flightManagement.Repository.HangBayRepository;
import com.project.flightManagement.Service.ChoNgoiService;
import com.project.flightManagement.Service.HangBayService;
import com.project.flightManagement.Service.HangVeService;
import com.project.flightManagement.Service.MayBayService;
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
    @PostMapping("/addPlane")
    public ResponseEntity<ResponseData> addPlane(@Valid @RequestBody MayBayDTO mbDTO, BindingResult bindingResult) {
        if (bindingResult.hasErrors()) {
            Map<String, String> fieldErrors = new HashMap<>();
            bindingResult.getFieldErrors().forEach(error ->
                    fieldErrors.put(error.getField(), error.getDefaultMessage()));
            response.setStatusCode(400);
            response.setData(fieldErrors);
            response.setMessage("There are some fields invalid");
            return new ResponseEntity<>(response, HttpStatus.BAD_REQUEST);
        }

        if (mbDTO == null || mbDTO.getSoHieu() == null) {
            response.setMessage("Invalid plane data!!");
            response.setData(null);
            response.setStatusCode(400); // Bad Request
            return new ResponseEntity<>(response, HttpStatus.BAD_REQUEST);
        }

        ResponseEntity<ResponseData> checkResult = checkExistSoHieu(mbDTO.getSoHieu());
        if (checkResult != null) {
            return checkResult;
        }

        Optional<MayBayDTO> savedMB = mayBayService.addNewMayBay(mbDTO);
        if (savedMB.isPresent()) {
            try {
                createSeats(savedMB.get());
                response.setMessage("Save plane successfully!!");
                response.setData(savedMB.get());
                response.setStatusCode(201); // Created
                return new ResponseEntity<>(response, HttpStatus.CREATED);
            } catch (Exception e) {
                response.setStatusCode(400);
                response.setMessage("Can't add seats when adding plane");
                response.setData(null);
                return new ResponseEntity<>(response, HttpStatus.BAD_REQUEST);
            }
        } else {
            response.setMessage("Save plane unsuccessfully!!");
            response.setData(null);
            response.setStatusCode(500); // Internal Server Error
            return new ResponseEntity<>(response, HttpStatus.INTERNAL_SERVER_ERROR);
        }
    }

    @PutMapping("/updatePlane/{idMB}")
    public ResponseEntity<ResponseData> updatePlane(@PathVariable("idMB") Integer idMB, @Valid @RequestBody MayBayDTO mbDTO, BindingResult bindingResult) {
        if (bindingResult.hasErrors()) {
            Map<String, String> fieldErrors = new HashMap<>();
            bindingResult.getFieldErrors().forEach(error ->
                    fieldErrors.put(error.getField(), error.getDefaultMessage()));
            response.setStatusCode(400);
            response.setData(fieldErrors);
            response.setMessage("There are some fields invalid");
            return new ResponseEntity<>(response, HttpStatus.BAD_REQUEST);
        }

        if (mbDTO == null || idMB == null) {
            response.setMessage("Invalid plane data!!");
            response.setData(null);
            response.setStatusCode(400); // Bad Request
            return new ResponseEntity<>(response, HttpStatus.BAD_REQUEST);
        }

        Optional<MayBayDTO> existingMB = mayBayService.getMayBayById(idMB);
        if (!existingMB.isPresent()) {
            response.setMessage("Plane not found!!");
            response.setData(null);
            response.setStatusCode(404); // Not Found
            return new ResponseEntity<>(response, HttpStatus.NOT_FOUND);
        }

        mbDTO.setIdMayBay(idMB);
        if (mbDTO.getSoHieu() != null && !Objects.equals(existingMB.get().getSoHieu(), mbDTO.getSoHieu())) {
            ResponseEntity<ResponseData> checkResult = checkExistSoHieu(mbDTO.getSoHieu());
            if (checkResult != null) {
                return checkResult;
            }
        }

        Optional<MayBayDTO> updatedMB = mayBayService.updateMayBay(mbDTO);
        if (!updatedMB.isPresent()) {
            response.setMessage("Update plane unsuccessfully!!");
            response.setData(null);
            response.setStatusCode(500); // Internal Server Error
            return new ResponseEntity<>(response, HttpStatus.INTERNAL_SERVER_ERROR);
        }

        // Xóa ghế cũ trước khi thêm ghế mới
        choNgoiService.deleteChoNgoiByMayBay(updatedMB.get());

        try {
            createSeats(updatedMB.get());
            response.setMessage("Update plane successfully!!");
            response.setData(updatedMB.get());
            response.setStatusCode(200); // OK
            return new ResponseEntity<>(response, HttpStatus.OK);
        } catch (Exception e) {
            response.setStatusCode(400);
            response.setMessage("Can't add seats when updating plane");
            response.setData(null);
            return new ResponseEntity<>(response, HttpStatus.BAD_REQUEST);
        }
    }

    private void createSeats(MayBayDTO mbDTO) {
        // Tạo ghế cho máy bay
        for (int iE = 1; iE <= mbDTO.getSoHangGheThuong(); iE++) {
            for (int jE = 0; jE < mbDTO.getSoCotGheThuong().length(); jE++) {
                ChoNgoiDTO cnDTO = new ChoNgoiDTO(HangVeMapper.toEntity(hangVeService.getHangVeById(2).orElseThrow()),
                        MayBayMapper.toEntity(mbDTO),
                        iE,
                        mbDTO.getSoCotGheThuong().charAt(jE),
                        ActiveEnum.ACTIVE
                );
                choNgoiService.addNewChoNgoi(cnDTO);
            }
        }
        for (int iE = 1; iE <= mbDTO.getSoHangGheVip(); iE++) {
            for (int jE = 0; jE < mbDTO.getSoCotGheVip().length(); jE++) {
                ChoNgoiDTO cnDTO = new ChoNgoiDTO(HangVeMapper.toEntity(hangVeService.getHangVeById(1).orElseThrow()),
                        MayBayMapper.toEntity(mbDTO),
                        iE,
                        mbDTO.getSoCotGheVip().charAt(jE),
                        mbDTO.getTrangThaiActive()
                );
                choNgoiService.addNewChoNgoi(cnDTO);
            }
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
}
