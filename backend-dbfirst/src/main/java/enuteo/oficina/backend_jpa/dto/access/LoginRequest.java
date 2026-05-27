package enuteo.oficina.backend_jpa.dto.access;

import lombok.Data;

@Data
public class LoginRequest {
    private String nome;
    private String senha;
}
