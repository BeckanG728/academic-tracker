package es.bsager.AcademicTracker.modules.auth.service;

import es.bsager.AcademicTracker.modules.auth.dto.request.LoginRequest;
import es.bsager.AcademicTracker.modules.auth.dto.request.RegisterRequest;
import es.bsager.AcademicTracker.modules.auth.dto.response.AuthResponse;

public interface AuthService {
    AuthResponse login(LoginRequest request);

    AuthResponse register(RegisterRequest request);
}
