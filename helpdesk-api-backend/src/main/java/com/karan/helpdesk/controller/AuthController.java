package com.karan.helpdesk.controller;

import com.karan.helpdesk.entity.Tenant;
import com.karan.helpdesk.entity.User;
import com.karan.helpdesk.repository.TenantRepository;
import com.karan.helpdesk.repository.UserRepository;
import com.karan.helpdesk.security.JwtUtil;
import jakarta.validation.Valid;
import lombok.Data;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.security.authentication.*;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.web.bind.annotation.*;

import java.util.Optional;
import java.util.UUID;

@RestController
@RequestMapping("/api/auth")
public class AuthController {

    @Autowired private AuthenticationManager authManager;
    @Autowired private UserRepository userRepository;
    @Autowired private TenantRepository tenantRepository;
    @Autowired private PasswordEncoder passwordEncoder;
    @Autowired private JwtUtil jwtUtil;

    @PostMapping("/register")
    public ResponseEntity<?> register(@RequestBody @Valid RegisterRequest request) {
        Optional<Tenant> tenantOpt = tenantRepository.findByName(request.getTenantName());
        if (tenantOpt.isEmpty()) return ResponseEntity.badRequest().body("Tenant not found");

        if (userRepository.findByEmail(request.getEmail()).isPresent()) {
            return ResponseEntity.badRequest().body("Email already in use");
        }

        User user = User.builder()
                .email(request.getEmail())
                .fullName(request.getFullName())
                .password(passwordEncoder.encode(request.getPassword()))
                .role(User.Role.valueOf(request.getRole()))
                .tenant(tenantOpt.get())
                .build();

        userRepository.save(user);

        String token = jwtUtil.generateToken(user.getEmail(), user.getRole().name(), user.getTenant().getId());

        return ResponseEntity.ok(new AuthResponse(token));
    }

    @PostMapping("/login")
    public ResponseEntity<?> login(@RequestBody LoginRequest request) {
        authManager.authenticate(new UsernamePasswordAuthenticationToken(request.getEmail(), request.getPassword()));
        User user = userRepository.findByEmail(request.getEmail()).orElseThrow();
        String token = jwtUtil.generateToken(user.getEmail(), user.getRole().name(), user.getTenant().getId());
        return ResponseEntity.ok(new AuthResponse(token));
    }

    @Data static class RegisterRequest {
        private String email;
        private String password;
        private String fullName;
        private String role;
        private String tenantName;
    }

    @Data static class LoginRequest {
        private String email;
        private String password;
    }

    @Data static class AuthResponse {
        private final String token;
    }
}
