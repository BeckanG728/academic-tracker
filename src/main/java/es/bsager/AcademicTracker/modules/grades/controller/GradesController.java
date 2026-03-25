package es.bsager.AcademicTracker.modules.grades.controller;

import es.bsager.AcademicTracker.modules.grades.dto.request.RegisterGradesRequest;
import es.bsager.AcademicTracker.modules.grades.dto.response.RegisterGradesResponse;
import es.bsager.AcademicTracker.modules.grades.service.GradesService;
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
public class GradesController {

    private final GradesService gradesService;

    @PostMapping("/grades")
    public ResponseEntity<ApiResponse<RegisterGradesResponse>> registerGrade(
            @Valid @RequestBody RegisterGradesRequest request,
            @PathVariable UUID subjectId
    ) {
        RegisterGradesResponse response = gradesService.registerGrades(request, subjectId);
        return new ResponseEntity<>(ApiResponse.success(response), HttpStatus.CREATED);
    }
}
