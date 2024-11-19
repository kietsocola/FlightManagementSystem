package com.project.flightManagement.Controller;

import com.project.flightManagement.DTO.ChucVuDTO.ChucVuDTO;
import com.project.flightManagement.DTO.ChuyenBayDTO.ChuyenBayDTO;
import com.project.flightManagement.DTO.NhanVienDTO.NhanVienDTO;
import com.project.flightManagement.Enum.ActiveEnum;
import com.project.flightManagement.Mapper.ChucVuMapper;
import com.project.flightManagement.Model.ChucVu;
import com.project.flightManagement.Model.NhanVien;
import com.project.flightManagement.Payload.ResponseData;
import com.project.flightManagement.Repository.NhanVienRepository;
import com.project.flightManagement.Service.ChucVuService;
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
    @Autowired
    private ChucVuService cvservice ;
    private ResponseData response =  new ResponseData();

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
    public ResponseEntity<ResponseData> getAllNhanVien(@RequestParam(defaultValue = "idNhanVien") String sortField ,@RequestParam(defaultValue = "asc") String sortOrder){
        Iterable<NhanVienDTO> listnvDTO = nvservice.getAllNhanVienSorted(sortField,sortOrder);
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
        Iterable<NhanVienDTO> nvDTO = nvservice.getNhanVienByEmail(email);
        if(nvDTO.iterator().hasNext()){
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
        Iterable<NhanVienDTO> nvDTO = nvservice.getNhanVienByCCCD(cccd);
        if(nvDTO.iterator().hasNext()){
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
        Iterable<NhanVienDTO> nvDTO = nvservice.getNhanVienBySDT(sdt);
        if(nvDTO.iterator().hasNext()){
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
        System.out.println(nvDTO.getEmail());


        if(existInfo(nvDTO,"email") != null){
            return existInfo(nvDTO,"email");
        }

        if(existInfo(nvDTO,"cccd") != null){
            return existInfo(nvDTO,"cccd");
        }

        if(existInfo(nvDTO,"sdt") != null){
            return existInfo(nvDTO,"sdt");
        }

        Optional<NhanVienDTO> saveNv = nvservice.addNhanVien(nvDTO);
        if (saveNv.isPresent()) {
            response.setMessage("Them nhan vien thanh cong");
            response.setData(saveNv.get()); // Trả về dữ liệu của khách hàng đã lưu
            response.setStatusCode(201); // Created
            return new ResponseEntity<>(response, HttpStatus.CREATED);
        } else {
            // Xử lý lỗi khi lưu không thành công
            response.setMessage("Them nhan vien that bai");
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

         Optional<NhanVienDTO> nhanviendto = nvservice.getNhanVienByIdNhanVien(idNhanVien);
         System.out.println("dang o update");
         if(!nhanviendto.get().getEmail().equals(nvDTO.getEmail())){

             if(existInfo(nvDTO,"email") != null){
                 return existInfo(nvDTO,"email");
             }
         }

         if(!nhanviendto.get().getCccd().equals(nvDTO.getCccd())){
             if(existInfo(nvDTO,"cccd") != null){
                 return existInfo(nvDTO,"cccd");
             }
         }


         if(!nhanviendto.get().getSoDienThoai().equals(nvDTO.getSoDienThoai())){
             if(existInfo(nvDTO,"sdt") != null){
                 return existInfo(nvDTO,"sdt");
             }
         }



         Optional<NhanVienDTO> saveNv = nvservice.addNhanVien(nvDTO);
         if (saveNv.isPresent()) {
             response.setMessage("Sua nhan vien thanh cong");
             response.setData(saveNv.get()); // Trả về dữ liệu của khách hàng đã lưu
             response.setStatusCode(201); // Created
             return new ResponseEntity<>(response, HttpStatus.CREATED);
         } else {
             // Xử lý lỗi khi lưu không thành công
             response.setMessage("Sua nhan vien that bai");
             response.setData(null);
             response.setStatusCode(500); // Internal Server Error
             return new ResponseEntity<>(response, HttpStatus.INTERNAL_SERVER_ERROR);
         }


     }

     public ResponseEntity<ResponseData> existInfo(NhanVienDTO nvDTO , String type){

        if(type.equals("email")){
                Iterable<NhanVienDTO> existingEmail = nvservice.getNhanVienByEmail(nvDTO.getEmail());
                int count = 0;
                for (NhanVienDTO nhanVien : existingEmail) {
                    count++;
                }
                if(count > 1){
                    NhanVienDTO error = new NhanVienDTO();
                    error.setEmail("Email đã tồn tại");
                    response.setMessage("Nhan vien voi email nay da ton tai");
                    response.setData(error);
                    response.setStatusCode(409); // Conflict
                    return new ResponseEntity<>(response, HttpStatus.CONFLICT);
                }
        }
        else if(type.equals("cccd")){
                Iterable<NhanVienDTO> existingCccd = nvservice.getNhanVienByCCCD(nvDTO.getCccd());
            int count = 0;
            for (NhanVienDTO nhanVien : existingCccd) {
                count++;
            }
                if(count > 1){
                    NhanVienDTO error = new NhanVienDTO();
                    error.setCccd("CCCD đã tồn tại");
                    response.setMessage("Nhan vien voi can cuoc cong dan nay da ton tai");
                    response.setData(error);
                    response.setStatusCode(409); // Conflict
                    return new ResponseEntity<>(response, HttpStatus.CONFLICT);
                }
        }
        else if(type.equals("sdt")){
                Iterable<NhanVienDTO> existingSDT = nvservice.getNhanVienBySDT(nvDTO.getSoDienThoai());
            int count = 0;
            for (NhanVienDTO nhanVien : existingSDT) {
                count++;
            }
                if(count > 1){
                    NhanVienDTO error = new NhanVienDTO();
                    error.setSoDienThoai("Số điện thoại đã tồn tại");
                    response.setMessage("Nhan vien voi so dien thoai nay da ton tai");
                    response.setData(error);
                    response.setStatusCode(409); // Conflict
                    return new ResponseEntity<>(response, HttpStatus.CONFLICT);
                }
        }

        return null;

     }


    @GetMapping("/getnhanvienbetween")
    public ResponseEntity<ResponseData> getNhanVienBetween(@RequestParam String start,@RequestParam String end){
        Iterable<NhanVienDTO> listNvDTO = nvservice.getNhanVienBetween(start, end);
        if(listNvDTO.iterator().hasNext()){
            response.setMessage("get list Nhan Viens between success!!");
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

    @GetMapping("/filter")
    public ResponseEntity<ResponseData> filterNhanVien(
            @RequestParam(required = false) String hoTen,
            @RequestParam(required = false) String email,
            @RequestParam(required = false) String soDienThoai,
            @RequestParam(required = false) String cccd,
            @RequestParam(required = false) int chucVuId) {

        Iterable<NhanVienDTO> nvDTO;
        // Nếu `chucVuId` là 0, bỏ qua lọc chức vụ
        if (chucVuId == 0) {
            nvDTO = nvservice.filterNhanVien(hoTen, email, soDienThoai, cccd, null);
        } else {
            Optional<ChucVuDTO> cvDTO = cvservice.getChucVuById(chucVuId);
            nvDTO = nvservice.filterNhanVien(hoTen, email, soDienThoai, cccd, ChucVuMapper.toEntity(cvDTO.get()));
        }

        if(nvDTO.iterator().hasNext()){
            response.setMessage("get list nhan vien success");
            response.setData(nvDTO);
            response.setStatusCode(200);
            return new ResponseEntity<>(response, HttpStatus.OK);
        } else {
            response.setMessage("get list nhanvien unsuccess!!");
            response.setData(null);
            response.setStatusCode(404);
            return new ResponseEntity<>(response, HttpStatus.NOT_FOUND);
        }
    }

}
