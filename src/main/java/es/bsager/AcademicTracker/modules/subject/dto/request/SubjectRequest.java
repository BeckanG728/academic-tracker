package es.bsager.AcademicTracker.modules.subject.dto.request;

public record SubjectRequest(
        String name,
        String code,
        int credits,
        String semester,
        String teacherName
) {
}
