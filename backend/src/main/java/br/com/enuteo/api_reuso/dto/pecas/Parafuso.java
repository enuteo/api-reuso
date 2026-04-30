package br.com.enuteo.api_reuso.dto.pecas;

import jakarta.annotation.Nonnull;
import lombok.Getter;
import lombok.Setter;

public class Parafuso extends AbstractPeca{
    
    @Nonnull
    private String categoria;
    private int comprimento;
    public String getCategoria() {
        return categoria;
    }
    public void setCategoria(String categoria) {
        this.categoria = categoria;
    }
    public int getComprimento() {
        return comprimento;
    }
    public void setComprimento(int comprimento) {
        this.comprimento = comprimento;
    }

}
