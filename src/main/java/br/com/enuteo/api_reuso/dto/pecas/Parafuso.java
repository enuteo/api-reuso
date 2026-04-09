package br.com.enuteo.api_reuso.dto.pecas;

import jakarta.annotation.Nonnull;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class Parafuso extends AbstractPeca{
    
    @Nonnull
    private String categoria;
    private int comprimento;

}
