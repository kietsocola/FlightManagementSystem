package com.project.flightManagement.Security;

import com.project.flightManagement.Service.InvalidTokenService;
import io.jsonwebtoken.JwtException;
import io.jsonwebtoken.Jwts;
import io.jsonwebtoken.io.Decoders;
import io.jsonwebtoken.security.Keys;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Component;
import org.springframework.stereotype.Service;

import javax.crypto.SecretKey;
import java.util.Date;
import java.util.UUID;

@Component
public class JwtTokenProvider {
    @Value("${jwt.secret}")
    private String secretKey;
    private final long JWT_EXPIRATION = 60 * 30 * 1000L; // 15 phút
    private final long JWT_REFRESH_EXPIRATION =  60 * 60 * 24 * 7 * 1000L; // 7 ngày


    @Autowired
    private InvalidTokenService invalidTokenService;

    // Tạo JWT từ username
    public String generateToken(String data) { // access token
        SecretKey key = Keys.hmacShaKeyFor(Decoders.BASE64.decode(secretKey));
        // Tạo ID cho token
        String tokenId = UUID.randomUUID().toString(); // Tạo ID ngẫu nhiên
        String jws = Jwts.builder()
                .setSubject(data)
                .setIssuedAt(new Date())
                .setId(tokenId)
                .setExpiration(new Date((new Date()).getTime() + JWT_EXPIRATION)) // Thời gian hết hạn
                .signWith(key)
                .compact();
        return jws;
    }

    public String generateRefreshToken(String data) {
        SecretKey key = Keys.hmacShaKeyFor(Decoders.BASE64.decode(secretKey));
        // Tạo ID cho token
        String tokenId = UUID.randomUUID().toString(); // Tạo ID ngẫu nhiên
        String jws = Jwts.builder()
                .setSubject(data)
                .setIssuedAt(new Date())
                .setId(tokenId)
                .setExpiration(new Date((new Date()).getTime() + JWT_REFRESH_EXPIRATION)) // Thời gian hết hạn
                .signWith(key)
                .compact();
        return jws;
    }

    // Lấy username từ JWT
    public String getUserNameFromJwtToken(String token) {
        return Jwts.parser().setSigningKey(secretKey).parseClaimsJws(token).getBody().getSubject();
    }
    public String getIdTokenFromJwtToken(String token) {
        return Jwts.parser().setSigningKey(secretKey).parseClaimsJws(token).getBody().getId();
    }
    public Date getExpirationTimeTokenFromJwtToken(String token) {
        return Jwts.parser().setSigningKey(secretKey).parseClaimsJws(token).getBody().getExpiration();
    }

    // Xác thực JWT
    public boolean validateJwtToken(String token) {
        try {
            String tokenId = getIdTokenFromJwtToken(token);
            if(invalidTokenService.existsByIdToken(tokenId)) {
                return false; // token nay da bi thu hoi. (dang duoc luu trong db)
            }
            Jwts.parser().setSigningKey(secretKey).parseClaimsJws(token);
            return true;
        } catch (JwtException | IllegalArgumentException e) {
            System.out.println("Invalid JWT token: " + e.getMessage());
        }
        return false;
    }
}
