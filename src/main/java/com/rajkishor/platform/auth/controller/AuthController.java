package com.rajkishor.platform.auth.controller;

import com.rajkishor.platform.auth.dto.*;
import com.rajkishor.platform.auth.service.AuthService;
import com.rajkishor.platform.common.response.ApiResponse;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/api/v1/auth")
@RequiredArgsConstructor
public class AuthController {

    private final AuthService service;

    @PostMapping("/register")
    public ApiResponse<String> register(
            @RequestBody @Valid RegisterRequest request
    ) {
        service.register(request);
        return ApiResponse.success("User registered successfully", null);
    }

    @PostMapping("/login")
    public ApiResponse<AuthResponse> login(
            @RequestBody @Valid LoginRequest request
    ) {
        return ApiResponse.success(
                "Login successful",
                service.login(request)
        );
    }

    @PostMapping("/refresh")
    public ApiResponse<AuthResponse> refresh(
            @RequestBody @Valid RefreshTokenRequest request
    ) {
        return ApiResponse.success(
                "Token refreshed",
                service.refreshToken(request)
        );
    }

    @PostMapping("/logout")
    public ApiResponse<String> logout(
            @RequestBody @Valid RefreshTokenRequest request
    ) {
        service.logout(request);
        return ApiResponse.success("Logged out successfully", null);
    }
}
