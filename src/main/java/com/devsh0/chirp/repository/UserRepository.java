package com.devsh0.chirp.repository;

import com.devsh0.chirp.entity.User;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;

import java.util.Optional;

public interface UserRepository extends JpaRepository<User, Long> {
    @Query("select user from User user where user.email=:email")
    Optional<User> findUserByEmail(String email);
    @Query("select user from User user where user.username=:username")
    Optional<User> findUserByUsername(String username);

    @Query("select user from User user where user.email=:emailOrUsername or user.username=:emailOrUsername")
    Optional<User> findByEmailOrUsername(String emailOrUsername);

    @Modifying(clearAutomatically = true)
    @Query("update User u set u.active=1 where u.id=:userId")
    void activateUser(Long userId);

    @Modifying
    @Query("update User u set u.password=:password where u.id=:userId")
    void resetPassword(Long userId, String password);
}
