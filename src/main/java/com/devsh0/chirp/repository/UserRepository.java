package com.devsh0.chirp.repository;

import com.devsh0.chirp.entity.User;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import javax.persistence.Table;

@Repository
@Table(name = "user")
public interface UserRepository extends JpaRepository<User, Long> {

}
