package com.example.demo.dto;

import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.Pattern;
import jakarta.validation.constraints.Size;

public record LoginRequest(

        @NotBlank(message = "Mobile number is required")
        @Pattern(
                regexp = "^[0-9]{10}$",
                message = "Mobile number must contain exactly 10 digits"
        )
        String mobile,

        @NotBlank(message = "Password is required")
        @Size(max = 72, message = "Password is too long")
        String password

) {
}
