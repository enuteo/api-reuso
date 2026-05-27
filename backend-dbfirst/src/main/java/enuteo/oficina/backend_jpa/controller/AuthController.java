package enuteo.oficina.backend_jpa.controller;

import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import enuteo.oficina.backend_jpa.dto.access.LoginRequest;
import enuteo.oficina.backend_jpa.dto.access.LoginResponse;

@RestController
@RequestMapping("/api")
public class AuthController {

    private final enuteo.oficina.backend_jpa.service.AuthService authService;

    public AuthController(enuteo.oficina.backend_jpa.service.AuthService authService) {
        this.authService = authService;
    }

    @PostMapping("/login")
    public ResponseEntity<LoginResponse> login(@RequestBody LoginRequest req) {
        LoginResponse resp = authService.login(req);
        return ResponseEntity.ok(resp);
    }
}
