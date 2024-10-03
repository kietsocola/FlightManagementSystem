package com.project.flightManagement.Controller;

import com.project.flightManagement.DTO.NhanVienDTO.NhanVienDTO;
import com.project.flightManagement.Enum.ActiveEnum;
import com.project.flightManagement.Model.NhanVien;
import com.project.flightManagement.Payload.ResponseData;
import com.project.flightManagement.Repository.NhanVienRepository;
import com.project.flightManagement.Service.NhanVienService;
import jakarta.validation.Valid;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.*;

import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Optional;

@RestController
@RequestMapping("/admin/nhanvien")
@CrossOrigin(origins = "http://localhost:5173")

public class NhanVienController {

    @Autowired
    private NhanVienService nvservice ;
    private ResponseData response =  new ResponseData();
    private NhanVienRepository repo ;

    @GetMapping("/getallnhanvien")
    public ResponseEntity<ResponseData> getAllNhanVien(){
        Iterable<NhanVienDTO> listNvDTO = nvservice.getAllNhanVien();
        if(listNvDTO.iterator().hasNext()){
            response.setMessage("get list Nhan Viens success!!");
            response.setData(listNvDTO);
            response.setStatusCode(200);
            return new ResponseEntity<>(response, HttpStatus.OK);
        } else {
            response.setMessage("get list Nhan Viens unsuccess!!");
            response.setData(null);
            response.setStatusCode(204);
            return new ResponseEntity<>(HttpStatus.NO_CONTENT);
        }
    }

    @GetMapping("/getallnhanviensorted")
    public ResponseEntity<ResponseData> getAllNhanVien(@RequestParam(defaultValue = "idNhanVien") String sortBy){
        Iterable<NhanVienDTO> listnvDTO = nvservice.getAllNhanVienSorted(sortBy);
        if(listnvDTO.iterator().hasNext()){
            response.setMessage("get list Nhan Viens success!!");
            response.setData(listnvDTO);
            response.setStatusCode(200);
            return new ResponseEntity<>(response, HttpStatus.OK);
        } else {
            response.setMessage("get list Nhan Viens unsuccess!!");
            response.setData(null);
            response.setStatusCode(204);
            return new ResponseEntity<>(HttpStatus.NO_CONTENT);
        }
    }


    @GetMapping("/getnhanvienbyhoten")
    public ResponseEntity<ResponseData> getNhanVienByHoTen(@RequestParam String hoTen){
        Iterable<NhanVienDTO> listNvDTO = nvservice.getNhanVienByhoTen(hoTen);
        if(listNvDTO.iterator().hasNext()){
            response.setMessage("get list Nhan Viens success!!");
            response.setData(listNvDTO);
            response.setStatusCode(200);
            return new ResponseEntity<>(response, HttpStatus.OK);
        } else {
            response.setMessage("get list Nhan Viens unsuccess!!");
            response.setData(null);
            response.setStatusCode(404);
            return new ResponseEntity<>(response, HttpStatus.NOT_FOUND);
        }
    }

    @GetMapping("/getnhanvienbyid")
    public ResponseEntity<ResponseData> getNhanVienById(@RequestParam int id){
        Optional<NhanVienDTO> nvDTO = nvservice.getNhanVienByIdNhanVien(id);
        if(nvDTO.isPresent()){
            response.setMessage("get list Nhan Viens success!!");
            response.setData(nvDTO);
            response.setStatusCode(200);
            return new ResponseEntity<>(response, HttpStatus.OK);
        } else {
            response.setMessage("get list Nhan Viens unsuccess!!");
            response.setData(null);
            response.setStatusCode(404);
            return new ResponseEntity<>(response, HttpStatus.NOT_FOUND);
        }
    }

    @GetMapping("/getnhanvienbyemail")
    public ResponseEntity<ResponseData> getNhanVienByEmail(@RequestParam String email){
        Optional<NhanVienDTO> nvDTO = nvservice.getNhanVienByEmail(email);
        if(nvDTO.isPresent()){
            response.setMessage("get list Nhan Viens success!!");
            response.setData(nvDTO);
            response.setStatusCode(200);
            return new ResponseEntity<>(response, HttpStatus.OK);
        } else {
            response.setMessage("get list Nhan Viens unsuccess!!");
            response.setData(null);
            response.setStatusCode(404);
            return new ResponseEntity<>(response, HttpStatus.NOT_FOUND);
        }
    }

