package com.devsh0.chirp.repository;

import com.devsh0.chirp.entity.User;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.CrudRepository;

import java.util.Optional;

public interface UserRepository extends CrudRepository<User, Long> {
    @Query("select user from User user where user.email=:email")
    Optional<User> findUserByEmail(String email);
    @Query("select user from User user where user.username=:username")
    Optional<User> findUserByUsername(String username);
}
