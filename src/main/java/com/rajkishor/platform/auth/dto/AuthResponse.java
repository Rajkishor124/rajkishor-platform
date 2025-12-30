package com.rajkishor.platform.auth.dto;

public record AuthResponse(
        String accessToken,
        String refreshToken
) {}
