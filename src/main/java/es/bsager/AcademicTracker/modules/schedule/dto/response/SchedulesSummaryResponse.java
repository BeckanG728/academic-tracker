package es.bsager.AcademicTracker.modules.schedule.dto.response;

import java.time.LocalTime;

public record SchedulesSummaryResponse(
        LocalTime startTime,
        LocalTime endTime,
        String classroom,
        String subjectName
) {
}
