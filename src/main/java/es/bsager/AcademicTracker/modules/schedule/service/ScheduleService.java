package es.bsager.AcademicTracker.modules.schedule.service;

import es.bsager.AcademicTracker.modules.schedule.dto.request.ScheduleRegisterRequest;
import es.bsager.AcademicTracker.modules.schedule.dto.response.ScheduleRegisterResponse;

import java.util.UUID;

public interface ScheduleService {
    ScheduleRegisterResponse registerSchedule(UUID subjectId, ScheduleRegisterRequest request);
}
