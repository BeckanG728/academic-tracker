package es.bsager.AcademicTracker.modules.subject.dto.request;

import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.Pattern;
import jakarta.validation.constraints.Positive;

public record UpdateSubjectRequest(
        @NotBlank(message = "El nombre no puede estar vacío")
        String name,

        @Positive(message = "Los créditos deben ser mayor a 0")
        int credits,

        @NotBlank(message = "El nombre del docente no puede estar vacio")
        String teacherName,

        @NotBlank(message = "El semestre no puede estar vacio")
        @Pattern(regexp = "^(20\\d{2})-[12]$", message = "Formato de semestre incorrecto")
        String semester
) {
}
