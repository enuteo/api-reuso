package br.com.enuteo.api_reuso.dto.pintura;

import br.com.enuteo.api_reuso.exception.PecaInvalida;

public interface PinturaFactory {

    // factory method
    public static AbstractPintura criaPintura(String nome){

        return switch (nome.toLowerCase()) {
            case "tintapu" -> new TintaPU();
            case "tintasemisintetico" -> new TintaSemiSintetico();
            case "tinner" -> new Tinner();
            default -> throw new PecaInvalida("Peça inválida: " + nome);
        };
    }
    
}
