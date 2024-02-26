package com.enigma.tokonyadia.security;

import com.auth0.jwt.JWT;
import com.auth0.jwt.JWTVerifier;
import com.auth0.jwt.algorithms.Algorithm;
import com.auth0.jwt.exceptions.JWTCreationException;
import com.auth0.jwt.exceptions.JWTVerificationException;
import com.auth0.jwt.interfaces.DecodedJWT;
import com.enigma.tokonyadia.dto.response.JwtClaim;
import com.enigma.tokonyadia.entity.UserCredential;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Component;

import java.time.Instant;
import java.util.List;

@Component
@Slf4j
public class JwtUtils {

    @Value("${app.tokonyadia.jwt-secret}")
    private String secretKey;

    @Value("${app.tokonyadia.jwt-expiration}")
    private String expirationInSecond;

    @Value("${app.tokonyadia.jwt-app-name}")
    private String appName;


    public String generatedToken(UserCredential userCredential) {

        List<String> roles = userCredential.getRoles().stream().map(role -> role.getEroll().name()).toList();
        try {
            return JWT
                    .create()
                    .withIssuer(appName)
                    .withSubject(userCredential.getId())
                    .withExpiresAt(Instant.now().plusSeconds(Long.parseLong(expirationInSecond)))
                    .withClaim("roles", roles)
                    .sign(Algorithm.HMAC512(secretKey));
        } catch (JWTCreationException creationException) {
            log.error("Error while creating token awt : {}", creationException.getMessage());
            throw new RuntimeException(creationException);
        }
    }

    public boolean verifierToken(String token) {
        try {
            Algorithm algorithm = Algorithm.HMAC512(secretKey);
            JWTVerifier verifier = JWT.require(algorithm).build();
            DecodedJWT decodedJWT = verifier.verify(token);
            return decodedJWT.getIssuer().equals(appName);
        } catch (JWTVerificationException verificationException) {
            log.error("Invalid verification JWT:{}", verificationException.getMessage());
            return false;
        }
    }

    public JwtClaim getUserInfoByToken(String token) {
        try {
            Algorithm algorithm = Algorithm.HMAC512(secretKey);
            JWTVerifier verifier = JWT.require(algorithm).build();
            DecodedJWT decodedJWT = verifier.verify(token);

            List<String> roles = decodedJWT.getClaim("roles").asList(String.class);
            return JwtClaim.builder()
                    .userId(decodedJWT.getSubject())
                    .roles(roles)
                    .build();

        } catch (JWTVerificationException verificationException) {
            log.error("Invalid verification JWT:{}", verificationException.getMessage());
            return null;
        }
    }
}
