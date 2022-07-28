package com.devsh0.chirp.repository;

import com.devsh0.chirp.entity.User;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

import java.util.Optional;

public interface UserRepository extends JpaRepository<User, Long> {
    @Query("select user from User user where user.email=:email")
    Optional<User> findUserByEmail(String email);
    @Query("select user from User user where user.username=:username")
    Optional<User> findUserByUsername(String username);
}