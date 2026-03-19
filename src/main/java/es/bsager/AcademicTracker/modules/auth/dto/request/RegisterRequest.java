package es.bsager.AcademicTracker.modules.auth.dto.request;

public record RegisterRequest(
        String name,
        String lastName,
        String user,
        String password
) {
}
