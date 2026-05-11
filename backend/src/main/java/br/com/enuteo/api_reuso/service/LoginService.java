package br.com.enuteo.api_reuso.service;

import org.jspecify.annotations.Nullable;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;

import br.com.enuteo.api_reuso.dto.login.LoginRequest;
import br.com.enuteo.api_reuso.dto.login.LoginResponse;
import br.com.enuteo.api_reuso.exception.InvalidUser;
import br.com.enuteo.api_reuso.repository.LoginRepository;

@Service
public class LoginService {
    
    @Value("${app.url.dashboard}")
    private String url;

    private final LoginRepository loginRepository;

    public LoginService(LoginRepository loginRepository) {
        this.loginRepository = loginRepository;
    }

    public LoginResponse login(LoginRequest request) {
        
        if(loginRepository.userExists(request.getEmail(), request.getPassword())){
            LoginResponse response = new LoginResponse();
            response.setUrl(url);
            return response;
        }else{
            throw new InvalidUser("Usuário ou senha inválidos");
        }
        
    }
    
}
