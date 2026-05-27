package enuteo.oficina.backend_jpa.service;

import enuteo.oficina.backend_jpa.dto.access.LoginRequest;
import enuteo.oficina.backend_jpa.dto.access.LoginResponse;

public interface AuthService {
    LoginResponse login(LoginRequest req);
}
