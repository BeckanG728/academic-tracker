package es.bsager.AcademicTracker.modules.subject.service;

import es.bsager.AcademicTracker.modules.subject.dto.request.CreateSubjectRequest;
import es.bsager.AcademicTracker.modules.subject.dto.response.CreateSubjectResponse;
import es.bsager.AcademicTracker.modules.subject.dto.response.SubjectResponse;
import es.bsager.AcademicTracker.modules.subject.enums.SubjectStatus;

import java.util.List;

public interface SubjectService {

    CreateSubjectResponse create(CreateSubjectRequest request);

    List<SubjectResponse> findAllSubjects(SubjectStatus status, String semester);

}
