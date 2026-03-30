package es.bsager.AcademicTracker.modules.schedule.service;

import es.bsager.AcademicTracker.modules.schedule.dto.request.ScheduleRegisterRequest;
import es.bsager.AcademicTracker.modules.schedule.dto.response.ScheduleDetailsResponse;
import es.bsager.AcademicTracker.modules.schedule.dto.response.ScheduleRegisterResponse;
import es.bsager.AcademicTracker.modules.schedule.dto.response.SchedulesSummaryResponse;

import java.util.List;
import java.util.Map;
import java.util.UUID;

public interface ScheduleService {
    ScheduleRegisterResponse registerSchedule(UUID subjectId, ScheduleRegisterRequest request);

    List<ScheduleDetailsResponse> getSchedulesBySubjectId(UUID subjectId);

    Map<String, List<SchedulesSummaryResponse>> getAllSchedules();

    ScheduleRegisterResponse updateSchedule(UUID subjectId, UUID scheduleId, ScheduleRegisterRequest request);

    void deleteSchedule(UUID subjectId, UUID scheduleId);
}
