package br.com.enuteo.api_reuso.dto.motor;

import br.com.enuteo.api_reuso.exception.PecaInvalida;

public interface MotorFactory {

    // factory method
    public static AbstractMotor criaMotor(String nome){

        return switch (nome.toLowerCase()) {
            case "motoreletrico" -> new MotorEletrico();
            case "motordiesel" -> new MotorDiesel();
            case "motorflex" -> new MotorFlex();
            default -> throw new PecaInvalida("Peça inválida: " + nome);
        };
    }
    
}
