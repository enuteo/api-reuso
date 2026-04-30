package br.com.enuteo.api_reuso.dto.pecas;

import lombok.Getter;
import lombok.Setter;

public abstract class AbstractPeca {

    private long id;
    private String nome;
    private int quantidade;
    public long getId() {
        return id;
    }
    public void setId(long id) {
        this.id = id;
    }
    public String getNome() {
        return nome;
    }
    public void setNome(String nome) {
        this.nome = nome;
    }
    public int getQuantidade() {
        return quantidade;
    }
    public void setQuantidade(int quantidade) {
        this.quantidade = quantidade;
    }
    
}
