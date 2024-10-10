package com.project.flightManagement.Service;

import com.project.flightManagement.Model.Quyen;
import com.project.flightManagement.Model.TaiKhoan;
import jakarta.transaction.Transactional;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Component;
import org.springframework.stereotype.Service;

import java.util.Collection;
import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

@Service
public class CustomUserDetailsService implements UserDetailsService {
    @Autowired
    private TaiKhoanService taiKhoanService;
    @Override
    @Transactional
    public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {
        Optional<TaiKhoan> taiKhoanOptional = taiKhoanService.getTaiKhoanByTenDangNhap(username);
        if(taiKhoanOptional.isEmpty()) {
            throw new UsernameNotFoundException("User not found with username: " + username);
        }
        TaiKhoan taiKhoan = taiKhoanOptional.get();

        // Nếu tài khoản chỉ có một quyền
        Quyen quyen = taiKhoan.getQuyen();
        if (quyen == null) {
            throw new UsernameNotFoundException("No roles found for user: " + username);
        }

        // Chuyển đổi quyền thành danh sách GrantedAuthority
        List<GrantedAuthority> authorityList = (List<GrantedAuthority>) mapRoleToAuthorities(quyen);

        return new org.springframework.security.core.userdetails.User(
                String.valueOf(taiKhoan.getIdTaiKhoan()),
                taiKhoan.getTenDangNhap(),
                authorityList
        );
    }

    private Collection<? extends GrantedAuthority> mapRoleToAuthorities(Quyen quyen) {
        return quyen.getChiTietQuyenList().stream()
                .map(chiTietQuyen -> new SimpleGrantedAuthority(chiTietQuyen.getChucNang().getTenChucNang()+ "_" + chiTietQuyen.getHanhDong()))
                .collect(Collectors.toList());
    }

}
