package br.com.enuteo.api_reuso.controller;

import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import br.com.enuteo.api_reuso.dto.login.LoginRequest;
import br.com.enuteo.api_reuso.dto.login.LoginResponse;
import br.com.enuteo.api_reuso.service.LoginService;

@RestController
@RequestMapping("/auth/login")
public class LoginController {

    private final LoginService loginService;
    
    public LoginController(LoginService loginService) {
        this.loginService = loginService;
    }

    @PostMapping("")
    public ResponseEntity<LoginResponse> login(@RequestBody LoginRequest request){
        System.out.println("login");
        System.out.println("");
        return ResponseEntity.ok(loginService.login(request));
        
    }
}
