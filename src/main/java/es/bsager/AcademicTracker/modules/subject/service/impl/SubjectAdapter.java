package es.bsager.AcademicTracker.modules.subject.service.impl;

import es.bsager.AcademicTracker.modules.subject.repository.SubjectRepository;
import es.bsager.AcademicTracker.shared.util.port.SubjectPort;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Component;
import org.springframework.transaction.annotation.Transactional;

import java.util.UUID;

@Component
@RequiredArgsConstructor
public class SubjectAdapter implements SubjectPort {
    private final SubjectRepository subjectRepository;

    @Override
    @Transactional(readOnly = true)
    public Boolean existsById(UUID subjectId) {
        return subjectRepository.existsById(subjectId);
    }
}
