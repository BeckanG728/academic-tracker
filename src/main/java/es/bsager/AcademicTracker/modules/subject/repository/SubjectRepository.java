package es.bsager.AcademicTracker.modules.subject.repository;

import es.bsager.AcademicTracker.modules.subject.entity.Subject;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.JpaSpecificationExecutor;

import java.util.UUID;

public interface SubjectRepository extends JpaRepository<Subject, UUID>, JpaSpecificationExecutor<Subject> {
    boolean existsByCode(String code);
}
