package es.bsager.AcademicTracker.modules.grades.service;

import es.bsager.AcademicTracker.modules.grades.dto.request.RegisterGradesRequest;
import es.bsager.AcademicTracker.modules.grades.dto.response.GradeDetailsResponse;
import es.bsager.AcademicTracker.modules.grades.dto.response.RegisterGradesResponse;
import es.bsager.AcademicTracker.modules.grades.enums.GradeType;

import java.util.List;
import java.util.UUID;

public interface GradesService {
    RegisterGradesResponse registerGrades(RegisterGradesRequest request, UUID subjectId);

    List<GradeDetailsResponse> getGradesBySubject(UUID subjectId, GradeType type);

    GradeDetailsResponse getGrade(UUID subjectId, UUID gradeId);
}
