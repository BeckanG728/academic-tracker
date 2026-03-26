package es.bsager.AcademicTracker.modules.subject.service.impl;

import es.bsager.AcademicTracker.modules.subject.dto.request.CreateSubjectRequest;
import es.bsager.AcademicTracker.modules.subject.dto.request.UpdateStatusRequest;
import es.bsager.AcademicTracker.modules.subject.dto.request.UpdateSubjectRequest;
import es.bsager.AcademicTracker.modules.subject.dto.response.CreateSubjectResponse;
import es.bsager.AcademicTracker.modules.subject.dto.response.SubjectResponse;
import es.bsager.AcademicTracker.modules.subject.dto.response.SubjectSummaryResponse;
import es.bsager.AcademicTracker.modules.subject.entity.Subject;
import es.bsager.AcademicTracker.modules.subject.enums.SubjectStatus;
import es.bsager.AcademicTracker.modules.subject.mapper.SubjectMapper;
import es.bsager.AcademicTracker.modules.subject.repository.SubjectRepository;
import es.bsager.AcademicTracker.modules.subject.service.SubjectService;
import es.bsager.AcademicTracker.shared.exception.ResourceHasDependenciesException;
import es.bsager.AcademicTracker.shared.exception.SubjectNotFoundException;
import es.bsager.AcademicTracker.shared.security.AuthenticatedUserProvider;
import es.bsager.AcademicTracker.shared.util.port.GradesPort;
import lombok.RequiredArgsConstructor;
import org.springframework.data.jpa.domain.Specification;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.math.BigDecimal;
import java.util.List;
import java.util.UUID;

@Service
@RequiredArgsConstructor
public class SubjectServiceImpl implements SubjectService {
    private final SubjectRepository subjectRepository;
    private final SubjectMapper subjectMapper;
    private final AuthenticatedUserProvider authenticatedUserProvider;
    private final GradesPort gradesPort;


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

    @Override
    @Transactional(readOnly = true)
    public SubjectSummaryResponse getSummary(UUID subjectId) {
        Subject subject = subjectRepository.findById(subjectId)
                .orElseThrow(() -> new SubjectNotFoundException("Asignatura no encontrada"));

        BigDecimal average = gradesPort.calculateModuleAverage(subjectId);
        return subjectMapper.toSubjectSummaryResponse(subject, average);
    }

    @Override
    @Transactional
    public SubjectResponse updateSubject(UUID subjectId, UpdateSubjectRequest request) {

        Subject subjectToUpdate = subjectRepository.findById(subjectId)
                .orElseThrow(() -> new SubjectNotFoundException("Asignatura no encontrada"));

        subjectToUpdate.setName(request.name());
        subjectToUpdate.setCredits(request.credits());
        subjectToUpdate.setSemester(request.semester());
        subjectToUpdate.setTeacherName(request.teacherName());
        Subject updated = subjectRepository.save(subjectToUpdate);

        return subjectMapper.toSubjectResponse(updated);
    }

    @Override
    @Transactional
    public SubjectResponse updateSubjectStatus(UUID subjectId, UpdateStatusRequest request) {
        Subject subjectToUpdate = subjectRepository.findById(subjectId)
                .orElseThrow(() -> new SubjectNotFoundException("Asignatura no encontrada"));

        subjectToUpdate.setStatus(request.status());
        Subject updated = subjectRepository.save(subjectToUpdate);
        return subjectMapper.toSubjectResponse(updated);
    }

    @Override
    @Transactional
    public void deleteSubject(UUID subjectId) {
        if (gradesPort.existsBySubjectId(subjectId)) {
            throw new ResourceHasDependenciesException(
                    "Eliminacion denegada porque la asignatura tiene notas asociadas"
            );
        }
        subjectRepository.deleteById(subjectId);
    }
}
