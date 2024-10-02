package com.project.flightManagement.Service.Impl;

import com.project.flightManagement.DTO.AuthDTO.LoginDTO;
import com.project.flightManagement.DTO.AuthDTO.SignupDTO;
import com.project.flightManagement.DTO.KhachHangDTO.KhachHangCreateDTO;
import com.project.flightManagement.Enum.ActiveEnum;
import com.project.flightManagement.Mapper.KhachHangMapper;
import com.project.flightManagement.Model.KhachHang;
import com.project.flightManagement.Model.Quyen;
import com.project.flightManagement.Model.TaiKhoan;
import com.project.flightManagement.Repository.TaiKhoanRepository;
import com.project.flightManagement.Service.KhachHangService;
import com.project.flightManagement.Service.TaiKhoanService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;
import java.util.Optional;
@Service

public class TaiKhoanServiceImpl implements TaiKhoanService {
    @Autowired
    private TaiKhoanRepository taiKhoanRepository;
    @Autowired
    private KhachHangService khachHangService;
    @Autowired
    private KhachHangMapper khachHangMapper;
    @Override
    public boolean checkLogin(LoginDTO loginDTO) {
        try {
            // Tìm tài khoản theo tên đăng nhập từ repository
            Optional<TaiKhoan> optionalTaiKhoan = taiKhoanRepository.findTaiKhoanByTenDangNhap(loginDTO.getUserName());

            if (optionalTaiKhoan.isPresent()) {
                TaiKhoan taiKhoan = optionalTaiKhoan.get();
                if (taiKhoan.getMatKhau().equals(loginDTO.getPassword())) {
                    return true; // Đăng nhập thành công
                } else {
                    return false; // Sai mật khẩu
                }
            } else {
                return false; // Không tìm thấy tài khoản
            }

        } catch (Exception e) {
            System.err.println("Đã xảy ra lỗi khi kiểm tra đăng nhập: " + e.getMessage());
            e.printStackTrace();
        }
        return false;
    }

    @Override
    public Optional<TaiKhoan> getTaiKhoanByTenDangNhap(String userName) {
        try {
            Optional<TaiKhoan> optionalTaiKhoan = taiKhoanRepository.findTaiKhoanByTenDangNhap(userName);
            if (optionalTaiKhoan.isPresent()) {
                return optionalTaiKhoan;
            } else {
                System.out.println("Không tìm thấy tài khoản với tên đăng nhập: " + userName);
                return Optional.empty();
            }
        } catch (Exception e) {
            // Log lỗi để dễ dàng theo dõi
            System.err.println("Đã xảy ra lỗi khi tìm tài khoản với tên đăng nhập: " + userName);
            e.printStackTrace();
            return Optional.empty();
        }
    }

    @Override
    public boolean createTaiKhoan(SignupDTO signupDTO) {
        try{
            KhachHangCreateDTO khachHangCreateDTO = khachHangMapper.toKhachHangCreateDTO(signupDTO);
            KhachHang khachHangNew = khachHangService.createKhachHang(khachHangCreateDTO);
            TaiKhoan taiKhoan = new TaiKhoan();
            taiKhoan.setTenDangNhap(signupDTO.getUserName());
            taiKhoan.setMatKhau(signupDTO.getPassword());
            taiKhoan.setKhachHang(khachHangNew);
            Quyen quyen = new Quyen();
            quyen.setIdQuyen(1);
            taiKhoan.setQuyen(quyen);
            taiKhoan.setTrangThaiActive(ActiveEnum.ACTIVE);
            taiKhoan.setThoiGianTao(LocalDateTime.now());
            taiKhoanRepository.save(taiKhoan);
            return true;
        }catch (Exception e) {
            System.out.println(e);
        }
        return false;
    }

    @Override
    public boolean existsTaiKhoanByTenDangNhap(String userName) {
        if(taiKhoanRepository.existsTaiKhoanByTenDangNhap(userName)) {
            return true;
        }
        return false;
    }

}
