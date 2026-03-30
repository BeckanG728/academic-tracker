package es.bsager.AcademicTracker.modules.schedule.controller;

import es.bsager.AcademicTracker.modules.schedule.dto.request.ScheduleRegisterRequest;
import es.bsager.AcademicTracker.modules.schedule.dto.response.ScheduleDetailsResponse;
import es.bsager.AcademicTracker.modules.schedule.dto.response.ScheduleRegisterResponse;
import es.bsager.AcademicTracker.modules.schedule.dto.response.SchedulesSummaryResponse;
import es.bsager.AcademicTracker.modules.schedule.service.ScheduleService;
import es.bsager.AcademicTracker.shared.response.ApiResponse;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Map;
import java.util.UUID;

@RestController
@RequiredArgsConstructor
public class ScheduleController {

    private final ScheduleService scheduleService;

    @PostMapping("/subjects/{subjectId}/schedules")
    public ResponseEntity<ApiResponse<ScheduleRegisterResponse>> registerSchedule(
            @PathVariable UUID subjectId,
            @RequestBody @Valid ScheduleRegisterRequest request
    ) {
        ScheduleRegisterResponse response = scheduleService.registerSchedule(subjectId, request);
        return new ResponseEntity<>(ApiResponse.success(response), HttpStatus.CREATED);
    }

    @GetMapping("/subjects/{subjectId}/schedules")
    public ResponseEntity<ApiResponse<List<ScheduleDetailsResponse>>> listSchedules(
            @PathVariable UUID subjectId
    ) {
        List<ScheduleDetailsResponse> response = scheduleService.getSchedulesBySubjectId(subjectId);
        return ResponseEntity.ok(ApiResponse.success(response));
    }

    @GetMapping("/schedules")
    public ResponseEntity<ApiResponse<Map<String, List<SchedulesSummaryResponse>>>> getWeeklySchedule() {
        Map<String, List<SchedulesSummaryResponse>> data = scheduleService.getAllSchedules();
        return ResponseEntity.ok(ApiResponse.success(data));
    }

    @PutMapping("/subjects/{subjectId}/schedules/{scheduleId}")
    public ResponseEntity<ApiResponse<ScheduleRegisterResponse>> updateSchedule(
            @PathVariable UUID subjectId,
            @PathVariable UUID scheduleId,
            @RequestBody @Valid ScheduleRegisterRequest request
    ) {
        ScheduleRegisterResponse data = scheduleService.updateSchedule(subjectId, scheduleId, request);
        return ResponseEntity.ok(ApiResponse.success(data));
    }

    @DeleteMapping("/subjects/{subjectId}/schedules/{scheduleId}")
    public ResponseEntity<Void> deleteSchedule(
            @PathVariable UUID subjectId,
            @PathVariable UUID scheduleId
    ) {
        scheduleService.deleteSchedule(subjectId, scheduleId);
        return ResponseEntity.noContent().build();
    }
}
