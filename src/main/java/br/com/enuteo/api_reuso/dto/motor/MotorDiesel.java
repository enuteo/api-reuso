package br.com.enuteo.api_reuso.dto.motor;

import jakarta.annotation.Nonnull;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class MotorDiesel extends AbstractMotor{
    
    @Nonnull
    private String categoria;
    private int comprimento;

}
