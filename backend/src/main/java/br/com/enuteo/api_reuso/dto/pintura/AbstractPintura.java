package br.com.enuteo.api_reuso.dto.pintura;

import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public abstract class AbstractPintura {

    private Long id;
    private String nome;
    private int quantidade;
    
}
