package es.bsager.AcademicTracker.modules.grades.service.impl;

import es.bsager.AcademicTracker.modules.grades.dto.request.RegisterGradesRequest;
import es.bsager.AcademicTracker.modules.grades.dto.response.GradeDetailsResponse;
import es.bsager.AcademicTracker.modules.grades.dto.response.RegisterGradesResponse;
import es.bsager.AcademicTracker.modules.grades.entity.Grades;
import es.bsager.AcademicTracker.modules.grades.enums.GradeType;
import es.bsager.AcademicTracker.modules.grades.mapper.GradesMapper;
import es.bsager.AcademicTracker.modules.grades.repository.GradesRepository;
import es.bsager.AcademicTracker.modules.grades.service.GradesService;
import jakarta.transaction.Transactional;
import lombok.RequiredArgsConstructor;
import org.springframework.data.jpa.domain.Specification;
import org.springframework.stereotype.Service;

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
}
