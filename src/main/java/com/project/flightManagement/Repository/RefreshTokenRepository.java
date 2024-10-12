package com.project.flightManagement.Repository;

import com.project.flightManagement.Enum.ActiveEnum;
import com.project.flightManagement.Model.RefreshToken;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.Optional;

@Repository
public interface RefreshTokenRepository extends JpaRepository<RefreshToken, String> {
    // Tìm RefreshToken bởi id và trạng thái ACTIVE
    Optional<RefreshToken> findByIdRefreshTokenAndTrangThaiActive(String idRefreshToken, ActiveEnum trangThaiActive);
}
