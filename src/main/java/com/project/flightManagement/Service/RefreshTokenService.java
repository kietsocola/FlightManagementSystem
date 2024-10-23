package com.project.flightManagement.Service;

import com.project.flightManagement.DTO.RefreshTokenDTO.RefreshTokenDTO;

public interface RefreshTokenService {
    boolean saveRefreshTokenIntoDatabase(RefreshTokenDTO refreshTokenDTO);
    boolean isTokenActive(String idRefreshToken);
    boolean deactivateToken(String idRefreshToken);
}
