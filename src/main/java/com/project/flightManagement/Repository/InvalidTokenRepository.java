package com.project.flightManagement.Repository;

import com.project.flightManagement.Model.InvalidToken;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.Date;
import java.util.Optional;
@Repository
public interface InvalidTokenRepository extends JpaRepository<InvalidToken, String> {
    boolean existsByIdToken(String idToken);
    void deleteByExpiryDateBefore(Date date);
}
