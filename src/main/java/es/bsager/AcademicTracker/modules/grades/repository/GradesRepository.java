package es.bsager.AcademicTracker.modules.grades.repository;

import es.bsager.AcademicTracker.modules.grades.entity.Grades;
import es.bsager.AcademicTracker.modules.grades.enums.GradeType;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import java.math.BigDecimal;
import java.util.UUID;

public interface GradesRepository extends JpaRepository<Grades, UUID> {
    boolean existsBySubjectIdAndType(UUID subjectId, GradeType type);

    int countBySubjectIdAndTypeIn(UUID subjectId, Iterable<GradeType> types);

    @Query("SELECT COALESCE(SUM(g.value), 0) FROM Grades g WHERE g.subjectId = :subjectId AND g.type IN :types")
    BigDecimal sumValuesBySubjectIdAndTypeIn(
            @Param("subjectId") UUID subjectId,
            @Param("types") Iterable<GradeType> types);
}
