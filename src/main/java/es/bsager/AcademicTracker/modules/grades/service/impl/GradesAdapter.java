package es.bsager.AcademicTracker.modules.grades.service.impl;

import es.bsager.AcademicTracker.modules.grades.enums.GradeType;
import es.bsager.AcademicTracker.modules.grades.repository.GradesRepository;
import es.bsager.AcademicTracker.shared.util.port.GradesPort;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Component;

import java.math.BigDecimal;
import java.math.RoundingMode;
import java.util.UUID;

@Component
@RequiredArgsConstructor
public class GradesAdapter implements GradesPort {

    private final GradesRepository gradesRepository;

    @Override
    public BigDecimal calculateModuleAverage(UUID subjectId) {
        if (gradesRepository.countBySubjectIdAndTypeIn(subjectId, GradeType.getModules()) == 0) {
            return null;
        }
        BigDecimal sum = gradesRepository.sumValuesBySubjectIdAndTypeIn(subjectId, GradeType.getModules());
        return sum.divide(BigDecimal.valueOf(4), 2, RoundingMode.HALF_UP);
    }
}
