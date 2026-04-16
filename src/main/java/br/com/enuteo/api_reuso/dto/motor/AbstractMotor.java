package br.com.enuteo.api_reuso.dto.motor;

import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public abstract class AbstractMotor {

    private Long id;
    private String nome;
    private int quantidade;
    
}
