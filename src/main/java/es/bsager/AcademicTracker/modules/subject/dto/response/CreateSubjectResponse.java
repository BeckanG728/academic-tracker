package es.bsager.AcademicTracker.modules.subject.dto.response;

import es.bsager.AcademicTracker.modules.subject.enums.SubjectStatus;

import java.time.Instant;
import java.util.UUID;

public record CreateSubjectResponse(
        UUID id,
        String name,
        String code,
        int credits,
        String semester,
        String teacherName,
        SubjectStatus status,
        Instant createAt
) {
}
