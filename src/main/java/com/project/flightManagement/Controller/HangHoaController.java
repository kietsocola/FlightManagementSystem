package com.project.flightManagement.Controller;

import com.project.flightManagement.DTO.HangHoaDTO.HangHoaDTO;
import com.project.flightManagement.Enum.ActiveEnum;
import com.project.flightManagement.Payload.ResponseData;
import com.project.flightManagement.Repository.LoaiHangHoaRepository;
import com.project.flightManagement.Service.HangHoaService;
import jakarta.persistence.EntityNotFoundException;
import jakarta.validation.Valid;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.dao.DataIntegrityViolationException;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.*;

import java.util.HashMap;
import java.util.Map;
import java.util.Optional;


@RestController
@CrossOrigin("http://localhost:5173")
public class HangHoaController {
    @Autowired
    private HangHoaService HangHoaService;
    private ResponseData response = new ResponseData();
    @Autowired
    private LoaiHangHoaRepository loaiHangHoaRepo;


    @GetMapping("/getAllMerchandises")
    public ResponseEntity<ResponseData> getAllHangHoa() {
        ResponseData response = new ResponseData();
        Iterable<HangHoaDTO> listHangHoaDTO = HangHoaService.getAllHangHoa();
        if (listHangHoaDTO.iterator().hasNext()) {
            response.setMessage("Get list of merchandise success!!");
            response.setData(listHangHoaDTO);
            response.setStatusCode(200);
            return new ResponseEntity<>(response, HttpStatus.OK);
        } else {
            response.setMessage("No merchandise found!!");
            response.setData(null);
            response.setStatusCode(204);
            return new ResponseEntity<>(HttpStatus.NO_CONTENT);
        }
    }

    @GetMapping("/getAllMerchandiseSorted")
    public ResponseEntity<ResponseData> getAllHangHoaSorted(@RequestParam(defaultValue = "idHangHoa") String sortBy,
                                                            @RequestParam(defaultValue = "asc") String order) {
        Iterable<HangHoaDTO> listHangHoaDTO = HangHoaService.getAllHangHoaSorted(sortBy, order);
        if (listHangHoaDTO.iterator().hasNext()) {
            response.setMessage("Get sorted merchandise success!!");
            response.setData(listHangHoaDTO);
            response.setStatusCode(200);
            return new ResponseEntity<>(response, HttpStatus.OK);
        } else {
            response.setMessage("No merchandise found!!");
            response.setData(null);
            response.setStatusCode(204);
            return new ResponseEntity<>(HttpStatus.NO_CONTENT);
        }
    }

    @GetMapping("/getMerchandiseById/{idHH}")
    public ResponseEntity<ResponseData> getHangHoaByIdHangHoa(@PathVariable int idHH) {
        ResponseData response = new ResponseData(); // Khởi tạo đối tượng response
        Optional<HangHoaDTO> HangHoaDTO = HangHoaService.getHangHoaByIdHangHoa(idHH);
        if (HangHoaDTO.isPresent()) {
            response.setMessage("Get merchandise by ID success!!");
            response.setData(HangHoaDTO.get()); // Chỉ lấy đối tượng DTO
            response.setStatusCode(200);
            return new ResponseEntity<>(response, HttpStatus.OK);
        } else {
            response.setMessage("merchandise not found!!");
            response.setData(null);
            response.setStatusCode(404);
            return new ResponseEntity<>(response, HttpStatus.NOT_FOUND);
        }
    }


    @PostMapping("/addNewMerchandise")
    public ResponseEntity<ResponseData> addNewHangHoa(@Valid @RequestBody HangHoaDTO hangHoaDTO, BindingResult bindingResult) {
        ResponseData response = new ResponseData();

        // Handle validation errors
        if (bindingResult.hasErrors()) {
            Map<String, String> errors = new HashMap<>();
            bindingResult.getFieldErrors().forEach(error ->
                    errors.put(error.getField(), error.getDefaultMessage()) // Get specific validation error messages
            );
            response.setData(errors);
            response.setStatusCode(400);
            return new ResponseEntity<>(response, HttpStatus.BAD_REQUEST);
        }

        // Check if idLoaiHangHoa exists
        boolean isLoaiExist = loaiHangHoaRepo.existsById(hangHoaDTO.getIdLoaiHangHoa());
        if (!isLoaiExist) {
            response.setMessage("Error: The specified idLoaiHangHoa does not exist.");
            response.setData(null);
            response.setStatusCode(400);
            return new ResponseEntity<>(response, HttpStatus.BAD_REQUEST);
        }

        // Add new merchandise
        try {
            Optional<HangHoaDTO> savedMerchandise = HangHoaService.addNewHangHoa(hangHoaDTO);
            if (savedMerchandise.isPresent()) {
                response.setMessage("Merchandise saved successfully!");
                response.setData(savedMerchandise.get());
                response.setStatusCode(201);
                return new ResponseEntity<>(response, HttpStatus.CREATED);
            } else {
                response.setMessage("Merchandise saving failed due to unknown reasons.");
                response.setData(null);
                response.setStatusCode(500);
                return new ResponseEntity<>(response, HttpStatus.INTERNAL_SERVER_ERROR);
            }
        } catch (Exception e) {
            response.setMessage("Error saving merchandise: " + e.getMessage());
            response.setData(null);
            response.setStatusCode(500);
            return new ResponseEntity<>(response, HttpStatus.INTERNAL_SERVER_ERROR);
        }
    }


