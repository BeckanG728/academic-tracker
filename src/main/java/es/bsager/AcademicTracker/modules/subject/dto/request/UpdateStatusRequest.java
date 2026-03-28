package es.bsager.AcademicTracker.modules.subject.dto.request;

import es.bsager.AcademicTracker.modules.subject.enums.SubjectStatus;
import jakarta.validation.constraints.NotNull;

public record UpdateStatusRequest(
        @NotNull(message = "El estado es obligatorio")
        SubjectStatus status
) {
}
