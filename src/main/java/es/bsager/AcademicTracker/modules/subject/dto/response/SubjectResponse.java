package es.bsager.AcademicTracker.modules.subject.dto.response;

import java.time.Instant;
import java.util.UUID;

public record SubjectResponse(
        UUID id,
        String name,
        String code,
        int credits,
        String semester,
        String teacherName,
        String status,
        Instant createAt
) {
}
