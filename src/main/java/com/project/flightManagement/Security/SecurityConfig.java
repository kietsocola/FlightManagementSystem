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
                        .requestMatchers("/auth/**").permitAll()
                        .requestMatchers("/taikhoan/me").hasAuthority("Quản lí tài khoản_VIEW")
                        .requestMatchers(HttpMethod.GET, "/taikhoan").hasAuthority("Quản lí tài khoản_VIEW")
                        .requestMatchers(HttpMethod.GET, "/taikhoan").hasAuthority("Quản lí tài khoản_VIEW")
                        .requestMatchers(HttpMethod.GET, "/taikhoan/{idTaiKhoan}").hasAuthority("Quản lí tài khoản_VIEW")
                        .requestMatchers(HttpMethod.PUT, "/taikhoan/update_password").authenticated()
                        .requestMatchers(HttpMethod.GET, "/taikhoan/findByKeyWord").hasAuthority("Quản lí tài khoản_VIEW")
                        .requestMatchers(HttpMethod.GET, "/taikhoan/getAllTaiKhoanSorted").hasAuthority("Quản lí tài khoản_VIEW")
                        .requestMatchers(HttpMethod.POST, "/taikhoan/addNewTaiKhoan").hasAuthority("Quản lí tài khoản_CREATE")
                        .requestMatchers(HttpMethod.PUT, "/taikhoan/updateTaiKhoan/{idTK}").hasAuthority("Quản lí tài khoản_EDIT")
                        .anyRequest().authenticated());
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
