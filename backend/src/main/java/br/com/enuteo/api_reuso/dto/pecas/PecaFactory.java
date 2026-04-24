package br.com.enuteo.api_reuso.dto.pecas;

import br.com.enuteo.api_reuso.exception.PecaInvalida;

public interface PecaFactory {

    // factory method
    public static AbstractPeca criaPeca(String nome){

        return switch (nome.toLowerCase()) {
            case "arroela" -> new Arroela();
            case "parafuso" -> new Parafuso();
            case "porca" -> new Porca();
            default -> throw new PecaInvalida("Peça inválida: " + nome);
        };
    }
    
}
