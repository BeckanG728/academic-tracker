package es.bsager.AcademicTracker.modules.grades.service.impl;

import es.bsager.AcademicTracker.modules.grades.dto.request.RegisterGradesRequest;
import es.bsager.AcademicTracker.modules.grades.dto.request.UpdateGradeRequest;
import es.bsager.AcademicTracker.modules.grades.dto.response.GradeDetailsResponse;
import es.bsager.AcademicTracker.modules.grades.dto.response.RegisterGradesResponse;
import es.bsager.AcademicTracker.modules.grades.entity.Grades;
import es.bsager.AcademicTracker.modules.grades.enums.GradeType;
import es.bsager.AcademicTracker.modules.grades.mapper.GradesMapper;
import es.bsager.AcademicTracker.modules.grades.repository.GradesRepository;
import es.bsager.AcademicTracker.modules.grades.service.GradesService;
import es.bsager.AcademicTracker.shared.exception.ResourceNotFoundException;
import lombok.RequiredArgsConstructor;
import org.springframework.data.jpa.domain.Specification;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.UUID;

@Service
@RequiredArgsConstructor
public class GradesServiceImpl implements GradesService {

    private final GradesRepository gradesRepository;
    private final GradesMapper gradesMapper;

    @Override
    @Transactional
    public RegisterGradesResponse registerGrades(RegisterGradesRequest request, UUID subjectId) {

        if (gradesRepository.existsBySubjectIdAndType(subjectId, request.type())) {
            throw new IllegalArgumentException("Ya existe una calificación de tipo " + request.type() + " para esta materia");
        }

        if (request.type().equals(GradeType.FINAL)) {
            if (gradesRepository.countBySubjectIdAndTypeIn(subjectId, GradeType.getModules()) < 4) {
                throw new IllegalArgumentException("No se puede registrar la nota final sin antes registrar las 4 notas modulares");
            }
        }

        Grades gradesToSave = gradesMapper.toEntity(request, subjectId);
        Grades save = gradesRepository.save(gradesToSave);

        return gradesMapper.toResponse(save);
    }

    @Override
    @Transactional(readOnly = true)
    public List<GradeDetailsResponse> getGradesBySubject(UUID subjectId, GradeType type) {
        Specification<Grades> spec = (root, query, criteriaBuilder) ->
                criteriaBuilder.equal(root.get("subjectId"), subjectId);

        if (type != null) {
            spec = spec.and(
                    (root, query, cb) -> cb.equal(root.get("type"), type)
            );
        }

        List<Grades> grades = gradesRepository.findAll(spec);
        return grades.stream().map(gradesMapper::toDetailsResponse).toList();
    }

    @Override
    @Transactional(readOnly = true)
    public GradeDetailsResponse getGrade(UUID subjectId, UUID gradeId) {
        Specification<Grades> spec = (root, query, criteriaBuilder) ->
                criteriaBuilder.equal(root.get("subjectId"), subjectId);
        spec = spec.and((root, query, cb) -> cb.equal(root.get("id"), gradeId));

        Grades grades = gradesRepository.findOne(spec).orElseThrow(() ->
                new ResourceNotFoundException("No se encontró la nota con ID: " + gradeId)
        );
        return gradesMapper.toDetailsResponse(grades);
    }

    @Override
    @Transactional
    public GradeDetailsResponse updateGrade(UUID subjectId, UUID gradeId, UpdateGradeRequest request) {
        Specification<Grades> spec = (root, query, criteriaBuilder) ->
                criteriaBuilder.equal(root.get("subjectId"), subjectId);
        spec = spec.and((root, query, cb) -> cb.equal(root.get("id"), gradeId));

        Grades grades = gradesRepository.findOne(spec).orElseThrow(() ->
                new ResourceNotFoundException("No se encontró la nota con ID: " + gradeId)
        );

        grades.setValue(request.value());
        grades.setNotes(request.notes());

        Grades gradesUpdated = gradesRepository.save(grades);
        return gradesMapper.toDetailsResponse(gradesUpdated);
    }

    @Override
    @Transactional
    public void deleteGrade(UUID subjectId, UUID gradeId) {
        Specification<Grades> spec = (root, query, criteriaBuilder) ->
                criteriaBuilder.equal(root.get("subjectId"), subjectId);

        spec = spec.and((root, query, cb) -> cb.equal(root.get("id"), gradeId));

        Grades grades = gradesRepository.findOne(spec).orElseThrow(() ->
                new ResourceNotFoundException("No se encontró la nota con ID: " + gradeId)
        );

        gradesRepository.delete(grades);
    }
}
