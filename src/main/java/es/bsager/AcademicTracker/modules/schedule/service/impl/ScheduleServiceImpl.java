package es.bsager.AcademicTracker.modules.schedule.service.impl;

import es.bsager.AcademicTracker.modules.schedule.dto.request.ScheduleRegisterRequest;
import es.bsager.AcademicTracker.modules.schedule.dto.response.ScheduleRegisterResponse;
import es.bsager.AcademicTracker.modules.schedule.entity.Schedule;
import es.bsager.AcademicTracker.modules.schedule.mapper.ScheduleMapper;
import es.bsager.AcademicTracker.modules.schedule.repository.ScheduleRepository;
import es.bsager.AcademicTracker.modules.schedule.service.ScheduleService;
import es.bsager.AcademicTracker.shared.util.port.SubjectPort;
import lombok.RequiredArgsConstructor;
import org.springframework.data.jpa.domain.Specification;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.UUID;

@Service
@RequiredArgsConstructor
public class ScheduleServiceImpl implements ScheduleService {

    private final ScheduleRepository scheduleRepository;
    private final SubjectPort subjectPort;
    private final ScheduleMapper scheduleMapper;

    @Override
    @Transactional
    public ScheduleRegisterResponse registerSchedule(UUID subjectId, ScheduleRegisterRequest request) {

        if (!request.startTime().isBefore(request.endTime())) {
            throw new IllegalArgumentException("La hora de inicio debe ser anterior a la hora de finalización");
        }

        validationOverlap(request);

        String subjectName = subjectPort.getSubjectName(subjectId);

        if (subjectName.isBlank()) {
            throw new IllegalArgumentException("La asignatura no existe");
        }

        Schedule scheduleToSave = scheduleMapper.toEntity(request, subjectId);
        Schedule savedSchedule = scheduleRepository.save(scheduleToSave);
        return scheduleMapper.toResponse(savedSchedule, subjectName);
    }

    private void validationOverlap(ScheduleRegisterRequest request) {
        Specification<Schedule> spec = (root, query, cb) ->
                cb.equal(root.get("dayOfWeek"), request.dayOfWeek());
        spec = spec.and(
                (root, query, cb) -> cb.lessThan(root.get("startTime"), request.endTime())
        );
        spec = spec.and(
                (root, query, cb) -> cb.greaterThan(root.get("endTime"), request.startTime())
        );
        boolean hasOverlap = scheduleRepository.count(spec) > 0;
        if (hasOverlap) {
            throw new IllegalArgumentException(
                    String.format("Ya existe un horario para %s de %s a %s",
                            request.dayOfWeek(),
                            request.startTime(),
                            request.endTime())
            );
        }
    }
}
