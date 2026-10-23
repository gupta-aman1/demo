package com.example.demo.security;

import com.example.demo.entity.User;

public record RefreshTokenRotationResult(
        User user,
        String rawRefreshToken
) {}