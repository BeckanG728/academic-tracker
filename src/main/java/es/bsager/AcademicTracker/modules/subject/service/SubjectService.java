package es.bsager.AcademicTracker.modules.subject.service;

import es.bsager.AcademicTracker.modules.subject.dto.request.SubjectRequest;
import es.bsager.AcademicTracker.modules.subject.dto.response.SubjectResponse;

public interface SubjectService {

    SubjectResponse create(SubjectRequest request);

}
