package es.bsager.AcademicTracker.modules.auth.dto.response;

import lombok.Builder;

import java.util.UUID;

@Builder
public record AuthResponse(
        String token,
        String tokenType,   // siempre "Bearer"
        long expiresIn,     // segundos
        UUID userId,
        String username
) {
}