package com.project.flightManagement.Service.Impl;

import com.project.flightManagement.DTO.AuthDTO.LoginDTO;
import com.project.flightManagement.DTO.AuthDTO.SignupDTO;
import com.project.flightManagement.DTO.KhachHangDTO.KhachHangCreateDTO;
import com.project.flightManagement.DTO.KhachHangDTO.KhachHangDTO;
import com.project.flightManagement.DTO.TaiKhoanDTO.TaiKhoanDTO;
import com.project.flightManagement.DTO.TaiKhoanDTO.TaiKhoanResponseDTO;
import com.project.flightManagement.DTO.TaiKhoanDTO.TaiKhoanUpdateNguoiDungDTO;
import com.project.flightManagement.Enum.ActiveEnum;
import com.project.flightManagement.Mapper.KhachHangMapper;
import com.project.flightManagement.Mapper.TaiKhoanMapper;
import com.project.flightManagement.Model.KhachHang;
import com.project.flightManagement.Model.Quyen;
import com.project.flightManagement.Model.TaiKhoan;
import com.project.flightManagement.Repository.TaiKhoanRepository;
import com.project.flightManagement.Security.JwtTokenProvider;
import com.project.flightManagement.Service.KhachHangService;
import com.project.flightManagement.Service.TaiKhoanService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import java.time.LocalDate;
import java.time.LocalDateTime;
import java.util.Collections;
import java.util.List;
import java.util.Optional;
import java.util.stream.StreamSupport;

@Service

public class TaiKhoanServiceImpl implements TaiKhoanService {
    @Autowired
    private TaiKhoanRepository taiKhoanRepository;
    @Autowired
    private KhachHangService khachHangService;
    @Autowired
    private PasswordEncoder passwordEncoder;
    @Autowired
    private JwtTokenProvider tokenProvider;
    @Autowired
    private TaiKhoanMapper taiKhoanMapper;
    @Override
    public int checkLogin(LoginDTO loginDTO) {
        Optional<TaiKhoan> optionalTaiKhoan = taiKhoanRepository.findTaiKhoanByTenDangNhap(loginDTO.getUserName());

        if (optionalTaiKhoan.isPresent()) {
            TaiKhoan taiKhoan = optionalTaiKhoan.get();
            if (taiKhoan.getTrangThaiActive() == ActiveEnum.IN_ACTIVE) {
                return -1; // tai khoan da bi khoa
            }
            else if (passwordEncoder.matches(loginDTO.getPassword(), taiKhoan.getMatKhau())) {
                return 0; // Đăng nhập thành công
            } else {
                return 1; // Sai mật khẩu
            }
        }
        return 2; // Không tìm thấy tài khoản
    }


    @Override
    public Optional<TaiKhoan> getTaiKhoanByTenDangNhap(String userName) {
        return taiKhoanRepository.findTaiKhoanByTenDangNhap(userName);
    }

    @Override
    public Optional<TaiKhoan> getTaiKhoanByEmail(String email) {
        return taiKhoanRepository.findByKhachHang_Email(email);
    }

    @Override
    public TaiKhoanResponseDTO getTaiKhoanByIdTaiKhoan(int idTaiKhoan) {
        Optional<TaiKhoan> taiKhoanOptional = taiKhoanRepository.findTaiKhoanByIdTaiKhoan(idTaiKhoan);
        if(taiKhoanOptional.isEmpty()) {
            return null;
        }
        TaiKhoan taiKhoan = taiKhoanOptional.get();
        return taiKhoanMapper.toTaiKhoanResponseDTO(taiKhoan);
    }

    @Override
    public Page<TaiKhoanDTO> getAllTaiKhoan(int page, int size) {

        Pageable pageable = PageRequest.of(page, size);
        Page<TaiKhoan> taiKhoanPage = taiKhoanRepository.findAll(pageable);
        return taiKhoanPage.map(taiKhoanMapper::toTaiKhoanDTO);
    }

