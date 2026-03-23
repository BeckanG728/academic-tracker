package es.bsager.AcademicTracker.modules.subject.service.impl;

import es.bsager.AcademicTracker.modules.subject.dto.request.SubjectRequest;
import es.bsager.AcademicTracker.modules.subject.dto.response.SubjectResponse;
import es.bsager.AcademicTracker.modules.subject.entity.Subject;
import es.bsager.AcademicTracker.modules.subject.mapper.SubjectMapper;
import es.bsager.AcademicTracker.modules.subject.repository.SubjectRepository;
import es.bsager.AcademicTracker.modules.subject.service.SubjectService;
import es.bsager.AcademicTracker.shared.exception.SubjectNotFoundException;
import es.bsager.AcademicTracker.shared.security.AuthenticatedUserProvider;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
@RequiredArgsConstructor
public class SubjectServiceImpl implements SubjectService {
    private final SubjectRepository subjectRepository;
    private final SubjectMapper subjectMapper;
    private final AuthenticatedUserProvider authenticatedUserProvider;


    @Override
    @Transactional
    public SubjectResponse create(SubjectRequest request) {
        if (subjectRepository.existsByCode(request.code())) {
            throw new SubjectNotFoundException(
                    "Ya existe una asignatura con el código: " + request.code()
            );
        }

        Subject subjectToSave = subjectMapper.toEntity(request, authenticatedUserProvider.getCurrentUserId());
        Subject saved = subjectRepository.save(subjectToSave);

        return subjectMapper.toResponse(saved);
    }
}
