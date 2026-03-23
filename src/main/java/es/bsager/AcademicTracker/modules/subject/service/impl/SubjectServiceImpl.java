package es.bsager.AcademicTracker.modules.subject.service.impl;

import es.bsager.AcademicTracker.modules.subject.dto.request.CreateSubjectRequest;
import es.bsager.AcademicTracker.modules.subject.dto.response.CreateSubjectResponse;
import es.bsager.AcademicTracker.modules.subject.dto.response.SubjectResponse;
import es.bsager.AcademicTracker.modules.subject.entity.Subject;
import es.bsager.AcademicTracker.modules.subject.enums.SubjectStatus;
import es.bsager.AcademicTracker.modules.subject.mapper.SubjectMapper;
import es.bsager.AcademicTracker.modules.subject.repository.SubjectRepository;
import es.bsager.AcademicTracker.modules.subject.service.SubjectService;
import es.bsager.AcademicTracker.shared.exception.SubjectNotFoundException;
import es.bsager.AcademicTracker.shared.security.AuthenticatedUserProvider;
import lombok.RequiredArgsConstructor;
import org.springframework.data.jpa.domain.Specification;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

@Service
@RequiredArgsConstructor
public class SubjectServiceImpl implements SubjectService {
    private final SubjectRepository subjectRepository;
    private final SubjectMapper subjectMapper;
    private final AuthenticatedUserProvider authenticatedUserProvider;


    @Override
    @Transactional
    public CreateSubjectResponse create(CreateSubjectRequest request) {
        if (subjectRepository.existsByCode(request.code())) {
            throw new SubjectNotFoundException(
                    "Ya existe una asignatura con el código: " + request.code()
            );
        }

        Subject subjectToSave = subjectMapper.toEntity(request, authenticatedUserProvider.getCurrentUserId());
        Subject saved = subjectRepository.save(subjectToSave);

        return subjectMapper.toResponse(saved);
    }

    @Override
    @Transactional(readOnly = true)
    public List<SubjectResponse> findAllSubjects(SubjectStatus status, String semester) {
        Specification<Subject> spec = (root, query, cb) -> null;

        if (status != null) {
            spec = spec.and((root, query, cb) ->
                    cb.equal(root.get("status"), status)
            );
        }

        if (semester != null && !semester.isBlank()) {
            spec = spec.and((root, query, cb) ->
                    cb.equal(root.get("semester"), semester)
            );
        }

        List<Subject> subjects = subjectRepository.findAll(spec);

        return subjects.stream()
                .map(subjectMapper::toSubjectResponse)
                .toList();
    }
}
