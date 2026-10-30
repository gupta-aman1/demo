package com.example.demo.service;
import com.example.demo.entity.RefreshToken;
import com.example.demo.entity.User;
import com.example.demo.repository.RefreshTokenRepository;
import com.example.demo.security.RefreshTokenRotationResult;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.server.ResponseStatusException;

import java.nio.charset.StandardCharsets;
import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;
import java.security.SecureRandom;
import java.time.Instant;
import java.util.Base64;

@Service
public class RefreshTokenService {

    private final RefreshTokenRepository refreshTokenRepository;
    private final SecureRandom secureRandom = new SecureRandom();
    private final long refreshTokenExpiration;

    public RefreshTokenService(
            RefreshTokenRepository refreshTokenRepository,
            @Value("${security.jwt.refresh-token-expiration:604800}")
            long refreshTokenExpiration) {
        this.refreshTokenRepository = refreshTokenRepository;
        this.refreshTokenExpiration = refreshTokenExpiration;
    }

    @Transactional
    public String createRefreshToken(User user) {
        String rawToken = generateRawToken();

        RefreshToken token = new RefreshToken();
        token.setUser(user);
        token.setTokenHash(hashToken(rawToken));
        token.setExpiresAt(
                Instant.now().plusSeconds(refreshTokenExpiration)
        );

        refreshTokenRepository.save(token);

        return rawToken;
    }

    @Transactional
    public RefreshTokenRotationResult rotateRefreshToken(
            String rawRefreshToken) {

        System.out.println("STEP 1: Refresh token received");

        String tokenHash = hashToken(rawRefreshToken);

        System.out.println("STEP 2: Token hash generated");

        RefreshToken oldToken = refreshTokenRepository
                .findByTokenHashForUpdate(tokenHash)
                .orElseThrow(this::invalidRefreshToken);

        System.out.println("STEP 3: Token found in database");

        System.out.println("STEP 4: Revoked = " + oldToken.isRevoked());
        System.out.println("STEP 5: Expiry = " + oldToken.getExpiresAt());
        System.out.println("STEP 6: User enabled = " + oldToken.getUser().isEnabled());


        if (oldToken.isRevoked()
                || !oldToken.getExpiresAt().isAfter(Instant.now())
                || !oldToken.getUser().isEnabled()) {
            throw invalidRefreshToken();
        }

        User user = oldToken.getUser();

        // Purana refresh token revoke karo
        oldToken.setRevoked(true);
        refreshTokenRepository.save(oldToken);

        // Naya refresh token generate aur store karo
        String newRawToken = generateRawToken();

        RefreshToken newToken = new RefreshToken();
        newToken.setUser(user);
        newToken.setTokenHash(hashToken(newRawToken));
        newToken.setExpiresAt(
                Instant.now().plusSeconds(refreshTokenExpiration)
        );

        refreshTokenRepository.save(newToken);

        return new RefreshTokenRotationResult(user, newRawToken);
    }

    private String generateRawToken() {
        byte[] randomBytes = new byte[32];
        secureRandom.nextBytes(randomBytes);

        return Base64.getUrlEncoder()
                .withoutPadding()
                .encodeToString(randomBytes);
    }

    public String hashToken(String rawToken) {
        try {
            MessageDigest digest =
                    MessageDigest.getInstance("SHA-256");

            byte[] hash = digest.digest(
                    rawToken.getBytes(StandardCharsets.UTF_8)
            );

            return Base64.getEncoder().encodeToString(hash);

        } catch (NoSuchAlgorithmException ex) {
            throw new IllegalStateException(
                    "SHA-256 is not available", ex
            );
        }
    }

    private ResponseStatusException invalidRefreshToken() {
        return new ResponseStatusException(
                HttpStatus.UNAUTHORIZED,
                "Invalid or expired refresh token"
        );
    }
}