    @PutMapping("/updateMerchandise/{idHangHoa}")
    public ResponseEntity<ResponseData> updateHangHoa(@PathVariable("idHangHoa") Integer idHangHoa, @Valid @RequestBody HangHoaDTO HangHoaDTO, BindingResult bindingResult) {
        ResponseData response = new ResponseData();

        // Handle validation errors
        if (bindingResult.hasErrors()) {
            Map<String, String> errors = new HashMap<>();
            bindingResult.getFieldErrors().forEach(error ->
                    errors.put(error.getField(), error.getDefaultMessage()) // Lấy thông báo lỗi cụ thể từ validation
            );
            response.setData(errors);
            response.setMessage("Validation failed for the provided data."); // Thông báo chung cho lỗi
            response.setStatusCode(400);
            return new ResponseEntity<>(response, HttpStatus.BAD_REQUEST);
        }

        // Check if idLoaiHangHoa exists
        boolean isLoaiExist = loaiHangHoaRepo.existsById(HangHoaDTO.getIdLoaiHangHoa());
        if (!isLoaiExist) {
            response.setMessage("Error: The specified idLoaiHangHoa does not exist.");
            response.setData(null);
            response.setStatusCode(400);
            return new ResponseEntity<>(response, HttpStatus.BAD_REQUEST);
        }

        // Update existing merchandise
        try {
            Optional<HangHoaDTO> updatedMerchandise = HangHoaService.updateHangHoa(idHangHoa, HangHoaDTO);
            if (updatedMerchandise.isPresent()) {
                response.setMessage("Merchandise updated successfully!");
                response.setData(updatedMerchandise.get());
                response.setStatusCode(200); // OK
                return new ResponseEntity<>(response, HttpStatus.OK);
            } else {
                response.setMessage("merchandise with ID " + idHangHoa + " not found!"); // Cung cấp ID cụ thể
                response.setData(null);
                response.setStatusCode(404); // Not Found
                return new ResponseEntity<>(response, HttpStatus.NOT_FOUND);
            }
        } catch (Exception e) {
            response.setMessage("Error updating merchandise: " + e.getMessage());
            response.setData(null);
            response.setStatusCode(500);
            return new ResponseEntity<>(response, HttpStatus.INTERNAL_SERVER_ERROR);
        }
    }


    @DeleteMapping("/deleteMerchandise/{idHH}")
    public ResponseEntity<ResponseData> deleteHangHoa(@PathVariable int idHH) {
        try {
            HangHoaService.deleteHangHoa(idHH);
            response.setMessage("Route deleted successfully!!");
            response.setData(null);
            response.setStatusCode(200);
            return new ResponseEntity<>(response, HttpStatus.OK);
        } catch (EntityNotFoundException e) {
            response.setMessage(e.getMessage());
            response.setData(null);
            response.setStatusCode(404);
            return new ResponseEntity<>(response, HttpStatus.NOT_FOUND);
        } catch (DataIntegrityViolationException e) {
            response.setMessage("Cannot delete merchanse as it is associated with other data.");
            response.setData(null);
            response.setStatusCode(409); // 409 Conflict status code
            return new ResponseEntity<>(response, HttpStatus.CONFLICT);
        } catch (Exception e) {
            response.setMessage("Error occurred while deleting the merchanse: " + e.getMessage());
            response.setData(null);
            response.setStatusCode(500);
            return new ResponseEntity<>(response, HttpStatus.INTERNAL_SERVER_ERROR);
        }
    }



    @GetMapping("/findMerchandise")
    public ResponseEntity<ResponseData> findByTenHangHoa(@RequestParam String keyword) {
        System.out.println("Searching for: " + keyword);
        Iterable<HangHoaDTO> listHangHoaDTO = HangHoaService.findByTenHangHoa(keyword);
        if (listHangHoaDTO.iterator().hasNext()) {
            response.setMessage("Get merchandise by merchandise success!!");
            response.setData(listHangHoaDTO);
            response.setStatusCode(200);
            return new ResponseEntity<>(response, HttpStatus.OK);
        } else {
            response.setMessage("No merchandise found for the merchandise!!");
            response.setData(null);
            response.setStatusCode(404);
            return new ResponseEntity<>(response, HttpStatus.NOT_FOUND);
        }
    }

    @PutMapping("/blockMerchandise/{idHangHoa}")
    public ResponseEntity<ResponseData> blockHangHoa(@PathVariable int idHangHoa){
        Optional<HangHoaDTO> existingHH = HangHoaService.getHangHoaByIdHangHoa(idHangHoa);
        if(existingHH.isPresent()){
            if(existingHH.get().getTrangThaiActive() == ActiveEnum.ACTIVE){
                Optional<HangHoaDTO> blockHH = HangHoaService.blockHangHoa(existingHH.get().getIdHangHoa());
                if(blockHH.isPresent()){
                    response.setMessage("Block merchandise successfully!!");
                    response.setData(blockHH.get());
                    response.setStatusCode(200); // OK
                    return new ResponseEntity<>(response, HttpStatus.OK);
                } else {
                    // Xử lý lỗi khi cập nhật không thành công
                    response.setMessage("Block merchandise unsuccessfully!!");
                    response.setData(null);
                    response.setStatusCode(500); // Internal Server Error
                    return new ResponseEntity<>(response, HttpStatus.INTERNAL_SERVER_ERROR);
                }
            } else {
                Optional<HangHoaDTO> blockHH = HangHoaService.unblockHangHoa(existingHH.get().getIdHangHoa());
                if(blockHH.isPresent()){
                    response.setMessage("Block merchandise successfully!!");
                    response.setData(blockHH.get());
                    response.setStatusCode(200); // OK
                    return new ResponseEntity<>(response, HttpStatus.OK);
                } else {
                    // Xử lý lỗi khi cập nhật không thành công
                    response.setMessage("Block merchandise unsuccessfully!!");
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
