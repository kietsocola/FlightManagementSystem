package com.project.flightManagement.Service;

import com.project.flightManagement.DTO.InvalidToken.InvalidTokenDTO;
import com.project.flightManagement.DTO.RefreshTokenDTO.RefreshTokenDTO;

public interface RefreshTokenService {
    void saveRefreshTokenIntoDatabase(RefreshTokenDTO refreshTokenDTO);
    boolean isTokenActive(String idRefreshToken);
    boolean deactivateToken(String idRefreshToken);
}
