package com.project.flightManagement.Service.Impl;

import com.project.flightManagement.DTO.AuthDTO.LoginDTO;
import com.project.flightManagement.DTO.AuthDTO.SignupDTO;
import com.project.flightManagement.DTO.KhachHangDTO.KhachHangCreateDTO;
import com.project.flightManagement.DTO.TaiKhoanDTO.TaiKhoanDTO;
import com.project.flightManagement.DTO.TaiKhoanDTO.TaiKhoanUpdateNguoiDungDTO;
import com.project.flightManagement.Enum.ActiveEnum;
import com.project.flightManagement.Mapper.KhachHangMapper;
import com.project.flightManagement.Mapper.TaiKhoanMapper;
import com.project.flightManagement.Model.KhachHang;
import com.project.flightManagement.Model.Quyen;
import com.project.flightManagement.Model.TaiKhoan;
import com.project.flightManagement.Repository.TaiKhoanRepository;
import com.project.flightManagement.Service.KhachHangService;
import com.project.flightManagement.Service.TaiKhoanService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Lazy;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.security.core.Authentication;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;
import java.util.Optional;
@Service

public class TaiKhoanServiceImpl implements TaiKhoanService {
    @Autowired
    private TaiKhoanRepository taiKhoanRepository;
    @Autowired
    private KhachHangService khachHangService;
    private KhachHangMapper khachHangMapper;
    @Autowired
    @Lazy
    private TaiKhoanMapper taiKhoanMapper;
    @Autowired
    private PasswordEncoder passwordEncoder;
    @Override
    public boolean checkLogin(LoginDTO loginDTO) {
        Optional<TaiKhoan> optionalTaiKhoan = taiKhoanRepository.findTaiKhoanByTenDangNhap(loginDTO.getUserName());

        if (optionalTaiKhoan.isPresent()) {
            TaiKhoan taiKhoan = optionalTaiKhoan.get();
            if (passwordEncoder.matches(loginDTO.getPassword(), taiKhoan.getMatKhau())) {
                return true; // Đăng nhập thành công
            }
        }
        return false; // Sai mật khẩu hoặc không tìm thấy tài khoản
    }


    @Override
    public Optional<TaiKhoan> getTaiKhoanByTenDangNhap(String userName) {
        return taiKhoanRepository.findTaiKhoanByTenDangNhap(userName);
    }

    @Override
    public TaiKhoanDTO getTaiKhoanByIdTaiKhoan(int idTaiKhoan) {
        Optional<TaiKhoan> taiKhoanOptional = taiKhoanRepository.findTaiKhoanByIdTaiKhoan(idTaiKhoan);
        if(taiKhoanOptional.isEmpty()) {
            return null;
        }
        TaiKhoan taiKhoan = taiKhoanOptional.get();
        return taiKhoanMapper.toTaiKhoanDTO(taiKhoan);
    }

    @Override
    public Page<TaiKhoanDTO> getAllTaiKhoan(int page, int size) {

        Pageable pageable = PageRequest.of(page, size);
        Page<TaiKhoan> taiKhoanPage = taiKhoanRepository.findAll(pageable);
        return taiKhoanPage.map(taiKhoanMapper::toTaiKhoanDTO);
    }

    @Override
    public boolean updateTaiKhoan(String userName, TaiKhoanUpdateNguoiDungDTO taiKhoanUpdateNguoiDungDTO) {
        Optional<TaiKhoan> optionalTaiKhoan = taiKhoanRepository.findTaiKhoanByTenDangNhap(userName);
        if (optionalTaiKhoan.isPresent()) {
            TaiKhoan taiKhoan = optionalTaiKhoan.get();
            taiKhoan.setMatKhau(passwordEncoder.encode(taiKhoanUpdateNguoiDungDTO.getMatKhau()));
            taiKhoanRepository.save(taiKhoan);
            return true;
        }
        return false;
    }

    @Override
    public boolean createTaiKhoan(SignupDTO signupDTO) {
        try{
            KhachHangCreateDTO khachHangCreateDTO = khachHangMapper.toKhachHangCreateDTO(signupDTO);
            KhachHang khachHangNew = khachHangService.createKhachHang(khachHangCreateDTO);
            TaiKhoan taiKhoan = new TaiKhoan();
            taiKhoan.setTenDangNhap(signupDTO.getUserName());
            taiKhoan.setMatKhau(passwordEncoder.encode(signupDTO.getPassword()));
            taiKhoan.setKhachHang(khachHangNew);
            Quyen quyen = new Quyen();
            quyen.setIdQuyen(1);
            taiKhoan.setQuyen(quyen);
            taiKhoan.setTrangThaiActive(ActiveEnum.ACTIVE);
            taiKhoan.setThoiGianTao(LocalDateTime.now());
            taiKhoanRepository.save(taiKhoan);
            return true;
        }catch (Exception e) {
            System.err.println("Lỗi khi tạo tài khoản: " + e.getMessage());
        }
        return false;
    }


    @Override
    public boolean existsTaiKhoanByTenDangNhap(String userName) {
        return taiKhoanRepository.existsTaiKhoanByTenDangNhap(userName);
    }

}
