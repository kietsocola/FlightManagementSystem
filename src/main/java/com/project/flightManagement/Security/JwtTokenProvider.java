package com.project.flightManagement.Security;

import com.project.flightManagement.Service.InvalidTokenService;
import io.jsonwebtoken.*;
import io.jsonwebtoken.io.Decoders;
import io.jsonwebtoken.security.Keys;
import io.jsonwebtoken.security.SignatureException;
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
    private final long JWT_EXPIRATION = 15 * 1000L; // 15 phút
    private final long JWT_REFRESH_EXPIRATION = 60 * 5 * 1000L; // 7 ngày


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

    public String getIdTokenFromExpiredJwtToken(String token) {
        try {
            Claims claims = Jwts.parserBuilder()
                    .setSigningKey(secretKey) // Secret key để ký token
                    .build()
                    .parseClaimsJws(token)     // Parse JWT nhưng bỏ qua expiration
                    .getBody();
            return claims.getId(); // Trả về ID từ token

        } catch (ExpiredJwtException expiredException) {
            // Token hết hạn nhưng vẫn giải mã được phần Claims
            System.out.println("Token hết hạn, nhưng vẫn lấy được dữ liệu");
            return expiredException.getClaims().getId(); // Lấy thông tin từ phần Claims của token đã hết hạn

        } catch (SignatureException e) {
            // Token không hợp lệ về mặt chữ ký
            System.out.println("Token không hợp lệ: " + e.getMessage());
            return null;
        }
    }

    public Date getExpirationTimeFromExpiredJwtToken(String token) {
        try {
            Claims claims = Jwts.parserBuilder()
                    .setSigningKey(secretKey) // Secret key để ký token
                    .build()
                    .parseClaimsJws(token)     // Parse JWT nhưng bỏ qua expiration
                    .getBody();

            return claims.getExpiration();

        } catch (ExpiredJwtException expiredException) {
            // Token hết hạn nhưng vẫn giải mã được phần Claims
            System.out.println("Token hết hạn, nhưng vẫn lấy được dữ liệu");
            return expiredException.getClaims().getExpiration(); // Lấy thông tin từ phần Claims của token đã hết hạn

        } catch (SignatureException e) {
            // Token không hợp lệ về mặt chữ ký
            System.out.println("Token không hợp lệ: " + e.getMessage());
            return null;
        }
    }

    public Date getExpirationTimeTokenFromJwtToken(String token) {
        return Jwts.parser().setSigningKey(secretKey).parseClaimsJws(token).getBody().getExpiration();
    }

    public boolean validateJwtToken(String token) {
        try {
            // Kiểm tra token có hợp lệ không
            String tokenId = getIdTokenFromJwtToken(token);
            if (invalidTokenService.existsByIdToken(tokenId)) {
                return false; // Token đã bị thu hồi
            }
            // Xác thực chữ ký
            Jwts.parser().setSigningKey(secretKey).parseClaimsJws(token);
            return true;
        } catch (SignatureException e) {
//            System.out.println("Invalid JWT signature: " + e.getMessage());
        } catch (MalformedJwtException e) {
//            System.out.println("Invalid JWT token: " + e.getMessage());
        } catch (ExpiredJwtException e) {
//            System.out.println("JWT token is expired: " + e.getMessage());
        } catch (UnsupportedJwtException e) {
//            System.out.println("JWT token is unsupported: " + e.getMessage());
        } catch (IllegalArgumentException e) {
//            System.out.println("JWT claims string is empty: " + e.getMessage());
        }
        return false;
    }
}
