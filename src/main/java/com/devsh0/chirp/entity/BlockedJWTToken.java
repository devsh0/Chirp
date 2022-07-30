package com.devsh0.chirp.entity;

import org.hibernate.annotations.Type;

import javax.persistence.*;

@Entity
@Table(name = "blocked_jwt_token")
public class BlockedJWTToken {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long Id;

    @Column(nullable = false)
    @Type(type = "org.hibernate.type.StringType")
    private String token;

    public static BlockedJWTToken from(String token) {
        var blockedToken = new BlockedJWTToken();
        blockedToken.token = token;
        return blockedToken;
    }
}
