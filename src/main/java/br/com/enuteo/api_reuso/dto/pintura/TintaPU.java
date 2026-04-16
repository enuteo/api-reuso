package br.com.enuteo.api_reuso.dto.pintura;

import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class TintaPU extends AbstractPintura {
    private String tipo;
    private int comprimento;
}
