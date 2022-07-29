package com.devsh0.chirp.configure;

import com.auth0.jwt.JWT;
import com.auth0.jwt.algorithms.Algorithm;
import com.auth0.jwt.exceptions.JWTVerificationException;
import com.devsh0.chirp.entity.User;
import com.devsh0.chirp.util.ApplicationProperties;
import org.springframework.stereotype.Component;

import java.util.Calendar;

@Component
public class JWTTokenUtils {
    private static final String SECRET = ApplicationProperties.get("security.jwt.secret");
    private static final int VALIDITY_PERIOD_DAYS = 30;
    private static final Algorithm JWT_ALGORITHM = Algorithm.HMAC256(SECRET);
    private static final JWTTokenUtils INSTANCE = new JWTTokenUtils();

    private JWTTokenUtils() {

    }

    public String generateToken(User user) {
        var now = Calendar.getInstance();
        var expiry = Calendar.getInstance();
        expiry.setTime(now.getTime());
        expiry.add(Calendar.DATE, VALIDITY_PERIOD_DAYS);
        return JWT.create()
                .withSubject(user.getUsername())
                .withIssuedAt(now.getTime())
                .withExpiresAt(expiry.getTime())
                .sign(JWT_ALGORITHM);
    }

    /**
     * @throws JWTVerificationException If token is not valid
     */
    public void verifyToken(String token) {

        var verifier = JWT.require(JWT_ALGORITHM).build();
        verifier.verify(token);
    }

    public static JWTTokenUtils the() {
        return INSTANCE;
    }
}
