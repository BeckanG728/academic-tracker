package es.bsager.AcademicTracker.modules.auth.controller;

import es.bsager.AcademicTracker.modules.auth.dto.request.LoginRequest;
import es.bsager.AcademicTracker.modules.auth.dto.request.RegisterRequest;
import es.bsager.AcademicTracker.modules.auth.dto.response.AuthResponse;
import es.bsager.AcademicTracker.modules.auth.dto.response.RegisterResponse;
import es.bsager.AcademicTracker.modules.auth.service.AuthService;
import es.bsager.AcademicTracker.shared.response.ApiResponse;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/auth")
@RequiredArgsConstructor
public class AuthController {

    private final AuthService authService;

    @PostMapping("/register")
    public ResponseEntity<ApiResponse<RegisterResponse>> register(@RequestBody @Valid RegisterRequest request) {
        ApiResponse<RegisterResponse> response = ApiResponse.success(authService.register(request));
        return new ResponseEntity<>(response, HttpStatus.CREATED);
    }

    @PostMapping("/login")
    public ResponseEntity<ApiResponse<AuthResponse>> login(@RequestBody @Valid LoginRequest request) {
        ApiResponse<AuthResponse> response = ApiResponse.success(authService.login(request));
        return ResponseEntity.ok(response);
    }
}
