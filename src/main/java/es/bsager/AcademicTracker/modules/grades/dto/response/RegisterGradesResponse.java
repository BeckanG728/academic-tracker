package es.bsager.AcademicTracker.modules.grades.dto.response;

import es.bsager.AcademicTracker.modules.grades.enums.GradeType;

import java.math.BigDecimal;
import java.time.Instant;
import java.util.UUID;

public record RegisterGradesResponse(
        UUID id,
        BigDecimal value,
        GradeType type,
        String notes,
        UUID subjectId,
        Instant createdAt
) {
}
