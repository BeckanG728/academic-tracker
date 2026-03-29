package es.bsager.AcademicTracker.shared.util.port;

import java.util.UUID;

public interface SubjectPort {
    Boolean existsById(UUID subjectId);
}
