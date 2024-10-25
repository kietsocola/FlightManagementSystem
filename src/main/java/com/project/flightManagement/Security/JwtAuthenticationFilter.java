package com.project.flightManagement.Security;

import com.project.flightManagement.Service.CustomUserDetailsService;
import jakarta.servlet.FilterChain;
import jakarta.servlet.ServletException;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.web.authentication.WebAuthenticationDetailsSource;
import org.springframework.stereotype.Component;
import org.springframework.web.filter.OncePerRequestFilter;

import java.io.IOException;

@Component
public class JwtAuthenticationFilter extends OncePerRequestFilter {
    @Autowired
    private JwtTokenProvider jwtTokenProvider;
    @Autowired
    private CustomUserDetailsService customUserDetailsService;

    @Override
    protected void doFilterInternal(HttpServletRequest request, HttpServletResponse response, FilterChain filterChain) throws ServletException, IOException {
        String token = getJwtFromRequest(request);
        System.out.println("Token: "+token);

        if (token != null) {
            if (jwtTokenProvider.validateJwtToken(token)) {
                String userName = jwtTokenProvider.getUserNameFromJwtToken(token);
                UserDetails userDetails = customUserDetailsService.loadUserByUsername(userName);

                if (userDetails != null) {
                    UsernamePasswordAuthenticationToken authentication = new UsernamePasswordAuthenticationToken(
                            userDetails, null, userDetails.getAuthorities());
                    authentication.setDetails(new WebAuthenticationDetailsSource().buildDetails(request));
                    SecurityContextHolder.getContext().setAuthentication(authentication);
                } else {
                    // Ghi log hoặc xử lý khi không tìm thấy người dùng
                    System.out.println("User not found: " + userName);
                }
            } else {
                // Ghi log hoặc xử lý khi token không hợp lệ
                System.out.println("Invalid JWT token: " + token);
            }
        } else {
            // Ghi log hoặc xử lý khi không có token
            System.out.println("No JWT token found in request");
        }
        System.out.println(request.getRequestURI());
        filterChain.doFilter(request, response);
    }
    private String getJwtFromRequest(HttpServletRequest request) {
        String bearerToken = request.getHeader("Authorization");
        if (bearerToken != null && bearerToken.startsWith("Bearer ")) {
            return bearerToken.substring(7);
        }
        return null;
    }
}
