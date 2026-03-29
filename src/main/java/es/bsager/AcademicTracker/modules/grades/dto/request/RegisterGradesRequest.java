package es.bsager.AcademicTracker.modules.grades.dto.request;

import es.bsager.AcademicTracker.modules.grades.enums.GradeType;
import jakarta.validation.constraints.DecimalMax;
import jakarta.validation.constraints.DecimalMin;
import jakarta.validation.constraints.NotNull;

import java.math.BigDecimal;

public record RegisterGradesRequest(
        @DecimalMax(value = "20.0")
        @DecimalMin(value = "0.0")
        BigDecimal value,

        @NotNull(message = "El tipo de nota es obligatorio")
        GradeType type,
        String notes
) {
}
