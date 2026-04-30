package br.com.enuteo.api_reuso.dto.pecas;

import lombok.Data;


public class Arroela extends AbstractPeca {
    private String tipo;
    private int comprimento;
    public String getTipo() {
        return tipo;
    }
    public void setTipo(String tipo) {
        this.tipo = tipo;
    }
    public int getComprimento() {
        return comprimento;
    }
    public void setComprimento(int comprimento) {
        this.comprimento = comprimento;
    }
}
