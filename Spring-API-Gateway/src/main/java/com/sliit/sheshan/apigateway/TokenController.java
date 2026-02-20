package com.sliit.sheshan.apigateway;

import java.nio.charset.StandardCharsets;
import java.time.Instant;
import java.util.Date;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.server.ResponseStatusException;

import io.jsonwebtoken.Jwts;
import io.jsonwebtoken.security.Keys;

@RestController
public class TokenController {

    private final byte[] secretBytes;
    private final long expirationSeconds;

    public TokenController(
            @Value("${jwt.secret}") String secret,
            @Value("${jwt.expiration-seconds:3600}") long expirationSeconds) {
        this.secretBytes = secret.getBytes(StandardCharsets.UTF_8);
        this.expirationSeconds = expirationSeconds;
    }

    @PostMapping("/token")
    public TokenResponse issueToken(@RequestBody TokenRequest request) {
        if (request == null || isBlank(request.username()) || isBlank(request.email())) {
            throw new ResponseStatusException(HttpStatus.BAD_REQUEST, "username and email are required");
        }

        Instant now = Instant.now();
        Instant expiresAt = now.plusSeconds(expirationSeconds);

        String token = Jwts.builder()
                .subject(request.username())
                .claim("email", request.email())
                .issuedAt(Date.from(now))
                .expiration(Date.from(expiresAt))
                .signWith(Keys.hmacShaKeyFor(secretBytes))
                .compact();

        return new TokenResponse(token, "Bearer", expiresAt.toString());
    }

    private static boolean isBlank(String value) {
        return value == null || value.trim().isEmpty();
    }

    public record TokenRequest(String username, String email) {}

    public record TokenResponse(String token, String tokenType, String expiresAt) {}
}
