package es.bsager.AcademicTracker.modules.grades.dto.request;

import jakarta.validation.constraints.DecimalMax;
import jakarta.validation.constraints.DecimalMin;
import jakarta.validation.constraints.NotNull;

import java.math.BigDecimal;

public record UpdateGradeRequest(
        @DecimalMax(value = "20.0")
        @DecimalMin(value = "0.0")
        BigDecimal value,
        @NotNull(message = "El campo debe incluirse")
        String notes
) {
}
