package es.bsager.AcademicTracker.modules.schedule.service.impl;

import es.bsager.AcademicTracker.modules.schedule.dto.request.ScheduleRegisterRequest;
import es.bsager.AcademicTracker.modules.schedule.dto.response.ScheduleDetailsResponse;
import es.bsager.AcademicTracker.modules.schedule.dto.response.ScheduleRegisterResponse;
import es.bsager.AcademicTracker.modules.schedule.dto.response.SchedulesSummaryResponse;
import es.bsager.AcademicTracker.modules.schedule.entity.Schedule;
import es.bsager.AcademicTracker.modules.schedule.mapper.ScheduleMapper;
import es.bsager.AcademicTracker.modules.schedule.repository.ScheduleRepository;
import es.bsager.AcademicTracker.modules.schedule.service.ScheduleService;
import es.bsager.AcademicTracker.shared.exception.ResourceNotFoundException;
import es.bsager.AcademicTracker.shared.util.port.SubjectPort;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.data.jpa.domain.Specification;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.Map;
import java.util.UUID;
import java.util.stream.Collectors;

@Service
@Slf4j
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

    @Override
    @Transactional(readOnly = true)
    public List<ScheduleDetailsResponse> getSchedulesBySubjectId(UUID subjectId) {
        if (!subjectPort.existsById(subjectId)) {
            throw new IllegalArgumentException("La asignatura con el ID establecido no existe");
        }

        Specification<Schedule> spec = (root, query, cb) -> cb.equal(root.get("subjectId"), subjectId);
        List<Schedule> schedules = scheduleRepository.findAll(spec);

        return schedules.stream()
                .map(scheduleMapper::toDetailsResponse)
                .toList();
    }

    @Override
    @Transactional(readOnly = true)
    public Map<String, List<SchedulesSummaryResponse>> getAllSchedules() {
        return scheduleRepository.findAll().stream()
                .collect(Collectors.groupingBy(
                        schedule -> schedule.getDayOfWeek().name(),
                        Collectors.mapping(schedule -> {
                            String subjectName = subjectPort.getSubjectName(schedule.getSubjectId());
                            return scheduleMapper.toSummaryResponse(schedule, subjectName);
                        }, Collectors.toList())
                ));
    }

    @Override
    @Transactional
    public ScheduleRegisterResponse updateSchedule(UUID subjectId, UUID scheduleId, ScheduleRegisterRequest request) {
        log.info("Inicia proceso de actualizacion");
        if (!request.startTime().isBefore(request.endTime())) {
            throw new IllegalArgumentException("La hora de inicio debe ser anterior a la hora de finalización");
        }

        log.info("Validacion hora inicio - fin pasada");
        validationOverlap(request);

        Schedule schedule = scheduleRepository.findById(scheduleId)
                .filter(subject -> subject.getSubjectId().equals(subjectId))
                .orElseThrow(() ->
                        new ResourceNotFoundException("Horario no encontrado para la asignatura especificada")
                );

        log.info("Se recupero el horario");
        schedule.setStartTime(request.startTime());
        schedule.setEndTime(request.endTime());
        schedule.setDayOfWeek(request.dayOfWeek());
        schedule.setClassroom(request.classroom());

        log.info("Se cambio los datos");

        Schedule updated = scheduleRepository.save(schedule);

        log.info("Se guardo el horario");
        String subjectName = subjectPort.getSubjectName(subjectId);
        log.info("Nombre de la materia: {}", subjectName);
        return scheduleMapper.toResponse(updated, subjectName);
    }
}
