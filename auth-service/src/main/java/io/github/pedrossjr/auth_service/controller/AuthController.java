package io.github.pedrossjr.auth_service.controller;

import io.github.pedrossjr.auth_service.dto.AuthRequest;
import io.github.pedrossjr.auth_service.service.AuthService;
import lombok.RequiredArgsConstructor;
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
    public ResponseEntity<?> register(@RequestBody AuthRequest request) {
        return ResponseEntity.ok(
            authService.register(request.getUsername(), request.getPassword())
        );
    }

    @PostMapping("/login")
    public ResponseEntity<?> login(@RequestBody AuthRequest request) {
        return ResponseEntity.ok(
            authService.login(request.getUsername(), request.getPassword())
        );
    }
}
