package br.com.enuteo.api_reuso.service;

import java.util.List;
import java.util.Map;

import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import br.com.enuteo.api_reuso.dto.login.LoginRequest;
import br.com.enuteo.api_reuso.dto.login.LoginResponse;
import br.com.enuteo.api_reuso.exception.InvalidUser;
import br.com.enuteo.api_reuso.repository.LoginRepository;

@Service
public class LoginService {

    private final LoginRepository loginRepository;
    private final PasswordEncoder passwordEncoder;

    public LoginService(LoginRepository loginRepository, PasswordEncoder passwordEncoder) {
        this.loginRepository = loginRepository;
        this.passwordEncoder = passwordEncoder;
    }

    public LoginResponse login(LoginRequest request) {
        Map<String, Object> user = loginRepository.findByEmail(request.getEmail());

        // Mesma mensagem para "não existe" e "senha errada" (não revela qual falhou).
        if (user == null || !passwordEncoder.matches(request.getSenha(), (String) user.get("senha"))) {
            throw new InvalidUser("Usuário ou senha inválidos");
        }

        Long id = ((Number) user.get("id")).longValue();
        String nome = (String) user.get("nome");
        int classe = ((Number) user.get("classe")).intValue();
        List<String> keywords = loginRepository.keywords(id, classe);

        return new LoginResponse(id, nome, classe, keywords);
    }
}
