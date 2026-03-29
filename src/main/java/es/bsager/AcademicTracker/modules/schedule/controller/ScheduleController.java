package es.bsager.AcademicTracker.modules.schedule.controller;

import es.bsager.AcademicTracker.modules.schedule.dto.request.ScheduleRegisterRequest;
import es.bsager.AcademicTracker.modules.schedule.dto.response.ScheduleRegisterResponse;
import es.bsager.AcademicTracker.modules.schedule.service.ScheduleService;
import es.bsager.AcademicTracker.shared.response.ApiResponse;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.UUID;

@RestController
@RequestMapping("/subjects/{subjectId}")
@RequiredArgsConstructor
public class ScheduleController {

    private final ScheduleService scheduleService;

    @PostMapping("/schedules")
    public ResponseEntity<ApiResponse<ScheduleRegisterResponse>> registerSchedule(
            @PathVariable UUID subjectId,
            @RequestBody @Valid ScheduleRegisterRequest request
    ) {
        ScheduleRegisterResponse response = scheduleService.registerSchedule(subjectId, request);
        return new ResponseEntity<>(ApiResponse.success(response), HttpStatus.CREATED);
    }

}
