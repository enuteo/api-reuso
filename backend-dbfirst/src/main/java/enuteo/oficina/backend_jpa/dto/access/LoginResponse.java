package enuteo.oficina.backend_jpa.dto.access;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.List;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class LoginResponse {
    private String nome;
    private Integer classe;
    private List<String> keywords;
}
