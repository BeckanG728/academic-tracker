package es.bsager.AcademicTracker.modules.grades.controller;

import es.bsager.AcademicTracker.modules.grades.dto.request.RegisterGradesRequest;
import es.bsager.AcademicTracker.modules.grades.dto.request.UpdateGradeRequest;
import es.bsager.AcademicTracker.modules.grades.dto.response.GradeDetailsResponse;
import es.bsager.AcademicTracker.modules.grades.dto.response.RegisterGradesResponse;
import es.bsager.AcademicTracker.modules.grades.enums.GradeType;
import es.bsager.AcademicTracker.modules.grades.service.GradesService;
import es.bsager.AcademicTracker.shared.response.ApiResponse;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;
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

    @GetMapping("/grades")
    public ResponseEntity<ApiResponse<List<GradeDetailsResponse>>> getGrades(
            @PathVariable UUID subjectId,
            @RequestParam(required = false) GradeType type
    ) {
        List<GradeDetailsResponse> response = gradesService.getGradesBySubject(subjectId, type);
        return ResponseEntity.ok(ApiResponse.success(response));
    }

    @GetMapping("/grades/{gradeId}")
    public ResponseEntity<ApiResponse<GradeDetailsResponse>> getGrade(
            @PathVariable UUID subjectId,
            @PathVariable UUID gradeId
    ) {
        GradeDetailsResponse response = gradesService.getGrade(subjectId, gradeId);
        return ResponseEntity.ok(ApiResponse.success(response));
    }

    @PutMapping("/grades/{gradeId}")
    public ResponseEntity<ApiResponse<GradeDetailsResponse>> updateGrade(
            @PathVariable UUID subjectId,
            @PathVariable UUID gradeId,
            @Valid @RequestBody UpdateGradeRequest request
    ) {
        GradeDetailsResponse response = gradesService.updateGrade(subjectId, gradeId, request);
        return ResponseEntity.ok(ApiResponse.success(response));
    }
}
