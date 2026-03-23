package es.bsager.AcademicTracker.modules.subject.controller;

import es.bsager.AcademicTracker.modules.subject.dto.request.CreateSubjectRequest;
import es.bsager.AcademicTracker.modules.subject.dto.response.CreateSubjectResponse;
import es.bsager.AcademicTracker.modules.subject.dto.response.SubjectResponse;
import es.bsager.AcademicTracker.modules.subject.enums.SubjectStatus;
import es.bsager.AcademicTracker.modules.subject.service.SubjectService;
import es.bsager.AcademicTracker.shared.response.ApiResponse;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/subjects")
@RequiredArgsConstructor
public class SubjectController {

    private final SubjectService subjectService;

    @PostMapping
    public ResponseEntity<ApiResponse<CreateSubjectResponse>> createSubject(@RequestBody CreateSubjectRequest request) {
        ApiResponse<CreateSubjectResponse> response = ApiResponse.success(subjectService.create(request));
        return new ResponseEntity<>(response, HttpStatus.CREATED);
    }

    @GetMapping
    public ResponseEntity<ApiResponse<List<SubjectResponse>>> getSubject(
            @RequestParam(required = false) SubjectStatus status,
            @RequestParam(required = false) String semester
    ) {
        List<SubjectResponse> data = subjectService.findAllSubjects(status, semester);
        return ResponseEntity.ok(ApiResponse.success(data));
    }
}