    @Override
    public String createPasswordResetToken(String email) {
        Optional<TaiKhoan> taiKhoanOptional = taiKhoanRepository.findByKhachHang_Email(email);
        if (taiKhoanOptional.isEmpty()) {
            throw new RuntimeException("tai khoang khong co voi " + email);
        }

        String tokenRefreshPassword = tokenProvider.generateToken(email);
        TaiKhoan taiKhoan = taiKhoanOptional.get();
        taiKhoan.setRefreshPasswordToken(tokenRefreshPassword);
        taiKhoanRepository.save(taiKhoan);
        return tokenRefreshPassword;
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
    public boolean updateTaiKhoan_RefreshPassword(TaiKhoan taiKhoan) {
        Optional<TaiKhoan> optionalTaiKhoan = taiKhoanRepository.findTaiKhoanByTenDangNhap(taiKhoan.getTenDangNhap());
        if (optionalTaiKhoan.isPresent()) {
            TaiKhoan taiKhoanNew = optionalTaiKhoan.get();
            taiKhoanNew.setMatKhau(passwordEncoder.encode(taiKhoan.getMatKhau()));
            taiKhoanRepository.save(taiKhoanNew);
            return true;
        }
        return false;
    }

    @Override
    public boolean createTaiKhoan(SignupDTO signupDTO) {
        try{
            KhachHangCreateDTO khachHangCreateDTO = KhachHangMapper.toKhachHangCreateDTO(signupDTO);
            KhachHang khachHangNew = khachHangService.createKhachHang(khachHangCreateDTO);
            TaiKhoan taiKhoan = new TaiKhoan();
            taiKhoan.setTenDangNhap(signupDTO.getUserName());
            taiKhoan.setMatKhau(passwordEncoder.encode(signupDTO.getPassword()));
            taiKhoan.setKhachHang(khachHangNew);
            Quyen quyen = new Quyen();
            quyen.setIdQuyen(2);
            taiKhoan.setQuyen(quyen);
            taiKhoan.setTrangThaiActive(ActiveEnum.ACTIVE);
            taiKhoan.setThoiGianTao(LocalDate.now());
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
    @Override
    public Iterable<TaiKhoanDTO> findByKeyword(String keyword) {
        Iterable<TaiKhoan> taiKhoanList = taiKhoanRepository.findByKeywordContainingIgnoreCase(keyword);
        // Kiểm tra nếu danh sách trống thì trả về null hoặc thông báo.
        if (!taiKhoanList.iterator().hasNext()) {
            System.out.println("Không tìm thấy tài khoản theo từ khóa!");
            return List.of();  // Trả về danh sách rỗng thay vì null để tránh lỗi NullPointerException.
        }

        // Sử dụng stream để map từng TaiKhoan sang TaiKhoanDTO.
        return StreamSupport.stream(taiKhoanList.spliterator(), false)
                .map(taiKhoanMapper::toTaiKhoanDTO)
                .toList(); // Chuyển về dạng List.
    }
    @Override
    public Iterable<TaiKhoanDTO> getAllTaiKhoanSorted(String field, String order) {
        try {
            List<TaiKhoan> TKList;
            if(order.equals("asc")){
                TKList = taiKhoanRepository.findAll(Sort.by(Sort.Direction.ASC, field));
            } else {
                TKList = taiKhoanRepository.findAll(Sort.by(Sort.Direction.DESC, field));
            }
            List<TaiKhoanDTO> taiKhoanDTOList = StreamSupport.stream(TKList.spliterator(), false)
                    .map(taiKhoanMapper::toTaiKhoanDTO)
                    .toList();
            return taiKhoanDTOList;
        } catch (IllegalArgumentException e) {
            // Xử lý lỗi nếu tham số sortBy không hợp lệ hoặc có lỗi khác liên quan đến tham số
            System.err.println("Invalid sorting field: " + field);
            return Collections.emptyList(); // Trả về danh sách rỗng
        } catch (Exception e) {
            // Xử lý các lỗi không lường trước khác
            System.err.println("An error occurred while fetching sorted planes: " + e.getMessage());
            return Collections.emptyList(); // Trả về danh sách rỗng nếu có lỗi
        }
    }
    @Override
    public Optional<TaiKhoanDTO> updateTaiKhoan(TaiKhoanDTO tkDTO) {
        Optional<TaiKhoan> existingTK = taiKhoanRepository.findById(tkDTO.getIdTaiKhoan());
        if(existingTK.isPresent()){
            TaiKhoan tk = TaiKhoanMapper.toTaiKhoan(tkDTO);
            tk.setMatKhau(passwordEncoder.encode(tkDTO.getMatKhau()));
            TaiKhoan tkSaved = taiKhoanRepository.save(tk);
            return Optional.of(taiKhoanMapper.toTaiKhoanDTO(tkSaved));
        }else {
            System.err.println("Account does not existing!!!");
            return Optional.empty();
        }
    }
    @Override
    public Optional<TaiKhoanDTO> addNewTaiKhoan(TaiKhoanDTO tkDTO) {
        try {
            System.out.println(tkDTO.toString());
            TaiKhoan tk = taiKhoanMapper.toTaiKhoan(tkDTO);
            tk.setMatKhau(passwordEncoder.encode(tkDTO.getMatKhau()));
            TaiKhoan savedTK = taiKhoanRepository.save(tk);
            return Optional.of(taiKhoanMapper.toTaiKhoanDTO(savedTK));
        }catch (Exception e){
            System.out.println(tkDTO.toString());
            System.err.println("Error occurred while save account: " + e.getMessage());
            return null;
        }
    }
    @Override
    public boolean checkExistTenDangNhap(TaiKhoanDTO tkDTO) {
        for(TaiKhoan tk : taiKhoanRepository.findAll()){
            if(tkDTO.getTenDangNhap().equals(tk.getTenDangNhap())){
                return true;
            }
        }
        return false;
    }
    @Override
    public boolean checkExistKhachHang(TaiKhoanDTO taiKhoanDTO) {
        for(TaiKhoan tk : taiKhoanRepository.findAll()){
            if(tk.getKhachHang() != null && tk.getKhachHang().getIdKhachHang() == taiKhoanDTO.getKhachHang().getIdKhachHang()) {
                return true;
            }
        }
        return false;
    }
    @Override
    public boolean checkExistNhanVien(TaiKhoanDTO taiKhoanDTO) {
        for(TaiKhoan tk : taiKhoanRepository.findAll()){
            if (tk.getNhanVien() != null &&  tk.getNhanVien().getIdNhanVien() == taiKhoanDTO.getNhanVien().getIdNhanVien()) {
                return true;
            }
        }
        return false;
    }

    @Override
    public void saveTaiKhoan(TaiKhoan taiKhoan) {
        taiKhoanRepository.save(taiKhoan);
    }
}
