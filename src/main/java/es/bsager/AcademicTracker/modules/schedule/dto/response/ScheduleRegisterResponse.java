package es.bsager.AcademicTracker.modules.schedule.dto.response;

import java.time.DayOfWeek;
import java.time.LocalTime;
import java.util.UUID;

public record ScheduleRegisterResponse(
        UUID id,
        DayOfWeek dayOfWeek,
        LocalTime startTime,
        LocalTime endTime,
        String classroom,
        UUID subjectId,
        String subjectName
) {
}
