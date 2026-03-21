package es.bsager.AcademicTracker.modules.auth.service.impl;

import es.bsager.AcademicTracker.modules.auth.dto.request.LoginRequest;
import es.bsager.AcademicTracker.modules.auth.dto.request.RegisterRequest;
import es.bsager.AcademicTracker.modules.auth.dto.response.AuthResponse;
import es.bsager.AcademicTracker.modules.auth.entity.UserEntity;
import es.bsager.AcademicTracker.modules.auth.enums.Role;
import es.bsager.AcademicTracker.modules.auth.repository.UserRepository;
import es.bsager.AcademicTracker.modules.auth.service.AuthService;
import es.bsager.AcademicTracker.shared.exception.BusinessException;
import es.bsager.AcademicTracker.shared.security.JwtUtil;
import lombok.RequiredArgsConstructor;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class AuthServiceImpl implements AuthService {

    private final UserRepository userRepository;
    private final PasswordEncoder passwordEncoder;
    private final AuthenticationManager authenticationManager;
    private final JwtUtil jwtUtil;

    @Override
    public AuthResponse login(LoginRequest request) {
        // Delega la autenticación a Spring Security
        // Lanza AuthenticationException si las credenciales son incorrectas
        authenticationManager.authenticate(
                new UsernamePasswordAuthenticationToken(
                        request.username(),
                        request.password()
                )
        );

        UserEntity user = userRepository.findByUsername(request.username());
        String token = jwtUtil.generateToken(user.getUsername());

        return toResponse(user, token);
    }

    @Override
    public AuthResponse register(RegisterRequest request) {
        if (userRepository.findByUsername(request.username()) != null) {
            throw new BusinessException("El nombre de usuario ya está en uso");
        }

        UserEntity user = UserEntity.builder()
                .username(request.username())
                .password(passwordEncoder.encode(request.password()))
                .role(Role.STUDENT)
                .build();

        userRepository.save(user);
        String token = jwtUtil.generateToken(user.getUsername());

        return toResponse(user, token);
    }

    private AuthResponse toResponse(UserEntity user, String token) {
        return new AuthResponse(
                user.getId(),
                user.getUsername(),
                user.getRole().name(),
                token
        );
    }
}
