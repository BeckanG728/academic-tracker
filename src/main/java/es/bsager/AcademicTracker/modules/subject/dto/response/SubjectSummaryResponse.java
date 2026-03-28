package es.bsager.AcademicTracker.modules.subject.dto.response;

import com.fasterxml.jackson.annotation.JsonInclude;
import es.bsager.AcademicTracker.modules.subject.enums.SubjectStatus;

import java.math.BigDecimal;
import java.time.Instant;
import java.util.UUID;

public record SubjectSummaryResponse(
        UUID id,
        String name,
        @JsonInclude(JsonInclude.Include.ALWAYS)
        BigDecimal currentAverage,
        SubjectStatus status,
        Instant createAt
) {
}
