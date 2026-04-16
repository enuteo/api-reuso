package br.com.enuteo.api_reuso.dto.pintura;

import jakarta.annotation.Nonnull;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class TintaSemiSintetico extends AbstractPintura{
    
    @Nonnull
    private String categoria;
    private int comprimento;

}
