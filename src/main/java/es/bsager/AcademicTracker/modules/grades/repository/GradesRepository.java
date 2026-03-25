package es.bsager.AcademicTracker.modules.grades.repository;

import es.bsager.AcademicTracker.modules.grades.entity.Grades;
import es.bsager.AcademicTracker.modules.grades.enums.GradeType;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.UUID;

public interface GradesRepository extends JpaRepository<Grades, UUID> {
    boolean existsBySubjectIdAndType(UUID subjectId, GradeType type);

    int countBySubjectIdAndTypeIn(UUID subjectId, Iterable<GradeType> types);
}
