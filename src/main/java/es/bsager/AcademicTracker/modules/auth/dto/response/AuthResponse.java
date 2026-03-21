package es.bsager.AcademicTracker.modules.auth.dto.response;

import java.util.UUID;

public record AuthResponse(
        UUID id,
        String username,
        String role,
        String token
) {
}