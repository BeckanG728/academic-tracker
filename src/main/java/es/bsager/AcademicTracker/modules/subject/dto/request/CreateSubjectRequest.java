package es.bsager.AcademicTracker.modules.subject.dto.request;

public record CreateSubjectRequest(
        String name,
        String code,
        int credits,
        String semester,
        String teacherName
) {
}