    @GetMapping("/getnhanvienbycccd")
    public ResponseEntity<ResponseData> getNhanVienByCccd(@RequestParam String cccd){
        Optional<NhanVienDTO> nvDTO = nvservice.getNhanVienByCCCD(cccd);
        if(nvDTO.isPresent()){
            response.setMessage("get list Nhan Viens success!!");
            response.setData(nvDTO);
            response.setStatusCode(200);
            return new ResponseEntity<>(response, HttpStatus.OK);
        } else {
            response.setMessage("get list Nhan Viens unsuccess!!");
            response.setData(null);
            response.setStatusCode(404);
            return new ResponseEntity<>(response, HttpStatus.NOT_FOUND);
        }
    }

    @GetMapping("/getnhanvienbysodienthoai")
    public ResponseEntity<ResponseData> getNhanVienBySoDienThoai(@RequestParam String sdt){
        Optional<NhanVienDTO> nvDTO = nvservice.getNhanVienBySDT(sdt);
        if(nvDTO.isPresent()){
            response.setMessage("get list Nhan Viens success!!");
            response.setData(nvDTO);
            response.setStatusCode(200);
            return new ResponseEntity<>(response, HttpStatus.OK);
        } else {
            response.setMessage("get list Nhan Viens unsuccess!!");
            response.setData(null);
            response.setStatusCode(404);
            return new ResponseEntity<>(response, HttpStatus.NOT_FOUND);
        }
    }

