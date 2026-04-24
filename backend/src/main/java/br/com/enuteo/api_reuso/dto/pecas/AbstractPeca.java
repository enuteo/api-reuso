package br.com.enuteo.api_reuso.dto.pecas;

import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public abstract class AbstractPeca {

    private Long id;
    private String nome;
    private int quantidade;
    
}
