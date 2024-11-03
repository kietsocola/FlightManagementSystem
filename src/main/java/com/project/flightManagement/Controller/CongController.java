package com.project.flightManagement.Controller;


import com.project.flightManagement.DTO.ChuyenBayDTO.ChuyenBayDTO;
import com.project.flightManagement.DTO.CongDTO.CongDTO;
import com.project.flightManagement.DTO.NhanVienDTO.NhanVienDTO;
import com.project.flightManagement.DTO.SanBayDTO.SanBayDTO;
import com.project.flightManagement.Mapper.SanBayMapper;
import com.project.flightManagement.Model.SanBay;
import com.project.flightManagement.Payload.ResponseData;
import com.project.flightManagement.Service.CongService;
import com.project.flightManagement.Service.NhanVienService;
import com.project.flightManagement.Service.SanBayService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.Optional;

@RestController
@RequestMapping("/admin/cong")
@CrossOrigin(origins = "http://localhost:5173")

public class CongController {
    @Autowired
    private CongService congService;
    @Autowired
    private SanBayService sanBayService;
    private ResponseData response =  new ResponseData();

    @GetMapping("/getallcong")
    public ResponseEntity<ResponseData> getallcong(){
        Iterable<CongDTO> dscongDTO = congService.getAllCong();
        if(dscongDTO.iterator().hasNext()){
            response.setMessage("get list Nhan Viens success!!");
            response.setData(dscongDTO);
            response.setStatusCode(200);
            return new ResponseEntity<>(response, HttpStatus.OK);
        } else {
            response.setMessage("get list Nhan Viens unsuccess!!");
            response.setData(null);
            response.setStatusCode(204);
            return new ResponseEntity<>(HttpStatus.NO_CONTENT);
        }
    }

    @GetMapping("/getcongbysanbay/{idSanBay}")
    public ResponseEntity<ResponseData> getcongbysanbay(@PathVariable String idSanBay){
        if(idSanBay.isEmpty() ){
            response.setMessage("get list cong unsuccess , id san bay trong!!");
            response.setData(null);
            response.setStatusCode(204);
            return new ResponseEntity<>(HttpStatus.NO_CONTENT);
        }
        int id = Integer.parseInt(idSanBay);
        System.out.println("dang thuc hien chuc nang get cong by san bay");
        Optional<SanBayDTO> sanBay = sanBayService.getSanBayById(id);
        Iterable<CongDTO> dscongDTO = congService.getCongBySanBay(SanBayMapper.toEntity(sanBay.get()));
        if(dscongDTO.iterator().hasNext()){
            response.setMessage("get list cong success!!");
            response.setData(dscongDTO);
            response.setStatusCode(200);
            return new ResponseEntity<>(response, HttpStatus.OK);
        } else {
            response.setMessage("get list cong unsuccess!!");
            response.setData(null);
            response.setStatusCode(204);
            return new ResponseEntity<>(HttpStatus.NO_CONTENT);
        }
    }

    @GetMapping("/getcongbyid/{idCong}")
    public ResponseEntity<ResponseData> getcongbyidcong(@PathVariable int idCong){
        Optional<CongDTO> cDTO = congService.getCongById(idCong);
        if (cDTO.isPresent()) {
            response.setMessage("get cong by id successfully!!");
            response.setData(cDTO.get()); // Trả về dữ liệu của khách hàng đã lưu
            response.setStatusCode(201); // Created
            return new ResponseEntity<>(response, HttpStatus.CREATED);
        } else {
            // Xử lý lỗi khi lưu không thành công
            response.setMessage("get cong by id unsuccessfully!!");
            response.setData(null);
            response.setStatusCode(500); // Internal Server Error
            return new ResponseEntity<>(response, HttpStatus.INTERNAL_SERVER_ERROR);
        }
    }

}
