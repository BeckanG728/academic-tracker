package es.bsager.AcademicTracker.modules.schedule.mapper;

import es.bsager.AcademicTracker.modules.schedule.dto.request.ScheduleRegisterRequest;
import es.bsager.AcademicTracker.modules.schedule.dto.response.ScheduleDetailsResponse;
import es.bsager.AcademicTracker.modules.schedule.dto.response.ScheduleRegisterResponse;
import es.bsager.AcademicTracker.modules.schedule.dto.response.SchedulesSummaryResponse;
import es.bsager.AcademicTracker.modules.schedule.entity.Schedule;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;

import java.util.UUID;

@Mapper(componentModel = "spring")
public interface ScheduleMapper {

    @Mapping(target = "id", ignore = true)
    @Mapping(target = "subjectId", source = "subjectId")
    @Mapping(target = "createAt", ignore = true)
    @Mapping(target = "updateAt", ignore = true)
    Schedule toEntity(ScheduleRegisterRequest request, UUID subjectId);

    @Mapping(target = "subjectName", source = "subjectName")
    ScheduleRegisterResponse toResponse(Schedule schedule, String subjectName);

    ScheduleDetailsResponse toDetailsResponse(Schedule schedule);

    SchedulesSummaryResponse toSummaryResponse(Schedule schedule, String subjectName);
}
