package es.bsager.AcademicTracker.modules.grades.dto.response;

import es.bsager.AcademicTracker.modules.grades.enums.GradeType;

import java.math.BigDecimal;
import java.util.UUID;

public record GradeDetailsResponse(
        UUID id,
        BigDecimal value,
        GradeType type,
        String notes
) {
}
