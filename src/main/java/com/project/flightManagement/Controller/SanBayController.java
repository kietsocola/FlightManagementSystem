package com.project.flightManagement.Controller;

import com.project.flightManagement.DTO.MayBayDTO.MayBayDTO;
import com.project.flightManagement.DTO.SanBayDTO.SanBayDTO;
import com.project.flightManagement.DTO.ThanhPhoDTO.ThanhPhoDTO;
import com.project.flightManagement.Enum.ActiveEnum;
import com.project.flightManagement.Mapper.ThanhPhoMapper;
import com.project.flightManagement.Model.ThanhPho;
import com.project.flightManagement.Payload.ResponseData;
import com.project.flightManagement.Service.SanBayService;
import com.project.flightManagement.Service.ThanhPhoService;
import jakarta.validation.Valid;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.*;

import java.util.*;

@RestController
@CrossOrigin(origins = "http://localhost:5173")
public class SanBayController {
    @Autowired
    private SanBayService sanBayService;
    @Autowired
    private ThanhPhoService thanhPhoService;

    private ResponseData response = new ResponseData();
    @GetMapping("/getAirport/{idSanBay}")
    public ResponseEntity<ResponseData> getSanBayById(@PathVariable int idSanBay) {
        Optional<SanBayDTO> sanBayDTO = sanBayService.getSanBayById(idSanBay);
        if (sanBayDTO.isPresent()) {
            response.setData(sanBayDTO);
            response.setMessage("Get airport by ID success!!");
            response.setStatusCode(200);
            return new ResponseEntity<>(response, HttpStatus.OK);
        } else {
            response.setStatusCode(404);
            response.setMessage("Airport not found!!");
            response.setData(null);
            return new ResponseEntity<>(response, HttpStatus.NOT_FOUND);
        }
    }
    @GetMapping("/getAllAirport")
    public ResponseEntity<ResponseData> getAllSanBay() {
        Iterable<SanBayDTO> sanBayDTOList = sanBayService.getAllSanBay();
        if (sanBayDTOList.iterator().hasNext()) {
            response.setData(sanBayDTOList);
            response.setStatusCode(200);
            response.setMessage("Get all airport success!!");
            return new ResponseEntity<>(response, HttpStatus.OK);
        } else {
            response.setStatusCode(404);
            response.setMessage("Airport not found!!");
            response.setData(null);
            return new ResponseEntity<>(response, HttpStatus.NOT_FOUND);
        }
    }
    @GetMapping("/findAirport")
    public ResponseEntity<ResponseData> findPlaneByKeyword(@RequestParam String keyword) {
        System.out.println("Search for: " + keyword);
        List<SanBayDTO> sanBayDTOList = sanBayService.findSanBayByKeyword(keyword);
        if (sanBayDTOList.iterator().hasNext()) {
            response.setData(sanBayDTOList);
            response.setStatusCode(200);
            response.setMessage("Find plane by keyword success!!");
            return new ResponseEntity<>(response, HttpStatus.OK);
        } else {
            response.setStatusCode(404);
            response.setMessage("Airport not found!!");
            response.setData(null);
            return new ResponseEntity<>(response, HttpStatus.NOT_FOUND);
        }
    }
    @GetMapping("/getAllAirportSorted")
    public ResponseEntity<ResponseData> getAllSanBaySorted(@RequestParam(defaultValue = "idSanBay") String sortBy,
                                                           @RequestParam(defaultValue = "asc") String order) {
        List<SanBayDTO> sanBayDTOList = sanBayService.getAllSanBaySorted(sortBy, order);
        if (sanBayDTOList.iterator().hasNext()) {
            response.setData(sanBayDTOList);
            response.setStatusCode(200);
            response.setMessage("Get all airport sorted success!!");
            return new ResponseEntity<>(response, HttpStatus.OK);
        } else {
            response.setStatusCode(404);
            response.setMessage("Airport not found!!");
            response.setData(null);
            return new ResponseEntity<>(response, HttpStatus.NOT_FOUND);
        }
    }
    @GetMapping("/getAirportByCity/{idThanhPho}")
    public ResponseEntity<ResponseData> getSanBayByThanhPho(@PathVariable int idThanhPho){
        Optional<ThanhPhoDTO> tpDTO = thanhPhoService.getThanhPhoById(idThanhPho);
        if(tpDTO.isPresent()){
            ThanhPho tp = tpDTO.map(ThanhPhoMapper::toEntity).get();
            List<SanBayDTO> sbList = sanBayService.getSanBayByThanhPho(tp);
            if (sbList.iterator().hasNext()){
                response.setData(sbList);
                response.setMessage("Get airport by city success!!");
                response.setStatusCode(200);
                return new ResponseEntity<>(response, HttpStatus.OK);
            } else {
                response.setStatusCode(404);
                response.setMessage("Get airport by city failed!!");
                response.setData(null);
                return new ResponseEntity<>(response, HttpStatus.NOT_FOUND);
            }
        } else {
            response.setMessage("Not found this city");
            response.setData(null);
            response.setStatusCode(404);
            return new ResponseEntity<>(response, HttpStatus.NOT_FOUND);
        }
    }
    public ResponseEntity<ResponseData> checkExistIcao(String icaoSanBay){
        Optional<SanBayDTO> existingSBByIcao = sanBayService.getSanBayByIcaoSanBay(icaoSanBay);
        if (existingSBByIcao.isPresent()) {
            response.setMessage("Airport with this ICAO already exists!!");
            Map<String, String> errorMessage = new HashMap<>();
            errorMessage.put("ICAO", "Airport with this ICAO already exists!!");
            response.setData(errorMessage);
            response.setStatusCode(409); // Conflict
            return new ResponseEntity<>(response, HttpStatus.CONFLICT);
        }
        return null;
    }
    public ResponseEntity<ResponseData> checkExistIata(String iataSanBay){
        Optional<SanBayDTO> existingSBByIata = sanBayService.getSanBayByIataSanBay(iataSanBay);
        if (existingSBByIata.isPresent()) {
            response.setMessage("Airport with this IATA already exists!!");
            Map<String, String> errorMessage = new HashMap<>();
            errorMessage.put("IATA", "Airport with this IATA already exists!!");
            response.setData(errorMessage);
            response.setStatusCode(409); // Conflict
            return new ResponseEntity<>(response, HttpStatus.CONFLICT);
        }
        return null;
    }
    @PostMapping("/addAirport")
    public ResponseEntity<ResponseData> addNewSanBay(@Valid @RequestBody SanBayDTO sbDTO, BindingResult bindingResult){
        if(bindingResult.hasErrors()){
            Map<String, String> fieldErrors = new HashMap<>();
            bindingResult.getFieldErrors().forEach(error ->
                    fieldErrors.put(error.getField(), error.getDefaultMessage()));
            response.setStatusCode(400);
            response.setData(fieldErrors);
            response.setMessage("There are some fields invalid");
            return new ResponseEntity<>(response, HttpStatus.BAD_REQUEST);
        }
        if(sbDTO != null && (sbDTO.getIcaoSanBay() != null && sbDTO.getIataSanBay() != null)){
            ResponseEntity<ResponseData> rs = checkExistIcao(sbDTO.getIcaoSanBay());
            if(rs!=null){
                return rs;
            }
            rs = checkExistIata(sbDTO.getIataSanBay());
            if(rs!=null){
                return rs;
            }
            Optional<SanBayDTO> savedSB = sanBayService.addNewSanBay(sbDTO);
            if (savedSB.isPresent()) {
                response.setMessage("Save airport successfully!!");
                response.setData(savedSB.get());
                response.setStatusCode(201); // Created
                return new ResponseEntity<>(response, HttpStatus.CREATED);
            } else {
                // Xử lý lỗi khi lưu không thành công
                response.setMessage("Save airport unsuccessfully!!");
                response.setData(null);
                response.setStatusCode(500); // Internal Server Error
                return new ResponseEntity<>(response, HttpStatus.INTERNAL_SERVER_ERROR);
            }
        } else {
            response.setMessage("Invalid airport data!!");
            response.setData(null);
            response.setStatusCode(400); // Bad Request
            return new ResponseEntity<>(response, HttpStatus.BAD_REQUEST);
        }
    }
    @PutMapping("/updateAirport/{idSanBay}")
    public ResponseEntity<ResponseData> updateAirport(@PathVariable("idSanBay") Integer idSanBay,@Valid @RequestBody SanBayDTO sbDTO, BindingResult bindingResult){
        if(bindingResult.hasErrors()){
            Map<String, String> fieldErrors = new HashMap<>();
            bindingResult.getFieldErrors().forEach(error ->
                    fieldErrors.put(error.getField(), error.getDefaultMessage()));
            response.setStatusCode(400);
            response.setData(fieldErrors);
            response.setMessage("There are some fields invalid");
            return new ResponseEntity<>(response, HttpStatus.BAD_REQUEST);
        }
        if(idSanBay != null && sbDTO != null){
            Optional<SanBayDTO> existingSB = sanBayService.getSanBayById(idSanBay);
            if(existingSB.isPresent()){
                sbDTO.setIdSanBay(idSanBay);
                if(sbDTO.getIataSanBay()!=null && !Objects.equals(existingSB.get().getIataSanBay(), sbDTO.getIataSanBay())){
                    ResponseEntity<ResponseData> rs = checkExistIata(sbDTO.getIataSanBay());
                    if(rs!=null){
                        return rs;
                    }
                }
                if(sbDTO.getIcaoSanBay()!=null && !Objects.equals(sbDTO.getIcaoSanBay(), existingSB.get().getIcaoSanBay())){
                    ResponseEntity<ResponseData> rs = checkExistIcao(sbDTO.getIcaoSanBay());
                    if(rs!=null){
                        return rs;
                    }
                }
                Optional<SanBayDTO> updatedSB = sanBayService.updateSanBay(sbDTO);
                if (updatedSB.isPresent()) {
                    response.setMessage("Update airport successfully!!");
                    response.setData(updatedSB.get());
                    response.setStatusCode(201); // Created
                    return new ResponseEntity<>(response, HttpStatus.CREATED);
                } else {
                    // Xử lý lỗi khi lưu không thành công
                    response.setMessage("Update airport unsuccessfully!!");
                    response.setData(null);
                    response.setStatusCode(500); // Internal Server Error
                    return new ResponseEntity<>(response, HttpStatus.INTERNAL_SERVER_ERROR);
                }
            } else {
                response.setMessage("Airport not found!!");
                response.setData(null);
                response.setStatusCode(404); // Not Found
                return new ResponseEntity<>(response, HttpStatus.NOT_FOUND);
            }
        }else {
            // Dữ liệu không hợp lệ
            response.setMessage("Invalid plane data!!");
            response.setData(null);
            response.setStatusCode(400); // Bad Request
            return new ResponseEntity<>(response, HttpStatus.BAD_REQUEST);
        }
    }
    @PutMapping("/blockAirport/{idSanBay}")
    public ResponseEntity<ResponseData> blockMayBay(@PathVariable int idSanBay){
        Optional<SanBayDTO> existingSB = sanBayService.getSanBayById(idSanBay);
        if(existingSB.isPresent()){
            if(existingSB.get().getTrangThaiActive() == ActiveEnum.ACTIVE){
                Optional<SanBayDTO> blockSB = sanBayService.blockSanBay(existingSB.get().getIdSanBay());
                if(blockSB.isPresent()){
                    response.setMessage("Block airport successfully!!");
                    response.setData(blockSB.get());
                    response.setStatusCode(200); // OK
                    return new ResponseEntity<>(response, HttpStatus.OK);
                } else {
                    // Xử lý lỗi khi cập nhật không thành công
                    response.setMessage("Block airport unsuccessfully!!");
                    response.setData(null);
                    response.setStatusCode(500); // Internal Server Error
                    return new ResponseEntity<>(response, HttpStatus.INTERNAL_SERVER_ERROR);
                }
            } else {
                Optional<SanBayDTO> blockSB = sanBayService.unblockSanBay(existingSB.get().getIdSanBay());
                if(blockSB.isPresent()){
                    response.setMessage("Block airport successfully!!");
                    response.setData(blockSB.get());
                    response.setStatusCode(200); // OK
                    return new ResponseEntity<>(response, HttpStatus.OK);
                } else {
                    // Xử lý lỗi khi cập nhật không thành công
                    response.setMessage("Block airport unsuccessfully!!");
                    response.setData(null);
                    response.setStatusCode(500); // Internal Server Error
                    return new ResponseEntity<>(response, HttpStatus.INTERNAL_SERVER_ERROR);
                }
            }
        } else {
            response.setMessage("Airport not found!!");
            response.setData(null);
            response.setStatusCode(404); // Not Found
            return new ResponseEntity<>(response, HttpStatus.NOT_FOUND);
        }
    }
}
