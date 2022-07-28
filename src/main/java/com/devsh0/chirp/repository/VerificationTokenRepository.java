package com.devsh0.chirp.repository;

import com.devsh0.chirp.entity.VerificationToken;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

@Repository
public interface VerificationTokenRepository extends JpaRepository<VerificationToken, Long> {
    @Query("select token from VerificationToken token where token.token=:token")
    VerificationToken findByToken(String token);
}
