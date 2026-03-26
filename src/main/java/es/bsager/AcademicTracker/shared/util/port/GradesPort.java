package es.bsager.AcademicTracker.shared.util.port;

import java.math.BigDecimal;
import java.util.UUID;

public interface GradesPort {
    BigDecimal calculateModuleAverage(UUID subjectId);
}
