package es.bsager.AcademicTracker.modules.grades.service;

import es.bsager.AcademicTracker.modules.grades.dto.request.RegisterGradesRequest;
import es.bsager.AcademicTracker.modules.grades.dto.response.RegisterGradesResponse;

import java.util.UUID;

public interface GradesService {
    RegisterGradesResponse registerGrades(RegisterGradesRequest request, UUID subjectId);
}
