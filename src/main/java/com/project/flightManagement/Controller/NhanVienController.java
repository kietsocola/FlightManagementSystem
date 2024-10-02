package com.project.flightManagement.Controller;

import com.project.flightManagement.Model.NhanVien;
import com.project.flightManagement.Repository.NhanVienRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/admin/nhanvien")
@CrossOrigin(origins = "http://localhost:5173")

public class NhanVienController {

    @Autowired
    private NhanVienRepository repo;


    @GetMapping
    public List<NhanVien> showProductList(Model model){
        return repo.findAll();
    }

    @PostMapping
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

}
