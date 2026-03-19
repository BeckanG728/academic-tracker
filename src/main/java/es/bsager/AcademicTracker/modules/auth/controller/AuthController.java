package es.bsager.AcademicTracker.modules.auth.controller;

import es.bsager.AcademicTracker.modules.auth.dto.response.AuthResponse;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/api/auth")
public class AuthController {

    @PostMapping("/login")
    public ResponseEntity<AuthResponse> login() {
        return null;
    }

    @PostMapping("/register")
    public ResponseEntity<AuthResponse> register() {
        return null;
    }

}
