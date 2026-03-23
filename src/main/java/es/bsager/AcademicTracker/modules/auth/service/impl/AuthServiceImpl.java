package es.bsager.AcademicTracker.modules.auth.service.impl;

import es.bsager.AcademicTracker.modules.auth.dto.request.LoginRequest;
import es.bsager.AcademicTracker.modules.auth.dto.request.RegisterRequest;
import es.bsager.AcademicTracker.modules.auth.dto.response.AuthResponse;
import es.bsager.AcademicTracker.modules.auth.dto.response.RegisterResponse;
import es.bsager.AcademicTracker.modules.auth.entity.UserEntity;
import es.bsager.AcademicTracker.modules.auth.mapper.UserMapper;
import es.bsager.AcademicTracker.modules.auth.repository.UserRepository;
import es.bsager.AcademicTracker.modules.auth.service.AuthService;
import es.bsager.AcademicTracker.shared.exception.InvalidCredentialsException;
import es.bsager.AcademicTracker.shared.exception.UserNotFoundException;
import es.bsager.AcademicTracker.shared.exception.UsernameAlreadyExistsException;
import es.bsager.AcademicTracker.shared.security.JwtUtil;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.AuthenticationException;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
@RequiredArgsConstructor
public class AuthServiceImpl implements AuthService {

    private final UserRepository userRepository;
    private final PasswordEncoder passwordEncoder;
    private final AuthenticationManager authenticationManager;
    private final JwtUtil jwtUtil;
    private final UserMapper userMapper;

    @Value("${jwt.expiration}")
    private long jwtExpiration;

    @Override
    @Transactional
    public RegisterResponse register(RegisterRequest request) {
        if (userRepository.existsByUsername(request.username())) {
            throw new UsernameAlreadyExistsException(
                    "El username '" + request.username() + "' ya está en uso"
            );
        }

        UserEntity user = userMapper.toEntity(request);

        // Asignar el hash de la contraseña después del mapeo
        UserEntity userWithPassword = UserEntity.builder()
                .username(user.getUsername())
                .displayName(user.getDisplayName())
                .password(passwordEncoder.encode(request.password()))
                .role(user.getRole())
                .status(user.getStatus())
                .build();

        UserEntity saved = userRepository.save(userWithPassword);
        return userMapper.toRegisterResponse(saved);
    }

    @Override
    @Transactional(readOnly = true)
    public AuthResponse login(LoginRequest request) {
        try {
            authenticationManager.authenticate(
                    new UsernamePasswordAuthenticationToken(
                            request.username(),
                            request.password()
                    )
            );
        } catch (AuthenticationException ex) {
            throw new InvalidCredentialsException("Credenciales incorrectas");
        }

        UserEntity user = userRepository.findByUsername(request.username())
                .orElseThrow(() -> new UserNotFoundException("Usuario no encontrado"));

        String token = jwtUtil.generateToken(user.getUsername(), user.getId(), user.getRole().name());

        return AuthResponse.builder()
                .token(token)
                .tokenType("Bearer")
                .expiresIn(jwtExpiration / 1000)
                .userId(user.getId())
                .username(user.getUsername())
                .build();
    }
}
