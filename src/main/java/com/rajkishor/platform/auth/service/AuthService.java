package com.rajkishor.platform.auth.service;

import com.rajkishor.platform.auth.dto.*;
import com.rajkishor.platform.auth.entity.RefreshToken;
import com.rajkishor.platform.auth.repository.RefreshTokenRepository;
import com.rajkishor.platform.common.exception.BadRequestException;
import com.rajkishor.platform.common.exception.NotFoundException;
import com.rajkishor.platform.common.exception.UnauthorizedException;
import com.rajkishor.platform.common.security.JwtUtil;
import com.rajkishor.platform.user.entity.User;
import com.rajkishor.platform.user.repository.RoleRepository;
import com.rajkishor.platform.user.repository.UserRepository;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.time.LocalDateTime;
import java.util.Set;
import java.util.UUID;

@Slf4j
@Service
@RequiredArgsConstructor
public class AuthService {

    private final UserRepository userRepo;
    private final RoleRepository roleRepo;
    private final PasswordEncoder encoder;
    private final JwtUtil jwtUtil;
    private final RefreshTokenRepository refreshTokenRepository;

    @Value("${security.jwt.refresh-token-expiry-days}")
    private long refreshExpiryDays;

    /* =========================
       REGISTER
       ========================= */
    @Transactional
    public void register(RegisterRequest request) {

        if (userRepo.findByEmail(request.email()).isPresent()) {
            throw new BadRequestException("Email already registered");
        }

        var roleUser = roleRepo.findByName("USER")
                .orElseThrow(() ->
                        new NotFoundException("Role USER not found"));

        User user = new User();
        user.setEmail(request.email());
        user.setPassword(encoder.encode(request.password()));
        user.setRoles(Set.of(roleUser));

        userRepo.save(user);

        log.info("User registered successfully: {}", request.email());
    }

    /* =========================
       LOGIN
       ========================= */
    @Transactional
    public AuthResponse login(LoginRequest request) {

        User user = userRepo.findByEmail(request.email())
                .orElseThrow(() ->
                        new UnauthorizedException("Invalid email or password"));

        if (!encoder.matches(request.password(), user.getPassword())) {
            throw new UnauthorizedException("Invalid email or password");
        }

        String accessToken = jwtUtil.generateToken(user.getEmail());
        RefreshToken refreshToken = createRefreshToken(user);

        log.info("User logged in: {}", request.email());

        return new AuthResponse(
                accessToken,
                refreshToken.getToken()
        );
    }

    /* =========================
       REFRESH TOKEN
       ========================= */
    @Transactional
    public AuthResponse refreshToken(RefreshTokenRequest request) {

        RefreshToken refreshToken = refreshTokenRepository
                .findByToken(request.refreshToken())
                .orElseThrow(() ->
                        new UnauthorizedException("Invalid refresh token"));

        if (refreshToken.getExpiryDate().isBefore(LocalDateTime.now())) {
            refreshTokenRepository.delete(refreshToken);
            throw new UnauthorizedException("Refresh token expired");
        }

        String newAccessToken =
                jwtUtil.generateToken(refreshToken.getUser().getEmail());

        return new AuthResponse(
                newAccessToken,
                refreshToken.getToken()
        );
    }

    /* =========================
       LOGOUT
       ========================= */
    @Transactional
    public void logout(RefreshTokenRequest request) {

        refreshTokenRepository.findByToken(request.refreshToken())
                .ifPresent(refreshTokenRepository::delete);
    }

    /* =========================
       INTERNAL
       ========================= */
    private RefreshToken createRefreshToken(User user) {

        RefreshToken rt = new RefreshToken();
        rt.setUser(user);
        rt.setToken(UUID.randomUUID().toString());
        rt.setExpiryDate(
                LocalDateTime.now().plusDays(refreshExpiryDays)
        );

        return refreshTokenRepository.save(rt);
    }
}
