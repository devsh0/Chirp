package com.devsh0.chirp.repository;

import com.devsh0.chirp.entity.BlockedJWTToken;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import java.util.Optional;

@Repository
public interface JWTTokenBlocklistRepository extends JpaRepository<BlockedJWTToken, Long> {
    @Query("select bjt from BlockedJWTToken bjt where bjt.token=:token")
    Optional<BlockedJWTToken> findByToken(String token);
}
