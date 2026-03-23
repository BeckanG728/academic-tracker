package es.bsager.AcademicTracker.modules.subject.controller;

import es.bsager.AcademicTracker.modules.subject.dto.request.SubjectRequest;
import es.bsager.AcademicTracker.modules.subject.dto.response.SubjectResponse;
import es.bsager.AcademicTracker.modules.subject.service.SubjectService;
import es.bsager.AcademicTracker.shared.response.ApiResponse;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/subjects")
@RequiredArgsConstructor
public class SubjectController {

    private final SubjectService subjectService;

    @PostMapping
    public ResponseEntity<ApiResponse<SubjectResponse>> createSubject(@RequestBody SubjectRequest request) {
        ApiResponse<SubjectResponse> response = ApiResponse.success(subjectService.create(request));
        return new ResponseEntity<>(response, HttpStatus.CREATED);
    }
}