    @PostMapping("/addnhanvien")
    public ResponseEntity<ResponseData> addNhanVien(@Valid @RequestBody NhanVienDTO nvDTO, BindingResult bindingResult){

        if(bindingResult.hasErrors()){
            Map<String, String> fieldErrors = new HashMap<>();
            bindingResult.getFieldErrors().forEach(error ->
                    fieldErrors.put(error.getField(), error.getDefaultMessage()));
            response.setStatusCode(400);
            response.setData(fieldErrors);
            response.setMessage("There are some fields invalid");
            return new ResponseEntity<>(response, HttpStatus.BAD_REQUEST);
        }

        Optional<NhanVienDTO> existingEmail = nvservice.getNhanVienByEmail(nvDTO.getEmail());
        if(existingEmail.isPresent()){
            response.setMessage("Nhan Vien with this email already exists!!");
            response.setData(null);
            response.setStatusCode(409); // Conflict
            return new ResponseEntity<>(response, HttpStatus.CONFLICT);
        }

        Optional<NhanVienDTO> existingCccd = nvservice.getNhanVienByCCCD(nvDTO.getCccd());
        if(existingCccd.isPresent()){
            response.setMessage("Nhan Vien with this cccd already exists!!");
            response.setData(null);
            response.setStatusCode(409); // Conflict
            return new ResponseEntity<>(response, HttpStatus.CONFLICT);
        }

        Optional<NhanVienDTO> existingSDT = nvservice.getNhanVienBySDT(nvDTO.getSoDienThoai());
        if(existingSDT.isPresent()){
            response.setMessage("Nhan Vien with this sdt already exists!!");
            response.setData(null);
            response.setStatusCode(409); // Conflict
            return new ResponseEntity<>(response, HttpStatus.CONFLICT);
        }

        Optional<NhanVienDTO> saveNv = nvservice.addNhanVien(nvDTO);
        if (saveNv.isPresent()) {
            response.setMessage("Save Nhan vien successfully!!");
            response.setData(saveNv.get()); // Trả về dữ liệu của khách hàng đã lưu
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


     @PutMapping("/updatenhanvien/{idNhanVien}")
     public ResponseEntity<ResponseData> updatenhanvien(@PathVariable("idNhanVien") Integer idNhanVien,@Valid @RequestBody NhanVienDTO nvDTO, BindingResult bindingResult){
         if(bindingResult.hasErrors()){
             Map<String, String> fieldErrors = new HashMap<>();
             bindingResult.getFieldErrors().forEach(error ->
                     fieldErrors.put(error.getField(), error.getDefaultMessage()));
             response.setStatusCode(400);
             response.setData(fieldErrors);
             response.setMessage("There are some fields invalid");
             return new ResponseEntity<>(response, HttpStatus.BAD_REQUEST);
         }

         Optional<NhanVienDTO> existingId = nvservice.getNhanVienByIdNhanVien(idNhanVien);
         if(existingId.isEmpty()){
             response.setMessage("Nhan vien with this id no exists!!");
             response.setData(null);
             response.setStatusCode(409); // Conflict
             return new ResponseEntity<>(response, HttpStatus.CONFLICT);
         }

         Optional<NhanVienDTO> existingEmail = nvservice.getNhanVienByEmail(nvDTO.getEmail());
         if(existingEmail.isPresent()){
             response.setMessage("Nhan Vien with this email already exists!!");
             response.setData(null);
             response.setStatusCode(409); // Conflict
             return new ResponseEntity<>(response, HttpStatus.CONFLICT);
         }

         Optional<NhanVienDTO> existingCccd = nvservice.getNhanVienByCCCD(nvDTO.getCccd());
         if(existingCccd.isPresent()){
             response.setMessage("Nhan Vien with this cccd already exists!!");
             response.setData(null);
             response.setStatusCode(409); // Conflict
             return new ResponseEntity<>(response, HttpStatus.CONFLICT);
         }

         Optional<NhanVienDTO> existingSDT = nvservice.getNhanVienBySDT(nvDTO.getSoDienThoai());
         if(existingSDT.isPresent()){
             response.setMessage("Nhan Vien with this sdt already exists!!");
             response.setData(null);
             response.setStatusCode(409); // Conflict
             return new ResponseEntity<>(response, HttpStatus.CONFLICT);
         }

         Optional<NhanVienDTO> saveNv = nvservice.addNhanVien(nvDTO);
         if (saveNv.isPresent()) {
             response.setMessage("Save Nhan vien successfully!!");
             response.setData(saveNv.get()); // Trả về dữ liệu của khách hàng đã lưu
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


    @GetMapping("/")
    public List<NhanVien> showProductList(Model model){
        return repo.findAll();
    }

    @PostMapping("/add1")
    public ResponseEntity<String> addNhanVien(@RequestBody NhanVien nhanVien) {
        // Log dữ liệu nhận được
        System.out.println("Dữ liệu nhận được: " + nhanVien);
        NhanVien nv = new NhanVien();
        nv.setHoTen(nhanVien.getHoTen());
        nv.setCccd(nhanVien.getCccd());
        nv.setEmail(nhanVien.getEmail());
        nv.setSoDienThoai(nhanVien.getSoDienThoai());
        nv.setChucVu(nhanVien.getChucVu());
        nv.setGioiTinhEnum(nhanVien.getGioiTinhEnum());
        nv.setTrangThaiActive(nhanVien.getTrangThaiActive());
        nv.setNgaySinh(nhanVien.getNgaySinh());
        repo.save(nv);
        // Xử lý thêm nhân viên ở đây
        return ResponseEntity.ok("thêm thành công");
    }

    @PutMapping("/edit1")
    public ResponseEntity<String> editNhanVien(@RequestBody NhanVien nhanVien) {
        // Log dữ liệu nhận được
        System.out.println("Dữ liệu nhận được: " + nhanVien);
        NhanVien nv = repo.findById(nhanVien.getIdNhanVien()).get();
        nv.setIdNhanVien(nhanVien.getIdNhanVien());
        nv.setHoTen(nhanVien.getHoTen());
        nv.setCccd(nhanVien.getCccd());
        nv.setChucVu(nhanVien.getChucVu());
        nv.setTrangThaiActive(nhanVien.getTrangThaiActive());
        nv.setSoDienThoai(nhanVien.getSoDienThoai());
        nv.setNgaySinh(nhanVien.getNgaySinh());
        nv.setGioiTinhEnum(nhanVien.getGioiTinhEnum());
        nv.setEmail(nhanVien.getEmail());
        repo.save(nv);
        // Xử lý thêm nhân viên ở đây
        return ResponseEntity.ok("thêm thành công");
    }


    @PutMapping("/delete1")
    public ResponseEntity<String> deleteNhanVien(@RequestBody NhanVien nhanVien) {
        // Log dữ liệu nhận được
        System.out.println("Dữ liệu nhận được: " + nhanVien.getHoTen());
        NhanVien nv = repo.findById(nhanVien.getIdNhanVien()).get();
        nv.setTrangThaiActive(ActiveEnum.IN_ACTIVE);
        repo.save(nv);
        // Xử lý thêm nhân viên ở đây
        return ResponseEntity.ok("thêm thành công");
    }


}
