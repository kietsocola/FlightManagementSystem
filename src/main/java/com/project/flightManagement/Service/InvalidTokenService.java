package com.project.flightManagement.Service;

import com.project.flightManagement.DTO.InvalidToken.InvalidTokenDTO;
import com.project.flightManagement.Model.InvalidToken;

public interface InvalidTokenService {
    boolean existsByIdToken(String idToken);
    void saveInvalidTokenIntoDatabase(InvalidTokenDTO invalidTokenDTO);
}
