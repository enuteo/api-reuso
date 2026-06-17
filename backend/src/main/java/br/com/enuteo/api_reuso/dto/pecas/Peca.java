package br.com.enuteo.api_reuso.dto.pecas;

import java.math.BigDecimal;

/**
 * Peça do catálogo. Modelo flat alinhado à tabela `peca` (id, nome, preco).
 * A quantidade NÃO vive aqui — pertence à tabela `estoque` (ver EstoqueItem).
 */
public class Peca {

    private Long id;
    private String nome;
    private BigDecimal preco;

    public Long getId() {
        return id;
    }
    public void setId(Long id) {
        this.id = id;
    }
    public String getNome() {
        return nome;
    }
    public void setNome(String nome) {
        this.nome = nome;
    }
    public BigDecimal getPreco() {
        return preco;
    }
    public void setPreco(BigDecimal preco) {
        this.preco = preco;
    }
}
