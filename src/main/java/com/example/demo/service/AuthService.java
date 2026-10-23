package com.example.demo.service;

import com.example.demo.dto.*;
import com.example.demo.entity.Role;
import com.example.demo.entity.User;
import com.example.demo.repository.UserRepository;
import com.example.demo.security.RefreshTokenRotationResult;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.http.HttpStatus;
import org.springframework.web.server.ResponseStatusException;
import com.example.demo.security.JwtService;

@Service
public class AuthService {

    private final UserRepository userRepository;
    private final PasswordEncoder passwordEncoder;

    private final JwtService jwtService;
    private final RefreshTokenService refreshTokenService;

    public AuthService(
            UserRepository userRepository,
            PasswordEncoder passwordEncoder,
            JwtService jwtService,
            RefreshTokenService refreshTokenService
    ) {
        this.userRepository = userRepository;
        this.passwordEncoder = passwordEncoder;
        this.jwtService = jwtService;
        this.refreshTokenService = refreshTokenService;
    }

    @Transactional
    public RegisterResponse register(RegisterRequest request) {

        if (userRepository.existsByMobile(request.getMobile())) {
            throw new ResponseStatusException(
                    HttpStatus.CONFLICT,
                    "Mobile number is already registered"
            );
        }

        User user = new User();

        user.setName(request.getName());
        user.setMobile(request.getMobile());

        user.setPassword(
                passwordEncoder.encode(request.getPassword())
        );

        user.setRole(Role.USER);
        user.setEnabled(true);

        User savedUser = userRepository.save(user);

        return new RegisterResponse(
                savedUser.getId(),
                savedUser.getName(),
                savedUser.getMobile(),
                savedUser.getRole().name(),
                savedUser.getCreatedAt()
        );
    }


    @Transactional
    public LoginResponse login(LoginRequest request) {

        User user = userRepository.findByMobile(request.mobile())
                .orElseThrow(() -> new ResponseStatusException(
                        HttpStatus.UNAUTHORIZED,
                        "Invalid mobile number or password"
                ));

        if (!passwordEncoder.matches(request.password(), user.getPassword()))
        {
            throw new ResponseStatusException(
                    HttpStatus.UNAUTHORIZED,
                    "Invalid mobile number or password"
            );
        }

        if (!user.isEnabled()) {
            throw new ResponseStatusException(
                    HttpStatus.UNAUTHORIZED,
                    "Invalid mobile number or password"
            );
        }

        String accessToken = jwtService.generateAccessToken(user);

        String refreshToken = refreshTokenService.createRefreshToken(user);

        return new LoginResponse(
                accessToken,
                refreshToken,
                "Bearer"
        );
    }


    @Transactional
    public LoginResponse refresh(RefreshTokenRequest request) {

        RefreshTokenRotationResult result =
                refreshTokenService.rotateRefreshToken(
                        request.refreshToken()
                );

        String accessToken =
                jwtService.generateAccessToken(result.user());

        return new LoginResponse(
                accessToken,
                result.rawRefreshToken(),
                "Bearer"
        );
    }
}