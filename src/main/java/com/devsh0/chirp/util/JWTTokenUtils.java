package com.devsh0.chirp.util;

import com.auth0.jwt.JWT;
import com.auth0.jwt.algorithms.Algorithm;
import com.auth0.jwt.exceptions.JWTVerificationException;
import com.auth0.jwt.interfaces.DecodedJWT;
import com.devsh0.chirp.entity.User;
import com.devsh0.chirp.exception.AuthenticationFailedException;
import org.springframework.stereotype.Component;

import java.util.Base64;
import java.util.Calendar;
import java.util.HashMap;

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

    public boolean isTokenExpired(DecodedJWT decodedJWT) {
        var payloadJson = new String(Base64.getDecoder().decode(decodedJWT.getPayload()));
        var payload = (HashMap) Utils.fromJson(payloadJson, HashMap.class);
        var expiryInt = (Integer) payload.get("exp");
        var expiry = expiryInt.longValue() * 1000;
        return System.currentTimeMillis() > expiry;
    }

    /**
     * @throws JWTVerificationException If token is not valid
     */
    public void verifyToken(String token) {
        try {
            var decodedJWT = JWT.decode(token);
            if (isTokenExpired(decodedJWT))
                throw new AuthenticationFailedException("token expired!");
            JWT_ALGORITHM.verify(decodedJWT);
        } catch (JWTVerificationException exc) {
            throw new AuthenticationFailedException("authentication failed!");
        }
    }

    public static JWTTokenUtils the() {
        return INSTANCE;
    }
}
