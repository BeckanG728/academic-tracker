package es.bsager.AcademicTracker.modules.subject.repository;

import es.bsager.AcademicTracker.modules.subject.entity.Subject;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.UUID;

public interface SubjectRepository extends JpaRepository<Subject, UUID> {
    boolean existsByCode(String code);
}
