package com.project.flightManagement.Security;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.Lazy;
import org.springframework.http.HttpMethod;
import org.springframework.security.access.AccessDecisionManager;
import org.springframework.security.access.AccessDecisionVoter;
import org.springframework.security.access.vote.AuthenticatedVoter;
import org.springframework.security.access.vote.RoleVoter;
import org.springframework.security.access.vote.UnanimousBased;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.config.annotation.authentication.configuration.AuthenticationConfiguration;
import org.springframework.security.config.annotation.method.configuration.EnableGlobalMethodSecurity;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.http.SessionCreationPolicy;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.web.SecurityFilterChain;
import org.springframework.security.web.access.expression.WebExpressionVoter;
import org.springframework.security.web.authentication.UsernamePasswordAuthenticationFilter;
import org.springframework.web.cors.CorsConfiguration;
import org.springframework.web.cors.UrlBasedCorsConfigurationSource;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

@Configuration
@EnableWebSecurity
@EnableGlobalMethodSecurity(prePostEnabled = true)
public class SecurityConfig {
    @Autowired
    @Lazy
    private JwtAuthenticationFilter jwtAuthenticationFilter;
    @Autowired
    @Lazy
    private CustomUserDetailsService customUserDetailsService;



    @Bean
    public SecurityFilterChain filterChain(HttpSecurity http) throws Exception {
        http
                .cors(cors -> cors.configurationSource(corsConfigurationSource()))
                .csrf(csrf -> csrf.disable())
                .authorizeHttpRequests((request) -> request
//                        .requestMatchers("/**").permitAll()
                        .requestMatchers("/auth/**").permitAll()
                        //Tai khoan
//                        .requestMatchers(HttpMethod.GET, "/taikhoan/**").hasAuthority("Quản lí tài khoản_VIEW")
                        .requestMatchers("/taikhoan/addNewTaiKhoan").hasAuthority("Quản lí tài khoản_CREATE")
                        .requestMatchers("/taikhoan/updateTaiKhoan/{idTK}").hasAuthority("Quản lí tài khoản_EDIT")
                        .requestMatchers("/taikhoan/update_password").hasAuthority("Quản lí tài khoản_EDIT")
                        // PTTT
//                        .requestMatchers("/getAllPTTT").hasAuthority("Quản lí PTTT_VIEW")
//                        .requestMatchers("/getAllPTTT").hasAuthority("Quản lí PTTT_VIEW")
//                        .requestMatchers("/getPTTTByID/{idPTTT}").hasAuthority("Quản lí PTTT_VIEW")
                        .requestMatchers("/addPTTT").hasAuthority("Quản lí PTTT_CREATE")
                        .requestMatchers("/updatePTTT/{idPTTT}").hasAuthority("Quản lí PTTT_EDIT")
                        // Khach hang
//                        .requestMatchers("/khachhang/getAllCustomer").hasAuthority("Quản lí khách hàng_VIEW")
//                        .requestMatchers("/khachhang/addCustomer").hasAuthority("Quản lí khách hàng_CREATE")
//                        .requestMatchers("/getallnhanvien").hasAuthority("Quản lí nhân viên_VIEW")
                        .requestMatchers("/addnhanvien").hasAuthority("Quản lí nhân viên_CREATE")
                        .requestMatchers("/updatenhanvien/{idNhanVien}").hasAuthority("Quản lí nhân viên_EDIT")
//                        .requestMatchers("/admin/maybay/getAllPlane").hasAuthority("Quản lí máy bay_VIEW")
                        .requestMatchers("/admin/maybay/addPlane").hasAuthority("Quản lí máy bay_CREATE")
                        .requestMatchers("/updatePlane/{idMB}").hasAuthority("Quản lí máy bay_EDIT")
                        // Quyen
//                        .requestMatchers(HttpMethod.GET, "/quyen/**").hasAuthority("Quản lí nhóm quyền_VIEW")
                        .requestMatchers("/quyen/create").hasAuthority("Quản lí nhóm quyền_CREATE")
                        .requestMatchers("/quyen/update/{idQuyen}").hasAuthority("Quản lí nhóm quyền_EDIT")
                        //San bay
//                        .requestMatchers("/admin/sanbay/getAllAirport").hasAuthority("Quản lí sân bay_VIEW")
//                        .requestMatchers("/admin/sanbay/getAirport/{idSanBay}").hasAuthority("Quản lí sân bay_VIEW")
//                        .requestMatchers("/admin/sanbay/findAirport").hasAuthority("Quản lí sân bay_VIEW")
//                        .requestMatchers("/admin/sanbay/getAllAirportSorted").hasAuthority("Quản lí sân bay_VIEW")
//                        .requestMatchers("/admin/sanbay/getAirportByCity/{idThanhPho}").hasAuthority("Quản lí sân bay_VIEW")
//                        .requestMatchers("/admin/sanbay/getAirportByNation/{idQuocGia}").hasAuthority("Quản lí sân bay_VIEW")
                        .requestMatchers("/admin/sanbay/addAirport").hasAuthority("Quản lí sân bay_CREATE")
                        .requestMatchers("/admin/sanbay/updateAirport/{idSanBay}").hasAuthority("Quản lí sân bay_EDIT")
                        .requestMatchers("/admin/sanbay/blockAirport/{idSanBay}").hasAuthority("Quản lí sân bay_EDIT")
                        //Chuyen bay
//                        .requestMatchers("/admin/chuyenbay/getallchuyenbay").hasAuthority("Quản lí chuyến bay_VIEW")
                        .requestMatchers("/admin/chuyenbay/addchuyenbay").hasAuthority("Quản lí chuyến bay_CREATE")
                        .requestMatchers("/admin/chuyenbay/updatechuyenbay/{idChuyenBay}").hasAuthority("Quản lí chuyến bay_EDIT")
                        .requestMatchers("/admin/chuyenbay/thongKeGioBayNhanVienByYear").hasAuthority("Quản lí thống kê_VIEW")
                        .requestMatchers("/admin/chuyenbay/thongKeGioBayNhanVienByYearAndMonth").hasAuthority("Quản lí thống kê_VIEW")
                        .requestMatchers("/admin/chuyenbay/thongKeGioBayNhanVienByYearAndQuy").hasAuthority("Quản lí thống kê_VIEW")
                        .requestMatchers("/admin/chuyenbay/thongKeChuyenBayTheoTrangThaiByYear").hasAuthority("Quản lí thống kê_VIEW")
                        .requestMatchers("/admin/chuyenbay/thongKeChuyenBayTheoTrangThaiByYearAndMonth").hasAuthority("Quản lí thống kê_VIEW")
                        .requestMatchers("/admin/chuyenbay/thongKeChuyenBayTheoTrangThaiByYearAndQuy").hasAuthority("Quản lí thống kê_VIEW")
                        .requestMatchers("/admin/chuyenbay/thongkeTuyenBayByYear").hasAuthority("Quản lí thống kê_VIEW")
                        .requestMatchers("/admin/chuyenbay/thongkeTuyenBayByYearAndQuy").hasAuthority("Quản lí thống kê_VIEW")
                        .requestMatchers("/admin/chuyenbay/thongkeTuyenBayByYearAndMonth").hasAuthority("Quản lí thống kê_VIEW")
                        .requestMatchers("/admin/chuyenbay/thongKeVeDaDungByYear").hasAuthority("Quản lí thống kê_VIEW")
                        .requestMatchers("/admin/chuyenbay/thongKeVeDaDungByYearAndMonth").hasAuthority("Quản lí thống kê_VIEW")
                        .requestMatchers("/admin/chuyenbay/thongKeVeDaDungByYearAndQuy").hasAuthority("Quản lí thống kê_VIEW")
                        //Hoa đơn
//                        .requestMatchers("/getAllHoaDon").hasAuthority("Quản lí hoá đơn_VIEW")
//                        .requestMatchers("/getHoaDonById/{idHD}").hasAuthority("Quản lí hoá đơn_VIEW")
                        .requestMatchers("/addHoaDon").hasAuthority("Quản lí hoá đơn_CREATE")
                        .requestMatchers("/updateHoaDon/{idHD}").hasAuthority("Quản lí hoá đơn_EDIT")
//                        .requestMatchers("/getListChiTietHoaDon/{idHD}").hasAuthority("Quản lí hoá đơn_VIEW")
//                        .requestMatchers("/getHoaDonByKeyWord").hasAuthority("Quản lí hoá đơn_VIEW")
//                        .requestMatchers("/getAllHoaDonSorted").hasAuthority("Quản lí hoá đơn_VIEW")
//                        .requestMatchers("/getHoaDonByField").hasAuthority("Quản lí hoá đơn_VIEW")
//                        .requestMatchers("/updateHoaDonState/{idHD}").hasAuthority("Quản lí hoá đơn_EDIT")
//                        .requestMatchers("/createHoaDon").hasAuthority("Quản lí hoá đơn_CREATE")
//                        .requestMatchers("/markDanhGia/{idHoaDon}").hasAuthority("Quản lí hoá đơn_EDIT")
                        .requestMatchers("/thongke/**").hasAuthority("Quản lí thống kê_VIEW")
                        //HangHoa
//                        .requestMatchers("/getAllMerchandises").hasAuthority("Quản lí hàng hoá_VIEW")
//                        .requestMatchers("/getAllMerchandiseSorted").hasAuthority("Quản lí hàng hoá_VIEW")
//                        .requestMatchers("/getMerchandiseById/{idHH}").hasAuthority("Quản lí hàng hoá_VIEW")
//                        .requestMatchers("/addNewMerchandise").hasAuthority("Quản lí hàng hoá_CREATE")
//                        .requestMatchers("/updateMerchandise/{idHangHoa}").hasAuthority("Quản lí hàng hoá_EDIT")
//                        .requestMatchers("/findMerchandise}").hasAuthority("Quản lí hàng hoá_VIEW")
//                        .requestMatchers("/blockMerchandise/{idHangHoa}").hasAuthority("Quản lí hàng hoá_EDIT")
                        //Chuc vu
//                        .requestMatchers(HttpMethod.GET, "/admin/chucvu/**").hasAuthority("Quản lí chức vụ_VIEW")
                        .requestMatchers(HttpMethod.POST, "/admin/chucvu/**").hasAuthority("Quản lí chức vụ_CREATE")
                        .requestMatchers(HttpMethod.PUT, "/admin/chucvu/**").hasAuthority("Quản lí chức vụ_EDIT")
                        //Danh gia
//                        .requestMatchers("/admin/danhgia/getAllReview").hasAuthority("Quản lí đánh giá_VIEW")
                        //Ve
//                        .requestMatchers("/ve/chuyenbay/{idChuyenBay}").hasAuthority("Quản lí vé_VIEW")
                        .requestMatchers(HttpMethod.PUT, "/ve/{idVe}").hasAuthority("Quản lí vé_EDIT")
//                        .requestMatchers(HttpMethod.GET, "/ve/{idVe}").hasAuthority("Quản lí vé_VIEW")
                        .requestMatchers(HttpMethod.POST, "/ve").hasAuthority("Quản lí vé_CREATE")
                        .requestMatchers("/ve/update_hanhkhach").hasAuthority("Quản lí vé_EDIT")
//                        .requestMatchers("/ve/search").hasAuthority("Quản lí vé_VIEW")
//                        .requestMatchers("/ve/statuses").hasAuthority("Quản lí vé_VIEW")
//                        .requestMatchers("/ve/giave/chuyenbay/{idChuyenBay}").hasAuthority("Quản lí vé_VIEW")
//                        .requestMatchers("/ve/getAll").hasAnyAuthority("Quản lí vé_VIEW", "Quản lí thống kê_VIEW")
                        //Tuyen bay
//                        .requestMatchers("/getAllRoutes").hasAuthority("Quản lí tuyến bay_VIEW")
//                        .requestMatchers("/getAllRoutesSorted").hasAuthority("Quản lí tuyến bay_VIEW")
//                        .requestMatchers("/getRouteById/{idTB}").hasAuthority("Quản lí tuyến bay_VIEW")
                        .requestMatchers("/addNewRoute").hasAuthority("Quản lí vé_CREATE")
                        .requestMatchers("/updateRoute/{idTuyenBay}").hasAuthority("Quản lí tuyến bay_EDIT")
                        .requestMatchers("/blockRoute/{idTuyenBay}").hasAuthority("Quản lí tuyến bay_EDIT")
                        //Loai hoa don
//                        .requestMatchers("/getAllLoaiHD").hasAuthority("Quản lí loại hoá đơn_VIEW")
//                        .requestMatchers("/getLoaiHDByID/{idLoaiHD}").hasAuthority("Quản lí loại hoá đơn_VIEW")
//                        .requestMatchers("/addLoaiHoaDon").hasAuthority("Quản lí loại hoá đơn_CREATE")
//                        .requestMatchers("/updateLoaiHoaDon/{idLoaiHD}").hasAuthority("Quản lí loại hoá đơn_EDIT")
//                        .requestMatchers("/getAllLoaiHDSorted").hasAuthority("Quản lí loại hoá đơn_VIEW")
//                        .requestMatchers("/getLoaiHDByKeyWord").hasAuthority("Quản lí loại hoá đơn_VIEW")
                        //Nhan vien
//                        .requestMatchers(HttpMethod.GET, "/admin/nhanvien/**").hasAuthority("Quản lí nhân viên_VIEW")
                        .requestMatchers(HttpMethod.POST, "/admin/nhanvien/**").hasAuthority("Quản lí nhân viên_CREATE")
                        .requestMatchers(HttpMethod.PUT, "/admin/nhanvien/**").hasAuthority("Quản lí nhân viên_EDIT")
                        //May Bay
//                        .requestMatchers("/admin/maybay/getPlane/{id}").hasAuthority("Quản lí máy bay_VIEW")
//                        .requestMatchers("/admin/maybay/getAllPlane").hasAuthority("Quản lí máy bay_VIEW")
//                        .requestMatchers("/admin/maybay/findPlane").hasAuthority("Quản lí máy bay_VIEW")
//                        .requestMatchers("/admin/maybay/getAllPlaneSorted").hasAuthority("Quản lí máy bay_VIEW")
//                        .requestMatchers("/admin/maybay/getPlaneByAirline/{idHangBay}").hasAuthority("Quản lí máy bay_VIEW")
//                        .requestMatchers("/admin/maybay/getPlaneByAirport/{idSanBay}").hasAuthority("Quản lí máy bay_VIEW")
                        .requestMatchers("/admin/maybay/addPlane").hasAuthority("Quản lí máy bay_CREATE")
                        .requestMatchers("/admin/maybay/updatePlane/**").hasAuthority("Quản lí máy bay_EDIT")
                        .requestMatchers("/admin/maybay/blockPlane/**").hasAuthority("Quản lí máy bay_EDIT")
                        .requestMatchers("/admin/maybay/getHoursOfPlane/**").hasAuthority("Quản lí thống kê_VIEW")
                        // Danh Gia
//                        .requestMatchers("/admin/danhgia/getReview/{idDanhGia}").hasAuthority("Quản lí đánh giá_VIEW")
//                        .requestMatchers("/admin/danhgia/getAllReview").hasAuthority("Quản lí đánh giá_VIEW")
//                        .requestMatchers("/admin/danhgia/replies/{parentId}").hasAuthority("Quản lí đánh giá_VIEW")
//                        .requestMatchers("/admin/danhgia/getReviewByCustomer/{idKhachHang}").hasAuthority("Quản lí đánh giá_VIEW")
//                        .requestMatchers("/admin/danhgia/getReviewByNameOfCustomer").hasAuthority("Quản lí đánh giá_VIEW")
//                        .requestMatchers("/admin/danhgia/getReviewByHangBay/{idHangBay}").hasAuthority("Quản lí đánh giá_VIEW")
//                        .requestMatchers("/admin/danhgia/getReviewByStartTimeAndEndTime").hasAuthority("Quản lí đánh giá_VIEW")
                        .requestMatchers("/admin/danhgia/addCMT").hasAuthority("Quản lí đánh giá_CREATE")
                        .requestMatchers("/admin/danhgia/addNewReview").hasAuthority("Quản lí đánh giá_CREATE")
                        .requestMatchers("/admin/danhgia/blockReview/{idDanhGia}").hasAuthority("Quản lí đánh giá_EDIT")
                        .anyRequest().permitAll());
        http.sessionManagement(session ->session
                .sessionCreationPolicy(SessionCreationPolicy.STATELESS));
        http.addFilterBefore(jwtAuthenticationFilter, UsernamePasswordAuthenticationFilter.class);
        return http.build();
    }
    @Bean
    public UrlBasedCorsConfigurationSource corsConfigurationSource() {
        UrlBasedCorsConfigurationSource source = new UrlBasedCorsConfigurationSource();
        CorsConfiguration config = new CorsConfiguration();
        config.setAllowCredentials(true);
        config.setAllowedOrigins(List.of("http://localhost:5173")); // Địa chỉ front-end
        config.setAllowedMethods(Arrays.asList("GET", "POST", "PUT", "DELETE", "OPTIONS"));
        config.setAllowedHeaders(List.of("*"));
        source.registerCorsConfiguration("/**", config);
        return source;
    }
    @Bean
    public BCryptPasswordEncoder bCryptPasswordEncoder() {
        return new BCryptPasswordEncoder();
    }
    @Bean
    public AuthenticationManager customAuthenticationManager(AuthenticationConfiguration authConfig) throws Exception {
        return authConfig.getAuthenticationManager();
    }
    @Bean
    public AccessDecisionManager accessDecisionManager() {
        List<AccessDecisionVoter<?>> decisionVoters = new ArrayList<>();
        decisionVoters.add(new WebExpressionVoter());
        decisionVoters.add(new RoleVoter());
        decisionVoters.add(new AuthenticatedVoter());

        return new UnanimousBased(decisionVoters);
    }
}
