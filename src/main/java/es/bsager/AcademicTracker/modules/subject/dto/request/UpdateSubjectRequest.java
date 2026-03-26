package es.bsager.AcademicTracker.modules.subject.dto.request;

public record UpdateSubjectRequest(
        String name,
        int credits,
        String teacherName,
        String semester
) {
}
