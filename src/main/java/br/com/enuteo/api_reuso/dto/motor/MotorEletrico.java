package br.com.enuteo.api_reuso.dto.motor;

import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class MotorEletrico extends AbstractMotor {
    private String tipo;
    private int comprimento;
}
