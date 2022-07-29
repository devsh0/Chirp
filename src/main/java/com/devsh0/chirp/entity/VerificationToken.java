package com.devsh0.chirp.entity;

import lombok.Getter;
import lombok.Setter;

import javax.persistence.*;
import java.util.Calendar;
import java.util.Date;
import java.util.UUID;

@Entity
@Getter
@Setter
public class VerificationToken {
    private static final int VALIDITY_PERIOD_HRS = 24;

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(name = "user_id")
    private Long userId;

    private String token;
    private Date expiry;

    public boolean hasExpired() {
        var now = Calendar.getInstance();
        var expiry = Calendar.getInstance();
        expiry.setTime(this.expiry);
        return now.after(expiry);
    }

    public static VerificationToken generateToken(User user) {
        var newToken = new VerificationToken();
        newToken.userId = user.getId();
        newToken.token = UUID.randomUUID().toString();
        newToken.expiry =  computeExpiry();
        return newToken;
    }

    private static Date computeExpiry() {
        var calendar = Calendar.getInstance();
        calendar.add(Calendar.HOUR, VALIDITY_PERIOD_HRS);
        return calendar.getTime();
    }
}
