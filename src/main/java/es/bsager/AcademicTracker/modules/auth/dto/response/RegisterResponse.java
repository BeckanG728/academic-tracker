package es.bsager.AcademicTracker.modules.auth.dto.response;

import lombok.Builder;

import java.time.Instant;
import java.util.UUID;

@Builder
public record RegisterResponse(
        UUID id,
        String username,
        String displayName,
        String role,
        String status,
        Instant createdAt
) {
}
