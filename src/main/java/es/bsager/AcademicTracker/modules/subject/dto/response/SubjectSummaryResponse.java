package es.bsager.AcademicTracker.modules.subject.dto.response;

import es.bsager.AcademicTracker.modules.subject.enums.SubjectStatus;

import java.math.BigDecimal;
import java.time.Instant;
import java.util.UUID;

public record SubjectSummaryResponse(
        UUID id,
        String name,
        BigDecimal currentAverage,
        SubjectStatus status,
        Instant createAt
) {
}
