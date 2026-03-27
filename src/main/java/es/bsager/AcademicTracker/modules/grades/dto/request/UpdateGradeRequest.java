package es.bsager.AcademicTracker.modules.grades.dto.request;

import jakarta.validation.constraints.DecimalMax;
import jakarta.validation.constraints.DecimalMin;

import java.math.BigDecimal;

public record UpdateGradeRequest(
        @DecimalMax(value = "20.0")
        @DecimalMin(value = "0.0")
        BigDecimal value,
        String notes
) {
}
