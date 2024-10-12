package com.project.flightManagement.Service.Impl;

import com.project.flightManagement.DTO.RefreshTokenDTO.RefreshTokenDTO;
import com.project.flightManagement.Enum.ActiveEnum;
import com.project.flightManagement.Mapper.RefreshTokenMapper;
import com.project.flightManagement.Model.RefreshToken;
import com.project.flightManagement.Repository.RefreshTokenRepository;
import com.project.flightManagement.Service.RefreshTokenService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.Optional;

@Service
public class RefreshTokenServiceImpl implements RefreshTokenService {
    @Autowired
    private RefreshTokenMapper refreshTokenMapper;

    @Autowired
    private RefreshTokenRepository refreshTokenRepository;

    @Override
    public void saveRefreshTokenIntoDatabase(RefreshTokenDTO refreshTokenDTO) {
        RefreshToken refreshToken = refreshTokenMapper.toRefreshToken(refreshTokenDTO);
        refreshTokenRepository.save(refreshToken);
    }

    @Override

    public boolean isTokenActive(String idRefreshToken) {
        Optional<RefreshToken> tokenOpt = refreshTokenRepository.findByIdRefreshTokenAndTrangThaiActive(idRefreshToken, ActiveEnum.ACTIVE);
        return tokenOpt.isPresent();
    }

    @Override
    public boolean deactivateToken(String idRefreshToken) {
        Optional<RefreshToken> tokenOpt = refreshTokenRepository.findByIdRefreshTokenAndTrangThaiActive(idRefreshToken, ActiveEnum.ACTIVE);
        if (tokenOpt.isPresent()) {
            RefreshToken refreshToken = tokenOpt.get();
            refreshToken.setTrangThaiActive(ActiveEnum.IN_ACTIVE);
            refreshTokenRepository.save(refreshToken);
            return true;
        }
        return false;
    }
}
