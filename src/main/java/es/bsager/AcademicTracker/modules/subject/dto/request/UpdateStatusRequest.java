package es.bsager.AcademicTracker.modules.subject.dto.request;

import es.bsager.AcademicTracker.modules.subject.enums.SubjectStatus;

public record UpdateStatusRequest(
        SubjectStatus status
) {
}
