package br.com.enuteo.api_reuso.controller;

import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;

import br.com.enuteo.api_reuso.dto.login.LoginRequest;
import br.com.enuteo.api_reuso.dto.login.LoginResponse;
import br.com.enuteo.api_reuso.service.LoginService;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;

@Slf4j
@RequestMapping("/auth/login/")
public class LoginController {

    private final LoginService loginService;
    
    public LoginController(LoginService loginService) {
        this.loginService = loginService;
    }

    public ResponseEntity<LoginResponse> login(@RequestBody LoginRequest request){

        log.info("login do usuario: {}", request.getEmail());
        return ResponseEntity.ok(loginService.login(request));
        
    }
}
