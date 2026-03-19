package es.bsager.AcademicTracker.modules.auth.dto.request;

public record LoginRequest(
        String user,
        String password
) {
}
