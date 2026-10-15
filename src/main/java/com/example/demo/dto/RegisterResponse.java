package com.example.demo.dto;

import java.time.Instant;

public record RegisterResponse(
        Long id,
        String name,
        String mobile,
        String role,
        Instant createdAt
) {
}